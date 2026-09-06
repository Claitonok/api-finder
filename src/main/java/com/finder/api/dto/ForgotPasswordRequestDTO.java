package com.finder.api.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ForgotPasswordRequestDTO {

    @Column(nullable = false, unique = true)
    private String email;

}
