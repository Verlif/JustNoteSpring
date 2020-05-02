package note.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import note.mapper.UserMapper;
import note.model.User;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(int userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setUserKey("");
        }
        return user;
    }

    /**
     * 校验用户的登陆信息
     * @param user  被校验的用户信息
     * @return  <p> 校验后的用户信息。   </p>
     *          <p> null - 校验失败。    </p>
     */
    public User checkLogin(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!TextUtils.isEmpty(user.getUserName())) {
            wrapper.eq("user_name", user.getUserName());
        } else if (!TextUtils.isEmpty(user.getUserEmail())) {
            wrapper.eq("user_email", user.getUserEmail());
        } else return null;
        wrapper.eq("user_key", user.getUserKey());
        User result = userMapper.selectOne(wrapper);
        if (result != null) {
            result.setUserKey("");
        }
        return result;
    }

    public synchronized User register(User user) {
        user.setCreateTime(new Date().getTime());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", user.getUserName());
        if (userMapper.selectCount(wrapper) == 1) {
            return null;
        } else {
            if (userMapper.insert(user) == 1) {
                return user;
            } else return null;
        }
    }

    public boolean update(User user) {
        return userMapper.updateById(user) == 1;
    }
}
