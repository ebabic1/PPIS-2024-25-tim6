package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.model.Role;
import ba.unsa.etf.ppis.tim6.repository.RoleRepository;
import ba.unsa.etf.ppis.tim6.model.User;
import ba.unsa.etf.ppis.tim6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        Role role = roleRepository.findById(user.getRole().getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}