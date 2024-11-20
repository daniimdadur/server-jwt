package spring.security.practice.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.security.practice.auth.model.entity.RoleEntity;
import spring.security.practice.auth.model.entity.TokenEntity;
import spring.security.practice.auth.model.entity.TokenType;
import spring.security.practice.auth.model.entity.UserEntity;
import spring.security.practice.auth.model.req.AuthenticationReq;
import spring.security.practice.auth.model.req.RegistrationReq;
import spring.security.practice.auth.model.res.AuthenticationRes;
import spring.security.practice.auth.repo.RoleRepo;
import spring.security.practice.auth.repo.TokenRepo;
import spring.security.practice.auth.repo.UserRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final TokenRepo tokenRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationRes register(RegistrationReq register) {
        this.userRepo.findByEmail(register.getEmail()).ifPresent(duplicate -> {
            throw new IllegalArgumentException("Email already exists");
        });

        var user = UserEntity.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .build();
        List<RoleEntity> roles = this.roleRepo.findByNameIn(register.getRoles());
        if (roles.isEmpty()) {
            roles = this.saveRoles(register.getRoles());
        }

        user.setRoles(roles);

        var saveUser = this.userRepo.save(user);
        var jwtToken = this.jwtService.generateToken(user);
        var refreshToken = this.jwtService.generateRefreshToken(user);
        var expiredToken = this.jwtService.expiredAt(jwtToken);

        saveUserToken(saveUser, jwtToken);
        return AuthenticationRes.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .expiredAt(expiredToken)
                .build();
    }

    @Override
    public AuthenticationRes authenticate(AuthenticationReq authenticationReq) {
        var user = this.userRepo.findByEmail(authenticationReq.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email is not found"));

        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationReq.getEmail(),
                        authenticationReq.getPassword()
                )
        );

        var jwtToken = this.jwtService.generateToken(user);
        var refreshToken = this.jwtService.generateRefreshToken(user);
        var expiredToken = this.jwtService.expiredAt(jwtToken);

        revokedAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationRes.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .expiredAt(expiredToken)
                .build();
    }

    private List<RoleEntity> saveRoles(List<String> roles) {

        List<RoleEntity> roleEntities = new ArrayList<>();
        if (roles.isEmpty()) {
            roleEntities.add(new RoleEntity("USER"));
        } else {
            for (String role : roles) {
                roleEntities.add(new RoleEntity(role));
            }
        }
        try {
            this.roleRepo.saveAll(roleEntities);
            log.info("roles saved");
            return roleEntities;
        } catch (Exception e) {
            log.warn("roles not saved");
            return Collections.emptyList();
        }
    }

    private void saveUserToken(UserEntity user, String jwt) {
        var token = TokenEntity.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        this.tokenRepo.save(token);
    }

    private void revokedAllUserTokens(UserEntity user) {
        var validUserTokens = this.tokenRepo.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        this.tokenRepo.saveAll(validUserTokens);
    }
}
