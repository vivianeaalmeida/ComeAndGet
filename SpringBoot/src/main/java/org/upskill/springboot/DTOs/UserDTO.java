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

    public String Name;

    public String Email;

    public String PhoneNumber;

    public String Role;
}
