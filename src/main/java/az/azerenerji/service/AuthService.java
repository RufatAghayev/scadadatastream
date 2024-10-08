package az.azerenerji.service;

import az.azerenerji.dto.request.LoginRequestDto;
import az.azerenerji.dto.request.RegisterRequestDto;
import az.azerenerji.dto.response.JWTAuthResponse;
import az.azerenerji.dto.response.RefreshTokenResponseDto;
import az.azerenerji.exception.*;
import az.azerenerji.model.TokenClaims;
import az.azerenerji.model.User;
import az.azerenerji.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public JWTAuthResponse login(LoginRequestDto dto) {
        User user = userRepository.findByUserName(dto.getUserName()).orElseThrow(UserNameNotFoundException::new);
        boolean isMatches = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (!isMatches) {
            throw new WrongPasswordException();
        }
        String email = dto.getUserName();
        String password = dto.getPassword();
        String uuid = UUID.randomUUID().toString();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenService.generateJwtToken(authentication);
        return new JWTAuthResponse(user.getId(), accessToken, uuid);
    }
    public RefreshTokenResponseDto refreshToken(HttpServletRequest request) {
        RefreshTokenResponseDto refreshResponseDto = new RefreshTokenResponseDto();
        String jwt = tokenService.getJWTFromRequest(request);

        try {
            tokenService.validateAndExtractClaims(jwt); // Tokeni yoxla

        } catch (ExpiredJwtException ex) {
            // Əgər token vaxtı bitibsə, claims-ləri bərpa et
            Claims claims = ex.getClaims();
            String username = claims.get(TokenClaims.SUBJECT.getValue(), String.class);
            List<String> authorities = claims.get("authorities", List.class); // İcazələri JWT-dən çıxar

            // İstifadəçini bazadan tap
            User user = userRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("can not find"));

            // Əgər token müddəti bitibsə, yenisini generasiya et
            if (claims.getExpiration().compareTo(Date.from(Instant.now())) < 0) {
                // İstifadəçi və icazələrlə yeni token yarat
                String token = tokenService.generateTokens(claims);
                refreshResponseDto.setRefreshToken(token);
                return refreshResponseDto;
            } else {
                throw new TokenNotValidException();
            }
        } catch (JwtException ex) {
            throw new TokenNotValidException();
        }

        return refreshResponseDto;

    }
    @Transactional(rollbackFor = Exception.class)
    public void signUp(RegisterRequestDto registerDto) {
        if (userRepository.existsByUserName(registerDto.getUsername())) {
            throw new UserNameAlreadyExistException();
        }

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new ConfirmPasswordNotSameException();
        }


        User user = new User();
        user.setUserName(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));


        userRepository.save(user);
    }
}