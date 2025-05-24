package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
