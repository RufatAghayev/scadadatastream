package az.azerenerji.controller;

import az.azerenerji.dto.request.CaptchaValidationRequest;
import az.azerenerji.dto.response.CaptchaResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/captcha")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class CapcthaController {

    @GetMapping
    public ResponseEntity<CaptchaResponse> generateCaptcha() {

        String captchaText = generateRandomText(6);

        CaptchaResponse captchaResponse = new CaptchaResponse(captchaText);
        return ResponseEntity.ok(captchaResponse);
    }

    private String generateRandomText(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            captcha.append(characters.charAt(random.nextInt(characters.length())));
        }
        return captcha.toString();
    }

    @PostMapping("/validate")
    public ResponseEntity<CaptchaResponseDto> validateCaptcha(@RequestBody CaptchaValidationRequest request) {
        if (request.getUserInput().equals(request.getCaptchaText())) {
            CaptchaResponseDto response = new CaptchaResponseDto(request.getUserInput(), request.getCaptchaText());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(new CaptchaResponseDto(request.getUserInput(), request.getCaptchaText()));
        }
    }

    public static class CaptchaResponse {
        private String captchaText;

        public CaptchaResponse(String captchaText) {
            this.captchaText = captchaText;
        }

        public String getCaptchaText() {
            return captchaText;
        }

        public void setCaptchaText(String captchaText) {
            this.captchaText = captchaText;
        }
    }
}
