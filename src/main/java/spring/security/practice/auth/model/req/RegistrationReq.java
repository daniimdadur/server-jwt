package spring.security.practice.auth.model.req;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationReq {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;
}
