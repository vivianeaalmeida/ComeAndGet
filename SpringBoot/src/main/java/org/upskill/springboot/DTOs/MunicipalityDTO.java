package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MunicipalityDTO {
    /**
     * The unique identifier for the municipality.
     */
    private String id;

    /**
     * The designation of a municipality.
     */
    private String designation;
}
