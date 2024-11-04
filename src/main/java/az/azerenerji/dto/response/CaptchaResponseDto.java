package az.azerenerji.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaptchaResponseDto {
    String userInput;
    String captchaText;

    public CaptchaResponseDto(String userInput, String captchaText) {
        this.userInput = userInput;
        this.captchaText = captchaText;
    }


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
