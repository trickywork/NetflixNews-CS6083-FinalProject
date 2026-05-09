package netflix.news.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Contract entity
 * Maps to jqy_contract table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    
    private Integer contractId;
    private Integer webSeriesId;
    private Integer productionHouseId;
    private LocalDate signDate;
    private LocalDate endDate;
    private BigDecimal perEpisodeFee;
    
    // Additional fields for display
    private String webSeriesName;
    private String productionHouseName;
}
