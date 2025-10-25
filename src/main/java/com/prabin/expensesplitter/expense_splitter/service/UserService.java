package com.prabin.expensesplitter.expense_splitter.service;

import com.prabin.expensesplitter.expense_splitter.jwt.JwtHelper;
import com.prabin.expensesplitter.expense_splitter.model.User;
import com.prabin.expensesplitter.expense_splitter.payload.request.LoginRequest;
import com.prabin.expensesplitter.expense_splitter.payload.request.RegisterRequest;
import com.prabin.expensesplitter.expense_splitter.payload.response.UserResponse;
import com.prabin.expensesplitter.expense_splitter.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;
    private final JwtHelper jwtHelper;


    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User user1 = userRepository.save(user);

        return UserResponse.builder().name(user1.getName())
                .email(user1.getEmail())
                .build();
    }

    public User getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Profile not found with email: " + authentication.getName()));
    }

    public User getPublicProfile(String email) {
        User currentUser = null;
        if (email == null) {
            currentUser = getCurrentProfile();
        } else {
            currentUser = userRepository.findByEmail(email).orElseThrow(() ->
                    new UsernameNotFoundException("Profile not found with email: " + email));
        }

        return currentUser;
    }

    public Map<String, Object> authenticateAndGenerateToken(LoginRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                    authRequest.getPassword()));
            UserDetails userDetails = appUserDetailsService.loadUserByUsername(authRequest.getEmail());
            String token = jwtHelper.generateToken(userDetails);
            log.error(token);
            return Map.of(
                    "token", token,
                    "user", getPublicProfile(authRequest.getEmail())
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Invalid email or password");
        }
    }
}

