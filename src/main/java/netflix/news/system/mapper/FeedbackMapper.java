package netflix.news.system.mapper;

import netflix.news.system.entity.Feedback;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * MyBatis mapper for Feedback entity
 */
@Mapper
public interface FeedbackMapper {
    
    // Find feedback by ID with related names
    @Select("SELECT f.feedback_id, f.account_id, f.web_series_id, f.feedback_text, " +
            "f.rating, f.feedback_date, a.full_name as accountName, ws.name as webSeriesName " +
            "FROM jqy_feedback f " +
            "LEFT JOIN jqy_account a ON f.account_id = a.account_id " +
            "LEFT JOIN jqy_web_series ws ON f.web_series_id = ws.web_series_id " +
            "WHERE f.feedback_id = #{id}")
    @Results({
        @Result(property = "feedbackId", column = "feedback_id"),
        @Result(property = "accountId", column = "account_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "feedbackText", column = "feedback_text"),
        @Result(property = "rating", column = "rating"),
        @Result(property = "feedbackDate", column = "feedback_date"),
        @Result(property = "accountName", column = "accountName"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    Feedback findById(@Param("id") Integer id);
    
    // Find all feedback with related names
    @Select("SELECT f.feedback_id, f.account_id, f.web_series_id, f.feedback_text, " +
            "f.rating, f.feedback_date, a.full_name as accountName, ws.name as webSeriesName " +
            "FROM jqy_feedback f " +
            "LEFT JOIN jqy_account a ON f.account_id = a.account_id " +
            "LEFT JOIN jqy_web_series ws ON f.web_series_id = ws.web_series_id " +
            "ORDER BY f.feedback_date DESC")
    @Results({
        @Result(property = "feedbackId", column = "feedback_id"),
        @Result(property = "accountId", column = "account_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "feedbackText", column = "feedback_text"),
        @Result(property = "rating", column = "rating"),
        @Result(property = "feedbackDate", column = "feedback_date"),
        @Result(property = "accountName", column = "accountName"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Feedback> findAll();
    
    // Find feedback by account ID
    @Select("SELECT f.feedback_id, f.account_id, f.web_series_id, f.feedback_text, " +
            "f.rating, f.feedback_date, a.full_name as accountName, ws.name as webSeriesName " +
            "FROM jqy_feedback f " +
            "LEFT JOIN jqy_account a ON f.account_id = a.account_id " +
            "LEFT JOIN jqy_web_series ws ON f.web_series_id = ws.web_series_id " +
            "WHERE f.account_id = #{accountId} ORDER BY f.feedback_date DESC")
    @Results({
        @Result(property = "feedbackId", column = "feedback_id"),
        @Result(property = "accountId", column = "account_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "feedbackText", column = "feedback_text"),
        @Result(property = "rating", column = "rating"),
        @Result(property = "feedbackDate", column = "feedback_date"),
        @Result(property = "accountName", column = "accountName"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Feedback> findByAccountId(@Param("accountId") Integer accountId);
    
    // Find feedback by web series ID
    @Select("SELECT f.feedback_id, f.account_id, f.web_series_id, f.feedback_text, " +
            "f.rating, f.feedback_date, a.full_name as accountName, ws.name as webSeriesName " +
            "FROM jqy_feedback f " +
            "LEFT JOIN jqy_account a ON f.account_id = a.account_id " +
            "LEFT JOIN jqy_web_series ws ON f.web_series_id = ws.web_series_id " +
            "WHERE f.web_series_id = #{webSeriesId} ORDER BY f.feedback_date DESC")
    @Results({
        @Result(property = "feedbackId", column = "feedback_id"),
        @Result(property = "accountId", column = "account_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "feedbackText", column = "feedback_text"),
        @Result(property = "rating", column = "rating"),
        @Result(property = "feedbackDate", column = "feedback_date"),
        @Result(property = "accountName", column = "accountName"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Feedback> findByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
    
    // Check if user already submitted feedback for a web series
    @Select("SELECT COUNT(*) FROM jqy_feedback WHERE account_id = #{accountId} AND web_series_id = #{webSeriesId}")
    int countByAccountAndWebSeries(@Param("accountId") Integer accountId, @Param("webSeriesId") Integer webSeriesId);
    
    // Find existing feedback by account and web series
    @Select("SELECT f.feedback_id, f.account_id, f.web_series_id, f.feedback_text, " +
            "f.rating, f.feedback_date FROM jqy_feedback f " +
            "WHERE f.account_id = #{accountId} AND f.web_series_id = #{webSeriesId}")
    Feedback findByAccountAndWebSeries(@Param("accountId") Integer accountId, @Param("webSeriesId") Integer webSeriesId);
    
    // Count all feedback
    @Select("SELECT COUNT(*) FROM jqy_feedback")
    long count();
    
    // Find with pagination
    @Select("SELECT f.feedback_id, f.account_id, f.web_series_id, f.feedback_text, " +
            "f.rating, f.feedback_date, a.full_name as accountName, ws.name as webSeriesName " +
            "FROM jqy_feedback f " +
            "LEFT JOIN jqy_account a ON f.account_id = a.account_id " +
            "LEFT JOIN jqy_web_series ws ON f.web_series_id = ws.web_series_id " +
            "ORDER BY f.feedback_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "feedbackId", column = "feedback_id"),
        @Result(property = "accountId", column = "account_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "feedbackText", column = "feedback_text"),
        @Result(property = "rating", column = "rating"),
        @Result(property = "feedbackDate", column = "feedback_date"),
        @Result(property = "accountName", column = "accountName"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Feedback> findWithPagination(@Param("offset") int offset, @Param("size") int size);
    
    // Insert new feedback
    @Insert("INSERT INTO jqy_feedback (account_id, web_series_id, feedback_text, rating, feedback_date) " +
            "VALUES (#{accountId}, #{webSeriesId}, #{feedbackText}, #{rating}, #{feedbackDate})")
    @Options(useGeneratedKeys = true, keyProperty = "feedbackId")
    int insert(Feedback feedback);
    
    // Update feedback
    @Update("UPDATE jqy_feedback SET feedback_text = #{feedbackText}, rating = #{rating} " +
            "WHERE feedback_id = #{feedbackId}")
    int update(Feedback feedback);
    
    // Delete feedback
    @Delete("DELETE FROM jqy_feedback WHERE feedback_id = #{id}")
    int deleteById(@Param("id") Integer id);
    
    // Get average rating for a web series
    @Select("SELECT AVG(rating) FROM jqy_feedback WHERE web_series_id = #{webSeriesId}")
    Double getAverageRatingByWebSeries(@Param("webSeriesId") Integer webSeriesId);
    
    // Get rating distribution (for statistics)
    @Select("SELECT rating, COUNT(*) as count FROM jqy_feedback GROUP BY rating ORDER BY rating")
    List<Map<String, Object>> getRatingDistribution();
    
    // Get recent feedback
    @Select("SELECT f.feedback_id, f.account_id, f.web_series_id, f.feedback_text, " +
            "f.rating, f.feedback_date, a.full_name as accountName, ws.name as webSeriesName " +
            "FROM jqy_feedback f " +
            "LEFT JOIN jqy_account a ON f.account_id = a.account_id " +
            "LEFT JOIN jqy_web_series ws ON f.web_series_id = ws.web_series_id " +
            "ORDER BY f.feedback_date DESC LIMIT #{limit}")
    @Results({
        @Result(property = "feedbackId", column = "feedback_id"),
        @Result(property = "accountId", column = "account_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "feedbackText", column = "feedback_text"),
        @Result(property = "rating", column = "rating"),
        @Result(property = "feedbackDate", column = "feedback_date"),
        @Result(property = "accountName", column = "accountName"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Feedback> findRecent(@Param("limit") int limit);
}
