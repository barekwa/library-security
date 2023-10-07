package com.example.project.dto.user;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class UserEditDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String lastname;

    @NotEmpty
    private String password;

    @NotEmpty
    private Long roleId;
}
