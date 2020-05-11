package note.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.model.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    List<Record> getSyncList(@Param("userId") int userId);

    /**
     * 通过本地的noteId获取相关的所有记录。
     * @param noteIdOL    记录对应的本地noteId。
     * @param userId    查询者id
     * @return  获取的记录列表。
     */
    List<Record> getRecordsByNoteIdOL(@Param("noteIdOL") int noteIdOL, @Param("userId") int userId);
    int saveRecords(@Param("records") List<Record> records, @Param("userId") int userId);
    int updateRecord(@Param("param") Record record, @Param("userId") int userId);
    int updateRecords(@Param("records") List<Record> records, @Param("userId") int userId);
}
