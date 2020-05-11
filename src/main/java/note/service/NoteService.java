package note.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import note.mapper.NoteMapper;
import note.model.Note;
import note.utils.ConsoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    public Note getNoteByIdOL(int noteIdOL) {
        return noteMapper.selectById(noteIdOL);
    }

    public Note getNoteById(int noteId, int userId) {
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        wrapper.eq("note_id", noteId);
        wrapper.eq("owner_id", userId);
        return noteMapper.selectOne(wrapper);
    }

    /**
     * 获取用户拥有的所有笔记本
     * @param userId    用户id
     * @return  查询结果列表
     */
    public List<Note> getUserNotes(int userId) {
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        wrapper.eq("owner_id", userId);
        return noteMapper.selectList(wrapper);
    }

    public List<Note> getSyncList(int userId) {
        return noteMapper.getSyncList(userId);
    }

    public List<Note> getNotes(List<Integer> noteIds) {
        if (noteIds.size() > 0) {
            return noteMapper.selectBatchIds(noteIds);
        } else return new ArrayList<>();
    }

    public List<Note> getSharedNotes(int userId) {
        return noteMapper.getSharedNotes(userId);
    }

    public boolean saveOrModifyNote(Note note) {
        note.setUpdateTime(new Date().getTime());
        if (noteMapper.selectById(note.getNoteIdOL()) == null) {
            note.setCreateTime(new Date().getTime());
            return noteMapper.insert(note) == 1;
        } else {
            return noteMapper.updateById(note) == 1;
        }
    }

    public boolean saveNotes(List<Note> notes, int userId) {
        if (notes.size() > 0) {
            noteMapper.saveNotes(notes, userId);
            return true;
        } else return false;
    }

    public boolean updateNotes(List<Note> notes, int userId) {
        if (notes.size() > 0) {
            noteMapper.updateNotes(notes, userId);
            return true;
        } else return false;
    }

    public void deleteNoteByIdOL(int noteIdOL) {
        noteMapper.deleteById(noteIdOL);
    }

    public boolean deleteNoteById(int noteId, int userId) {
        UpdateWrapper<Note> wrapper = new UpdateWrapper<>();
        wrapper.eq("note_id", noteId);
        wrapper.eq("owner_id", userId);
        return noteMapper.delete(wrapper) == 1;
    }
}
