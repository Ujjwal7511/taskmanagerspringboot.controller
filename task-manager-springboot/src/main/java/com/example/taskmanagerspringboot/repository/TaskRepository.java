package com.example.taskmanagerspringboot.repository;

import com.example.taskmanagerspringboot.entity.Task;
import com.example.taskmanagerspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserOrderByPriorityAsc(User user);
}
