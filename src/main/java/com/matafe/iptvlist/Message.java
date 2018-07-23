package com.matafe.iptvlist;

/**
 * Message
 * 
 * @author matafe@gmail.com
 */
public class Message {

    private String text;

    public String getText() {
	return text;
    }

    @Override
    public String toString() {
	return text;
    }

    public static class Builder {

	private final Message theMessage;

	public Builder() {
	    this.theMessage = new Message();
	}

	public Builder text(final String text) {
	    this.theMessage.text = text;
	    return this;
	}

	public Message build() {
	    return this.theMessage;
	}

    }
}
