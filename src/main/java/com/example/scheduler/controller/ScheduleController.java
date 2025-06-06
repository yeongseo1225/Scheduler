package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleDTO;
import com.example.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    //일정 등록
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        Long writerId = Long.parseLong(request.get("writerId"));
        String password = request.get("password");

        return scheduleService.createSchedule(title, writerId, password);
    }

    //전체 일정 조회
    @GetMapping
    public List<ScheduleDTO> getSchedules(
            @RequestParam(required = false) Long writerId,
            @RequestParam(required = false) String modifiedDate
    ) {
        return scheduleService.getSchedules(writerId, modifiedDate);
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ScheduleDTO getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    //선택한 일정 수정
    @PatchMapping("/{id}")
    public ScheduleDTO updateSchedule(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        String title = request.get("title");
        String writer = request.get("writer");
        String password = request.get("password");

        return scheduleService.updateSchedule(id, title, writer, password);
    }

    //선택한 일정 삭제
    @DeleteMapping("/{id}")
    public String deleteSchedule(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        String password = request.get("password");

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호가 누락되었습니다.");
        }

        scheduleService.deleteSchedule(id, password);
        return "삭제 성공";
    }


}
