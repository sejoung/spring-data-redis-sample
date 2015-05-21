package kr.co.killers.redis.util;

import java.util.Calendar;
import java.util.Random;

public class KeyUtils {

	static final String SERVERID = "UBOXAUTHAPI01";

	public static String getSessionId() {
		return MD5.digest2HexString(SERVERID + Long.toString(System.currentTimeMillis()) + new Random().nextInt());
	}

	public static String getNonceId() {
		Calendar today = Calendar.getInstance();
		return Long.toHexString((today.get(Calendar.YEAR) - 2000) * 1000 + today.get(Calendar.DAY_OF_YEAR)) + MD5.digest2HexString(("SERVERID") + Long.toString(System.currentTimeMillis()) + new Random().nextInt());
	}
}
