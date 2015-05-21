package com.e3.smsqueue.entity;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.e3.smsqueue.entity.CurrentQueueStore;
import com.e3.smsqueue.entity.SMS;

public class StoreTest {

	@Test
	public void testCurrentStore() {
		SMSStore store = new CurrentQueueStore(100);
		testStore(store);
	}
	
	@Test
	public void testBlockingStore() {
		SMSStore store = new BlockingQueueStore(100);
		testStore(store);
	}

	private void testStore(SMSStore store) {
		for (int i = 0; i < 100; i++) {
			store.put(new SMS("11100" + i, "86", "CCC"));
		}
		assertEquals(store.getSize(), 100);
		List<SMS> smsPackage = store.getOnePackage();
		assertEquals(store.getSize(), 100 - CurrentQueueStore.BATCH_SIZE);
		for (int i = 0; i < smsPackage.size(); i++) {
			assertEquals(smsPackage.get(i).getReceiver(), "11100" + i);
		}

		store.clearQueue();
		assertEquals(store.getSize(), 0);
		smsPackage = store.getOnePackage();
		assertEquals(store.getSize(), 0);
		
		store.put(new SMS("11100", "86", "CCC"));
		smsPackage = store.getOnePackage();
		assertEquals(smsPackage.size(), 1);
		assertEquals(store.getSize(), 0);
	}

}
