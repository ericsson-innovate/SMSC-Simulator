package com.e3.smsqueue.entity;

import com.e3.smsqueue.StoreType;

public class SMSStoreFactory {
	static SMSStore outStore = new CurrentQueueStore(200);
	static SMSStore inStore = new CurrentQueueStore(200);

	public static SMSStore getStore(String smsBox) {
		if (StoreType.isInStore(smsBox))
			return inStore;
		else
			return outStore;
	}
}
