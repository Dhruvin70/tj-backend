package com.project.tj.com.project.tj.entities;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PasswordReset {
   private String email;
   private String password;


}
