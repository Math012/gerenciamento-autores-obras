package com.math012.autoresobras.business.DTO.request;

public class LoginRequestDTOFixture {
    public static LoginRequestDTO build(String username,String password){
        return new LoginRequestDTO(username,password);
    }
}