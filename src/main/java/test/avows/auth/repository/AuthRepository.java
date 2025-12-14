package test.avows.auth.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import test.avows.auth.entity.User;

import java.util.Optional;


public interface AuthRepository extends JpaRepository<@NonNull User, @NonNull Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
}
