package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.dto.FeedbackDTO;
import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.Feedback;
import netflix.news.system.security.UserPrincipal;
import netflix.news.system.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Feedback operations
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;
    
    /**
     * Get all feedback (Employee only)
     */
    @GetMapping
    public ApiResponse<List<Feedback>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal.isEmployee()) {
            return ApiResponse.success(feedbackService.findAll());
        } else {
            // Customers can only see their own feedback
            return ApiResponse.success(feedbackService.findByAccountId(principal.getAccountId()));
        }
    }
    
    /**
     * Get feedback with pagination
     */
    @GetMapping("/page")
    public ApiResponse<PageResponse<Feedback>> getPage(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        return ApiResponse.success(feedbackService.findWithPagination(pageRequest));
    }
    
    /**
     * Get feedback by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<Feedback> getById(@PathVariable Integer id) {
        Feedback feedback = feedbackService.findById(id);
        if (feedback == null) {
            return ApiResponse.notFound("Feedback not found");
        }
        return ApiResponse.success(feedback);
    }
    
    /**
     * Get my feedback (for customers)
     */
    @GetMapping("/my")
    public ApiResponse<List<Feedback>> getMyFeedback(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal.getAccountId() == null) {
            return ApiResponse.badRequest("No account associated with this user");
        }
        return ApiResponse.success(feedbackService.findByAccountId(principal.getAccountId()));
    }
    
    /**
     * Get feedback by web series ID
     */
    @GetMapping("/web-series/{webSeriesId}")
    public ApiResponse<List<Feedback>> getByWebSeriesId(@PathVariable Integer webSeriesId) {
        return ApiResponse.success(feedbackService.findByWebSeriesId(webSeriesId));
    }
    
    /**
     * Get recent feedback
     */
    @GetMapping("/recent")
    public ApiResponse<List<Feedback>> getRecent(@RequestParam(defaultValue = "5") int limit) {
        return ApiResponse.success(feedbackService.findRecent(limit));
    }
    
    /**
     * Get rating distribution
     */
    @GetMapping("/stats/rating-distribution")
    public ApiResponse<List<Map<String, Object>>> getRatingDistribution() {
        return ApiResponse.success(feedbackService.getRatingDistribution());
    }
    
    /**
     * Create or update feedback (Customers only)
     */
    @PostMapping
    public ApiResponse<Feedback> create(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody FeedbackDTO dto) {
        if (principal.getAccountId() == null) {
            return ApiResponse.badRequest("No account associated with this user");
        }
        try {
            Feedback feedback = feedbackService.createOrUpdate(principal.getAccountId(), dto);
            return ApiResponse.success("Feedback submitted successfully", feedback);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Delete feedback
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Integer id) {
        if (!feedbackService.canDelete(id, principal.getAccountId(), principal.getRole())) {
            return ApiResponse.forbidden("You don't have permission to delete this feedback");
        }
        try {
            feedbackService.delete(id);
            return ApiResponse.success("Feedback deleted successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
