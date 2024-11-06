package com.example.demo.controller;

import com.example.demo.Exceptions.ProjectNotFoundException;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Service.ProjectService;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/user")
    public List<Project> getAllUserProjects(@RequestParam String uid) throws UserNotFoundException {
        return projectService.getUserProjects(uid);
    }

    @PostMapping("/addUser")
    public ResponseEntity<Project> assignUserToProject(
            @RequestParam Integer projectId,
            @RequestParam String uid) {
        try {
            Project updatedProject = projectService.assignUserToProject(projectId, uid);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (UserNotFoundException | ProjectNotFoundException u) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
