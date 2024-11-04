package az.azerenerji.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CaptchaValidationRequest {
    @NotBlank
    private String userInput;

    @NotBlank
    private String captchaText;


    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }
}