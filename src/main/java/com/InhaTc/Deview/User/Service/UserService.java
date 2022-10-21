package com.InhaTc.Deview.User.Service;

import com.InhaTc.Deview.User.Entitiy.UserEntity;
import com.InhaTc.Deview.User.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String username, final String password){
        return userRepository.findByUserIdAndPassword(username, password);
    }
}
