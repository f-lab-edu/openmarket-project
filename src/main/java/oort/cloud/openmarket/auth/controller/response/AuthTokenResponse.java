package oort.cloud.openmarket.auth.controller.response;

public class AuthTokenResponse {
    private String accessToken;
    private String refreshToken;

    private AuthTokenResponse(){}

    public static AuthTokenResponse of(String accessToken, String refreshToken){
        AuthTokenResponse authToken = new AuthTokenResponse();
        authToken.accessToken = accessToken;
        authToken.refreshToken = refreshToken;
        return authToken;
    }

    public static AuthTokenResponse ofAccessTokenOnly(String accessToken){
        AuthTokenResponse authToken = new AuthTokenResponse();
        authToken.accessToken = accessToken;
        return authToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
