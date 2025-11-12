package com.springApp.config;

import com.springApp.entity.RolEntity;
import com.springApp.entity.UserEntity;
import com.springApp.entity.states.UserState;
import com.springApp.repositories.RolRepository;
import com.springApp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UserRepository userRepository;





    @Override
    public void run(String... args) throws Exception {
        /*========================
            -----Roles------
         ========================
        * */

        if (rolRepository.count() == 0) {
            logger.info("🔄 Creating  Roles...");

            rolRepository.save(new RolEntity("admin"));
            rolRepository.save(new RolEntity("teacher"));
            rolRepository.save(new RolEntity("student"));
            rolRepository.save(new RolEntity("staff"));
            rolRepository.save(new RolEntity("finance"));

            logger.info("✅ Roles Created Successfully::::Mysql");
            logger.info("📊 Total roles: " + rolRepository.count());
        } else {
            logger.info("ℹ️ DB already contains Roles. Total roles: " + rolRepository.count());
        }

        /*========================
            -----Student------
         ========================
        * */

        if (userRepository.count() == 0) {
            logger.info("🔄 Creating  Users...");

            userRepository.save( new UserEntity(
                    "Alfonso",
                    passwordEncoder.encode("admin"),
                    "admin.rivera@gmail.com",
                    UserState.ACTIVE,
                    new RolEntity(1L,"admin")
            ));

            userRepository.save( new UserEntity(
                    "Elida",
                    passwordEncoder.encode("elida"),
                    "elida.estefany@gmail.com",
                    UserState.ACTIVE,
                    new RolEntity(2L, "teacher")
            ));

            userRepository.save( new UserEntity(
                    "Kaisy",
                    passwordEncoder.encode("kaisy"),
                    "kaisy.ramos@gmail.com",
                    UserState.ACTIVE,
                    new RolEntity(3L, "student")
            )); 

            logger.info("✅ Roles Created Successfully::::Mysql");
            logger.info("📊 Total roles: " + userRepository.count());
        } else {
            logger.info("ℹ️ DB already contains Users. Total users: " + userRepository.count());
        }
    }
}
