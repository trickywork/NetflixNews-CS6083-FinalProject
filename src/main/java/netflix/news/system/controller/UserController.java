package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.dto.CreateEmployeeRequest;
import netflix.news.system.entity.User;
import netflix.news.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for User management (Employee only)
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Get all users
     */
    @GetMapping
    public ApiResponse<List<User>> getAll() {
        return ApiResponse.success(userService.findAll());
    }
    
    /**
     * Get users by role
     */
    @GetMapping("/role/{role}")
    public ApiResponse<List<User>> getByRole(@PathVariable String role) {
        return ApiResponse.success(userService.findByRole(role));
    }
    
    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<User> getById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return ApiResponse.notFound("User not found");
        }
        return ApiResponse.success(user);
    }
    
    /**
     * Create new employee user
     */
    @PostMapping("/employee")
    public ApiResponse<User> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        try {
            User user = userService.createEmployee(request.getUsername(), request.getPassword());
            return ApiResponse.success("Employee created successfully", user);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Delete user
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ApiResponse.success("User deleted successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
