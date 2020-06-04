package note.controller;

import note.mapper.UtilMapper;
import note.utils.ConsoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UtilMapper utilMapper;

    private static String[] tableNames = new String[]{"t_note", "t_record", "t_user", "t_type", "t_share"};

    @RequestMapping("test/check")
    public String check() {
        if (utilMapper.createTNote() == 0) {
            ConsoleUtil.println("!创建表 t_note 失败");
        } else ConsoleUtil.println("创建表 t_note 成功");

        if (utilMapper.createTRecord() == 0) {
            ConsoleUtil.println("!创建表 t_record 失败");
        } else ConsoleUtil.println("创建表 t_record 成功");

        if (utilMapper.createTUser() == 0) {
            ConsoleUtil.println("!创建表 t_user 失败");
        } else ConsoleUtil.println("创建表 t_user 成功");

        if (utilMapper.createTType() == 0) {
            ConsoleUtil.println("!创建表 t_type 失败");
        } else ConsoleUtil.println("创建表 t_type 成功");

        if (utilMapper.createTShare() == 0) {
            ConsoleUtil.println("!创建表 t_share 失败");
        } else ConsoleUtil.println("创建表 t_share 成功");

        return "haha";
    }
}
