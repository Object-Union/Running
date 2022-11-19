package com.example.running.Bean;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Component
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "activity_participate")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private Boolean finish;

    @OneToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    public Participant(Integer id, Integer userId, Boolean finish) {
        this.id = id;
        this.userId = userId;
        this.finish = finish;
    }
}
