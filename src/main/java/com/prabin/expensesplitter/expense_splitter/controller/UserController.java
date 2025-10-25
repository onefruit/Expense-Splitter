package com.prabin.expensesplitter.expense_splitter.controller;

import com.prabin.expensesplitter.expense_splitter.payload.request.LoginRequest;
import com.prabin.expensesplitter.expense_splitter.payload.request.RegisterRequest;
import com.prabin.expensesplitter.expense_splitter.payload.response.UserResponse;
import com.prabin.expensesplitter.expense_splitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerProfile(@RequestBody RegisterRequest request) {
        UserResponse registerProfile = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerProfile);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        try {
            Map<String, Object> response = userService.authenticateAndGenerateToken(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }
}
