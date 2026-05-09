package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.dto.ScheduleDTO;
import netflix.news.system.entity.Schedule;
import netflix.news.system.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Schedule operations
 */
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    
    @Autowired
    private ScheduleService scheduleService;
    
    /**
     * Get all schedules
     */
    @GetMapping
    public ApiResponse<List<Schedule>> getAll() {
        return ApiResponse.success(scheduleService.findAll());
    }
    
    /**
     * Get schedules with pagination
     */
    @GetMapping("/page")
    public ApiResponse<PageResponse<Schedule>> getPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        return ApiResponse.success(scheduleService.findWithPagination(pageRequest));
    }
    
    /**
     * Get schedule by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<Schedule> getById(@PathVariable Integer id) {
        Schedule schedule = scheduleService.findById(id);
        if (schedule == null) {
            return ApiResponse.notFound("Schedule not found");
        }
        return ApiResponse.success(schedule);
    }
    
    /**
     * Get schedules by web series ID
     */
    @GetMapping("/web-series/{webSeriesId}")
    public ApiResponse<List<Schedule>> getByWebSeriesId(@PathVariable Integer webSeriesId) {
        return ApiResponse.success(scheduleService.findByWebSeriesId(webSeriesId));
    }
    
    /**
     * Get schedules between dates
     */
    @GetMapping("/range")
    public ApiResponse<List<Schedule>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ApiResponse.success(scheduleService.findByDateRange(start, end));
    }
    
    /**
     * Get upcoming schedules
     */
    @GetMapping("/upcoming")
    public ApiResponse<List<Schedule>> getUpcoming(@RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.success(scheduleService.findUpcoming(limit));
    }
    
    /**
     * Create new schedule (Employee only)
     */
    @PostMapping
    public ApiResponse<Schedule> create(@Valid @RequestBody ScheduleDTO dto) {
        try {
            Schedule created = scheduleService.create(dto);
            return ApiResponse.success("Schedule created successfully", created);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Update schedule (Employee only)
     */
    @PutMapping("/{id}")
    public ApiResponse<Schedule> update(@PathVariable Integer id, @Valid @RequestBody ScheduleDTO dto) {
        try {
            dto.setScheduleId(id);
            Schedule updated = scheduleService.update(dto);
            return ApiResponse.success("Schedule updated successfully", updated);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Delete schedule (Employee only)
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        try {
            scheduleService.delete(id);
            return ApiResponse.success("Schedule deleted successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
