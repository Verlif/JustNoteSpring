package note.controller;

import note.filter.RequestFilter;
import note.model.Note;
import note.model.Record;
import note.model.result.Result;
import note.model.result.SusResult;
import note.service.NoteService;
import note.service.RecordService;
import note.utils.ConsoleUtil;
import note.utils.JSONGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SyncController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private RecordService recordService;

    /**
     * 获取服务器中用户所有的笔记本与记录更新信息
     * @param userId
     * @return
     */
    @PostMapping("sync/serverList")
    public Result getServerList(@RequestAttribute(name = RequestFilter.USER_ID) int userId) {
        List<Note> notes = noteService.getSyncList(userId);
        List<Record> records = recordService.getSyncList(userId);
        Result result = new SusResult();
        result.addList("notes", notes);
        result.addList("records", records);
        return result;
    }

    @PostMapping("sync/upload")
    public Result uploadList(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {

        ConsoleUtil.println(body);

        ArrayList<Note> notesUpdate = JSONGet.getList(body, "notes.update", Note.class);
        ArrayList<Note> notesInsert = JSONGet.getList(body, "notes.insert", Note.class);
        ArrayList<Record> recordsUpdate = JSONGet.getList(body, "records.update", Record.class);
        ArrayList<Record> recordsInsert = JSONGet.getList(body, "records.insert", Record.class);

        noteService.saveNotes(notesInsert, userId);
        noteService.updateNotes(notesUpdate, userId);
        recordService.saveRecords(recordsInsert, userId);
        recordService.updateRecords(recordsUpdate, userId);

        return new SusResult();
    }

    @PostMapping("sync/download")
    public Result downloadList(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        ConsoleUtil.println(body);

        ArrayList<Integer> noteIds = JSONGet.getList(body, "noteIds", Integer.class);
        ArrayList<Integer> recordIds = JSONGet.getList(body, "recordIds", Integer.class);

        ConsoleUtil.println(noteIds);
        ConsoleUtil.println(recordIds);

        Result result = new SusResult();
        result.addList("notes", noteService.getNotes(noteIds));
        result.addList("records", recordService.getRecords(recordIds));

        return result;
    }
}
