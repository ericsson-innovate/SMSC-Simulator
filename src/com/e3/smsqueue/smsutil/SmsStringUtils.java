package com.e3.smsqueue.smsutil;

import org.apache.commons.lang.StringUtils;

import com.e3.smsqueue.StoreType;

public class SmsStringUtils extends StringUtils{

	
	public static boolean isValidSmsBox(String str)
    {
        return !isBlank(str) && (str == StoreType.IN_BOX || str==StoreType.OUT_BOX);
    }

}
