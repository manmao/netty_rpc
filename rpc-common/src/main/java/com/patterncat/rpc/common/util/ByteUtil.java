//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.patterncat.rpc.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Hex;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtil {
    private static Logger logger = LoggerFactory.getLogger(ByteUtil.class);
    public static final String CHARSET_UCS2 = "UTF-16BE";
    private static final double POWER_6 = Math.pow(10.0D, 6.0D);
    public static int[] table = new int[]{0, 4489, 8978, 12955, 17956, 22445, 25910, 29887, 35912, 40385, 44890, 48851, 51820, 56293, 59774, 63735, 4225, 264, 13203, 8730, 22181, 18220, 30135, 25662, 40137, 36160, 49115, 44626, 56045, 52068, 63999, 59510, 8450, 12427, 528, 5017, 26406, 30383, 17460, 21949, 44362, 48323, 36440, 40913, 60270, 64231, 51324, 55797, 12675, 8202, 4753, 792, 30631, 26158, 21685, 17724, 48587, 44098, 40665, 36688, 64495, 60006, 55549, 51572, 16900, 21389, 24854, 28831, 1056, 5545, 10034, 14011, 52812, 57285, 60766, 64727, 34920, 39393, 43898, 47859, 21125, 17164, 29079, 24606, 5281, 1320, 14259, 9786, 57037, 53060, 64991, 60502, 39145, 35168, 48123, 43634, 25350, 29327, 16404, 20893, 9506, 13483, 1584, 6073, 61262, 65223, 52316, 56789, 43370, 47331, 35448, 39921, 29575, 25102, 20629, 16668, 13731, 9258, 5809, 1848, 65487, 60998, 56541, 52564, 47595, 43106, 39673, 35696, 33800, 38273, 42778, 46739, 49708, 54181, 57662, 61623, 2112, 6601, 11090, 15067, 20068, 24557, 28022, 31999, 38025, 34048, 47003, 42514, 53933, 49956, 61887, 57398, 6337, 2376, 15315, 10842, 24293, 20332, 32247, 27774, 42250, 46211, 34328, 38801, 58158, 62119, 49212, 53685, 10562, 14539, 2640, 7129, 28518, 32495, 19572, 24061, 46475, 41986, 38553, 34576, 62383, 57894, 53437, 49460, 14787, 10314, 6865, 2904, 32743, 28270, 23797, 19836, 50700, 55173, 58654, 62615, 32808, 37281, 41786, 45747, 19012, 23501, 26966, 30943, 3168, 7657, 12146, 16123, 54925, 50948, 62879, 58390, 37033, 33056, 46011, 41522, 23237, 19276, 31191, 26718, 7393, 3432, 16371, 11898, 59150, 63111, 50204, 54677, 41258, 45219, 33336, 37809, 27462, 31439, 18516, 23005, 11618, 15595, 3696, 8185, 63375, 58886, 54429, 50452, 45483, 40994, 37561, 33584, 31687, 27214, 22741, 18780, 15843, 11370, 7921, 3960};
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public ByteUtil() {
    }

    public static byte parseByte(byte[] data, Index index) {
        return data[index.getAndAdd()];
    }

    public static int parseByte2IntSign(byte[] data, Index index) {
        int sign = (data[index.get()] & 255) >> 7 == 1 ? -1 : 1;
        return (data[index.getAndAdd()] & 127) * sign;
    }

    public static int parseByte2Int(byte[] data, Index index) {
        return data[index.getAndAdd()] & 255;
    }

    public static int parseWord(byte[] data, Index index) {
        return ((data[index.getAndAdd()] & 255) << 8) + (data[index.getAndAdd()] & 255);
    }

    public static int parseWord2Short(byte[] data, Index index) {
        return (short)((data[index.getAndAdd()] & 255) << 8 | data[index.getAndAdd()] & 255);
    }

    public static int parseWordSign(byte[] data, Index index) {
        int sign = (data[index.get()] & 255) >> 7 == 1 ? -1 : 1;
        return (((data[index.getAndAdd()] & 127) << 8) + (data[index.getAndAdd()] & 255)) * sign;
    }

    public static long parseDWord(byte[] data, Index index) {
        return ((long)(data[index.getAndAdd()] & 255) << 24) + (long)((data[index.getAndAdd()] & 255) << 16) + (long)((data[index.getAndAdd()] & 255) << 8) + (long)(data[index.getAndAdd()] & 255);
    }

    public static long parseDWordLittleEndian(byte[] data, Index index) {
        return (long)(data[index.getAndAdd()] & 255) + (long)((data[index.getAndAdd()] & 255) << 8) + (long)((data[index.getAndAdd()] & 255) << 16) + (long)((data[index.getAndAdd()] & 255) << 24);
    }

    public static long parseDWordLittleEndianSign(byte[] data, Index index) {
        long low = (long)(data[index.getAndAdd()] & 255) + (long)((data[index.getAndAdd()] & 255) << 8) + (long)((data[index.getAndAdd()] & 255) << 16);
        int sign = (data[index.get()] & 255) >> 7 == 1 ? -1 : 1;
        long high = (long)((data[index.getAndAdd()] & 127) << 24);
        return (low + high) * (long)sign;
    }

    public static int parseWordLittleEndian(byte[] data, Index index) {
        return (data[index.getAndAdd()] & 255) + ((data[index.getAndAdd()] & 255) << 8);
    }

    public static int parseWordLittleEndianSign(byte[] data, Index index) {
        return (short)(data[index.getAndAdd()] & 255 | (data[index.getAndAdd()] & 255) << 8);
    }

    public static byte[] arrayCopy(byte[] data, Index index, int dstArrLen) {
        byte[] byteArr = new byte[dstArrLen];
        System.arraycopy(data, index.get(), byteArr, 0, byteArr.length);
        index.add(byteArr.length);
        return byteArr;
    }

    public static byte[] arrayCopyString(byte[] data, Index index, int dstArrLen) {
        byte[] byteArr = new byte[dstArrLen - 1];
        System.arraycopy(data, index.get(), byteArr, 0, dstArrLen - 1);
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

    public static void putWordLittleEndian(byte[] data, int value, Index index) {
        data[index.getAndAdd()] = (byte)(value & 255);
        data[index.getAndAdd()] = (byte)(value >> 8 & 255);
    }

    public static void putDword(byte[] data, long value, Index index) {
        data[index.getAndAdd()] = (byte)((int)(value >> 24 & 255L));
        data[index.getAndAdd()] = (byte)((int)(value >> 16 & 255L));
        data[index.getAndAdd()] = (byte)((int)(value >> 8 & 255L));
        data[index.getAndAdd()] = (byte)((int)(value & 255L));
    }

    public static void putDwordLittleEndian(byte[] data, long value, Index index) {
        data[index.getAndAdd()] = (byte)((int)(value & 255L));
        data[index.getAndAdd()] = (byte)((int)(value >> 8 & 255L));
        data[index.getAndAdd()] = (byte)((int)(value >> 16 & 255L));
        data[index.getAndAdd()] = (byte)((int)(value >> 24 & 255L));
    }

    public static void putArray(byte[] data, byte[] subArr, Index index) {
        byte[] arr$ = subArr;
        int len$ = subArr.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            data[index.getAndAdd()] = b;
        }

    }

    public static void putOrder(byte[] data, int order, Index index) {
        Calendar cal = Calendar.getInstance();
        putByte(data, cal.get(1) % 2000, index);
        putByte(data, cal.get(2) + 1, index);
        putByte(data, cal.get(5), index);
        putByte(data, cal.get(11), index);
        putByte(data, cal.get(12), index);
        putByte(data, cal.get(13), index);
        putByte(data, order, index);
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

    public static int bcdDec(byte b) {
        return Integer.parseInt(Integer.toString(b & 255, 16), 10);
    }

    public static int bcdDec(byte[] data, Index index) {
        return bcdDec(parseByte(data, index));
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

    public static byte[] calendar2bcd(byte[] data, Calendar cal, Index index) {
        String str = String.valueOf(cal.get(1) - 2000);
        data[index.getAndAdd()] = (byte)Integer.parseInt(str, 16);
        str = cal.get(2) + 1 + "";
        data[index.getAndAdd()] = (byte)Integer.parseInt(str, 16);
        str = cal.get(5) + "";
        data[index.getAndAdd()] = (byte)Integer.parseInt(str, 16);
        str = cal.get(11) + "";
        data[index.getAndAdd()] = (byte)Integer.parseInt(str, 16);
        str = cal.get(12) + "";
        data[index.getAndAdd()] = (byte)Integer.parseInt(str, 16);
        str = cal.get(13) + "";
        data[index.getAndAdd()] = (byte)Integer.parseInt(str, 16);
        return data;
    }

    public static Date bcd2Date(byte[] data, Index index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000 + bcdDec(data[index.getAndAdd()]), bcdDec(data[index.getAndAdd()]) - 1, bcdDec(data[index.getAndAdd()]), bcdDec(data[index.getAndAdd()]), bcdDec(data[index.getAndAdd()]), bcdDec(data[index.getAndAdd()]));
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date bytes2Date(byte[] data, Index index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000 + data[index.getAndAdd()], data[index.getAndAdd()] - 1, data[index.getAndAdd()], data[index.getAndAdd()], data[index.getAndAdd()], data[index.getAndAdd()]);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static String getString(byte[] data, Index index, int len) {
        return getString2(data, index, len);
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

    public static long getLongOrder(byte[] data) {
        try {
            BigInteger bigInteger = new BigInteger(1, data);
            return bigInteger.longValue();
        } catch (Exception var2) {
            return 0L;
        }
    }

    public static byte[] getBytes(String data, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    public static int getBit(int i, int idx) {
        return i >> idx & 1;
    }

    public static int getBit(long l, int idx) {
        return (int)(l >> idx & 1L);
    }

    public static double lnglatTran(int degree, double min, double sec) {
        return BigDecimal.valueOf(sec).divide(BigDecimal.valueOf(60L), 7, RoundingMode.HALF_UP).add(BigDecimal.valueOf(min)).divide(BigDecimal.valueOf(60L), 7, RoundingMode.HALF_UP).add(BigDecimal.valueOf((long)degree)).doubleValue();
    }

    public static final BigInteger parseByteToBigInteger(byte[] bytes, Index index, int length) {
        byte[] data = new byte[length];
        int startPos = index.get();
        System.arraycopy(bytes, startPos, data, 0, length);
        index.add(length);
        BigInteger bigInteger = new BigInteger(1, data);
        return bigInteger;
    }

    public static int getCrcItu(byte[] bytes) {
        int crc = 65535;
        byte[] arr$ = bytes;
        int len$ = bytes.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            crc = crc >>> 8 ^ table[(crc ^ b) & 255];
        }

        return ~crc;
    }

    public static boolean checkCrcItu(byte[] bytes, int passRes) {
        int calres = getCrcItu(bytes);
        int calResVal = calres & '\uffff';
        logger.info("calResVal:{}", calResVal);
        return calResVal == passRes;
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

    public static String parseByte2Bcd(byte[] data, Index index, int len) {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < len; ++i) {
            try {
                int temp = bcdDec(data[index.getAndAdd()]);
                if (temp < 10) {
                    result.append("0");
                }

                result.append(temp);
            } catch (Exception var6) {
                logger.warn("parseByte2Bcd error: {}", var6.getMessage(), var6);
                index.add(len - i - 1);
                result.delete(0, result.length());
                break;
            }
        }

        return result.toString();
    }

    public static String getString2(byte[] data, Index index, int len) {
        String result = null;
        if (len < 1) {
            return null;
        } else {
            try {
                byte[] byteArr = new byte[len];
                System.arraycopy(data, index.get(), byteArr, 0, len);
                index.add(len);

                for(int i = byteArr.length - 1; i >= 0 && byteArr[i] == -1; --i) {
                    byteArr[i] = 0;
                }

                result = new String(byteArr, "GBK");
            } catch (ArrayIndexOutOfBoundsException var6) {
                logger.warn(var6.getMessage(), var6);
            } catch (Exception var7) {
                logger.error(var7.getMessage(), var7);
            }

            return result.trim();
        }
    }

    public static String getString3(byte[] data, Index index, int len) {
        String result = null;
        if (len < 1) {
            return null;
        } else {
            try {
                byte[] byteArr = new byte[len];
                System.arraycopy(data, index.get(), byteArr, 0, len);
                index.add(len);
                result = new String(byteArr, "GBK");
            } catch (Exception var5) {
                logger.error(var5.getMessage(), var5);
            }

            return result;
        }
    }

    public static void main(String[] arg) {
        byte[] data = new byte[]{116, 101, 115, 116, -1};
        String value = getString(data, new Index(), 5);
        System.out.println(value);
    }

    public static int getLength(byte[] data, Index index) {
        int flagBit = (data[index.get()] & 255) >> 7;
        int result;
        if (flagBit == 1) {
            result = ((data[index.getAndAdd()] & 127) << 8) + (data[index.getAndAdd()] & 255);
        } else {
            result = data[index.getAndAdd()] & 127;
        }

        return result;
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

    public static void putByteArr(byte[] data, byte[] value, Index index) {
        byte[] arr$ = value;
        int len$ = value.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            data[index.getAndAdd()] = b;
        }

    }

    public static String getBytesStr(byte[] data, Index index, int len) {
        try {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < len; ++i) {
                String tmp = Integer.toHexString(parseByte2Int(data, index));
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

    public static byte[] parseLongToBytes(long data) {
        byte[] bytes = new byte[]{(byte)((int)(data >> 56 & 255L)), (byte)((int)(data >> 48 & 255L)), (byte)((int)(data >> 40 & 255L)), (byte)((int)(data >> 32 & 255L)), (byte)((int)(data >> 24 & 255L)), (byte)((int)(data >> 16 & 255L)), (byte)((int)(data >> 8 & 255L)), (byte)((int)(data & 255L))};
        return bytes;
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

    public static double parseLat(byte[] data, Index index) {
        double lat = (double)parseDWord(data, index) / POWER_6;
        return lat;
    }

    public static double parseLng(byte[] data, Index index) {
        double lng = (double)parseDWord(data, index) / POWER_6;
        return lng;
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

    public static double getFormatedVal(double src, double unit) {
        int length = 2;
        if (unit == 1.0E-6D) {
            length = 7;
        }

        return getFormatedVal(src, unit, length);
    }

    public static String getFormatHdVer(int hdver) {
        StringBuilder hdVerStr = new StringBuilder();
        hdVerStr.append(hdver >> 4 & 15);
        hdVerStr.append(".");
        hdVerStr.append(hdver & 15);
        return hdVerStr.toString();
    }

    public static final String dumpHex(byte[] data, boolean toLowerCase) {
        return new String(encodeHex(data, toLowerCase));
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        int n = l * 3 - 1;
        char[] out = new char[n];
        int i = 0;

        for(int j = 0; i < l; ++i) {
            out[j++] = toDigits[(240 & data[i]) >>> 4];
            out[j++] = toDigits[15 & data[i]];
            if (j < n) {
                out[j++] = ' ';
            }
        }

        return out;
    }

    public static byte checksum(byte[] msg, int pos) {
        byte b = msg[pos];
        int length = msg.length;

        for(int i = pos + 1; i < length - 2; ++i) {
            b ^= msg[i];
        }

        return b;
    }

    public static int parseCanByte2Int(byte[] in, Index index) {
        int value = in[index.add()] & 255;
        return value == 255 ? -1 : value;
    }

    public static long parseCanDWord(byte[] in, Index index) {
        long value = (long)(((in[index.add()] & 255) << 24) + ((in[index.add()] & 255) << 16) + ((in[index.add()] & 255) << 8) + (in[index.add()] & 255)) & 4294967295L;
        return value == 4294967295L ? -1L : value;
    }

    public static int parseCanWord(byte[] in, Index index) {
        int value = ((in[index.add()] & 255) << 8) + (in[index.add()] & 255);
        return value == 65535 ? -1 : value;
    }

    public static long compareDateInSeconds(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return 0L;
        } else {
            DateTime dateTime1 = new DateTime(date1);
            DateTime dateTime2 = new DateTime(date2);
            dateTime1 = dateTime1.millisOfSecond().setCopy(0);
            dateTime2 = dateTime2.millisOfSecond().setCopy(0);
            return dateTime1.getMillis() - dateTime2.getMillis();
        }
    }

    public static int getInt(byte[] data) {
        return getInt((byte[])data, 0);
    }

    public static int getInt(Byte[] data) {
        return getInt((Byte[])data, 0);
    }

    public static int getInt(byte[] additionInfo, int start) {
        int ch1 = additionInfo[start] & 255;
        int ch2 = additionInfo[start + 1] & 255;
        int ch3 = additionInfo[start + 2] & 255;
        int ch4 = additionInfo[start + 3] & 255;
        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
    }

    public static int getInt(Byte[] additionInfo, int start) {
        int ch1 = additionInfo[start] & 255;
        int ch2 = additionInfo[start + 1] & 255;
        int ch3 = additionInfo[start + 2] & 255;
        int ch4 = additionInfo[start + 3] & 255;
        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
    }

    public static int[] parseEmsbus(byte[] in, Index index) {
        byte data = in[index.add()];
        int[] emsbus = new int[]{data & 1, data >> 1 & 1, data >> 2 & 1, data >> 3 & 1};
        return emsbus;
    }

    public static String parseByteVer(byte[] in, Index index) {
        StringBuilder sb = new StringBuilder("v");
        int ver = parseByte2Int(in, index);
        sb.append(ver >> 4 & 15);
        sb.append(".");
        sb.append(ver & 15);
        return sb.toString();
    }

    public static String parseSoftVer(byte[] in, Index index) {
        StringBuilder sb = new StringBuilder("v");
        sb.append(in[index.add()] & 255).append(".").append(in[index.add()] & 255).append(".").append(in[index.add()] & 255).append(".").append(in[index.add()] & 255);
        return sb.toString();
    }

    public static void arrayCopy(byte[] data, byte[] sub, Index index) {
        byte[] arr$ = sub;
        int len$ = sub.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            data[index.add()] = b;
        }

    }

    public static byte[] getMsbWord(int data) {
        byte[] value = new byte[2];
        if (data < 0) {
            data = Math.abs(data);
            value[0] = (byte)(data >> 8 & 127 | 128);
        } else {
            value[0] = (byte)(data >> 8 & 255);
        }

        value[1] = (byte)(data & 255);
        return value;
    }

    public static byte[] getWord(int data) {
        byte[] value = new byte[]{(byte)(data >> 8 & 255), (byte)(data & 255)};
        return value;
    }

    public static byte[] getDWord(long data) {
        byte[] value = new byte[]{(byte)((int)(data >> 24 & 255L)), (byte)((int)(data >> 16 & 255L)), (byte)((int)(data >> 8 & 255L)), (byte)((int)(data & 255L))};
        return value;
    }

    public static byte getByte(int data) {
        return (byte)(data & 255);
    }

    public static byte getMsbByte(int data) {
        byte value;
        if (data < 0) {
            data = Math.abs(data);
            value = (byte)(data & 127 | 128);
        } else {
            value = (byte)(data & 255);
        }

        return value;
    }

    public static byte[] calendar2bcd(Calendar cal) {
        byte[] ba = new byte[6];
        String str = String.valueOf(cal.get(1) - 2000);
        ba[0] = (byte)Integer.parseInt(str, 16);
        str = cal.get(2) + 1 + "";
        ba[1] = (byte)Integer.parseInt(str, 16);
        str = cal.get(5) + "";
        ba[2] = (byte)Integer.parseInt(str, 16);
        str = cal.get(11) + "";
        ba[3] = (byte)Integer.parseInt(str, 16);
        str = cal.get(12) + "";
        ba[4] = (byte)Integer.parseInt(str, 16);
        str = cal.get(13) + "";
        ba[5] = (byte)Integer.parseInt(str, 16);
        return ba;
    }

    public static String getFormatSoftVer(long result) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(result >> 24 & 255L)).append(".");
        sb.append(String.valueOf(result >> 16 & 255L)).append(".");
        sb.append(String.valueOf(result >> 8 & 255L)).append(".");
        sb.append(String.valueOf(result & 255L));
        return sb.toString();
    }

    public static void putPlvLength(byte[] data, int value, Index index) {
        if (value >= 128) {
            putByte(data, (value & '\uffff') >>> 8 | 128, index);
            putByte(data, value & 255, index);
        } else {
            putByte(data, value & 255, index);
        }

    }

    public static byte[] transPlvLenToBytes(int value) {
        return value < 128 ? new byte[]{(byte)(value & 255)} : new byte[]{(byte)(value >> 8 | 128), (byte)(value & 255)};
    }

    public static byte[] transfer2Bytes(String str) {
        String[] ss = str.split(" ");
        byte[] in = null;
        int i;
        if (ss.length == 1) {
            in = new byte[str.length() / 2];

            for(i = 0; i < in.length; ++i) {
                in[i] = (byte)Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
        } else {
            in = new byte[ss.length];

            for(i = 0; i < in.length; ++i) {
                in[i] = (byte)Integer.parseInt(ss[i], 16);
            }
        }

        return in;
    }
}
