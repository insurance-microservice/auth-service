package test.avows.auth.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import test.avows.auth.entity.User;
import test.avows.auth.repository.AuthRepository;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class UserAdminSeeder implements CommandLineRunner {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String username;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.password}")
    private String password;


    @Override
    public void run(String... args) throws Exception {
        if (authRepository.findByUsernameOrEmail(username, email).isEmpty()) {
            User adminUser = User.builder()
                    .username(username)
                    .email(email)
                    .passwordHash(passwordEncoder.encode(password))
                    .role("ADMIN")
                    .isActive(true)
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build();

            authRepository.save(adminUser);
            System.out.println("Admin user created: " + username);
        } else {
            System.out.println("Admin user already exists: " + username);
        }
    }
}
