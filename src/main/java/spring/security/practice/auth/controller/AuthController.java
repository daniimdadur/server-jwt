package spring.security.practice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.security.practice.auth.model.req.AuthenticationReq;
import spring.security.practice.auth.model.req.RegistrationReq;
import spring.security.practice.auth.model.res.AuthenticationRes;
import spring.security.practice.auth.service.AuthenticationService;
import spring.security.practice.base.BaseController;
import spring.security.practice.base.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController extends BaseController<AuthenticationRes> {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegistrationReq req) {
        var result = this.authenticationService.register(req);
        return getResponse(result);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody AuthenticationReq req) {
        var result = this.authenticationService.authenticate(req);
        return getResponse(result);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
