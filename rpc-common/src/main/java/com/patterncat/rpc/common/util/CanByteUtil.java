//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.patterncat.rpc.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanByteUtil {
    private static Logger logger = LoggerFactory.getLogger(CanByteUtil.class);
    public static final String CHARSET_UCS2 = "UTF-16BE";
    private static final double POWER_6 = Math.pow(10.0D, 6.0D);

    public CanByteUtil() {
    }

    public static byte parseByte(byte[] data, Index index) {
        return data[index.getAndAdd()];
    }

    public static int parseByte2IntSign(byte[] data, Index index) {
        int sign = (data[index.get()] & 255) >> 7 == 1 ? -1 : 1;
        return (data[index.getAndAdd()] & 127) * sign;
    }

    public static long parseDWord2LongSign(byte[] data, Index index) {
        int sign = (data[index.get()] & 255) >> 7 == 1 ? -1 : 1;
        long value = ((long)(data[index.getAndAdd()] & 127) << 24) + (long)((data[index.getAndAdd()] & 255) << 16) + (long)((data[index.getAndAdd()] & 255) << 8) + (long)(data[index.getAndAdd()] & 255);
        return value * (long)sign;
    }

    public static int parseByte2Int(byte[] data, Index index) {
        return data[index.getAndAdd()] & 255;
    }

    public static int parseWord(byte[] data, Index index) {
        return ((data[index.getAndAdd()] & 255) << 8) + (data[index.getAndAdd()] & 255);
    }

    public static int parseWordSign(byte[] data, Index index) {
        int sign = (data[index.get()] & 255) >> 7 == 1 ? -1 : 1;
        return (((data[index.getAndAdd()] & 127) << 8) + (data[index.getAndAdd()] & 255)) * sign;
    }

    public static long parseDWord(byte[] data, Index index) {
        return ((long)(data[index.getAndAdd()] & 255) << 24) + (long)((data[index.getAndAdd()] & 255) << 16) + (long)((data[index.getAndAdd()] & 255) << 8) + (long)(data[index.getAndAdd()] & 255);
    }

    public static byte[] arrayCopy(byte[] data, Index index, int dstArrLen) {
        byte[] byteArr = new byte[dstArrLen];
        System.arraycopy(data, index.get(), byteArr, 0, byteArr.length);
        index.add(byteArr.length);
        return byteArr;
    }

    public static byte[] arrayCopyString(byte[] data, Index index, int dstArrLen) {
        byte[] byteArr = new byte[dstArrLen];
        System.arraycopy(data, index.get(), byteArr, 0, dstArrLen);
        index.add(dstArrLen);
        return byteArr;
    }

    public static void putByte(byte[] data, int value, Index index) {
        data[index.getAndAdd()] = (byte)(value & 255);
    }

    public static void putWord(byte[] data, int value, Index index) {
        data[index.getAndAdd()] = (byte)(value >> 8 & 255);
        data[index.getAndAdd()] = (byte)(value & 255);
    }

    public static void putDword(byte[] data, long value, Index index) {
        data[index.getAndAdd()] = (byte)((int)(value >> 24 & 255L));
        data[index.getAndAdd()] = (byte)((int)(value >> 16 & 255L));
        data[index.getAndAdd()] = (byte)((int)(value >> 8 & 255L));
        data[index.getAndAdd()] = (byte)((int)(value & 255L));
    }

    public static void putStringByteArr(byte[] data, byte[] value, Index index) {
        byte[] arr$ = value;
        int len$ = value.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            data[index.getAndAdd()] = b;
        }

        data[index.getAndAdd()] = 0;
    }

    public static void putByteArr(byte[] data, byte[] value, Index index) {
        byte[] arr$ = value;
        int len$ = value.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            data[index.getAndAdd()] = b;
        }

    }

    public static int bcdDec(byte b) {
        return Integer.parseInt(Integer.toString(b & 255, 16), 10);
    }

    public static int bcdDec(byte[] data, Index index) {
        return Integer.parseInt(Integer.toString(data[index.getAndAdd()] & 255, 16), 10);
    }

    public static byte bcdEnc2Byte(int value) {
        return (byte)Integer.parseInt(Integer.toString(value & 255), 16);
    }

    public static int bcdEnc(int value) {
        return Integer.parseInt(Integer.toString(value & 255), 16);
    }

    public static byte[] calendar2bcd(Calendar cal, byte[] data, int start) {
        String str = String.valueOf(cal.get(1) - 2000);
        data[start++] = (byte)Integer.parseInt(str, 16);
        str = cal.get(2) + 1 + "";
        data[start++] = (byte)Integer.parseInt(str, 16);
        str = cal.get(5) + "";
        data[start++] = (byte)Integer.parseInt(str, 16);
        str = cal.get(11) + "";
        data[start++] = (byte)Integer.parseInt(str, 16);
        str = cal.get(12) + "";
        data[start++] = (byte)Integer.parseInt(str, 16);
        str = cal.get(13) + "";
        data[start++] = (byte)Integer.parseInt(str, 16);
        return data;
    }

    public static Date bcd2Date(byte[] data, Index index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000 + ByteUtil.bcdDec(data[index.getAndAdd()]), ByteUtil.bcdDec(data[index.getAndAdd()]) - 1, ByteUtil.bcdDec(data[index.getAndAdd()]), ByteUtil.bcdDec(data[index.getAndAdd()]), ByteUtil.bcdDec(data[index.getAndAdd()]), ByteUtil.bcdDec(data[index.getAndAdd()]));
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static String getString(byte[] data, Index index, int len) {
        String result = null;
        if (len < 1) {
            return null;
        } else {
            try {
                result = new String(arrayCopyString(data, index, len), "GBK");
            } catch (UnsupportedEncodingException var5) {
                logger.error(var5.getMessage(), var5);
            }

            return result;
        }
    }

    public static String getStringUCS2(byte[] data, Index index, int len) {
        String result = null;
        if (len == 0) {
            return "";
        } else {
            try {
                result = new String(arrayCopy(data, index, len), "UTF-16BE");
            } catch (UnsupportedEncodingException var5) {
                logger.error(var5.getMessage(), var5);
            }

            return result;
        }
    }

    public static short CRC16Caculate(byte[] AP, int j) {
        byte CRC_H4 = 0;
        short[] CRCTable = new short[]{0, 4129, 8258, 12387, 16516, 20645, 24774, 28903, -32504, -28375, -24246, -20117, -15988, -11859, -7730, -3601};
        short CRC16 = (short)('\uffff' - (j & '\uffff'));

        for(int i = 0; i < AP.length; ++i) {
            try {
                CRC_H4 = (byte)(CRC16 >> 12 & 15);
                CRC16 = (short)(CRC16 << 4);
                CRC16 ^= CRCTable[CRC_H4 ^ AP[i] >> 4 & 15];
                CRC_H4 = (byte)(CRC16 >> 12 & 15);
                CRC16 = (short)(CRC16 << 4);
                CRC16 ^= CRCTable[CRC_H4 ^ AP[i] & 15];
            } catch (Exception var7) {
                System.out.println(CRC_H4 + "" + (AP[i] >> 4 & 15));
            }
        }

        return CRC16;
    }

    public static String getMessageStr(byte[] message) {
        String result = "";
        char[] chArr = Hex.encodeHex(message);

        for(int i = 0; i < chArr.length; ++i) {
            result = result + String.valueOf(chArr[i]).toUpperCase();
            if (i % 2 != 0) {
                result = result + " ";
            }
        }

        return result;
    }

    public static byte[] getBytes(String data) {
        return getBytes(data, "GBK");
    }

    public static byte[] getBytes(String data, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    public static int parseCanByte2Int(byte[] in, Index index) {
        int value = in[index.add()] & 255;
        return value == 255 ? -1 : value;
    }

    public static int parseCanWord(byte[] in, Index index) {
        int value = ((in[index.add()] & 255) << 8) + (in[index.add()] & 255);
        return value == 65535 ? -1 : value;
    }

    public static long parseCanDWord(byte[] in, Index index) {
        long value = (long)(((in[index.add()] & 255) << 24) + ((in[index.add()] & 255) << 16) + ((in[index.add()] & 255) << 8) + (in[index.add()] & 255)) & 4294967295L;
        return value == 4294967295L ? -1L : value;
    }

    public static double getFormatedVal(double src, double unit) {
        int length = 2;
        if (unit == 1.0E-6D) {
            length = 7;
        }

        return ByteUtil.getFormatedVal(src, unit, length);
    }

    public static double getFormatedVal(double src, double unit, int length) {
        if (src == -1.0D) {
            return src;
        } else {
            if (length < 1 || length > 20) {
                length = 2;
            }

            BigDecimal b = new BigDecimal(src * unit);
            return b.setScale(length, 4).doubleValue();
        }
    }

    public static String parseHardVer(byte[] in, Index index) {
        StringBuilder sb = new StringBuilder("v");
        sb.append(Integer.toString(in[index.add()] & 255, 16));
        return sb.insert(2, '.').toString();
    }

    public static String parseSoftVer(byte[] in, Index index) {
        StringBuilder sb = new StringBuilder("v");
        sb.append(in[index.add()] & 255).append(".").append(in[index.add()] & 255).append(".").append(in[index.add()] & 255).append(".").append(in[index.add()] & 255);
        return sb.toString();
    }

    public static double parseLat(byte[] data, Index index) {
        double lat = (double)parseDWord(data, index) / POWER_6;
        return lat;
    }

    public static double parseLng(byte[] data, Index index) {
        double lng = (double)parseDWord(data, index) / POWER_6;
        return lng;
    }

    public static String parseByte2Bcd(byte[] data, Index index, int len) {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < len; ++i) {
            int temp = bcdDec(data[index.getAndAdd()]);
            if (temp < 10) {
                result.append("0");
            }

            result.append(temp);
        }

        return result.toString();
    }

    public static byte[] parseLongToBytes(long data) {
        byte[] bytes = new byte[]{(byte)((int)(data >> 56 & 255L)), (byte)((int)(data >> 48 & 255L)), (byte)((int)(data >> 40 & 255L)), (byte)((int)(data >> 32 & 255L)), (byte)((int)(data >> 24 & 255L)), (byte)((int)(data >> 16 & 255L)), (byte)((int)(data >> 8 & 255L)), (byte)((int)(data & 255L))};
        return bytes;
    }

    public static long parseBytesToLong(byte[] bytes, Index index) {
        long value = -72057594037927936L & (long)bytes[index.getAndAdd()] << 56 | 71776119061217280L & (long)bytes[index.getAndAdd()] << 48 | 280375465082880L & (long)bytes[index.getAndAdd()] << 40 | 1095216660480L & (long)bytes[index.getAndAdd()] << 32 | 4278190080L & (long)bytes[index.getAndAdd()] << 24 | 16711680L & (long)bytes[index.getAndAdd()] << 16 | 65280L & (long)bytes[index.getAndAdd()] << 8 | 255L & (long)bytes[index.getAndAdd()];
        return value;
    }

    public static byte[] parseString2Bcd(String data, int length) {
        byte[] value = new byte[length];
        int j = 0;

        for(int i = 0; i < length * 2; ++i) {
            if (i % 2 == 0) {
                String tmepStr = data.substring(i, i + 2);
                value[j++] = (byte)Integer.parseInt(tmepStr, 16);
            }
        }

        return value;
    }

    public static String getIdentityCardID(byte[] data, Index index) {
        StringBuilder result = new StringBuilder();

        int temp;
        for(temp = 0; temp < 8; ++temp) {
            temp = bcdDec(data[index.getAndAdd()]);
            if (temp < 10) {
                result.append("0");
            }

            result.append(temp);
        }

        if ((data[index.get()] & 15) == 10) {
            result.append(data[index.getAndAdd()] >> 4 & 15);
            result.append("X");
        } else {
            temp = bcdDec(data[index.getAndAdd()]);
            if (temp < 10) {
                result.append("0");
            }

            result.append(temp);
        }

        return result.toString();
    }

    public static byte[] parseIdentityCardID(String data) {
        if (data != null && data.length() == 18) {
            if (data.endsWith("X")) {
                data = data.replaceAll("X", "A");
            }

            return parseString2Bcd(data, 9);
        } else {
            return null;
        }
    }

    public static byte[] parseDriverNoToByte(String value) {
        String regex = "^[0-9A-Fa-f]*$";
        if (value != null && value.matches(regex) && value.length() == 32) {
            byte[] valueBy = new byte[16];
            int j = 0;

            for(int i = 0; i < value.length(); i += 2) {
                String temp = value.substring(i, i + 2);
                valueBy[j++] = (byte)Integer.parseInt(temp, 16);
            }

            return valueBy;
        } else {
            return null;
        }
    }

    public static Map<Integer, byte[]> parsePlvAdditionInfo(byte[] data, int index) {
        Map<Integer, byte[]> additionInfo = new HashMap();

        for(int cnt = 0; index + 1 < data.length && cnt < 100; ++cnt) {
            int id = ((data[index++] & 255) << 8) + (data[index++] & 255);
            int length = data[index++] & 255;
            if (length == 0 || index + length > data.length) {
                break;
            }

            byte[] result = new byte[length];
            System.arraycopy(data, index, result, 0, length);
            additionInfo.put(id, result);
            index += length;
        }

        return additionInfo;
    }

    public static String getBytesStr(byte[] data, Index index, int len) {
        try {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < len; ++i) {
                String tmp = Integer.toHexString(ByteUtil.parseByte2Int(data, index));
                if (tmp.length() == 1) {
                    tmp = "0" + tmp;
                }

                sb.append(tmp);
            }

            return sb.toString().toUpperCase();
        } catch (NumberFormatException var6) {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println();
        String str = "abc四街xx";
        byte[] data = str.getBytes();
        byte[] arr = new byte[data.length + 1];
        putStringByteArr(arr, data, new Index());
        System.out.println(new String(arr));
    }
}
