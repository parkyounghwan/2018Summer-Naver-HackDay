package com.naver.park.feed.model;

public class APIResponse {
	private boolean Success;
	private int errCode;
	private Product result;

	public boolean isSuccess() {
		return Success;
	}
	public void setSuccess(boolean success) {
		Success = success;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public Product getResult() {
		return result;
	}
	public void setResult(Product result) {
		this.result = result;
	}
}
