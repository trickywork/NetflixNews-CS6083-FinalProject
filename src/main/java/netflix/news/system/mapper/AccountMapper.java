package netflix.news.system.mapper;

import netflix.news.system.entity.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MyBatis mapper for Account entity
 */
@Mapper
public interface AccountMapper {
    
    // Find account by ID
    @Select("SELECT account_id, full_name, full_address, open_date, monthly_service_charge " +
            "FROM jqy_account WHERE account_id = #{accountId}")
    Account findById(@Param("accountId") Integer accountId);
    
    // Find all accounts
    @Select("SELECT account_id, full_name, full_address, open_date, monthly_service_charge " +
            "FROM jqy_account ORDER BY open_date DESC")
    List<Account> findAll();
    
    // Search accounts by name
    @Select("SELECT account_id, full_name, full_address, open_date, monthly_service_charge " +
            "FROM jqy_account WHERE full_name LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY full_name")
    List<Account> searchByName(@Param("keyword") String keyword);
    
    // Count all accounts
    @Select("SELECT COUNT(*) FROM jqy_account")
    long count();
    
    // Find accounts with pagination
    @Select("SELECT account_id, full_name, full_address, open_date, monthly_service_charge " +
            "FROM jqy_account ORDER BY account_id LIMIT #{offset}, #{size}")
    List<Account> findWithPagination(@Param("offset") int offset, @Param("size") int size);
    
    // Insert new account
    @Insert("INSERT INTO jqy_account (full_name, full_address, open_date, monthly_service_charge) " +
            "VALUES (#{fullName}, #{fullAddress}, #{openDate}, #{monthlyServiceCharge})")
    @Options(useGeneratedKeys = true, keyProperty = "accountId")
    int insert(Account account);
    
    // Update account
    @Update("UPDATE jqy_account SET full_name = #{fullName}, full_address = #{fullAddress}, " +
            "monthly_service_charge = #{monthlyServiceCharge} WHERE account_id = #{accountId}")
    int update(Account account);
    
    // Delete account
    @Delete("DELETE FROM jqy_account WHERE account_id = #{accountId}")
    int deleteById(@Param("accountId") Integer accountId);
}
