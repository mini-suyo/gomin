package com.ssafy.sushi.global.infra.fcm;


import com.ssafy.sushi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fcm_tokens",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_fcm_tokens_user_token",
                        columnNames = {"user_id", "token"}
                )
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String token;

    public static FcmToken create(User user, String token) {
        FcmToken fcmToken = new FcmToken();
        fcmToken.user = user;
        fcmToken.token = token;
        return fcmToken;
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
