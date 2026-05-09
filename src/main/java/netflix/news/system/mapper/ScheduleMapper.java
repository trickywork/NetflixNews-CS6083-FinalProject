package netflix.news.system.mapper;

import netflix.news.system.entity.Schedule;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MyBatis mapper for Schedule entity
 */
@Mapper
public interface ScheduleMapper {
    
    // Find schedule by ID with web series name
    @Select("SELECT s.schedule_id, s.web_series_id, s.start_datetime, s.end_datetime, " +
            "ws.name as webSeriesName FROM jqy_schedule s " +
            "LEFT JOIN jqy_web_series ws ON s.web_series_id = ws.web_series_id " +
            "WHERE s.schedule_id = #{id}")
    @Results({
        @Result(property = "scheduleId", column = "schedule_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "startDatetime", column = "start_datetime"),
        @Result(property = "endDatetime", column = "end_datetime"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    Schedule findById(@Param("id") Integer id);
    
    // Find all schedules with web series names
    @Select("SELECT s.schedule_id, s.web_series_id, s.start_datetime, s.end_datetime, " +
            "ws.name as webSeriesName FROM jqy_schedule s " +
            "LEFT JOIN jqy_web_series ws ON s.web_series_id = ws.web_series_id " +
            "ORDER BY s.start_datetime DESC")
    @Results({
        @Result(property = "scheduleId", column = "schedule_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "startDatetime", column = "start_datetime"),
        @Result(property = "endDatetime", column = "end_datetime"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Schedule> findAll();
    
    // Find schedules by web series ID
    @Select("SELECT s.schedule_id, s.web_series_id, s.start_datetime, s.end_datetime, " +
            "ws.name as webSeriesName FROM jqy_schedule s " +
            "LEFT JOIN jqy_web_series ws ON s.web_series_id = ws.web_series_id " +
            "WHERE s.web_series_id = #{webSeriesId} ORDER BY s.start_datetime DESC")
    @Results({
        @Result(property = "scheduleId", column = "schedule_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "startDatetime", column = "start_datetime"),
        @Result(property = "endDatetime", column = "end_datetime"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Schedule> findByWebSeriesId(@Param("webSeriesId") Integer webSeriesId);
    
    // Find schedules between dates
    @Select("SELECT s.schedule_id, s.web_series_id, s.start_datetime, s.end_datetime, " +
            "ws.name as webSeriesName FROM jqy_schedule s " +
            "LEFT JOIN jqy_web_series ws ON s.web_series_id = ws.web_series_id " +
            "WHERE s.start_datetime >= #{startDate} AND s.start_datetime <= #{endDate} " +
            "ORDER BY s.start_datetime")
    @Results({
        @Result(property = "scheduleId", column = "schedule_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "startDatetime", column = "start_datetime"),
        @Result(property = "endDatetime", column = "end_datetime"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Schedule> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                    @Param("endDate") LocalDateTime endDate);
    
    // Find upcoming schedules
    @Select("SELECT s.schedule_id, s.web_series_id, s.start_datetime, s.end_datetime, " +
            "ws.name as webSeriesName FROM jqy_schedule s " +
            "LEFT JOIN jqy_web_series ws ON s.web_series_id = ws.web_series_id " +
            "WHERE s.start_datetime >= NOW() ORDER BY s.start_datetime LIMIT #{limit}")
    @Results({
        @Result(property = "scheduleId", column = "schedule_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "startDatetime", column = "start_datetime"),
        @Result(property = "endDatetime", column = "end_datetime"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Schedule> findUpcoming(@Param("limit") int limit);
    
    // Count all schedules
    @Select("SELECT COUNT(*) FROM jqy_schedule")
    long count();
    
    // Find with pagination
    @Select("SELECT s.schedule_id, s.web_series_id, s.start_datetime, s.end_datetime, " +
            "ws.name as webSeriesName FROM jqy_schedule s " +
            "LEFT JOIN jqy_web_series ws ON s.web_series_id = ws.web_series_id " +
            "ORDER BY s.schedule_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "scheduleId", column = "schedule_id"),
        @Result(property = "webSeriesId", column = "web_series_id"),
        @Result(property = "startDatetime", column = "start_datetime"),
        @Result(property = "endDatetime", column = "end_datetime"),
        @Result(property = "webSeriesName", column = "webSeriesName")
    })
    List<Schedule> findWithPagination(@Param("offset") int offset, @Param("size") int size);
    
    // Insert new schedule
    @Insert("INSERT INTO jqy_schedule (web_series_id, start_datetime, end_datetime) " +
            "VALUES (#{webSeriesId}, #{startDatetime}, #{endDatetime})")
    @Options(useGeneratedKeys = true, keyProperty = "scheduleId")
    int insert(Schedule schedule);
    
    // Update schedule
    @Update("UPDATE jqy_schedule SET web_series_id = #{webSeriesId}, " +
            "start_datetime = #{startDatetime}, end_datetime = #{endDatetime} " +
            "WHERE schedule_id = #{scheduleId}")
    int update(Schedule schedule);
    
    // Delete schedule
    @Delete("DELETE FROM jqy_schedule WHERE schedule_id = #{id}")
    int deleteById(@Param("id") Integer id);
}
