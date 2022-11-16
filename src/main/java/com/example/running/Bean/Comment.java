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
@Table(name = "user_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "moment_id")
    private Integer momentId;

    @Column(name = "user_id")
    private Integer userId;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Comment(Integer momentId, Integer userId, String comment, LocalDateTime date) {
        this.momentId = momentId;
        this.userId = userId;
        this.comment = comment;
        this.date = date;
    }
}
