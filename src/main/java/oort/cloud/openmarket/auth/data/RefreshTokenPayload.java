package oort.cloud.openmarket.auth.data;

import oort.cloud.openmarket.user.enums.UserRole;

public class RefreshTokenPayload {
    private Long userId;

    public static RefreshTokenPayload of(Long userId){
        RefreshTokenPayload refreshTokenPayload = new RefreshTokenPayload();
        refreshTokenPayload.userId = userId;
        return refreshTokenPayload;
    }

    public Long getUserId() {
        return userId;
    }

}
