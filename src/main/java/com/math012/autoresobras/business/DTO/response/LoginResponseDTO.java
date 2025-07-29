package com.math012.autoresobras.business.DTO.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class LoginResponseDTO {
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
}
