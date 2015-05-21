package kr.co.killers.redis.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MD5 {
	private static SecureRandom random = null;
	private static MessageDigest md = null;

	static {
		try {
			randomGeneratorInit();
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] getNextNonce() {
		byte[] nextNonce = new byte[16];
		random.nextBytes(nextNonce);

		for (int j = 0; j < nextNonce.length; ++j) {
			int i = nextNonce[j] & 0xFF;
			if ((i < 32) || (i > 128)) {
				nextNonce[j] = (byte) (32 + i % 64);
			}
		}

		return nextNonce;
	}

	public static byte[] digest(byte[] data) {
		byte[] b = (byte[]) null;
		synchronized (md) {
			md.reset();
			b = md.digest(data);
		}
		return b;
	}

	public static byte[] digest(String data) {
		byte[] b = (byte[]) null;
		synchronized (md) {
			md.reset();
			b = md.digest(data.getBytes());
		}
		return b;
	}

	public static String digest2HexString(String data, String data2) {
		byte[] digest = (byte[]) null;
		synchronized (md) {
			md.reset();
			md.update(data.getBytes());
			md.update(data2.getBytes());
			digest = md.digest();
		}
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			String value = Integer.toHexString(b & 0xFF);
			if (value.length() == 1) {
				sb.append("0");
			}
			sb.append(value);
		}
		return sb.toString();
	}

	public static String digest2HexString(String data) {
		byte[] digest = (byte[]) null;
		synchronized (md) {
			md.reset();
			md.update(data.getBytes());
			digest = md.digest();
		}
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			String value = Integer.toHexString(b & 0xFF);
			if (value.length() == 1) {
				sb.append("0");
			}
			sb.append(value);
		}
		return sb.toString();
	}


	private static void randomGeneratorInit() throws NoSuchAlgorithmException {
		random = SecureRandom.getInstance("SHA1PRNG");
	}
}
