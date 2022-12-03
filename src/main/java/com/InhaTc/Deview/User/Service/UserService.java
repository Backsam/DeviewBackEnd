package com.InhaTc.Deview.User.Service;

import com.InhaTc.Deview.Profile.Service.ProfileService;
import com.InhaTc.Deview.User.Entitiy.UserEntity;
import com.InhaTc.Deview.User.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileService profileService;

    //회원가입
    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null ||userEntity.getUserId() == null){
            throw new RuntimeException("Invalid arguments");
        }
        final String userId = userEntity.getUserId();
        if(userRepository.existsByUserId(userId)){
            log.warn("Username already exists [}", userId);
            throw new RuntimeException("Username already exist");
        }

        UserEntity user = userRepository.save(userEntity);
        profileService.createProfile(user);
        return user;

    }

    //로그인
    public UserEntity getByCredentials(final String userId, final String password, final PasswordEncoder encoder){
        final UserEntity originalUser = userRepository.findByUserId(userId);

        if(originalUser != null && encoder.matches(password, originalUser.getPassword())){
            return originalUser;
        }
        return null;
    }
}
