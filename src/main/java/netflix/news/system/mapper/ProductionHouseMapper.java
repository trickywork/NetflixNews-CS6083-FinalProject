package netflix.news.system.mapper;

import netflix.news.system.entity.ProductionHouse;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MyBatis mapper for ProductionHouse entity
 */
@Mapper
public interface ProductionHouseMapper {
    
    // Find production house by ID
    @Select("SELECT production_house_id, name, address, year_established " +
            "FROM jqy_production_house WHERE production_house_id = #{id}")
    ProductionHouse findById(@Param("id") Integer id);
    
    // Find all production houses
    @Select("SELECT production_house_id, name, address, year_established " +
            "FROM jqy_production_house ORDER BY name")
    List<ProductionHouse> findAll();
    
    // Search production houses by name
    @Select("SELECT production_house_id, name, address, year_established " +
            "FROM jqy_production_house WHERE name LIKE CONCAT('%', #{keyword}, '%') ORDER BY name")
    List<ProductionHouse> searchByName(@Param("keyword") String keyword);
    
    // Count all production houses
    @Select("SELECT COUNT(*) FROM jqy_production_house")
    long count();
    
    // Find with pagination
    @Select("SELECT production_house_id, name, address, year_established " +
            "FROM jqy_production_house ORDER BY production_house_id LIMIT #{offset}, #{size}")
    List<ProductionHouse> findWithPagination(@Param("offset") int offset, @Param("size") int size);
    
    // Insert new production house
    @Insert("INSERT INTO jqy_production_house (name, address, year_established) " +
            "VALUES (#{name}, #{address}, #{yearEstablished})")
    @Options(useGeneratedKeys = true, keyProperty = "productionHouseId")
    int insert(ProductionHouse productionHouse);
    
    // Update production house
    @Update("UPDATE jqy_production_house SET name = #{name}, address = #{address}, " +
            "year_established = #{yearEstablished} WHERE production_house_id = #{productionHouseId}")
    int update(ProductionHouse productionHouse);
    
    // Delete production house
    @Delete("DELETE FROM jqy_production_house WHERE production_house_id = #{id}")
    int deleteById(@Param("id") Integer id);
}
