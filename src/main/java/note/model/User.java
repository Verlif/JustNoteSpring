package note.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import note.model.base.JSONBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor
@TableName("t_user")
public class User extends JSONBuilder implements Serializable {

    @TableId("user_id")
    private int userId;
    @NonNull
    @TableField("user_name")
    private String userName;
    @NonNull
    @TableField("user_key")
    private String userKey;
    @TableField("user_desc")
    private String userDesc;
    @TableField("user_email")
    private String userEmail;
    @TableField("create_time")
    private Timestamp createTime;

    public void setCreateTime(long time) {
        this.createTime = new Timestamp(time);
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
