package az.azerenerji.controller;

import az.azerenerji.dto.request.LoginRequestDto;
import az.azerenerji.dto.request.RegisterRequestDto;
import az.azerenerji.dto.response.RefreshTokenResponseDto;
import az.azerenerji.security.jwt.JwtUtil;
import az.azerenerji.service.AuthService;
import az.azerenerji.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private  final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping(path = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody RegisterRequestDto requestDto) {
        authService.signUp(requestDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

//    @GetMapping("/generate-token")
//    public String generateToken() {
//        Map<String, Object> claims = new HashMap<>();
//        return tokenService.generateTokens(claims);

    }

