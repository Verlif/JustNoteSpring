package note.filter;

import io.jsonwebtoken.Claims;
import note.utils.ConsoleUtil;
import note.utils.JwtUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestFilter implements Filter {

    //与前端统一的位于请求头的token参数名
    public static final String TOKEN_NAME = "token";
    //在token中UserId的参数名，可用于向Controller中传入用户Id
    public static final String USER_ID = "userId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        response.setContentType("application/json;charset=UTF-8");

        if (req.getHeaders(TOKEN_NAME).hasMoreElements()) {
            //获取request请求头中的token
            String token = req.getHeaders(TOKEN_NAME).nextElement();
            Claims claims = JwtUtils.parseJWT(token);

            /*
             * 若token无效，则拒绝后续请求动作
             * 从有效的token中查找"USER_ID_IN_TOKEN"参数
             * 将userId添加到request参数列表中，便于后续获取
             * */
            if (claims != null) {
                int userId = (int) claims.get(USER_ID);
                req.setAttribute(USER_ID, userId);
                chain.doFilter(request, response);
            }
        }
    }

}
