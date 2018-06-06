package com.utils;

import java.util.ArrayList;
import java.util.List;


public class BytesUtils {

	public static byte[] long2Byte(long x) {
		byte[] bb = new byte[8];
		bb[0] = (byte) (x >> 56);
		bb[1] = (byte) (x >> 48);
		bb[2] = (byte) (x >> 40);
		bb[3] = (byte) (x >> 32);
		bb[4] = (byte) (x >> 24);
		bb[5] = (byte) (x >> 16);
		bb[6] = (byte) (x >> 8);
		bb[7] = (byte) (x >> 0);
		return bb;
	}

	public static long byte2Long(byte[] bb) {
		return ((((long) bb[0] & 0xff) << 56) | (((long) bb[1] & 0xff) << 48)
				| (((long) bb[2] & 0xff) << 40) | (((long) bb[3] & 0xff) << 32)
				| (((long) bb[4] & 0xff) << 24) | (((long) bb[5] & 0xff) << 16)
				| (((long) bb[6] & 0xff) << 8) | (((long) bb[7] & 0xff) << 0));
	}

	public static byte[] int2Byte(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	public static List<byte[]> split(byte[] bytes, int size) {
		if(bytes == null || bytes.length == 0 || size <= 0){
			return null;
		}
		byte[] row = null;
		List<byte[]> datas = new ArrayList<byte[]>();
		for(int i=0; i<bytes.length; i++){
			if(i % size == 0){
				if(row != null){
					datas.add(row);
				}
				row = new byte[size];
			}
			row[i % size] = bytes[i];
		}
		datas.add(row);
		return datas;
	}
}
