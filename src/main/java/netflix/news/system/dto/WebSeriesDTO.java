package netflix.news.system.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

/**
 * Web Series DTO for create/update operations
 */
@Data
public class WebSeriesDTO {
    
    private Integer webSeriesId;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Number of episodes is required")
    @Min(value = 1, message = "Number of episodes must be at least 1")
    private Integer numberOfEpisodes;
    
    @NotBlank(message = "Original language is required")
    private String originalLanguage;
    
    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;
    
    @NotBlank(message = "Type is required")
    private String type;
    
    private Integer totalViewers = 0;
    
    private String technicalInterruption = "No";
    
    // Related data
    private List<Integer> countryIds;
    private List<String> dubbingLanguages;
    private List<String> subtitleLanguages;
}
