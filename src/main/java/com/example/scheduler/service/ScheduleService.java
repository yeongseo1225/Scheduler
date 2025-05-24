package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleDTO;
import com.example.scheduler.model.Schedule;
import com.example.scheduler.model.Writer;
import com.example.scheduler.repository.ScheduleRepository;
import com.example.scheduler.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final WriterRepository writerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, WriterRepository writerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.writerRepository = writerRepository;
    }



    //1.일정 생성하기
    public ScheduleDTO createSchedule(String title, long writerId, String password) {
        LocalDateTime now = LocalDateTime.now();

        // 1. 일정 엔티티 생성
        Schedule schedule = new Schedule();
        schedule.setTitle(title);
        schedule.setWriterId(writerId);
        schedule.setPassword(password);
        schedule.setCreatedAt(now);
        schedule.setModifiedAt(now);

        // 2. DB 저장
        Long id = scheduleRepository.save(schedule); // ← JDBC 기반 저장

        // 3. 작성자 이름을 직접 조회 (JDBC)
        Writer writer = writerRepository.findById(writerId); // ← JDBC 기반 findById()

        // 4. 응답 DTO 구성
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(id);
        dto.setTitle(title);
        dto.setWriterId(writerId);
        dto.setWriterName(writer.getName()); // 직접 조인한 결과
        dto.setCreatedAt(now);
        dto.setModifiedAt(now);

        return dto;
    }

    //일정 전체 조회 3단계
    public List<ScheduleDTO> getSchedules(Long writerId, String modifiedDate) {
        return scheduleRepository.findAllByWriterId(writerId, modifiedDate).stream()
                .map(schedule -> {
                    Writer writer = writerRepository.findById(schedule.getWriterId());
                    return new ScheduleDTO(
                            schedule.getId(),
                            schedule.getTitle(),
                            schedule.getWriterId(),
                            writer.getName(),
                            schedule.getCreatedAt(),
                            schedule.getModifiedAt()
                    );
                })
                .collect(Collectors.toList());
    }


    //일정 단건 조회
    public ScheduleDTO getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다.");
        }

        Writer writer = writerRepository.findById(schedule.getWriterId());

        return new ScheduleDTO(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getWriterId(),
                writer.getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }


    public ScheduleDTO updateSchedule(Long id, String title, String writerName, String password) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다.");
        }

        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Writer writer = writerRepository.findOrCreateByName(writerName);

        schedule.setTitle(title);
        schedule.setWriterId(writer.getId());
        schedule.setModifiedAt(LocalDateTime.now());

        scheduleRepository.update(schedule);

        return new ScheduleDTO(
                schedule.getId(),
                schedule.getTitle(),
                writer.getId(),
                writer.getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    //선택 일정 삭제
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다.");
        }

        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.deleteById(id);
    }



}

