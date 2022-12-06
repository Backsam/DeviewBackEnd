package com.InhaTc.Deview.File.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="file") // 테이블 명을 작성
public class FileEntity {
    @Id // primary key임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fid;

    @Column(nullable = false, unique = true, length = 1000)
    private String filename;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String created_dt;
}