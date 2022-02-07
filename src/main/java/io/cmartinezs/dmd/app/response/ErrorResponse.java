package io.cmartinezs.dmd.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * @author Carlos
 * @version 1.0
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    @NonNull
    private String code;
    @NonNull
    private String message;
    private List<ErrorDetail> fieldErrors;
}
