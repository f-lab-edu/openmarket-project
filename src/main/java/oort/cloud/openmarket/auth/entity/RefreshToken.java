package oort.cloud.openmarket.auth.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    protected RefreshToken(){}

    public static RefreshToken createRefreshToken(Long userId, String token, LocalDateTime expiredAt){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.userId = userId;
        refreshToken.token = token;
        refreshToken.expiredAt = expiredAt;
        return refreshToken;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Long getTokenId(){
        return tokenId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return tokenId.equals(that.tokenId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId, userId);
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                ", expiredAt=" + expiredAt +
                '}';
    }
}
