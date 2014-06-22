package org.spacehq.mc.classic.protocol.packet;

import org.spacehq.packetlib.io.NetInput;
import org.spacehq.packetlib.io.NetOutput;

import java.io.IOException;
import java.util.Arrays;

public class ClassicPacketUtil {
	public static String readString(NetInput in) throws IOException {
		return new String(in.readBytes(64), "UTF-8").trim();
	}

	public static void writeString(NetOutput out, String str) throws IOException {
		byte data[] = new byte[64];
		Arrays.fill(data, (byte) ' ');
		System.arraycopy(str.getBytes("UTF-8"), 0, data, 0, Math.min(str.length(), data.length));
		out.writeBytes(data);
	}

	public static byte[] readBytes(NetInput in) throws IOException {
		short length = in.readShort();
		byte bytes[] = in.readBytes(1024);
		return Arrays.copyOfRange(bytes, 0, length);
	}

	public static void writeBytes(NetOutput out, byte b[]) throws IOException {
		out.writeShort(b.length);
		byte data[] = new byte[1024];
		System.arraycopy(b, 0, data, 0, Math.min(b.length, data.length));
		out.writeBytes(data);
	}
}
