package com.example.demo.Service;

import com.example.demo.Exceptions.ProjectNotFoundException;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveProject(Project project){
        projectRepository.save(project);
    }
    public Project assignUserToProject(Integer projectId, String uid) throws ProjectNotFoundException, UserNotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found " + projectId));
            User user = userRepository.findByNfcTagUid(uid)
                    .orElseThrow(() -> new UserNotFoundException("User not found with uid " + uid));
            project.getUsers().add(user);
        return projectRepository.save(project);
    }

    public List<Project> getUserProjects(String uid) throws UserNotFoundException {
        User user = userRepository.findByNfcTagUid(uid)
                .orElseThrow(() -> new UserNotFoundException("User not found with uid " + uid));
        return user.getProjects();
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

}
