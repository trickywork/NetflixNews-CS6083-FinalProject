package netflix.news.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Country entity
 * Maps to jqy_country table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    
    private Integer countryId;
    private String countryName;
}
