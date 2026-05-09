package netflix.news.system.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MyBatis mapper for Web Series Subtitle Languages
 */
@Mapper
public interface WebSeriesSubtitleMapper {
    
    // Add subtitle language to web series
    @Insert("INSERT INTO jqy_web_series_subtitle (web_series_id, subtitle_language) VALUES (#{webSeriesId}, #{language})")
    int insert(@Param("webSeriesId") Integer webSeriesId, @Param("language") String language);
    
    // Remove subtitle language from web series
    @Delete("DELETE FROM jqy_web_series_subtitle WHERE web_series_id = #{webSeriesId} AND subtitle_language = #{language}")
    int delete(@Param("webSeriesId") Integer webSeriesId, @Param("language") String language);
    
    // Remove all subtitle languages from web series
    @Delete("DELETE FROM jqy_web_series_subtitle WHERE web_series_id = #{webSeriesId}")
    int deleteByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
    
    // Get subtitle languages for web series
    @Select("SELECT subtitle_language FROM jqy_web_series_subtitle WHERE web_series_id = #{webSeriesId}")
    List<String> findByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
    
    // Get all distinct subtitle languages
    @Select("SELECT DISTINCT subtitle_language FROM jqy_web_series_subtitle ORDER BY subtitle_language")
    List<String> findAllDistinct();
}
