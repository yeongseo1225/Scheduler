package com.example.scheduler.controller;

import com.example.scheduler.dto.WriterDTO;
import com.example.scheduler.model.Writer;
import com.example.scheduler.repository.WriterRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/writers")
public class WriterController {

    private final WriterRepository writerRepository;

    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    //사용자 등록
    @PostMapping
    public WriterDTO createWriter(@RequestBody Map<String, String> request) {
        Writer writer = new Writer();
        writer.setName(request.get("name"));
        writer.setEmail(request.get("email"));
        writer.setCreatedAt(LocalDateTime.now());
        writer.setModifiedAt(LocalDateTime.now());

        Long id = writerRepository.save(writer);
        writer.setId(id);

        return new WriterDTO(
                writer.getId(),
                writer.getName(),
                writer.getEmail(),
                writer.getCreatedAt(),
                writer.getModifiedAt()
        );
    }
}
