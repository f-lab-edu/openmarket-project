package oort.cloud.openmarket.auth.controller;

import jakarta.validation.Valid;
import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/v1/auth/sign-up")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignUpRequest request){
        authService.signUp(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
