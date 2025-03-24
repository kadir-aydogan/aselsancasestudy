package tr.com.aselsankadir.casestudy.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.aselsankadir.casestudy.domain.common.AuthenticationUseCase;
import tr.com.aselsankadir.casestudy.domain.common.Email;
import tr.com.aselsankadir.casestudy.domain.user.JwtToken;
import tr.com.aselsankadir.casestudy.presentation.dto.LoginRequest;
import tr.com.aselsankadir.casestudy.presentation.dto.RegisterRequest;
import tr.com.aselsankadir.casestudy.presentation.dto.ResponseBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Kayıt Olma ve Giriş Yapma", description = "Kayıt olup, giriş yapabilirsiniz.")
public class AuthenticationController {

    private final AuthenticationUseCase authenticationUseCase;


    @PostMapping("/register")
    @Operation(
            summary = "Kayıt Ol"
    )
    public ResponseEntity<ResponseBody<Void>> registerCustomer(@RequestBody @Valid RegisterRequest request) {
        authenticationUseCase.register(new Email(request.getName()), request.getPassword(), request.getRole());
        return ResponseEntity.ok(ResponseBody.success());
    }

    @PostMapping("/login")
    @Operation(
            summary = "Giriş Yap"
    )
    public ResponseEntity<ResponseBody<JwtToken>> login(@RequestBody @Valid LoginRequest request) {
        final JwtToken response = authenticationUseCase.login(new Email(request.getName()), request.getPassword());
        //TODO MAPPER
        return ResponseEntity.ok(ResponseBody.success(response));
    }
}
