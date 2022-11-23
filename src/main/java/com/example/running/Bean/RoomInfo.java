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
@Table(name = "room_info")
public class RoomInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roomKey;

    private Integer peopleNum;

    private Boolean finish;

    @Column(name = "route_id")
    private Integer routeId;

    @ManyToOne
    @JoinColumn(name = "route_id", updatable = false, insertable = false)
    private DriftRun driftRun;

    public RoomInfo(Integer id, String roomKey, Integer peopleNum, Boolean finish, Integer routeId) {
        this.id = id;
        this.roomKey = roomKey;
        this.peopleNum = peopleNum;
        this.finish = finish;
        this.routeId = routeId;
    }
}
