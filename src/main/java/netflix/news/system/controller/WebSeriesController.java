package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.dto.WebSeriesDTO;
import netflix.news.system.entity.WebSeries;
import netflix.news.system.service.WebSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Web Series operations
 */
@RestController
@RequestMapping("/api/web-series")
public class WebSeriesController {
    
    @Autowired
    private WebSeriesService webSeriesService;
    
    /**
     * Get all web series
     */
    @GetMapping
    public ApiResponse<List<WebSeries>> getAll() {
        return ApiResponse.success(webSeriesService.findAll());
    }
    
    /**
     * Get web series with pagination
     */
    @GetMapping("/page")
    public ApiResponse<PageResponse<WebSeries>> getPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        return ApiResponse.success(webSeriesService.findWithPagination(pageRequest));
    }
    
    /**
     * Get web series by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<WebSeries> getById(@PathVariable Integer id) {
        WebSeries webSeries = webSeriesService.findById(id);
        if (webSeries == null) {
            return ApiResponse.notFound("Web series not found");
        }
        return ApiResponse.success(webSeries);
    }
    
    /**
     * Search web series by name
     */
    @GetMapping("/search")
    public ApiResponse<List<WebSeries>> search(@RequestParam String keyword) {
        return ApiResponse.success(webSeriesService.searchByName(keyword));
    }
    
    /**
     * Get web series by type
     */
    @GetMapping("/type/{type}")
    public ApiResponse<List<WebSeries>> getByType(@PathVariable String type) {
        return ApiResponse.success(webSeriesService.findByType(type));
    }
    
    /**
     * Get all types
     */
    @GetMapping("/types")
    public ApiResponse<List<String>> getTypes() {
        return ApiResponse.success(webSeriesService.getAllTypes());
    }
    
    /**
     * Get top rated web series
     */
    @GetMapping("/top-rated")
    public ApiResponse<List<WebSeries>> getTopRated(@RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.success(webSeriesService.getTopRated(limit));
    }
    
    /**
     * Get viewers statistics by type
     */
    @GetMapping("/stats/viewers-by-type")
    public ApiResponse<List<Map<String, Object>>> getViewersByType() {
        return ApiResponse.success(webSeriesService.getViewersByType());
    }
    
    /**
     * Create new web series (Employee only)
     */
    @PostMapping
    public ApiResponse<WebSeries> create(@Valid @RequestBody WebSeriesDTO dto) {
        try {
            WebSeries created = webSeriesService.create(dto);
            return ApiResponse.success("Web series created successfully", created);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Update web series (Employee only)
     */
    @PutMapping("/{id}")
    public ApiResponse<WebSeries> update(@PathVariable Integer id, @Valid @RequestBody WebSeriesDTO dto) {
        try {
            dto.setWebSeriesId(id);
            WebSeries updated = webSeriesService.update(dto);
            return ApiResponse.success("Web series updated successfully", updated);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Delete web series (Employee only)
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        try {
            webSeriesService.delete(id);
            return ApiResponse.success("Web series deleted successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
