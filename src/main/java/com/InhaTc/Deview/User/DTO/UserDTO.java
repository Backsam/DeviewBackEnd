package com.InhaTc.Deview.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String token;
    private String userId;
    private String password;
    private String role;
    private String id;
    private long totalPortfolio;
    private long totalView;
    private long totalLike;
}
