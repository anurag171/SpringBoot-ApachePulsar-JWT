package com.anurag.iot.data.api.model;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  AuthRequest {
    @Parameter(name = "UserName" , required = true)
    private String username;
    @Parameter(name = "Password" , required = true)
    private String password;
}