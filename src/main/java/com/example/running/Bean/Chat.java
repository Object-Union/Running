package com.example.running.Bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Component
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime date;

    private Integer senderId;

    private Integer recipientId;

    private String content;
}
