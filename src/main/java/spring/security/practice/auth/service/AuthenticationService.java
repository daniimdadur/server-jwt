package spring.security.practice.auth.service;

import spring.security.practice.auth.model.req.AuthenticationReq;
import spring.security.practice.auth.model.req.RegistrationReq;
import spring.security.practice.auth.model.res.AuthenticationRes;

public interface AuthenticationService {
    AuthenticationRes register(RegistrationReq register);
    AuthenticationRes authenticate(AuthenticationReq authenticationReq);
}
