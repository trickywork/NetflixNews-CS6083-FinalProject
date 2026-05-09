package netflix.news.system.mapper;

import netflix.news.system.entity.Producer;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MyBatis mapper for Producer entity
 */
@Mapper
public interface ProducerMapper {
    
    // Find producer by ID
    @Select("SELECT producer_id, name, address, phone_number, email_address " +
            "FROM jqy_producer WHERE producer_id = #{id}")
    Producer findById(@Param("id") Integer id);
    
    // Find all producers
    @Select("SELECT producer_id, name, address, phone_number, email_address " +
            "FROM jqy_producer ORDER BY name")
    List<Producer> findAll();
    
    // Search producers by name
    @Select("SELECT producer_id, name, address, phone_number, email_address " +
            "FROM jqy_producer WHERE name LIKE CONCAT('%', #{keyword}, '%') ORDER BY name")
    List<Producer> searchByName(@Param("keyword") String keyword);
    
    // Find producer by email
    @Select("SELECT producer_id, name, address, phone_number, email_address " +
            "FROM jqy_producer WHERE email_address = #{email}")
    Producer findByEmail(@Param("email") String email);
    
    // Count all producers
    @Select("SELECT COUNT(*) FROM jqy_producer")
    long count();
    
    // Find with pagination
    @Select("SELECT producer_id, name, address, phone_number, email_address " +
            "FROM jqy_producer ORDER BY producer_id LIMIT #{offset}, #{size}")
    List<Producer> findWithPagination(@Param("offset") int offset, @Param("size") int size);
    
    // Insert new producer
    @Insert("INSERT INTO jqy_producer (name, address, phone_number, email_address) " +
            "VALUES (#{name}, #{address}, #{phoneNumber}, #{emailAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "producerId")
    int insert(Producer producer);
    
    // Update producer
    @Update("UPDATE jqy_producer SET name = #{name}, address = #{address}, " +
            "phone_number = #{phoneNumber}, email_address = #{emailAddress} " +
            "WHERE producer_id = #{producerId}")
    int update(Producer producer);
    
    // Delete producer
    @Delete("DELETE FROM jqy_producer WHERE producer_id = #{id}")
    int deleteById(@Param("id") Integer id);
    
    // Find producers by production house ID
    @Select("SELECT p.producer_id, p.name, p.address, p.phone_number, p.email_address " +
            "FROM jqy_producer p " +
            "INNER JOIN jqy_producer_production_house pph ON p.producer_id = pph.producer_id " +
            "WHERE pph.production_house_id = #{productionHouseId}")
    List<Producer> findByProductionHouseId(@Param("productionHouseId") Integer productionHouseId);
    
    // Link producer to production house
    @Insert("INSERT INTO jqy_producer_production_house (producer_id, production_house_id) " +
            "VALUES (#{producerId}, #{productionHouseId})")
    int linkToProductionHouse(@Param("producerId") Integer producerId, 
                               @Param("productionHouseId") Integer productionHouseId);
    
    // Unlink producer from production house
    @Delete("DELETE FROM jqy_producer_production_house " +
            "WHERE producer_id = #{producerId} AND production_house_id = #{productionHouseId}")
    int unlinkFromProductionHouse(@Param("producerId") Integer producerId, 
                                   @Param("productionHouseId") Integer productionHouseId);
}
