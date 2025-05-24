package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleDTO;
import com.example.scheduler.model.Schedule;
import com.example.scheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    //1.일정 생성하기
    public ScheduleDTO createSchedule(String title, String writer, String password) {
        LocalDateTime now = LocalDateTime.now();

        Schedule schedule = new Schedule();
        schedule.setTitle(title);
        schedule.setWriter(writer);
        schedule.setPassword(password);
        schedule.setCreatedAt(now);
        schedule.setModifiedAt(now);

        Long id = scheduleRepository.save(schedule);

        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(id);
        dto.setTitle(title);
        dto.setWriter(writer);
        dto.setCreatedAt(now);
        dto.setModifiedAt(now);
        return dto;
    }

    //일정 전체 조회
    public List<ScheduleDTO> getSchedules(String writer, String modifiedDate) {
        return scheduleRepository.findAll(writer, modifiedDate).stream()
                .map(schedule -> new ScheduleDTO(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getWriter(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ))
                .collect(Collectors.toList());
    }

    //일정 단건 조회
    public ScheduleDTO getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다.");
        }

        return new ScheduleDTO(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    public ScheduleDTO updateSchedule(Long id, String title, String writer, String password) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다.");
        }

        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        schedule.setTitle(title);
        schedule.setWriter(writer);
        schedule.setModifiedAt(LocalDateTime.now());

        scheduleRepository.update(schedule);

        return new ScheduleDTO(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getWriter(),
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

