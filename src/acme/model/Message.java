package acme.model;

public class Message {

	// for msgType value, only the following 4 are valid:
	// success, info, warning, danger

	private String msgType;
	private String message;

	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


}
