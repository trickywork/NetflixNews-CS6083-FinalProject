package netflix.news.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * User entity for authentication and authorization
 * Maps to jqy_user table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private Integer userId;
    private String username;
    private String password;
    private String role;  // 'customer' or 'employee'
    private Integer accountId;  // Link to customer account (NULL for employee)
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
