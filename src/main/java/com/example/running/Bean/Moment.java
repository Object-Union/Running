package com.example.running.Bean;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_moment")
public class Moment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    @Column(name = "user_id")
    private Integer userId;

    private Integer likeNum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime date;

    @ElementCollection
    @CollectionTable(name = "moment_pictures",
            joinColumns = @JoinColumn(name = "moment_id", referencedColumnName = "id")
    )
    @Column(name = "picture_url")
    private List<String> pictures;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "moment_id", referencedColumnName = "id")
    @JsonIgnore
    private List<Comment> comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Moment(String title, String content, Integer userId, Integer likeNum, LocalDateTime date) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.likeNum = likeNum;
        this.date = date;
    }
}
