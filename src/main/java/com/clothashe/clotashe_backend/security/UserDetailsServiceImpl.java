package com.clothashe.clotashe_backend.security;

import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import com.clothashe.clotashe_backend.repository.auth.AuthInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AuthInfoRepository authInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthInfoEntity authInfo = authInfoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return authInfo;
    }
}
