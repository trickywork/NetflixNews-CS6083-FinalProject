package netflix.news.system.mapper;

import netflix.news.system.entity.Country;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MyBatis mapper for Country entity
 */
@Mapper
public interface CountryMapper {
    
    // Find country by ID
    @Select("SELECT country_id, country_name FROM jqy_country WHERE country_id = #{id}")
    Country findById(@Param("id") Integer id);
    
    // Find all countries
    @Select("SELECT country_id, country_name FROM jqy_country ORDER BY country_name")
    List<Country> findAll();
    
    // Find country by name
    @Select("SELECT country_id, country_name FROM jqy_country WHERE country_name = #{name}")
    Country findByName(@Param("name") String name);
    
    // Insert new country
    @Insert("INSERT INTO jqy_country (country_name) VALUES (#{countryName})")
    @Options(useGeneratedKeys = true, keyProperty = "countryId")
    int insert(Country country);
    
    // Update country
    @Update("UPDATE jqy_country SET country_name = #{countryName} WHERE country_id = #{countryId}")
    int update(Country country);
    
    // Delete country
    @Delete("DELETE FROM jqy_country WHERE country_id = #{id}")
    int deleteById(@Param("id") Integer id);
    
    // Find countries by web series ID
    @Select("SELECT c.country_id, c.country_name FROM jqy_country c " +
            "INNER JOIN jqy_web_series_country wsc ON c.country_id = wsc.country_id " +
            "WHERE wsc.web_series_id = #{webSeriesId}")
    List<Country> findByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
}
