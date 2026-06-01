package com.ucr.salud.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;

    @Email(message = "El correo no es válido")
    @NotBlank(message = "El correo no puede estar en blanco")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!?]).{8,}$",
            message = "Mínimo 8 caracteres, al menos una mayúscula, una minúscula, " +
                    "un número y un carácter especial (@#$%^&*!?)")
    private String password;

    public UserDTO() {
    }

    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
