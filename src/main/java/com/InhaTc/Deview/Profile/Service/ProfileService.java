package com.InhaTc.Deview.Profile.Service;

import com.InhaTc.Deview.Profile.DTO.ProfileDTO;
import com.InhaTc.Deview.Profile.Entity.Profile;
import com.InhaTc.Deview.Profile.Repository.ProfileRepository;
import com.InhaTc.Deview.User.Entitiy.UserEntity;
import com.InhaTc.Deview.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;

    public void createProfile(UserEntity user){
        if(user != null){
            Profile profile = Profile.builder()
                    .introduce("")
                    .field("")
                    .techStack("")
                    .website("")
                    .user(user)
                    .build();
            profileRepository.save(profile);
        }
    }

    public Profile getProfile(String userId){
        UserEntity user = userRepository.findByUserId(userId);
        if(user != null) {
            return profileRepository.findByUser(user);
        }else{
            throw new RuntimeException("프로필 정보가 없습니다.");
        }
    }

    public Profile updateProfile(ProfileDTO dto, String userId){
        UserEntity user = userRepository.findByUserId(userId);
        if(user != null){
        Profile profile = profileRepository.findByUser(user);
            if(profile != null){
                profile.setIntroduce(dto.getIntroduce());
                profile.setField(dto.getField());
                profile.setTechStack(dto.getTechStack());
                profile.setWebsite(dto.getWebsite());
                return profileRepository.save(profile);
            }else{
                throw new RuntimeException("프로필 정보가 없습니다.");
            }
        }else {
            throw new RuntimeException("유저 정보가 없습니다.");
        }
    }
}
