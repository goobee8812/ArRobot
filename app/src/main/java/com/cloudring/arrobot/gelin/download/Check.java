package com.cloudring.arrobot.gelin.download;

import java.util.Collection;
import java.util.Map;

/**
 * create by luoren 2017/4/5
 * 工具类：检查对象是否为空
 */
public class Check {

	public static boolean isEmpty(CharSequence str) {
		return isNull(str) || str.length() == 0||"null".equalsIgnoreCase(str.toString());
	}

	public static boolean isEmpty(Object[] os) {
		return isNull(os) || os.length == 0;
	}

	public static boolean isEmpty(Collection<?> l) {
		return isNull(l) || l.isEmpty();
	}

	public static boolean isEmpty(Map<?, ?> m) {
		return isNull(m) || m.isEmpty();
	}

	public static boolean isNull(Object o) {return o == null;}
}
