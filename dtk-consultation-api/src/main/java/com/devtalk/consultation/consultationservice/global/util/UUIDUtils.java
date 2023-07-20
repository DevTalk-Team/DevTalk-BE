package com.devtalk.consultation.consultationservice.global.util;

import com.fasterxml.uuid.Generators;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtils {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    //UUID 생성 후 binary 변환
    static public byte[] createUUID() {
        UUID uuidV1 = Generators.timeBasedGenerator().generate();
        String[] uuidV1Parts = uuidV1.toString().split("-");
        String sequentialUUID = uuidV1Parts[2]+uuidV1Parts[1]+uuidV1Parts[0]+uuidV1Parts[3]+uuidV1Parts[4];

        String sequentialUuidV1 = String.join("", sequentialUUID);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        System.out.println(sequentialUuidV1.substring(0, 16));
        System.out.println(sequentialUuidV1.substring(16));
        bb.putLong(Long.parseUnsignedLong(sequentialUuidV1.substring(0, 16), 16));
        bb.putLong(Long.parseUnsignedLong(sequentialUuidV1.substring(16), 16));
        return bb.array();
    }

    //사람이 식별 가능한 문자열로 변환
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }
}
