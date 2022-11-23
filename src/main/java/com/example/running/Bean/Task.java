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
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer target;

    @Column(name = "medal_id")
    private Integer medal_id;

    @OneToOne
    @JoinColumn(name = "medal_id", insertable = false, updatable = false)
    private Medal medal;
}
