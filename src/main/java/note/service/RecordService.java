package note.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import note.mapper.RecordMapper;
import note.mapper.ShareMapper;
import note.model.Note;
import note.model.Record;
import note.model.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private ShareMapper shareMapper;
    @Autowired
    private NoteService noteService;

    public Record getRecordById(int recordId) {
        return recordMapper.selectById(recordId);
    }

    public List<Record> getRecordsByNoteIdOL(int noteIdOL, int userId) {
        return recordMapper.getRecordsByNoteIdOL(noteIdOL, userId);
    }

    public List<Record> getSyncList(int userId) {
        return recordMapper.getSyncList(userId);
    }

    public List<Record> getRecords(List<Integer> recordIds) {
        if (recordIds.size() > 0) {
            return recordMapper.selectBatchIds(recordIds);
        } else return new ArrayList<>();
    }

    public boolean saveOrModifyRecord(Record record, int userId) {
        record.setUpdateTime(new Date().getTime());
        Record recordInServer = recordMapper.selectById(record.getRecordIdOL());
        if (recordInServer == null) {
            // 判定笔记本是否是此用户所有
            Note note = noteService.getNoteById(record.getNoteId(), userId);
            // 判定记录的创建者是否是请求者
            if (record.getCreatorId() == userId && note != null) {
                record.setCreateTime(new Date().getTime());
                return recordMapper.insert(record) == 1;
            } else {
                List<Share> shares = shareMapper.getNoteShares(record.getNoteId(), userId);
                return false;
            }
        } else if (recordInServer.getCreatorId() != userId) {
            List<Share> shares = shareMapper.getRecordShares(record.getRecordIdOL(), record.getCreatorId());
            for (Share s : shares) {
                if (s.getShareType() == Share.TYPE_WRITE && s.getUserList().contains(userId + "")) {
                    return recordMapper.updateRecord(record, record.getCreatorId()) == 1;
                }
            }
            return false;
        }
        else {
            return recordMapper.updateRecord(record, userId) == 1;
        }
    }

    public boolean saveRecords(List<Record> records, int userId) {
        if (records.size() > 0) {
            return recordMapper.saveRecords(records, userId) > 0;
        } else return false;
    }

    public boolean updateRecords(List<Record> records, int userId) {
        if (records.size() > 0) {
            recordMapper.updateRecords(records, userId);
            return true;
        } else return false;
    }

    public void deleteRecordByIdOL(int recordId) {
        recordMapper.deleteById(recordId);
    }

    public boolean deleteRecordById(int recordId, int userId) {
        UpdateWrapper<Record> wrapper = new UpdateWrapper<>();
        wrapper.eq("record_id", recordId);
        wrapper.eq("creator_id", userId);
        return recordMapper.delete(wrapper) == 1;
    }
}
