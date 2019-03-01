package yich.base.util;


import yich.base.dbc.Require;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;


public class BitUtil {
	/**
	 * Source: https://stackoverflow.com/questions/1936857/convert-integer-into-byte-array-java
	 * */
	// Using BigInteger:
	public byte[] bigIntToByteArray(final int i) {
		BigInteger bigInt = BigInteger.valueOf(i);
		return bigInt.toByteArray();
	}
	// Using DataOutputStream:
	public byte[] intToByteArray(final int i) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(i);
		dos.flush();
		return bos.toByteArray();
	}
	// Using ByteBuffer:
	public byte[] intToBytes_(final int i) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(i);
		return bb.array();
	}
		
	// DIY: convert a byte to a byte array
	public static byte[] getBitArray(byte x) {
		byte[] ba = new byte[8];
		for (int i = 7; i >= 0; i--) {
			ba[i] = (byte) (x & 1);
			x = (byte) (x >> 1);
		}
		return ba;
	}

	// DIY: convert a byte to a string representation
	public static String getBitString(byte x) {
		byte[] ba = getBitArray(x);
		String str = "";
		for (int i = 0; i < 8; i++) {
			str = str + ba[i];
		}
		return str;
	}

	// DIY: convert a short to 2 bytes
	public static byte[] shortToBytes(short x) {
		byte[] ba = new byte[2];
		int temp = x & 0xffff;
		for (int i = 1; i >= 0; i--) {
			ba[i] = (byte) (temp & 0xff);
			temp = temp >> 8;
		}
		return ba;
	}

	// DIY: convert a integer to 4 bytes
	public static byte[] intToBytes(int x) {
		byte[] ba = new byte[4];
		for (int i = 3; i >= 0; i--) {
			ba[i] = (byte) (x & 0xff);
			x = x >> 8;
		}
		return ba;
	}

	// DIY: convert a long to 8 bytes
	public static byte[] longToBytes(long x) {
		byte[] ba = new byte[8];
		for (int i = 7; i >= 0; i--) {
			ba[i] = (byte) (x & 0xff);
			x = x >> 8;
		}
		return ba;
	}

	// DIY: print a byte in the form of binary
	public static void printBit(byte x) {
		// System.out.println(Integer.toBinaryString(x & 0xFF));
		String str = getBitString(x);
		System.out.println(str);
	}

	// DIY: print a byte array in the form of binary
	public static void printBitArray(byte[] ba) {
		Require.argumentNotNull(ba);
		
		for (int i = 0; i < ba.length; i++) {
			System.out.print(getBitString(ba[i]) + " ");
		}
		System.out.println();
	}

	// DIY:
    public static void printIntBit(int i) {
        printBitArray(intToBytes(i));
    }

	// DIY: transform a byte to a unsigned number
	public static int getUnsignedByte(byte num) {
		int re = num & 0xFF;
		return re;
	}

	// DIY:
    public static int getUnsignedInt(int num) {
        int re = num & 0xFFFFFFFF;
        return re;
    }


    // DIY: convert 2 bytes to a short
	public static short bytesToShort(byte[] ba) {
		Require.argumentNotNull(ba);
		Require.argument(ba.length == 2, ba.length + " bytes input", "2 bytes required");
		
		short re = (short) (ba[0] << 8);
		re = (short) (re | ((short) (ba[1] & 0xff)));
		return re;
	}
	
	// DIY: convert 4 bytes to a integer
	public static int bytesToInt(byte[] ba) {
		Require.argumentNotNull(ba);
		Require.argument(ba.length == 4, ba.length + " bytes input", "4 bytes required");

		int re = ba[0] << 24;
		re = re | (ba[1] << 16 & 0xffffff);
		re = re | (ba[2] << 8 & 0xffff);
		re = re | ba[3] & 0xff;
		return re;
	}
	
	// DIY: convert 8 bytes to a long
	public static long bytesToLong(byte[] ba) {
		Require.argumentNotNull(ba);
		Require.argument(ba.length == 8, ba.length + " bytes input", "8 bytes required");

		long re = ((long) ba[0] << 56); //printBitArray(longToBytes(re));
		re = re | (((long) ba[1] << 48) & 0xffffffffffffffL); //printBitArray(longToBytes(re));
		re = re | (((long) ba[2] << 40) & 0xffffffffffffL); //printBitArray(longToBytes(re));
		re = re | (((long) ba[3] << 32) & 0xffffffffffL); //printBitArray(longToBytes(re));
		re = re | (((long) ba[4] << 24) & 0xffffffffL); //printBitArray(longToBytes(re));
		re = re | (((long) ba[5] << 16) & 0xffffffL); //printBitArray(longToBytes(re));
		re = re | (((long) ba[6] << 8) & 0xffffL); //printBitArray(longToBytes(re));
		re = re | ((long) ba[7] & 0xffL); //printBitArray(longToBytes(re));
		return re;
	}
	

}
