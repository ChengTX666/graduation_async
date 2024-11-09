package nefu.graduation_async.exception;
import lombok.*;
import nefu.graduation_async.dto.Code;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XException extends RuntimeException{
    private Code code;
    private int codeN;
    private String message;
}
