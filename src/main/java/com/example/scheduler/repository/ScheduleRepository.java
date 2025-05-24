package com.example.scheduler.repository;

import com.example.scheduler.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String sql = "INSERT INTO schedules (title, password, writer_id, created_at, modified_at) VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.execute((PreparedStatementCreator) connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTitle());
            ps.setString(2, schedule.getPassword());
            ps.setLong(3, schedule.getWriterId());
            ps.setObject(4, schedule.getCreatedAt());
            ps.setObject(5, schedule.getModifiedAt());
            return ps;
        }, ps -> {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
            else throw new RuntimeException("일정 저장 실패");
        });
    }

    //일정 전체 조회
    public List<Schedule> findAllByWriterId(Long writerId, String modifiedDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedules WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (writerId != null) {
            sql.append(" AND writer_id = ?");
            params.add(writerId);
        }

        if (modifiedDate != null && !modifiedDate.isBlank()) {
            sql.append(" AND DATE(modified_at) = ?");
            params.add(LocalDate.parse(modifiedDate));
        }

        sql.append(" ORDER BY modified_at DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setTitle(rs.getString("title"));
            schedule.setWriterId(rs.getLong("writer_id"));
            schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            schedule.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return schedule;
        });
    }

    //일정 단건 조회
    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedules WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            Schedule s = new Schedule();
            s.setId(rs.getLong("id"));
            s.setTitle(rs.getString("title"));
            s.setWriterId(rs.getLong("writer_id"));
            s.setPassword(rs.getString("password"));
            s.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            s.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return s;
        });

        return result.isEmpty() ? null : result.get(0);
    }

    //단건 일정 수정
    public void update(Schedule schedule) {
        String sql = "UPDATE schedules SET title = ?, writer_id = ?, modified_at = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                schedule.getTitle(),
                schedule.getWriterId(),
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

