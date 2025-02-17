package Demo_security.demo.exception;

public enum ErrorCode {
    UNCATEGORYZED_EXCEPTION(9999,"Ucategotyzed error"),
    USER_EXISTED(1003,"User existed"),
    USERNAME_INVALID(1004,"Username must be at least 3 "),
    PASSWORD_INVALID(1005, "Password must be at least 6 characters"),
    USER_NOT_EXIST(1006,"Password wrong");

    private int code;
   private  String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
