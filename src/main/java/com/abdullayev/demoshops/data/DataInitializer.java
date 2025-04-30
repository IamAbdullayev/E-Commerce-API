package com.abdullayev.demoshops.data;

import com.abdullayev.demoshops.models.User;
import com.abdullayev.demoshops.repositories.UserRepository;
import com.abdullayev.demoshops.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExist();
    }

    private void createDefaultUserIfNotExist() {
        for (int i = 1; i <= 5; ++i) {
            String defaultEmail = "user" + i + "@gmail.com";

            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("User Name " + i);
            user.setLastName("Usr Surname " + i);
            user.setEmail(defaultEmail);
            user.setPassword("12345");
            userRepository.save(user);

            System.out.println("Default vet user " + i + " crated successfully.");
        }
    }


}
