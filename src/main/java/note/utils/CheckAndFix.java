package note.utils;

import note.mapper.NoteMapper;
import note.mapper.UtilMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckAndFix {

    @Autowired
    private UtilMapper utilMapper;
    @Autowired
    private NoteMapper noteMapper;

    public boolean onDatabase() {
        ConsoleUtil.println("123");
        ConsoleUtil.println(noteMapper == null);
        ConsoleUtil.println(noteMapper.getSharedNotes(1));
        ConsoleUtil.println(utilMapper.checkTableExist("t_test"));
        ConsoleUtil.println(utilMapper.checkTableExist("t_test2"));
        return false;
    }

}
