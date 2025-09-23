package com.example.vueintegration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final List<Map<String, String>> users = List.of(
            Map.of("id", "1", "name", "Sara", "email", "sara@example.com"),
            Map.of("id", "2", "name", "Budi", "email", "budi@example.com")
    );

    // ✅ Ambil semua user
    @GetMapping
    public List<Map<String, String>> getUsers() {
        return users;
    }

    // ✅ Ambil detail user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getUserById(@PathVariable String id) {
        return users.stream()
                .filter(user -> user.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
