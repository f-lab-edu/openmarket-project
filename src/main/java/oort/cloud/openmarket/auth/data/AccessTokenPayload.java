package oort.cloud.openmarket.auth.data;

import oort.cloud.openmarket.user.enums.UserRole;

public class AccessTokenPayload {
    private Long userId;
    private UserRole userRole;

    public static AccessTokenPayload of(Long userId, UserRole role){
        AccessTokenPayload accessTokenPayload = new AccessTokenPayload();
        accessTokenPayload.userId = userId;
        accessTokenPayload.userRole = role;
        return accessTokenPayload;
    }

    public Long getUserId() {
        return userId;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
