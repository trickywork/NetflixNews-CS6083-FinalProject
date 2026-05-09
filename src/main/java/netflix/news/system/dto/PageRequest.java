package netflix.news.system.dto;

import lombok.Data;

/**
 * Pagination request DTO
 */
@Data
public class PageRequest {
    
    private int page = 1;
    private int size = 10;
    private String sortBy;
    private String sortOrder = "asc";  // 'asc' or 'desc'
    private String keyword;  // Search keyword
    
    // Calculate offset for SQL queries
    public int getOffset() {
        return (page - 1) * size;
    }
}
