package note.func;

public interface CodeHandler {
    void setCode(String phoneNumber, String code);
    String getCode(String phoneNumber);
    boolean verifyCode(String phoneNumber, String code);
    void delCode(String phoneNumber);
}
