package acme.dbmodel;

public class Log {


	private int id;
	private int userid;
	private String operation;
	private int tableid;
	private String time;
	private String message;
	
	private String username;
	private String tablename;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userid;
	}
	public void setUserId(int userid) {
		this.userid = userid;
	}
	public int getTableId() {
		return tableid;
	}
	public void setTableId(int tableid) {
		this.tableid = tableid;
	}

	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time){
		this.time = time;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	
	public String getTableName() {
		return tablename;
	}
	public void setTableName(String tablename) {
		this.tablename = tablename;
	}
	

}
