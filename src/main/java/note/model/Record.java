package note.model;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import note.model.base.JSONBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_record")
public class Record extends JSONBuilder implements Serializable {

    @TableField("record_id")
    private int recordId;   // 记录的id
    @TableId(value = "record_id_online", type = IdType.AUTO)
    private int recordIdOL;     // 记录在线id
    @TableField("record_title")
    private String recordTitle;     // 记录标题
    @TableField("record_content")
    private String recordContent;   // 记录内容
    @TableField("note_id")
    private int noteId;         // 所属笔记id
    @TableField("creator_id")
    private int creatorId;        // 记录创建者id
    @TableField("create_time")
    private Timestamp createTime;       // 记录创建时间
    @TableField("update_time")
    private Timestamp updateTime;       // 记录更新时间

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
     * @return  笔记的标题
     */
    public String toString() {
        return recordTitle;
    }
}
