package com.e3.smsqueue.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.e3.smsqueue.entity.JSonUtils;
import com.e3.smsqueue.entity.SMS;

public class JSonUtilsTest {

	@Test
	public void testToJSON() {
		List<SMS> smsPackage = getTestPackage();
		String result = JSonUtils.toJSON(smsPackage);
		System.out.println(result);
		assertTrue(result.length() > 5*10);
		smsPackage.clear();
		result = JSonUtils.toJSON(smsPackage);
		System.out.println(result);
		assertEquals("[]", result.trim());
		
		smsPackage.add(new SMS("01", null, "c"));
		result = JSonUtils.toJSON(smsPackage);
		System.out.println(result);
		assertEquals("[{\"receiver\":\"01\",\"content\":\"c\"}]", result.trim());
		
	}
	private List<SMS> getTestPackage() {
		List<SMS> smsPackage = new ArrayList<SMS>();
		for (int i = 0; i < 5; i++) {
			smsPackage.add(new SMS("11100" + i, "86", "CCC"));
		}
		return smsPackage;
	}
	@Test
	public void testFromJSON() {
		List<SMS> smsPackage = getTestPackage();
		String result = JSonUtils.toJSON(smsPackage);
		List<SMS> results = JSonUtils.fromJSON(result);
		assertEquals(smsPackage.size(), results.size());
		results = JSonUtils.fromJSON("[]");
		assertEquals(results.size(), 0);
		results = JSonUtils.fromJSON("");
		assertEquals(results.size(), 0);
	}
}
