package com.project.tj.com.project.tj.entities;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtResponse {
    private String username;
    private String jwtToken;
}
