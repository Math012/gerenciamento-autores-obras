package com.math012.autoresobras.business.DTO.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class LoginRequestDTO {
    private String username;
    private String password;
}
