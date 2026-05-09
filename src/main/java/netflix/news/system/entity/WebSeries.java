package netflix.news.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

/**
 * Web Series entity
 * Maps to jqy_web_series table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSeries {
    
    private Integer webSeriesId;
    private String name;
    private Integer numberOfEpisodes;
    private String originalLanguage;
    private LocalDate releaseDate;
    private String type;  // Comedy, Drama, Romance, History, Sci-Fi, Animation, Food, Travel, Animal Planet, Action, Thriller, Crime
    private Integer totalViewers;
    private String technicalInterruption;  // 'Yes' or 'No'
    
    // Additional fields for related data (not in main table)
    private List<String> dubbingLanguages;
    private List<String> subtitleLanguages;
    private List<Country> countries;
    private Double averageRating;
}
