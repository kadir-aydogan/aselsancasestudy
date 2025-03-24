package tr.com.aselsankadir.casestudy.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.aselsankadir.casestudy.domain.user.Role;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Kullanıcı adı boş olamaz.")
    @Size(min = 3, max = 50, message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır.")
    private String name;

    @NotBlank(message = "Şifre boş olamaz.")
    @Size(min = 6, max = 100, message = "Şifre en az 6 karakter olmalıdır.")
    private String password;

    @NotNull(message = "Kullanıcı rolü seçiniz.")
    private Role role;
}
