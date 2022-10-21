package com.InhaTc.Deview.User.Entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    private String role;      // 일반사용자, 기업

    //private String authProvider; // OAuth 정보제공자
}
