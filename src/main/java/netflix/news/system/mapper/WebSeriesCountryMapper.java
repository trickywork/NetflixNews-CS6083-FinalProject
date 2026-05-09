package netflix.news.system.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MyBatis mapper for Web Series - Country relationship
 */
@Mapper
public interface WebSeriesCountryMapper {
    
    // Add country to web series
    @Insert("INSERT INTO jqy_web_series_country (web_series_id, country_id) VALUES (#{webSeriesId}, #{countryId})")
    int insert(@Param("webSeriesId") Integer webSeriesId, @Param("countryId") Integer countryId);
    
    // Remove country from web series
    @Delete("DELETE FROM jqy_web_series_country WHERE web_series_id = #{webSeriesId} AND country_id = #{countryId}")
    int delete(@Param("webSeriesId") Integer webSeriesId, @Param("countryId") Integer countryId);
    
    // Remove all countries from web series
    @Delete("DELETE FROM jqy_web_series_country WHERE web_series_id = #{webSeriesId}")
    int deleteByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
    
    // Get country IDs for web series
    @Select("SELECT country_id FROM jqy_web_series_country WHERE web_series_id = #{webSeriesId}")
    List<Integer> findCountryIdsByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
    
    // Get web series IDs for country
    @Select("SELECT web_series_id FROM jqy_web_series_country WHERE country_id = #{countryId}")
    List<Integer> findWebSeriesIdsByCountryId(@Param("countryId") Integer countryId);
}
