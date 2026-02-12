package com.example.taskmanagerspringboot.controller;

import com.example.taskmanagerspringboot.entity.Task;
import com.example.taskmanagerspringboot.entity.User;
import com.example.taskmanagerspringboot.repository.UserRepository;
import com.example.taskmanagerspringboot.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listTasks(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        List<Task> tasks = taskService.findAllByUser(user);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/add")
    public String showAddTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", Task.Priority.values());
        return "add-task";
    }

    @PostMapping("/add")
    public String addTask(@Valid @ModelAttribute Task task, BindingResult result, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            model.addAttribute("priorities", Task.Priority.values());
            return "add-task";
        }
        if (!taskService.isValidDate(task.getDueDate())) {
            result.rejectValue("dueDate", "error.task", "Invalid date format. Please enter the date in yyyy-MM-dd format.");
            model.addAttribute("priorities", Task.Priority.values());
            return "add-task";
        }
        // Set user
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        task.setUser(user);
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Task task = taskService.findById(id).orElse(null);
        if (task == null || !task.getUser().getUsername().equals(userDetails.getUsername())) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", task);
        model.addAttribute("priorities", Task.Priority.values());
        return "edit-task";
    }

    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, @Valid @ModelAttribute Task task, BindingResult result, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            model.addAttribute("priorities", Task.Priority.values());
            return "edit-task";
        }
        if (!taskService.isValidDate(task.getDueDate())) {
            result.rejectValue("dueDate", "error.task", "Invalid date format. Please enter the date in yyyy-MM-dd format.");
            model.addAttribute("priorities", Task.Priority.values());
            return "edit-task";
        }
        Task existingTask = taskService.findById(id).orElse(null);
        if (existingTask == null || !existingTask.getUser().getUsername().equals(userDetails.getUsername())) {
            return "redirect:/tasks";
        }
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());
        taskService.save(existingTask);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Task task = taskService.findById(id).orElse(null);
        if (task != null && task.getUser().getUsername().equals(userDetails.getUsername())) {
            taskService.deleteById(id);
        }
        return "redirect:/tasks";
    }


}
