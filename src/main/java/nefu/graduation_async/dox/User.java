package nefu.graduation_async.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;


import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("user")
public class User {
    public static final String ROLE_STUDENT = "qpCf";
    public static final String ROLE_TEACHER = "kU4T";
    public static final String ROLE_ADMIN = "R2md";
    @Id
    @CreatedBy
    private String id;
    private String name;
    private String account;
    private String password;
    private String role;
    private String department;
    private int group;
    private String teacher;
    private String student;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;
}
