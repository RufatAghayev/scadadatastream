package az.azerenerji.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class JWTAuthResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accessToken;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;
    private String tokenType = "Bearer";

    public JWTAuthResponse(Long id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }


}
