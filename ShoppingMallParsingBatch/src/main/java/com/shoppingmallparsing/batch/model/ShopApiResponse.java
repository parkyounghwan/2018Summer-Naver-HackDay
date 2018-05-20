package com.shoppingmallparsing.batch.model;

import com.shoppingmallparsing.batch.model.interpark.ShopItem;

public class ShopApiResponse {
	private boolean isSuccess;
	private int errCode;
	private ShopItem result;

	public boolean isSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public ShopItem getResult() {
		return result;
	}
	public void setResult(ShopItem result) {
		this.result = result;
	}
}
