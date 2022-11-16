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
}
