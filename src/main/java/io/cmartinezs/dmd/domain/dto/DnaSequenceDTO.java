package io.cmartinezs.dmd.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Carlos
 * @version 1.0
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DnaSequenceDTO {
    private String sequence;
    private LocalDateTime createdAt;
    private String md5;
    private boolean mutant;
}
