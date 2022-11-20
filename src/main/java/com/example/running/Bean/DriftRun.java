package com.example.running.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "run_drift_route")
public class DriftRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String picture;

    private String route;

    private String bgm;

    private Boolean onActivity;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "drift_run_id")
    @JsonIgnore
    private List<Scenery> sceneries;
}
