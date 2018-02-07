
package cc.gzvtc.vo;

public enum ReturnCodeType implements StatusType {
    SUCCESS(0, "success"), 
    ACCESS_RESTRICTIONS(-1, "access restrictions"), 
    PARAMETER_ERROR(-2, "parameter error"), 
	FAILURE(-3, "failure"); 

    private int code;
    private String message;

    private ReturnCodeType(int code, String message) {
		this.code = code;
		this.message = message;
    }

    public int getStatus() {
    	return code;
    }

    public ReturnCodeType setCode(int code) {
    	this.code = code;
    	return this;
    }

    public String getMessage() {
    	return message;
    }

    public ReturnCodeType setMessage(String message) {
    	this.message = message;
    	return this;
    }
}
