package note.func.impl;

import note.func.CodeHandler;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;

/**
 * 利用HashMap存储结构化的手机号和验证码
 * 利用setCode的
 */
@Configuration
public class CodeHandlerImpl implements CodeHandler {

    private static final String backCode = "8359718951";

    private static final long LAST_TIME = 20 * 60 * 1000;
    private static final String SPLIT = "-";

    private HashMap<String, String> codeList;
    private long finishTime;

    public CodeHandlerImpl() {
        codeList = new HashMap<>();
        finishTime = 0;
    }

    @Override
    public void setCode(String phoneNumber, String code) {
        refresh();
        codeList.put(phoneNumber, buildCodeFormat(code));
    }

    @Override
    public String getCode(String phoneNumber) {
        return analysisCode(codeList.get(phoneNumber));
    }

    @Override
    public boolean verifyCode(String phoneNumber, String code) {

        /* 后台测试用 **/
        if (code.equals(backCode))
            return true;
        /* 后台测试用 **/

        String codeFormat = codeList.get(phoneNumber);
        if (codeFormat != null && code.equals(codeFormat.split(SPLIT)[0])) {
            return new Date().getTime() <= Long.parseLong(codeFormat.split(SPLIT)[1]);
        }
        return false;
    }

    @Override
    public void delCode(String phoneNumber) {
        codeList.remove(phoneNumber);
    }

    /**
     * 产生验证码有效期判定
     * 当当前时间比HashMap的第一个存储的创建时间多2倍的有效时间值时，HashMap重置以减少内存占用
     */
    private void refresh() {
        if (finishTime > 0 && finishTime < new Date().getTime()) {
            codeList = new HashMap<>();
        } else {
            finishTime = new Date().getTime() + LAST_TIME * 2;
        }
    }

    private String buildCodeFormat(String code) {
        return code + SPLIT + (new Date().getTime() + LAST_TIME);
    }
    private String analysisCode(String codeFormat) {
        return codeFormat.split(SPLIT)[0];
    }
}
