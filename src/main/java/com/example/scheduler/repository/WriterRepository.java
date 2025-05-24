package com.example.scheduler.repository;

import com.example.scheduler.model.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class WriterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long save(Writer writer) {
        String sql = "INSERT INTO writers (name, email, created_at, modified_at) VALUES (?, ?, ?, ?)";

        return jdbcTemplate.execute((PreparedStatementCreator) connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, writer.getName());
            ps.setString(2, writer.getEmail());
            ps.setObject(3, writer.getCreatedAt());
            ps.setObject(4, writer.getModifiedAt());
            return ps;
        }, ps -> {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new RuntimeException("작성자 등록 실패");
            }
        });
    }

    public Writer findById(Long id) {
        String sql = "SELECT * FROM writers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Writer writer = new Writer();
            writer.setId(rs.getLong("id"));
            writer.setName(rs.getString("name"));
            writer.setEmail(rs.getString("email"));
            writer.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            writer.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return writer;
        });
    }

    public Writer findOrCreateByName(String name) {
        String sql = "SELECT * FROM writers WHERE name = ? LIMIT 1";
        List<Writer> writers = jdbcTemplate.query(sql, new Object[]{name}, (rs, rowNum) -> {
            Writer w = new Writer();
            w.setId(rs.getLong("id"));
            w.setName(rs.getString("name"));
            w.setEmail(rs.getString("email"));
            w.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            w.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            return w;
        });

        if (!writers.isEmpty()) return writers.get(0);

        // 작성자 없으면 새로 등록
        Writer newWriter = new Writer();
        newWriter.setName(name);
        newWriter.setEmail(name + "@example.com"); // 가짜 이메일
        newWriter.setCreatedAt(LocalDateTime.now());
        newWriter.setModifiedAt(LocalDateTime.now());

        Long id = save(newWriter);
        newWriter.setId(id);
        return newWriter;
    }
}
