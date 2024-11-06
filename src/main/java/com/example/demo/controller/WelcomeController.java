package com.example.demo.controller;

import com.example.demo.Exceptions.ProjectNotFoundException;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Service.ProjectService;
import com.example.demo.Service.UserService;
import com.example.demo.entity.LogInLogOutTime;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome";
    }

    @PostMapping("/userDetails")
    public String showUserProjects(@RequestParam String uid, Model model) throws UserNotFoundException {
        try {
            User user = userService.findUserByUid(uid)
                    .orElseThrow(()-> new UserNotFoundException("User not found with uid " + uid));
            List<Project> projects = projectService.getUserProjects(uid);
            List<LogInLogOutTime> logs = user.getLogInLogOutTimeList();
            model.addAttribute("user", user);
            model.addAttribute("projects", projects);
            model.addAttribute("logs", logs);
            return "userDetails";
        }
        catch (UserNotFoundException u){
            model.addAttribute("error", "User not found!");
            return "welcome";
        }
    }

    @GetMapping("/allProjects")
    public String getAllProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "/allProjects";
    }

    @GetMapping("/projects/add")
    public String showAddProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "/addProject";
    }

    @PostMapping("/projects/save")
    public String saveProject(@ModelAttribute Project project) {
        projectService.saveProject(project);
        return "redirect:/allProjects";
    }

    @PostMapping("/projects/assignToUser")
    public String assignProjectToUser(@RequestParam String userUid, @RequestParam Integer projectId, Model model) {
        try {
            User user = userService.findUserByUid(userUid)
                    .orElseThrow(() -> new UserNotFoundException("User not found with UID: " + userUid));
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + projectId));
            if (!user.getProjects().contains(project)) {
                user.getProjects().add(project);
            }
            if (!project.getUsers().contains(user)) {
                project.getUsers().add(user);
            }
            userService.saveUser(user);
            projectRepository.save(project);
            model.addAttribute("statusMessage", "Project '" + project.getName() + "' was successfully assigned to user " + user.getName() + " " + user.getSurname()+".");
            model.addAttribute("statusType", "success");
        } catch (UserNotFoundException | ProjectNotFoundException e ) {
            model.addAttribute("statusMessage", e.getMessage());
            model.addAttribute("statusType", "danger");
        }
        return "welcome";
    }
}
