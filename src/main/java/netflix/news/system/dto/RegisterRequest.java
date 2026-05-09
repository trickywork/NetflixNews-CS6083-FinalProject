package netflix.news.system.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * Registration request DTO for new users
 */
@Data
public class RegisterRequest {
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;
    
    @NotBlank(message = "Full name is required")
    private String fullName;
    
    @NotBlank(message = "Full address is required")
    private String fullAddress;
    
    @DecimalMin(value = "0.0", message = "Monthly service charge must be non-negative")
    private BigDecimal monthlyServiceCharge = new BigDecimal("9.99");
    
    // Role is always 'customer' for registration, employees are created by admin
}
