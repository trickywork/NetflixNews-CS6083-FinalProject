package netflix.news.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Producer entity
 * Maps to jqy_producer table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producer {
    
    private Integer producerId;
    private String name;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    
    // Additional fields for related data
    private List<ProductionHouse> productionHouses;
}
