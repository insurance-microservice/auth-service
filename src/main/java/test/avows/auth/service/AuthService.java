package test.avows.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.avows.auth.dto.TokenResponseDto;
import test.avows.auth.dto.UserLoginDto;
import test.avows.auth.dto.UserRegisterDto;
import test.avows.auth.entity.User;
import test.avows.auth.exception.ApiException;
import test.avows.auth.repository.AuthRepository;
import test.avows.auth.util.JwtUtil;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void registerUser(UserRegisterDto param) {

        if (authRepository.findByUsernameOrEmail(param.getUsername(), param.getEmail()).isPresent()) {
            throw new ApiException(400, "User Exist", "Username or email already exists");
        }

        User user = User.builder()
                .username(param.getUsername())
                .email(param.getEmail())
                .passwordHash(passwordEncoder.encode(param.getPassword()))
                .role("USER")
                .isActive(true)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();

        authRepository.save(user);
    }

    public TokenResponseDto loginUser(UserLoginDto param) {
        User user = authRepository.findByUsernameOrEmail(param.getIdentity(), param.getIdentity())
                .orElseThrow(() -> new ApiException(400, "Invalid Data", "Invalid username or password"));

        if(!user.getIsActive()) {
            throw new ApiException(400, "Inactive User", "User account is inactive");
        }

        if (!passwordEncoder.matches(param.getPassword(), user.getPasswordHash())) {
            throw new ApiException(400, "Invalid credential", "wrong username/email or password");
        }

        String token = jwtUtil.generateToken(user);
        return new TokenResponseDto(token);
    }

}
