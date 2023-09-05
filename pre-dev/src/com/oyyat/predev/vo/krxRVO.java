package com.oyyat.predev.vo;

public class krxRVO extends ApiHeaderVO {
	private String token_type;
	private String access_token;
	private String expires_in;
	private String message;

	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "krxRVO [token_type=" + token_type + ", access_token=" + access_token + ", expires_in=" + expires_in
				+ ", message=" + message + "]";
	}





}
