package test.avows.auth.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import test.avows.auth.common.ApiResponse;
import test.avows.auth.dto.TokenResponseDto;
import test.avows.auth.dto.UserLoginDto;
import test.avows.auth.dto.UserRegisterDto;
import test.avows.auth.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse registerUser(@RequestBody UserRegisterDto param) {
        authService.registerUser(param);

        return new ApiResponse(
                true,
                "User registered successfully",
                null
        );
    }

    @PostMapping("/login")
    public TokenResponseDto loginUser(@RequestBody UserLoginDto param) {
        return authService.loginUser(param);
    }
}
