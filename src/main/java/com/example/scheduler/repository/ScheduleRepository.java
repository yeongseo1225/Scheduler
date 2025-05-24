package com.example.scheduler.repository;

import com.example.scheduler.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long save(Schedule schedule) {
        String sql = "INSERT INTO schedules (title, writer, password, created_at, modified_at) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.execute((PreparedStatementCreator) connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTitle());
            ps.setString(2, schedule.getWriter());
            ps.setString(3, schedule.getPassword());
            ps.setObject(4, schedule.getCreatedAt());
            ps.setObject(5, schedule.getModifiedAt());
            return ps;
        }, preparedStatement -> {
            preparedStatement.executeUpdate();
            var rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
            else throw new RuntimeException("ID 생성 실패");
        });
    }
    //일저 전체 조회
    public List<Schedule> findAll(String writer, String modifiedDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedules WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (writer != null && !writer.isEmpty()) {
            sql.append(" AND writer = ?");
            params.add(writer);
        }

        if (modifiedDate != null && !modifiedDate.isEmpty()) {
            sql.append(" AND DATE(modified_at) = ?");
            params.add(LocalDate.parse(modifiedDate));
        }

        sql.append(" ORDER BY modified_at DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
            Schedule s = new Schedule();
            s.setId(rs.getLong("id"));
            s.setTitle(rs.getString("title"));
            s.setWriter(rs.getString("writer"));
            s.setPassword(rs.getString("password")); // 저장용, 응답엔 사용 안함
            s.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            s.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return s;
        });
    }
    //일정 단건 조회
    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedules WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            Schedule s = new Schedule();
            s.setId(rs.getLong("id"));
            s.setTitle(rs.getString("title"));
            s.setWriter(rs.getString("writer"));
            s.setPassword(rs.getString("password"));
            s.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            s.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return s;
        });

        return result.isEmpty() ? null : result.get(0);
    }

    //단건 일정 수정
    public void update(Schedule schedule) {
        String sql = "UPDATE schedules SET title = ?, writer = ?, modified_at = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                schedule.getTitle(),
                schedule.getWriter(),
                schedule.getModifiedAt(),
                schedule.getId()
        );
    }

    //선택 일정 삭제
    public void deleteById(Long id) {
        String sql = "DELETE FROM schedules WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}

