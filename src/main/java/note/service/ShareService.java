package note.service;

import note.mapper.ShareMapper;
import note.model.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShareService {

    @Autowired
    private ShareMapper shareMapper;

    public Share getShareById(int shareId) {
        return shareMapper.selectById(shareId);
    }

    public List<Share> getNoteShares(int noteIdOL, int userId) {
        return shareMapper.getNoteShares(noteIdOL, userId);
    }

    public List<Share> getRecordShares(int recordId, int userId) {
        return shareMapper.getRecordShares(recordId, userId);
    }

    public boolean saveOrModifyShare(Share share) {
        if (share.getShareId() == 0) {
            share.setCreateTime(new Date().getTime());
            return shareMapper.insert(share) == 1;
        } else {
            return shareMapper.updateById(share) == 1;
        }
    }

    public boolean deleteShareById(int shareId, int userId) {
        return shareMapper.deleteShare(shareId, userId) > 0;
    }

}
