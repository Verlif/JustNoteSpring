package note.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import note.model.base.JSONBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_share")
public class Share extends JSONBuilder implements Serializable {

    /**
     * 可读权限
     */
    public static final int TYPE_READ = 1001;
    /**
     * 可读可写权限
     */
    public static final int TYPE_WRITE = 1002;

    @TableId("share_id")
    private int shareId;    // 共享id
    @TableField("note_id")
    private int noteId;     // 共享的笔记本id
    @TableField("share_type")
    private int shareType;  // 共享方式
    @TableField(exist = false)
    private String shareDesc;    // 共享说明
    @TableField("user_list")
    private ArrayList<Integer> userList;    // 共享用户列表
    @TableField(exist = false)
    private String userListString;
    @TableField("create_time")
    private Timestamp createTime;   // 创建时间

    public String getUserList() {
        return userListString;
    }

    public void setUserList(ArrayList<Integer> userList) {
        this.userList = userList;
        userListString = Arrays.toString(userList.toArray());
    }

    public void setUserList(String string) {
        setUserListString(string);
    }

    public void setUserListString(String userListString) {
        this.userListString = userListString;
        String[] list = userListString.replace("[", "").replace("]", "").replace(" ", "").split(",");
        userList = new ArrayList<>();
        for (String s : list) {
            userList.add(Integer.valueOf(s));
        }
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setCreateTime(long time) {
        this.createTime = new Timestamp(time);
    }

}
