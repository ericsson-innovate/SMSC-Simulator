package com.e3.smsqueue;


public class StoreType {
	
	public static final String IN_BOX="inbox";
	public static final String OUT_BOX="outbox";
	
	public static boolean isInStore(String str)
	{
		return str.equalsIgnoreCase(StoreType.IN_BOX) ? true : false;
	}
	
    public StoreType()
    {
    }

}
