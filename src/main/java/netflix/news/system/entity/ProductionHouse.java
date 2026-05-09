package netflix.news.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Production House entity
 * Maps to jqy_production_house table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionHouse {
    
    private Integer productionHouseId;
    private String name;
    private String address;
    private Integer yearEstablished;
    
    // Additional fields for related data
    private List<Producer> producers;
}
