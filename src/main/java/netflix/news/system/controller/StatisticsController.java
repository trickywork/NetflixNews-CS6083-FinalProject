package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Statistics and Dashboard data
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    
    @Autowired
    private StatisticsService statisticsService;
    
    /**
     * Get dashboard overview statistics
     */
    @GetMapping("/dashboard")
    public ApiResponse<Map<String, Object>> getDashboardStats() {
        return ApiResponse.success(statisticsService.getDashboardStats());
    }
    
    /**
     * Get viewers by type for charts
     */
    @GetMapping("/viewers-by-type")
    public ApiResponse<List<Map<String, Object>>> getViewersByType() {
        return ApiResponse.success(statisticsService.getViewersByType());
    }
    
    /**
     * Get rating distribution for charts
     */
    @GetMapping("/rating-distribution")
    public ApiResponse<List<Map<String, Object>>> getRatingDistribution() {
        return ApiResponse.success(statisticsService.getRatingDistribution());
    }
    
    /**
     * Get web series types
     */
    @GetMapping("/web-series-types")
    public ApiResponse<List<String>> getWebSeriesTypes() {
        return ApiResponse.success(statisticsService.getWebSeriesTypes());
    }
}
