package netflix.news.system.service;

import netflix.news.system.dto.FeedbackDTO;
import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.Feedback;
import netflix.news.system.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Service for feedback management
 */
@Service
public class FeedbackService {
    
    @Autowired
    private FeedbackMapper feedbackMapper;
    
    /**
     * Find feedback by ID
     */
    public Feedback findById(Integer id) {
        return feedbackMapper.findById(id);
    }
    
    /**
     * Get all feedback
     */
    public List<Feedback> findAll() {
        return feedbackMapper.findAll();
    }
    
    /**
     * Find feedback by account ID
     */
    public List<Feedback> findByAccountId(Integer accountId) {
        return feedbackMapper.findByAccountId(accountId);
    }
    
    /**
     * Find feedback by web series ID
     */
    public List<Feedback> findByWebSeriesId(Integer webSeriesId) {
        return feedbackMapper.findByWebSeriesId(webSeriesId);
    }
    
    /**
     * Get feedback with pagination
     */
    public PageResponse<Feedback> findWithPagination(PageRequest pageRequest) {
        List<Feedback> list = feedbackMapper.findWithPagination(
                pageRequest.getOffset(), pageRequest.getSize());
        long total = feedbackMapper.count();
        return PageResponse.of(list, pageRequest.getPage(), pageRequest.getSize(), total);
    }
    
    /**
     * Create or update feedback (one per account per web series)
     */
    @Transactional
    public Feedback createOrUpdate(Integer accountId, FeedbackDTO dto) {
        // Check if feedback already exists
        Feedback existing = feedbackMapper.findByAccountAndWebSeries(accountId, dto.getWebSeriesId());
        
        if (existing != null) {
            // Update existing feedback
            existing.setFeedbackText(dto.getFeedbackText());
            existing.setRating(dto.getRating());
            feedbackMapper.update(existing);
            return feedbackMapper.findById(existing.getFeedbackId());
        } else {
            // Create new feedback
            Feedback feedback = new Feedback();
            feedback.setAccountId(accountId);
            feedback.setWebSeriesId(dto.getWebSeriesId());
            feedback.setFeedbackText(dto.getFeedbackText());
            feedback.setRating(dto.getRating());
            feedback.setFeedbackDate(LocalDate.now());
            
            feedbackMapper.insert(feedback);
            return feedbackMapper.findById(feedback.getFeedbackId());
        }
    }
    
    /**
     * Delete feedback
     */
    @Transactional
    public void delete(Integer id) {
        feedbackMapper.deleteById(id);
    }
    
    /**
     * Check if user can delete feedback (owner or employee)
     */
    public boolean canDelete(Integer feedbackId, Integer accountId, String role) {
        if ("employee".equals(role)) {
            return true;
        }
        Feedback feedback = feedbackMapper.findById(feedbackId);
        return feedback != null && feedback.getAccountId().equals(accountId);
    }
    
    /**
     * Get average rating for web series
     */
    public Double getAverageRating(Integer webSeriesId) {
        return feedbackMapper.getAverageRatingByWebSeries(webSeriesId);
    }
    
    /**
     * Get rating distribution
     */
    public List<Map<String, Object>> getRatingDistribution() {
        return feedbackMapper.getRatingDistribution();
    }
    
    /**
     * Get recent feedback
     */
    public List<Feedback> findRecent(int limit) {
        return feedbackMapper.findRecent(limit);
    }
    
    /**
     * Get total count
     */
    public long count() {
        return feedbackMapper.count();
    }
}
