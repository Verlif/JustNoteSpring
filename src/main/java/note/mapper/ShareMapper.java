package note.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.model.Share;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShareMapper extends BaseMapper<Share> {

    /**
     * 获取record的共享列表
     * @param recordIdOL  record的在线id
     * @param userId    record的创建者id
     * @return  共享列表
     */
    List<Share> getRecordShares(@Param("recordId") int recordIdOL, @Param("userId") int userId);
    List<Share> getNoteShares(@Param("noteId") int noteIdOL, @Param("userId") int userId);
    int deleteShare(@Param("shareId") int shareId, @Param("userId") int userId);
}
