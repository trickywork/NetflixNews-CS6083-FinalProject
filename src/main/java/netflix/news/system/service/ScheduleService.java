package netflix.news.system.service;

import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.dto.ScheduleDTO;
import netflix.news.system.entity.Schedule;
import netflix.news.system.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for schedule management
 */
@Service
public class ScheduleService {
    
    @Autowired
    private ScheduleMapper scheduleMapper;
    
    /**
     * Find schedule by ID
     */
    public Schedule findById(Integer id) {
        return scheduleMapper.findById(id);
    }
    
    /**
     * Get all schedules
     */
    public List<Schedule> findAll() {
        return scheduleMapper.findAll();
    }
    
    /**
     * Find schedules by web series ID
     */
    public List<Schedule> findByWebSeriesId(Integer webSeriesId) {
        return scheduleMapper.findByWebSeriesId(webSeriesId);
    }
    
    /**
     * Find schedules between dates
     */
    public List<Schedule> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.findByDateRange(startDate, endDate);
    }
    
    /**
     * Find upcoming schedules
     */
    public List<Schedule> findUpcoming(int limit) {
        return scheduleMapper.findUpcoming(limit);
    }
    
    /**
     * Get schedules with pagination
     */
    public PageResponse<Schedule> findWithPagination(PageRequest pageRequest) {
        List<Schedule> list = scheduleMapper.findWithPagination(
                pageRequest.getOffset(), pageRequest.getSize());
        long total = scheduleMapper.count();
        return PageResponse.of(list, pageRequest.getPage(), pageRequest.getSize(), total);
    }
    
    /**
     * Create new schedule
     */
    @Transactional
    public Schedule create(ScheduleDTO dto) {
        // Validate end time is after start time
        if (!dto.getEndDatetime().isAfter(dto.getStartDatetime())) {
            throw new RuntimeException("End datetime must be after start datetime");
        }
        
        Schedule schedule = new Schedule();
        schedule.setWebSeriesId(dto.getWebSeriesId());
        schedule.setStartDatetime(dto.getStartDatetime());
        schedule.setEndDatetime(dto.getEndDatetime());
        
        scheduleMapper.insert(schedule);
        return scheduleMapper.findById(schedule.getScheduleId());
    }
    
    /**
     * Update schedule
     */
    @Transactional
    public Schedule update(ScheduleDTO dto) {
        Schedule existing = scheduleMapper.findById(dto.getScheduleId());
        if (existing == null) {
            throw new RuntimeException("Schedule not found");
        }
        
        // Validate end time is after start time
        if (!dto.getEndDatetime().isAfter(dto.getStartDatetime())) {
            throw new RuntimeException("End datetime must be after start datetime");
        }
        
        existing.setWebSeriesId(dto.getWebSeriesId());
        existing.setStartDatetime(dto.getStartDatetime());
        existing.setEndDatetime(dto.getEndDatetime());
        
        scheduleMapper.update(existing);
        return scheduleMapper.findById(dto.getScheduleId());
    }
    
    /**
     * Delete schedule
     */
    @Transactional
    public void delete(Integer id) {
        scheduleMapper.deleteById(id);
    }
    
    /**
     * Get total count
     */
    public long count() {
        return scheduleMapper.count();
    }
}
