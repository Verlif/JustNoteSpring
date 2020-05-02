package note.controller;

import note.model.User;
import note.model.result.FailResult;
import note.model.result.Result;
import note.model.result.SusResult;
import note.service.UserService;
import note.utils.ConsoleUtil;
import note.utils.JSONGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("user/get/id")
    @ResponseBody
    public Result getUserById(@RequestBody String body) {
        int userId = JSONGet.getValue(body, Integer.class);
        User user = userService.getUserById(userId);
        if (user != null) {
            return new SusResult(user);
        } else return new FailResult("未能查询到相关用户-" + userId);
    }
    
    @RequestMapping("user/update")
    public Result updateUser(@RequestBody String body) {
        User user = JSONGet.getValue(body, User.class);
        if (userService.update(user)) {
            return new SusResult(user);
        } else return new FailResult("更新失败");
    }

}
