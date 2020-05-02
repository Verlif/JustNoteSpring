package note.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_type")
public class Type {
    @TableId("id")
    private int id;
    @TableField("type")
    private int type;
    @TableField("desc")
    private String desc;
}
