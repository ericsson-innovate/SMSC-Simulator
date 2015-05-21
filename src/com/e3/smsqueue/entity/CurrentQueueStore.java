package com.e3.smsqueue.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CurrentQueueStore implements SMSStore {

	private ConcurrentLinkedQueue<SMS> queue;
	private int queueSize;

	public CurrentQueueStore(int queueSize) {
		super();
		queue = new ConcurrentLinkedQueue<SMS>();
		this.queueSize = queueSize;
	}

	public boolean put(SMS sms) {
		if (queue.size() > queueSize) {
			return false;
		}
		queue.add(sms);
		return true;
	}

	public int clearQueue() {
		int size = queue.size();
		queue.clear();
		return size;
	}

	public List<SMS> getOnePackage() {
		List<SMS> smsPackage = new ArrayList<SMS>();
		for (int i = 0; i < BATCH_SIZE; i++) {
			SMS sms = queue.poll();
			if (sms == null) {
				break;
			}

			if (isExpiredData(sms)) {
				continue;
			}
			smsPackage.add(sms);
		}
		return smsPackage;
	}

	public List<SMS> getQueue() {
		SMS[] arrays = new SMS[queue.size()];
		queue.toArray(arrays);
		return Arrays.asList(arrays);
	}

	private boolean isExpiredData(SMS sms) {
		return sms.getTimestamp() <= (System.currentTimeMillis() - 3 * DAY);
	}

	@Override
	public int getSize() {
		return queue.size();
	}

}
