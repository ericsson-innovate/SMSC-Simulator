package com.e3.smsqueue.entity;

public class SMS {
    private String receiver;

    private String areaCode;

    private String content;

    private String requestId;
    
    private String deliveryStatus;
    
    private long   timestamp;

    public SMS() {
        timestamp = System.currentTimeMillis();

    }

    public SMS(String receiver, String areaCode, String content) {
        super();
        this.receiver = receiver;
        this.areaCode = areaCode;
        this.content = content;
        this.deliveryStatus = "MessageWaiting";
        timestamp = System.currentTimeMillis();
    }
    
    public SMS(String receiver, String areaCode, String content, String requestId) {
        super();
        this.receiver = receiver;
        this.areaCode = areaCode;
        this.content = content;
        this.setRequestId(requestId);
        this.deliveryStatus = "MessageWaiting";
        timestamp = System.currentTimeMillis();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

}
