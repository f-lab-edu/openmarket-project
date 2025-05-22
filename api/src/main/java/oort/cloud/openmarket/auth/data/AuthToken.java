package oort.cloud.openmarket.auth.data;

public class AuthToken {
    private String accessToken;
    private String refreshToken;

    private AuthToken(){}

    public static AuthToken of(String accessToken, String refreshToken){
        AuthToken authToken = new AuthToken();
        authToken.accessToken = accessToken;
        authToken.refreshToken = refreshToken;
        return authToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
