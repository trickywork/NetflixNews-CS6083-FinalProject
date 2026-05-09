package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.dto.LoginRequest;
import netflix.news.system.dto.LoginResponse;
import netflix.news.system.dto.RegisterRequest;
import netflix.news.system.entity.User;
import netflix.news.system.security.UserPrincipal;
import netflix.news.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST Controller for authentication operations
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Register a new customer user
     */
    @PostMapping("/register")
    public ApiResponse<User> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            return ApiResponse.success("Registration successful", user);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Login and get JWT token
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return ApiResponse.success("Login successful", response);
        } catch (Exception e) {
            return ApiResponse.unauthorized(e.getMessage());
        }
    }
    
    /**
     * Get current user info
     */
    @GetMapping("/me")
    public ApiResponse<User> getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null) {
            return ApiResponse.unauthorized("Not authenticated");
        }
        User user = userService.findById(principal.getUserId());
        return ApiResponse.success(user);
    }
    
    /**
     * Change password
     */
    @PostMapping("/change-password")
    public ApiResponse<Void> changePassword(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        try {
            userService.changePassword(principal.getUserId(), oldPassword, newPassword);
            return ApiResponse.success("Password changed successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
