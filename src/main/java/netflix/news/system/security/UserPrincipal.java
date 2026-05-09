package netflix.news.system.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Principal for storing authenticated user information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal {
    
    private Integer userId;
    private String username;
    private String role;
    private Integer accountId;
    
    /**
     * Check if user is an employee
     */
    public boolean isEmployee() {
        return "employee".equals(role);
    }
    
    /**
     * Check if user is a customer
     */
    public boolean isCustomer() {
        return "customer".equals(role);
    }
}
