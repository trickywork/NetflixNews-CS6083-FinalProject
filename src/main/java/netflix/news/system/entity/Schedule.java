package netflix.news.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Schedule entity
 * Maps to jqy_schedule table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    
    private Integer scheduleId;
    private Integer webSeriesId;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    
    // Additional fields for display
    private String webSeriesName;
}
