package note.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.model.Share;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShareMapper extends BaseMapper<Share> {

    List<Share> getRecordShares(@Param("recordId") int recordId, @Param("userId") int userId);
    List<Share> getNoteShares(@Param("noteId") int noteId, @Param("userId") int userId);
    int deleteShare(@Param("shareId") int shareId, @Param("userId") int userId);
}
