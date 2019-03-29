package com.liaoyangfan.dto.exception;

public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 8001104932052565973L;
	private Integer code;
	private String level;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public CustomException() {
		super();
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(String message, Integer code) {
		super(message);
		this.code = code;
	}

	public CustomException(String message, Integer code, String level) {
		this(message, code);
		this.level = level;
	}

	public CustomException(Throwable cause) {
		super(cause);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(String message, Integer code, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public CustomException(String message, Integer code, String level, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.level = level;
	}

}
