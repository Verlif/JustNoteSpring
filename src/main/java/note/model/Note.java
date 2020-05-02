package note.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import note.model.base.JSONBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_note")
public class Note extends JSONBuilder implements Serializable {

    @TableField("note_id")
    private int noteId;     // 笔记本Id
    @TableId("note_id_online")
    private int noteIdOL;   // 笔记本在线id
    @TableField("note_title")
    private String noteTitle;   // 笔记本标题
    @TableField("note_desc")
    private String noteDesc;    // 笔记本描述
    @TableField("creator_id")
    private int creatorId;      // 笔记本创建者id
    @TableField("owner_id")
    private int ownerId;        // 笔记本拥有者id
    @TableField("create_time")
    private Timestamp createTime;   // 笔记本创建时间
    @TableField("update_time")
    private Timestamp updateTime;   // 笔记本更新时间

    public void setCreateTime(long time) {
        this.createTime = new Timestamp(time);
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(long time) {
        this.updateTime = new Timestamp(time);
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 为了兼容listView，修改toString
     * @return  笔记本的标题
     */
    public String toString() {
        return noteTitle;
    }

}
