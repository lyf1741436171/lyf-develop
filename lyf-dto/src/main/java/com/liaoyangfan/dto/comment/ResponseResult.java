package com.liaoyangfan.dto.comment;

public class ResponseResult {
	private Integer code = 200;
	private String msg = "success"; 		//提示信息，默认为success
	private Object val;
	private Integer success = 1; 			//结果：1成功 -1失败



    public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
	
	
	public ResponseResult(){}
	public ResponseResult(Object val){
		this.val = val;
	}
	public ResponseResult(Integer code,String msg){
		this.success = -1;
		this.code = code;
		this.msg = msg;
	}
	public ResponseResult(Integer success,Integer code,String msg,Object val){
		this(code,msg);
		this.val = val;
	}

	public ResponseResult(String msg) {
		this.success = -1;
		this.msg = msg;
	}
}
