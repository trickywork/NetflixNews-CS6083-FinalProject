package netflix.news.system.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Schedule DTO for create/update operations
 */
@Data
public class ScheduleDTO {
    
    private Integer scheduleId;
    
    @NotNull(message = "Web series ID is required")
    private Integer webSeriesId;
    
    @NotNull(message = "Start datetime is required")
    private LocalDateTime startDatetime;
    
    @NotNull(message = "End datetime is required")
    private LocalDateTime endDatetime;
}
