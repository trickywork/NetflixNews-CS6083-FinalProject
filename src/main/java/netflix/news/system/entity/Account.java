package netflix.news.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Account entity for customer accounts
 * Maps to jqy_account table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    
    private Integer accountId;
    private String fullName;
    private String fullAddress;
    private LocalDate openDate;
    private BigDecimal monthlyServiceCharge;
}
