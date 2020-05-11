package note.controller;

import note.filter.RequestFilter;
import note.model.Note;
import note.model.Share;
import note.model.result.FailResult;
import note.model.result.Result;
import note.model.result.SusResult;
import note.service.NoteService;
import note.service.ShareService;
import note.utils.JSONGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShareController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private ShareService shareService;

    @RequestMapping("share/save")
    public Result addShare(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        Share share = JSONGet.getValue(body, Share.class);
        // 权限判定，通过共享的笔记本所属判定
        Note note = noteService.getNoteByIdOL(share.getNoteId());
        if (note == null || note.getOwnerId() != userId) {
            return new FailResult("权限不足");
        }
        if (shareService.saveOrModifyShare(share)) {
            return new SusResult(share);
        } else return new FailResult("未知错误");
    }

    @RequestMapping("share/modify")
    public Result editShare(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        Share share = JSONGet.getValue(body, Share.class);
        // 权限判定，通过共享的笔记本所属判定
        Note note = noteService.getNoteByIdOL(share.getNoteId());
        if (note == null || note.getOwnerId() != userId) {
            return new FailResult("权限不足");
        }
        if (shareService.saveOrModifyShare(share)) {
            return new SusResult(share);
        } else return new FailResult("未知错误");
    }

    @RequestMapping("share/get/note")
    public Result getNoteShare(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        int noteIdOL = JSONGet.getValue(body, Integer.class);
        List<Share> shares = shareService.getNoteShares(noteIdOL, userId);
        Result result = new SusResult();
        result.addList("shares", shares);
        return result;
    }

    @RequestMapping("share/get/record")
    public Result getRecordShare(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        int recordIdOL = JSONGet.getValue(body, Integer.class);
        List<Share> shares = shareService.getRecordShares(recordIdOL, userId);
        Result result = new SusResult();
        result.addList("shares", shares);
        return result;
    }

    @RequestMapping("share/delete")
    public Result deleteShare(
            @RequestBody String body,
            @RequestAttribute(name = RequestFilter.USER_ID) int userId
    ) {
        int shareId = JSONGet.getValue(body, Integer.class);
        if (shareService.deleteShareById(shareId, userId)) {
            return new SusResult();
        } else return new FailResult("权限不足");
    }
}
