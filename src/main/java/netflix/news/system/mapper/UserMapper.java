package netflix.news.system.mapper;

import netflix.news.system.entity.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MyBatis mapper for User entity
 */
@Mapper
public interface UserMapper {
    
    // Find user by username
    @Select("SELECT user_id, username, password, role, account_id, created_at, last_login " +
            "FROM jqy_user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);
    
    // Find user by ID
    @Select("SELECT user_id, username, password, role, account_id, created_at, last_login " +
            "FROM jqy_user WHERE user_id = #{userId}")
    User findById(@Param("userId") Integer userId);
    
    // Check if username exists
    @Select("SELECT COUNT(*) FROM jqy_user WHERE username = #{username}")
    int countByUsername(@Param("username") String username);
    
    // Insert new user
    @Insert("INSERT INTO jqy_user (username, password, role, account_id, created_at) " +
            "VALUES (#{username}, #{password}, #{role}, #{accountId}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
    
    // Update last login time
    @Update("UPDATE jqy_user SET last_login = #{lastLogin} WHERE user_id = #{userId}")
    int updateLastLogin(@Param("userId") Integer userId, @Param("lastLogin") LocalDateTime lastLogin);
    
    // Update user password
    @Update("UPDATE jqy_user SET password = #{password} WHERE user_id = #{userId}")
    int updatePassword(@Param("userId") Integer userId, @Param("password") String password);
    
    // Delete user
    @Delete("DELETE FROM jqy_user WHERE user_id = #{userId}")
    int deleteById(@Param("userId") Integer userId);
    
    // Find all users (for admin)
    @Select("SELECT user_id, username, role, account_id, created_at, last_login " +
            "FROM jqy_user ORDER BY created_at DESC")
    List<User> findAll();
    
    // Find users by role
    @Select("SELECT user_id, username, role, account_id, created_at, last_login " +
            "FROM jqy_user WHERE role = #{role} ORDER BY created_at DESC")
    List<User> findByRole(@Param("role") String role);
}
