package com.example.scheduler.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class Schedule {
    private Long id;
    private String title;
    private String password;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
