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
@Table(name = "run_record")
public class RunRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private Integer duration;

    private Float mileage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime date;

    private Float calorie;

    private Float speed;

    private String route;

    private String runType;

    private Integer roomId;

    @Column(name = "drift_route_id")
    private Integer driftRouteId;

    @OneToOne()
    @JoinColumn(name = "drift_route_id", insertable = false, updatable = false)
    private DriftRun driftRun;
}
