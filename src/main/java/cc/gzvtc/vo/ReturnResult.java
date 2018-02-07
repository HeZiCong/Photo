package cc.gzvtc.vo;

public class ReturnResult {

    private int status;

    private String message;
    
    private Object data;

    public int getStatus() {
    	return status;
    }

    public ReturnResult setStatus(StatusType status) {
		this.status = status.getStatus();
		this.message=status.getMessage();
		return this;
    }
    
    public String getMessage(){
    	return message;
    }
    public ReturnResult setMessage(StatusType message) {
		this.message = message.getMessage();
		return this;
    }

    public Object getData() {
    	return data;
    }

    public ReturnResult setData(Object Data) {
    	this.data = Data;
    	return this;
    }

}
