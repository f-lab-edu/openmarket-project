package oort.cloud.openmarket.auth.service;

import oort.cloud.openmarket.auth.data.AuthToken;
import oort.cloud.openmarket.user.data.UserDto;

public interface TokenService {
    String refreshAccessToken(String refreshToken);

    AuthToken createAuthToken(UserDto user);

    void logout(String refreshToken);
}
