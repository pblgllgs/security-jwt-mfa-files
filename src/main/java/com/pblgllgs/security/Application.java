package com.pblgllgs.security;

import com.pblgllgs.security.domain.RequestContext;
import com.pblgllgs.security.entity.RoleEntity;
import com.pblgllgs.security.enums.Authority;
import com.pblgllgs.security.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository){
        return args -> {
            RequestContext.setUserId(0L);

            if ((long) roleRepository.findAll().size() == 0){
                var userRole = new RoleEntity();
                userRole.setName(Authority.USER.name());
                userRole.setAuthorities(Authority.USER);

                var adminRole = new RoleEntity();
                adminRole.setName(Authority.ADMIN.name());
                adminRole.setAuthorities(Authority.ADMIN);

                Iterable<RoleEntity> roles = List.of(userRole,adminRole);
                roleRepository.saveAll(roles);
            }

            RequestContext.start();
        };
    }

}