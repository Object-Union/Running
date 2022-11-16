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
@Table(name = "user_info")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private Boolean gender;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_like",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Column(name = "moment_id")
    @JsonIgnore
    private List<Integer> likedArticle;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "friend",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Column(name = "friend_id")
    @JsonIgnore
    private List<Integer> friendList;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_medal",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Column(name = "medal_id")
    @JsonIgnore
    private List<Integer> medalList;

//    @OneToMany()
//    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private List<RunRecord> runRecords;
}
