package netflix.news.system.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Contract DTO for create/update operations
 */
@Data
public class ContractDTO {
    
    private Integer contractId;
    
    @NotNull(message = "Web series ID is required")
    private Integer webSeriesId;
    
    @NotNull(message = "Production house ID is required")
    private Integer productionHouseId;
    
    @NotNull(message = "Sign date is required")
    private LocalDate signDate;
    
    // End date will be calculated as sign_date + 1 year
    
    @NotNull(message = "Per episode fee is required")
    @DecimalMin(value = "0.0", message = "Per episode fee must be non-negative")
    private BigDecimal perEpisodeFee;
}
