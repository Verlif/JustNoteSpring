package note.controller;

import note.filter.RequestFilter;
import note.func.CodeHandler;
import note.model.User;
import note.model.result.FailResult;
import note.model.result.Result;
import note.model.result.SusResult;
import note.service.UserService;
import note.utils.JSONGet;
import note.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private CodeHandler codeHandler;

    /**
     * 登陆mapper。
     * @param body  需求参数 -
     *              <p> - userName - 用户名。 </p>
     *              <p> - userEmail - 用户邮箱。 </p>
     *              <p> - userKey - 用户key。 </p>
     * @return  用户信息
     */
    @RequestMapping("login/login")
    @ResponseBody
    public Result login (@RequestBody String body) {
        User param = JSONGet.getValue(body, User.class);
        User user = userService.checkLogin(param);
        if (user != null) {
            HashMap<String, Serializable> map = new HashMap<>();
            map.put(RequestFilter.USER_ID, user.getUserId());
            String jwt = JwtUtils.createJWT(map);
            Result result = new SusResult();
            result.addObject(user);
            result.addObject(jwt);
            return result;
        } else return new FailResult("信息不匹配");
    }

    @RequestMapping("login/register")
    @ResponseBody
    public Result register (@RequestBody String body) {
        User user = JSONGet.getValue(body, User.class);
        User result = userService.register(user);
        if (result != null) {
            return new SusResult(result);
        } else return new FailResult("注册失败");
    }

    @GetMapping("hello")
    public String hello () {
        return "Hello World!!!";
    }
}
