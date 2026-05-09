package netflix.news.system.mapper;

import netflix.news.system.entity.WebSeries;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MyBatis mapper for WebSeries entity
 */
@Mapper
public interface WebSeriesMapper {
    
    // Find web series by ID
    @Select("SELECT web_series_id, name, number_of_episodes, original_language, release_date, " +
            "type, total_viewers, technical_interruption FROM jqy_web_series WHERE web_series_id = #{id}")
    WebSeries findById(@Param("id") Integer id);
    
    // Find all web series
    @Select("SELECT web_series_id, name, number_of_episodes, original_language, release_date, " +
            "type, total_viewers, technical_interruption FROM jqy_web_series ORDER BY release_date DESC")
    List<WebSeries> findAll();
    
    // Search web series by name
    @Select("SELECT web_series_id, name, number_of_episodes, original_language, release_date, " +
            "type, total_viewers, technical_interruption FROM jqy_web_series " +
            "WHERE name LIKE CONCAT('%', #{keyword}, '%') ORDER BY name")
    List<WebSeries> searchByName(@Param("keyword") String keyword);
    
    // Find web series by type
    @Select("SELECT web_series_id, name, number_of_episodes, original_language, release_date, " +
            "type, total_viewers, technical_interruption FROM jqy_web_series " +
            "WHERE type = #{type} ORDER BY release_date DESC")
    List<WebSeries> findByType(@Param("type") String type);
    
    // Count all web series
    @Select("SELECT COUNT(*) FROM jqy_web_series")
    long count();
    
    // Find web series with pagination
    @Select("SELECT web_series_id, name, number_of_episodes, original_language, release_date, " +
            "type, total_viewers, technical_interruption FROM jqy_web_series " +
            "ORDER BY web_series_id LIMIT #{offset}, #{size}")
    List<WebSeries> findWithPagination(@Param("offset") int offset, @Param("size") int size);
    
    // Insert new web series
    @Insert("INSERT INTO jqy_web_series (name, number_of_episodes, original_language, release_date, " +
            "type, total_viewers, technical_interruption) VALUES (#{name}, #{numberOfEpisodes}, " +
            "#{originalLanguage}, #{releaseDate}, #{type}, #{totalViewers}, #{technicalInterruption})")
    @Options(useGeneratedKeys = true, keyProperty = "webSeriesId")
    int insert(WebSeries webSeries);
    
    // Update web series
    @Update("UPDATE jqy_web_series SET name = #{name}, number_of_episodes = #{numberOfEpisodes}, " +
            "original_language = #{originalLanguage}, release_date = #{releaseDate}, type = #{type}, " +
            "total_viewers = #{totalViewers}, technical_interruption = #{technicalInterruption} " +
            "WHERE web_series_id = #{webSeriesId}")
    int update(WebSeries webSeries);
    
    // Delete web series
    @Delete("DELETE FROM jqy_web_series WHERE web_series_id = #{id}")
    int deleteById(@Param("id") Integer id);
    
    // Get distinct types
    @Select("SELECT DISTINCT type FROM jqy_web_series ORDER BY type")
    List<String> findAllTypes();
    
    // Get total viewers by type (for statistics)
    @Select("SELECT type, SUM(total_viewers) as viewers FROM jqy_web_series GROUP BY type ORDER BY viewers DESC")
    List<java.util.Map<String, Object>> getViewersByType();
    
    // Get top rated web series
    @Select("SELECT ws.web_series_id, ws.name, ws.number_of_episodes, ws.original_language, " +
            "ws.release_date, ws.type, ws.total_viewers, ws.technical_interruption, " +
            "AVG(f.rating) as avg_rating FROM jqy_web_series ws " +
            "LEFT JOIN jqy_feedback f ON ws.web_series_id = f.web_series_id " +
            "GROUP BY ws.web_series_id ORDER BY avg_rating DESC LIMIT #{limit}")
    List<WebSeries> findTopRated(@Param("limit") int limit);
}
