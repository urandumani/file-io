package com.ekipa.model;

public class MessageModel {
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public static MessageModel message(String msg) {
		MessageModel messageModel = new MessageModel();
		messageModel.setMessage(msg);
		return messageModel;
	}
}
