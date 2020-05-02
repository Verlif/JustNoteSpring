package note.controller;

import note.filter.RequestFilter;
import note.model.Note;
import note.model.result.FailResult;
import note.model.result.Result;
import note.model.result.SusResult;
import note.service.NoteService;
import note.utils.JSONGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("note/get/id")
    public Result getNoteById(@RequestBody String body) {
        int id = JSONGet.getValue(body, Integer.class);
        Note note = noteService.getNoteById(id);
        if (note != null) {
            return new SusResult(note);
        } else return new FailResult("未查询到相关笔记本-" + id);
    }

    @PostMapping("note/get/user")
    public Result getUserNotes(@RequestBody String body) {
        int userId = JSONGet.getValue(body, Integer.class);
        List<Note> notes = noteService.getUserNotes(userId);
        return new SusResult("notes", notes);
    }

    @PostMapping("note/get/shared")
    public Result getSharedNotes(
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        return new SusResult("notes", noteService.getSharedNotes(userId));
    }

    @PostMapping("note/save")
    public Result saveNote(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        Note note = JSONGet.getValue(body, Note.class);
        if (note != null) {
            note.setOwnerId(userId);
            note.setCreatorId(userId);
            if (noteService.saveOrModifyNote(note)) {
                return new SusResult(note);
            } else return new FailResult("无法保存笔记本");
        } else return new FailResult("上传数据不完整");
    }

    @PostMapping("note/modify")
    public Result modifyNote(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        Note note = JSONGet.getValue(body, Note.class);
        if (note != null) {
            if (note.getOwnerId() == userId) {
                if (noteService.saveOrModifyNote(note)) {
                    return new SusResult(note);
                } else return new FailResult("无法修改笔记本");
            } else return new FailResult("无法更改其他拥有者的笔记本");
        } else return new FailResult("上传数据不完整");
    }

    @PostMapping("note/delete")
    public Result deleteNote(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        int noteId = JSONGet.getValue(body, Integer.class);
        Note note = noteService.getNoteById(noteId);
        if (note != null) {
            if (note.getOwnerId() == userId) {
                noteService.deleteNoteById(noteId);
                return new SusResult();
            } else return new FailResult("无法删除其他拥有者的笔记本");
        } else return new FailResult("无效的noteId-" + noteId);
    }
}
