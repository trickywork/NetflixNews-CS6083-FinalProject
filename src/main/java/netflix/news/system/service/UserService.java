package netflix.news.system.service;

import netflix.news.system.dto.LoginRequest;
import netflix.news.system.dto.LoginResponse;
import netflix.news.system.dto.RegisterRequest;
import netflix.news.system.entity.Account;
import netflix.news.system.entity.User;
import netflix.news.system.mapper.AccountMapper;
import netflix.news.system.mapper.UserMapper;
import netflix.news.system.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for user authentication and management
 */
@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private AccountMapper accountMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    /**
     * Register a new customer user with account
     */
    @Transactional
    public User register(RegisterRequest request) {
        // Check if username already exists
        if (userMapper.countByUsername(request.getUsername()) > 0) {
            throw new RuntimeException("Username already exists");
        }
        
        // Create account first
        Account account = new Account();
        account.setFullName(request.getFullName());
        account.setFullAddress(request.getFullAddress());
        account.setOpenDate(LocalDate.now());
        account.setMonthlyServiceCharge(request.getMonthlyServiceCharge());
        accountMapper.insert(account);
        
        // Create user with hashed password
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("customer");
        user.setAccountId(account.getAccountId());
        user.setCreatedAt(LocalDateTime.now());
        userMapper.insert(user);
        
        // Clear password before returning
        user.setPassword(null);
        return user;
    }
    
    /**
     * Authenticate user and return JWT token
     */
    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("Invalid username or password");
        }
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        // Update last login time
        userMapper.updateLastLogin(user.getUserId(), LocalDateTime.now());
        
        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user);
        
        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .userId(user.getUserId())
                .accountId(user.getAccountId())
                .build();
    }
    
    /**
     * Find user by username
     */
    public User findByUsername(String username) {
        User user = userMapper.findByUsername(username);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }
    
    /**
     * Find user by ID
     */
    public User findById(Integer userId) {
        User user = userMapper.findById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }
    
    /**
     * Get all users (admin only)
     */
    public List<User> findAll() {
        List<User> users = userMapper.findAll();
        users.forEach(u -> u.setPassword(null));
        return users;
    }
    
    /**
     * Get users by role
     */
    public List<User> findByRole(String role) {
        List<User> users = userMapper.findByRole(role);
        users.forEach(u -> u.setPassword(null));
        return users;
    }
    
    /**
     * Change user password
     */
    @Transactional
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        
        userMapper.updatePassword(userId, passwordEncoder.encode(newPassword));
    }
    
    /**
     * Create employee user (admin only)
     */
    @Transactional
    public User createEmployee(String username, String password) {
        if (userMapper.countByUsername(username) > 0) {
            throw new RuntimeException("Username already exists");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("employee");
        user.setAccountId(null);
        user.setCreatedAt(LocalDateTime.now());
        userMapper.insert(user);
        
        user.setPassword(null);
        return user;
    }
    
    /**
     * Delete user
     */
    @Transactional
    public void deleteUser(Integer userId) {
        userMapper.deleteById(userId);
    }
}
