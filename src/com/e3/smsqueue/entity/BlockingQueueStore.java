package com.e3.smsqueue.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueStore implements SMSStore {

	private LinkedBlockingQueue<SMS> queue;

	public BlockingQueueStore(int queueSize) {
		super();
		queue = new LinkedBlockingQueue<SMS>(queueSize);
	}

	public boolean put(SMS sms) {
		return queue.offer(sms);
	}

	public int clearQueue() {
		int size = queue.size();
		queue.clear();
		return size;
	}

	public List<SMS> getOnePackage() {
		List<SMS> smsPackage = new ArrayList<SMS>();
		queue.drainTo(smsPackage, BATCH_SIZE);
		return smsPackage;
	}

	public List<SMS> getQueue() {
		SMS[] arrays = new SMS[queue.size()];
		queue.toArray(arrays);
		return Arrays.asList(arrays);
	}

	@SuppressWarnings("unused")
	private static boolean isExpiredData(SMS sms) {
		return sms.getTimestamp() <= (System.currentTimeMillis() - 3 * DAY);
	}

	@Override
	public int getSize() {
		return queue.size();
	}

}
