package nefu.graduation_async.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Score {
    @CreatedBy
    @Id
    private String id;//自己生成
    private String studentId;
    private String processId;
    private int group;
    private String teacher;
    private String processName;
    private String detail;
}