package com.InhaTc.Deview.User.Entitiy;

import com.InhaTc.Deview.Comment.Entity.Comment;
import com.InhaTc.Deview.Portfolio.Entity.Likes;
import com.InhaTc.Deview.Profile.Entity.Profile;
import com.InhaTc.Deview.User.Constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "userId")})
public class UserEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String uuid;     // 유저에게 부여되는 고유 id

    @Column(nullable = false)
    private String userId;   // 유저가 로그인시 사용하는 id

    @Column(nullable = false)
    private String password;  // 비밀번호

    @Enumerated(EnumType.STRING)
    private UserRole role;      // 일반사용자, 기업

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Likes> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    //private String authProvider; // OAuth 정보제공자
}
