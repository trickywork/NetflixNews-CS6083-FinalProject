package netflix.news.system.mapper;

import netflix.news.system.entity.Contract;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MyBatis mapper for Contract entity
 */
@Mapper
public interface ContractMapper {
    
    // Find contract by ID with related names
    @Select("SELECT c.contract_id, c.web_series_id, c.production_house_id, c.sign_date, " +
            "c.end_date, c.per_episode_fee, ws.name as webSeriesName, ph.name as productionHouseName " +
            "FROM jqy_contract c " +
            "LEFT JOIN jqy_web_series ws ON c.web_series_id = ws.web_series_id " +
            "LEFT JOIN jqy_production_house ph ON c.production_house_id = ph.production_house_id " +
            "WHERE c.contract_id = #{id}")
    @Results({
        @Result(property = "contractId", column = "contract_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "productionHouseId", column = "production_house_id"),
        @Result(property = "signDate", column = "sign_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "perEpisodeFee", column = "per_episode_fee"),
        @Result(property = "webSeriesName", column = "webSeriesName"),
        @Result(property = "productionHouseName", column = "productionHouseName")
    })
    Contract findById(@Param("id") Integer id);
    
    // Find all contracts with related names
    @Select("SELECT c.contract_id, c.web_series_id, c.production_house_id, c.sign_date, " +
            "c.end_date, c.per_episode_fee, ws.name as webSeriesName, ph.name as productionHouseName " +
            "FROM jqy_contract c " +
            "LEFT JOIN jqy_web_series ws ON c.web_series_id = ws.web_series_id " +
            "LEFT JOIN jqy_production_house ph ON c.production_house_id = ph.production_house_id " +
            "ORDER BY c.sign_date DESC")
    @Results({
        @Result(property = "contractId", column = "contract_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "productionHouseId", column = "production_house_id"),
        @Result(property = "signDate", column = "sign_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "perEpisodeFee", column = "per_episode_fee"),
        @Result(property = "webSeriesName", column = "webSeriesName"),
        @Result(property = "productionHouseName", column = "productionHouseName")
    })
    List<Contract> findAll();
    
    // Find contracts by web series ID
    @Select("SELECT c.contract_id, c.web_series_id, c.production_house_id, c.sign_date, " +
            "c.end_date, c.per_episode_fee, ws.name as webSeriesName, ph.name as productionHouseName " +
            "FROM jqy_contract c " +
            "LEFT JOIN jqy_web_series ws ON c.web_series_id = ws.web_series_id " +
            "LEFT JOIN jqy_production_house ph ON c.production_house_id = ph.production_house_id " +
            "WHERE c.web_series_id = #{webSeriesId}")
    @Results({
        @Result(property = "contractId", column = "contract_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "productionHouseId", column = "production_house_id"),
        @Result(property = "signDate", column = "sign_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "perEpisodeFee", column = "per_episode_fee"),
        @Result(property = "webSeriesName", column = "webSeriesName"),
        @Result(property = "productionHouseName", column = "productionHouseName")
    })
    List<Contract> findByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
    
    // Find contracts by production house ID
    @Select("SELECT c.contract_id, c.web_series_id, c.production_house_id, c.sign_date, " +
            "c.end_date, c.per_episode_fee, ws.name as webSeriesName, ph.name as productionHouseName " +
            "FROM jqy_contract c " +
            "LEFT JOIN jqy_web_series ws ON c.web_series_id = ws.web_series_id " +
            "LEFT JOIN jqy_production_house ph ON c.production_house_id = ph.production_house_id " +
            "WHERE c.production_house_id = #{productionHouseId}")
    @Results({
        @Result(property = "contractId", column = "contract_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "productionHouseId", column = "production_house_id"),
        @Result(property = "signDate", column = "sign_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "perEpisodeFee", column = "per_episode_fee"),
        @Result(property = "webSeriesName", column = "webSeriesName"),
        @Result(property = "productionHouseName", column = "productionHouseName")
    })
    List<Contract> findByProductionHouseId(@Param("productionHouseId") Integer productionHouseId);
    
    // Count all contracts
    @Select("SELECT COUNT(*) FROM jqy_contract")
    long count();
    
    // Find with pagination
    @Select("SELECT c.contract_id, c.web_series_id, c.production_house_id, c.sign_date, " +
            "c.end_date, c.per_episode_fee, ws.name as webSeriesName, ph.name as productionHouseName " +
            "FROM jqy_contract c " +
            "LEFT JOIN jqy_web_series ws ON c.web_series_id = ws.web_series_id " +
            "LEFT JOIN jqy_production_house ph ON c.production_house_id = ph.production_house_id " +
            "ORDER BY c.contract_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "contractId", column = "contract_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "productionHouseId", column = "production_house_id"),
        @Result(property = "signDate", column = "sign_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "perEpisodeFee", column = "per_episode_fee"),
        @Result(property = "webSeriesName", column = "webSeriesName"),
        @Result(property = "productionHouseName", column = "productionHouseName")
    })
    List<Contract> findWithPagination(@Param("offset") int offset, @Param("size") int size);
    
    // Insert new contract (end_date is calculated as sign_date + 1 year)
    @Insert("INSERT INTO jqy_contract (web_series_id, production_house_id, sign_date, end_date, per_episode_fee) " +
            "VALUES (#{webSeriesId}, #{productionHouseId}, #{signDate}, DATE_ADD(#{signDate}, INTERVAL 1 YEAR), #{perEpisodeFee})")
    @Options(useGeneratedKeys = true, keyProperty = "contractId")
    int insert(Contract contract);
    
    // Update contract
    @Update("UPDATE jqy_contract SET web_series_id = #{webSeriesId}, production_house_id = #{productionHouseId}, " +
            "sign_date = #{signDate}, end_date = DATE_ADD(#{signDate}, INTERVAL 1 YEAR), " +
            "per_episode_fee = #{perEpisodeFee} WHERE contract_id = #{contractId}")
    int update(Contract contract);
    
    // Delete contract
    @Delete("DELETE FROM jqy_contract WHERE contract_id = #{id}")
    int deleteById(@Param("id") Integer id);
    
    // Get total contract value (for statistics)
    @Select("SELECT SUM(c.per_episode_fee * ws.number_of_episodes) as total_value " +
            "FROM jqy_contract c " +
            "JOIN jqy_web_series ws ON c.web_series_id = ws.web_series_id")
    java.math.BigDecimal getTotalContractValue();
}
