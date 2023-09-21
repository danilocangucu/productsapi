package info.danilocangucu.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import info.danilocangucu.models.User;
import info.danilocangucu.repositories.ProductRepository;
import info.danilocangucu.repositories.UserRepository;
import info.danilocangucu.services.UserService;
import info.danilocangucu.views.AdminView;
import info.danilocangucu.views.UserView;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;


    @GetMapping("/private/users")
    @PermitAll
    @JsonView(UserView.class)
    public ResponseEntity<?> findUser(
            @RequestHeader("Authorization") String authHeader) {
        String userId = userService.getUserIdFromHeader(authHeader);
        Optional<User> user = userRepository.findById(userId);

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .build());
    }

    @GetMapping("/private/admin/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @JsonView(AdminView.class)
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
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
    @Secured("ROLE_USER")
    private ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String authHeader) {
        String userId = userService.getUserIdFromHeader(authHeader);
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/private/admin/users/{id}")
    @JsonView(UserView.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (userRepository.existsById(id)) {
            productRepository.deleteAllByUserId(id);
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
