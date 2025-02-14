package org.upskill.springboot.Models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "user_temp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @NonNull
    @Min(5)
    @Max(100)
    public String Name;

    @Min(5)
    @Max(60)
    @NonNull
    public String Email;

    @NonNull
    public String PhoneNumber;

    @NonNull
    public String Role;
}
