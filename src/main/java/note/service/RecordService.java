package note.service;

import note.mapper.RecordMapper;
import note.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordMapper recordMapper;

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

    public boolean saveOrModifyRecord(Record record) {
        record.setUpdateTime(new Date().getTime());
        if (recordMapper.selectById(record.getRecordIdOL()) == null) {
            record.setCreateTime(new Date().getTime());
            return recordMapper.insert(record) == 1;
        } else {
            return recordMapper.updateById(record) == 1;
        }
    }

    public boolean saveRecords(List<Record> records, int userId) {
        if (records.size() > 0) {
            recordMapper.saveRecords(records, userId);
            return true;
        } else return false;
    }

    public boolean updateRecords(List<Record> records, int userId) {
        if (records.size() > 0) {
            recordMapper.updateRecords(records, userId);
            return true;
        } else return false;
    }

    public void deleteRecordById(int recordId) {
        recordMapper.deleteById(recordId);
    }
}
