package com.e3.smsqueue.entity;

import java.util.List;

public interface SMSStore {
	static final int DAY = 1000 * 60 * 60 * 24;

	static final int BATCH_SIZE = 5;

	public boolean put(SMS sms);

	public int clearQueue();

	public List<SMS> getOnePackage();

	public List<SMS> getQueue();

	public int getSize();
}
