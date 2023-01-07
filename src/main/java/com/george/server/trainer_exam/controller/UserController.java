package com.george.server.trainer_exam.controller;

import com.george.server.trainer_exam.auth.request.SignupRequest;
import com.george.server.trainer_exam.auth.response.MessageResponse;
import com.george.server.trainer_exam.model.user.Role;
import com.george.server.trainer_exam.model.user.ERole;
import com.george.server.trainer_exam.model.user.User;
import com.george.server.trainer_exam.repository.user.RoleRepository;
import com.george.server.trainer_exam.repository.user.UserRepository;
import com.george.server.trainer_exam.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/palladium/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ROLE_DEVELOPER') or hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/get/all/roles")
    @PreAuthorize("hasRole('ROLE_DEVELOPER') or hasRole('ROLE_ADMIN')")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/get/all/users")
    @PreAuthorize("hasRole('ROLE_DEVELOPER') or hasRole('ROLE_ADMIN')")
    public List<User> getUsersByRoleName(@RequestParam("name") ERole name) {
        return userRepository.findByRoles_Name(name);
    }

    @GetMapping("/get/user")
    @PreAuthorize("hasRole('ROLE_DEVELOPER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_EXECUTOR')")
    public User getUserById(@RequestParam(value = "id") long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ROLE_DEVELOPER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> editUser(@RequestBody SignupRequest user,
                                      @RequestParam(value = "id") long id) {

        User updateUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found"));

        updateUser.setInfoAboutUser(user.getInfoAboutUser());
        updateUser.setPassword(encoder.encode(user.getPassword()));
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        updateUser.setFullName(user.getFullName());

        Set<String> strRoles = user.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_DEVELOPER":
                        Role developerRole = roleRepository.findByName(ERole.ROLE_DEVELOPER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(developerRole);

                        break;

                    case "ROLE_TEACHER":
                        Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(teacherRole);

                        break;

                    case "ROLE_STUDENT":
                        Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(studentRole);

                        break;


                    default:
                        Role defaultRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(defaultRole);
                }
            });
        }

        updateUser.setRoles(roles);
        userRepository.save(updateUser);

        return ResponseEntity.ok(new MessageResponse("User successfully edited"));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_DEVELOPER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "id") long id) {
        userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found")
        );

        userRepository.deleteById(id);

        return ResponseEntity.ok(new MessageResponse("User successfully deleted"));
    }

}
