package netflix.news.system.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MyBatis mapper for Web Series Dubbing Languages
 */
@Mapper
public interface WebSeriesDubbingMapper {
    
    // Add dubbing language to web series
    @Insert("INSERT INTO jqy_web_series_dubbing (web_series_id, dubbing_language) VALUES (#{webSeriesId}, #{language})")
    int insert(@Param("webSeriesId") Integer webSeriesId, @Param("language") String language);
    
    // Remove dubbing language from web series
    @Delete("DELETE FROM jqy_web_series_dubbing WHERE web_series_id = #{webSeriesId} AND dubbing_language = #{language}")
    int delete(@Param("webSeriesId") Integer webSeriesId, @Param("language") String language);
    
    // Remove all dubbing languages from web series
    @Delete("DELETE FROM jqy_web_series_dubbing WHERE web_series_id = #{webSeriesId}")
    int deleteByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
    
    // Get dubbing languages for web series
    @Select("SELECT dubbing_language FROM jqy_web_series_dubbing WHERE web_series_id = #{webSeriesId}")
    List<String> findByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
    
    // Get all distinct dubbing languages
    @Select("SELECT DISTINCT dubbing_language FROM jqy_web_series_dubbing ORDER BY dubbing_language")
    List<String> findAllDistinct();
}
