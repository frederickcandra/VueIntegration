package com.example.vueintegration.controller;

import com.example.vueintegration.dto.request.LoginRequest;
import com.example.vueintegration.dto.request.RegisterRequest;
import com.example.vueintegration.model.User;
import com.example.vueintegration.repository.UserRepository;
import com.example.vueintegration.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;

    public UserController(UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         AuthenticationManager authenticationManager,
                         JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username/password");
        }

        final UserDetails userDetails = userRepository.findByUsername(request.getUsername())
            .map(u -> org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .roles("USER")
                .build())
            .orElseThrow(() -> new RuntimeException("User not found"));

        final String jwt = jwtUtil.generateToken(userDetails);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dogs/random")
    public ResponseEntity<?> getRandomDog() {
        String dogApiUrl = "https://dog.ceo/api/breeds/image/random";
        return ResponseEntity.ok(restTemplate.getForObject(dogApiUrl, Object.class));
    }

    @GetMapping("/users")
    public List<Map<String, String>> getUsers() {
        return userRepository.findAll().stream()
            .map(user -> {
                Map<String, String> userMap = new HashMap<>();
                userMap.put("id", user.getId().toString());
                userMap.put("username", user.getUsername());
                userMap.put("email", user.getEmail());
                return userMap;
            })
            .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(user -> {
                Map<String, String> userMap = new HashMap<>();
                userMap.put("id", user.getId().toString());
                userMap.put("username", user.getUsername());
                userMap.put("email", user.getEmail());
                return ResponseEntity.ok(userMap);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
