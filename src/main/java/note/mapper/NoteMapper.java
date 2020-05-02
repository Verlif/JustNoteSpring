package note.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import note.model.Note;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoteMapper extends BaseMapper<Note> {

    List<Note> getSyncList(@Param("userId") int userId);
    List<Note> getSharedNotes(@Param("userId") int userId);
    int saveNotes(@Param("notes") List<Note> notes, @Param("userId") int userId);
    int updateNotes(@Param("notes") List<Note> notes, @Param("userId") int userId);
}
