package netflix.news.system.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

/**
 * Feedback DTO for create/update operations
 */
@Data
public class FeedbackDTO {
    
    private Integer feedbackId;
    
    @NotNull(message = "Web series ID is required")
    private Integer webSeriesId;
    
    private String feedbackText;
    
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
}
