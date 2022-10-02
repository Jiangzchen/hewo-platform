package org.hewo.infrastructure.utils;

import org.hewo.infrastructure.constant.ResourceConstant;

public class ThreadLocalGroup {
	private static final String USERID = "userId";
	private static final String TOKEN = "token";
	private static final String REFRESHTOKEN = "refreshToken";
	private static final String SESSIONID = "sessionId";

	public ThreadLocalGroup() {
	}

	public static void set(String key, Object value) {
		ThreadLocalGroupUtil.put(key, value);
	}

	public static Object get(String key) {
		return ThreadLocalGroupUtil.get(key);
	}

	public static void remove() {
		ThreadLocalGroupUtil.remove();
	}

	public static void setUserId(Long userId) {
		ThreadLocalGroupUtil.put("userId", userId);
	}

	public static Long getUserId() {
		return returnObjectValueInteger(ThreadLocalGroupUtil.get("userId"));
	}

	public static void setRefreshToken(String refreshToken) {
		ThreadLocalGroupUtil.put("refreshToken", refreshToken);
	}

	public static String getRefreshToken() {
		return ThreadLocalGroupUtil.get("refreshToken") == null ? null : String.valueOf(ThreadLocalGroupUtil.get("refreshToken"));
	}

	public static void setToken(String token) {
		ThreadLocalGroupUtil.put("token", token);
	}

	public static void setSessionId(String token) {
		ThreadLocalGroupUtil.put("sessionId", token);
	}

	public static String getToken() {
		return ThreadLocalGroupUtil.get("token") == null ? null : String.valueOf(ThreadLocalGroupUtil.get("token"));
	}

	public static String getLangueId() {
		return returnObjectValue(ThreadLocalGroupUtil.get(ResourceConstant.LANG));
	}

	public static String getSessionId() {
		return returnObjectValue(ThreadLocalGroupUtil.get("sessionId"));
	}

	public static String getStringValue(String key) {
		return returnObjectValue(ThreadLocalGroupUtil.get(key));
	}

	private static String returnObjectValue(Object value) {
		return value == null ? null : value.toString();
	}

	private static Long returnObjectValueInteger(Object value) {
		return value == null ? null : Long.valueOf(value.toString());
	}
}
