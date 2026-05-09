package netflix.news.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

/**
 * Feedback entity
 * Maps to jqy_feedback table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    
    private Integer feedbackId;
    private Integer accountId;
    private Integer webSeriesId;
    private String feedbackText;
    private Integer rating;  // 1-5
    private LocalDate feedbackDate;
    
    // Additional fields for display
    private String accountName;
    private String webSeriesName;
}
