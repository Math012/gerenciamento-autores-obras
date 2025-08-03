package com.math012.autoresobras.business.DTO.response;

import java.util.List;

public class LoginResponseDTOFixture {
    public static LoginResponseDTO build(Long id, String username, String password, List<String> roles) {
        return new LoginResponseDTO(id, username, password, roles);
    }
}