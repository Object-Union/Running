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
@Table(name = "drift_route_scenery")
public class Scenery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "drift_run_id")
    private Integer driftRunId;

    private String name;

    private String brief;

    private String picture;

    private String position;
}
