package com.example.running.Bean;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@Component
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "run_room")
public class RunRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer roomId;

    @Column(name = "user_id")
    private Integer userId;

    private BigDecimal x;

    private BigDecimal y;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public RunRoom(Integer id, Integer roomId, Integer userId) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
    }
}
