package com.InhaTc.Deview.Security;

import com.InhaTc.Deview.User.Entitiy.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class EmptyUser extends UserEntity {
    public EmptyUser(){super();}
}
