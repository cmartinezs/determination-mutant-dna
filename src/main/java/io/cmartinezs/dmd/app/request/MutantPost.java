package io.cmartinezs.dmd.app.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * @author Carlos
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MutantPost {
    @NotEmpty
    private String[] dna;
}
