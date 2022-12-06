package com.InhaTc.Deview.Profile.Repository;

import com.InhaTc.Deview.Profile.Entity.Profile;
import com.InhaTc.Deview.User.Entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser(UserEntity user);
}
