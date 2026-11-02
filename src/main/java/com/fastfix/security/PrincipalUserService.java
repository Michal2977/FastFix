package com.fastfix.security;

import com.fastfix.entity.User;
import com.fastfix.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public PrincipalUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid Data Or Email Is Not Confirmed");
        }
        return new PrincipalUser(user);
    }
}
