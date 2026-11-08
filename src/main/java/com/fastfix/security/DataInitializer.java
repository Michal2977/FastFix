package com.fastfix.security;

import com.fastfix.entity.Role;
import com.fastfix.entity.User;
import com.fastfix.repository.RoleRepository;
import com.fastfix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

   private final RoleRepository roleRepository;
   private final UserRepository userRepository;
   private final BCryptPasswordEncoder passwordEncoder;

   @Value("${app.admin.first-name}")
   private String firstName;
   @Value("${app.admin.last-name}")
   private String lastName;
   @Value("${app.admin.email}")
   private String email;
   @Value("${app.admin.password}")
   private String password;


    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0){
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("MANAGER"));
            roleRepository.save(new Role("TECHNICIAN"));
            roleRepository.save(new Role("RECEPTIONIST"));

        }

        if (userRepository.countByRole_Role("ADMIN") == 0){
            Role role = roleRepository.findByRole("ADMIN");
            User admin = new User();
            admin.setRole(Set.of(role));
            admin.setCreateAt(LocalDateTime.now());
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setEmail(email);

            userRepository.save(admin);
        }
    }
}
