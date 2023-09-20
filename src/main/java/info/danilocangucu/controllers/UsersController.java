package info.danilocangucu.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import info.danilocangucu.models.User;
import info.danilocangucu.repositories.UserRepository;
import info.danilocangucu.services.UserService;
import info.danilocangucu.views.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/private/users")
    @JsonView(UserView.class)
    public ResponseEntity<User> findUser(
            @RequestHeader("Authorization") String authHeader) {
        String userId = userService.getUserIdFromHeader(authHeader);
        Optional<User> user = userRepository.findById(userId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .build());

    }

    @PutMapping("/private/users")
    private ResponseEntity<Void> putProduct(
            @RequestBody User userUpdate,
            @RequestHeader("Authorization") String authHeader) {
        String userId = userService.getUserIdFromHeader(authHeader);
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        User updatedUser = new User (
                userId,
          userUpdate.getName(),
          userUpdate.getEmail(),
          passwordEncoder.encode(userUpdate.getPassword()),
                "ROLE_USER"
        );
        userRepository.save(updatedUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/private/users")
    private ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String authHeader) {
        String userId = userService.getUserIdFromHeader(authHeader);
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/private/users/{id}")
    @JsonView(UserView.class)
    public ResponseEntity<?> deleteUser(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id) {
        String userId = userService.getUserIdFromHeader(authHeader);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User admin = user.get();
        if (!admin.getRole().equals("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
