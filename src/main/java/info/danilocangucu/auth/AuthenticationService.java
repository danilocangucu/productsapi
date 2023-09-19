package info.danilocangucu.auth;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import info.danilocangucu.configs.JwtService;
import info.danilocangucu.models.User;
import info.danilocangucu.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Validator;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private Validator validator;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(User request) {
        Set<ConstraintViolation<User>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            buildStringAndThrowException(violations);
        } else if (emailExists(request.getEmail()).isPresent()) {
            //
            buildStringAndThrowException(new HashSet<>());
        }

        var user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role("ROLE_USER")
            .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Set<ConstraintViolation<AuthenticationRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            buildStringAndThrowException(violations);
        }

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    private static <T> void buildStringAndThrowException(Set<ConstraintViolation<T>> violations) {
        System.out.println("buildString");
        StringBuilder sb = new StringBuilder();
        if (violations.isEmpty()) {
        }
        for (ConstraintViolation<T> violation : violations) {
            sb.append(violation.getMessage()).append(". ");
        }
        throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
    }

    private Optional<User> emailExists(String email) {
        return userRepository.findByEmail(email);
    }

}
