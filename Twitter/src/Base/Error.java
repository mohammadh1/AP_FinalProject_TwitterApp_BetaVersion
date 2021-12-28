package Base;

public class Error {
    private boolean hasError;
    private String description;
    private int errorCode;
    private String errorType;
    public Error(int errorCode) {
        this.errorCode = errorCode;
        // enum errorCode inja bayad shoro be kar kone
    }

    public boolean isHasError() {
        return hasError;
    }
    public String getDescription() {
        return description;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public String getErrorType() {
        return errorType;
    }
}
