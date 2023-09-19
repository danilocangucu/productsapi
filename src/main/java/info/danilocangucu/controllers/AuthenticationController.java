package info.danilocangucu.controllers;

import info.danilocangucu.exception.ValidationException;
import info.danilocangucu.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.danilocangucu.auth.AuthenticationRequest;
import info.danilocangucu.auth.AuthenticationResponse;
import info.danilocangucu.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @Valid @RequestBody User request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            System.out.println("has errors");
            createAndSendErrorResponse(bindingResult);
        }
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
        @Valid @RequestBody AuthenticationRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) { createAndSendErrorResponse(bindingResult); }
        return ResponseEntity.ok(service.authenticate(request));
    }

    private static void createAndSendErrorResponse(BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        throw new ValidationException(errors);
    }
}
