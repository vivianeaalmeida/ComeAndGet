package org.upskill.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Municipality. It contains the designation
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MunicipalityDTO {
    /**
     * The designation of a municipality.
     */
    private String designation;
}
