package note.controller;

import note.filter.RequestFilter;
import note.model.Record;
import note.model.result.FailResult;
import note.model.result.Result;
import note.model.result.SusResult;
import note.service.RecordService;
import note.service.ShareService;
import note.utils.JSONGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private ShareService shareService;

    @PostMapping("record/get/id")
    public Result getRecordById(
            @RequestBody String body,
            @RequestAttribute(RequestFilter.USER_ID) int userId
    ) {
        int recordId = JSONGet.getValue(body, Integer.class);
        Record record = recordService.getRecordById(recordId);
        if (record != null) {
            return new SusResult(record);
        } else return new FailResult("无法查询到相关记录");
    }

    @PostMapping("record/get/note")
    public Result getNoteRecords(
            @RequestBody String body,
            @RequestAttribute(RequestFilter.USER_ID) int userId
    ) {
        int noteId = JSONGet.getValue(body, "noteId", Integer.class);
        List<Record> records = recordService.getRecordsByNoteIdOL(noteId, userId);
        return new SusResult("records", records);
    }

    @PostMapping("record/save")
    public Result saveRecord(
            @RequestBody String body,
            @RequestAttribute(RequestFilter.USER_ID) int userId
    ) {
        Record record = JSONGet.getValue(body, Record.class);
        record.setCreatorId(userId);
        if (recordService.saveOrModifyRecord(record)) {
            return new SusResult(record);
        } else return new FailResult("无法保存记录");
    }

    @PostMapping("record/modify")
    public Result modifyRecord(
            @RequestBody String body,
            @RequestAttribute(RequestFilter.USER_ID) int userId
    ) {
        Record record = JSONGet.getValue(body, Record.class);
        if (record != null) {
            if (record.getCreatorId() == userId) {
                if (recordService.saveOrModifyRecord(record)) {
                    return new SusResult(record);
                } else return new FailResult("无法更新记录");
            } else return new FailResult("无法更新其他用户创建的记录");
        } else return new FailResult("上传记录出错");
    }

    @PostMapping("record/delete")
    public Result deleteRecord(
            @RequestBody String body,
            @RequestAttribute(RequestFilter.USER_ID) int userId
    ) {
        int recordId = JSONGet.getValue(body, Integer.class);
        Record record = recordService.getRecordById(recordId);
        if (record != null) {
            if (record.getCreatorId() == userId) {
                recordService.deleteRecordById(recordId);
                return new SusResult(record);
            } else return new FailResult("无法删除其他创建者的记录");
        } else return new FailResult("没有该记录-" + recordId);
    }

}
