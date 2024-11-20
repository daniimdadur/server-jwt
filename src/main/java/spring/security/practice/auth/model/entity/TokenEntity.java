package spring.security.practice.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import spring.security.practice.util.CommonUtil;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_token")
public class TokenEntity {

    @Id
    @Column(name = "tid", length = 36)
    public String id;

    @Column(name = "token", unique = true)
    public String token;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    public TokenType tokenType = TokenType.BEARER;

    @Column(name = "revoked")
    public boolean revoked;

    @Column(name = "expired")
    public boolean expired;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity user;

    @PrePersist
    private void generateId() {
        if (this.id == null) {
            this.id = CommonUtil.getUUID();
        }
    }
}
