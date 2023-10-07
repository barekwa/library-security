package com.example.project.dto.user;

import lombok.*;

import javax.validation.constraints.NotEmpty;
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
@Builder
public class UserLoginDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;
}
