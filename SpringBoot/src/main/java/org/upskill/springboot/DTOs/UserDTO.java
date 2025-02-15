package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO extends RepresentationModel<UserDTO> {

    public String id;

    public String name;

    public String email;

    public String phoneNumber;

    public String role;
}
