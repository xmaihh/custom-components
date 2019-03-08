package tp.custom_components.project.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;

/**
 * 一个byte数据的工具类，可供java和Android使用
 **/
public class ByteUtil {

    private static final String TAG = ByteUtil.class.getSimpleName();

    /**
     * 将byte[]数据转换为16进制的字符串，如byte[]{0x11, 0xaa, 0xbb, 0xcc}转换成"11aabbcc"
     * 默认转换出来的字符串全部为小写
     *
     * @param src byte数组
     * @return hex字符串
     */
    public static String toHex(byte[] src) {
        if (src == null || src.length == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = src.length; i < len; i++) {
            builder.append(String.format("%02x", src[i]));
        }
        return builder.toString();
    }

    /**
     * 将byte[]数据转换为16进制的字符串，如byte[]{0x11, 0xaa, 0xbb, 0xcc}转换成"11aabbcc"
     * 默认转换出来的字符串全部为小写 从开始位置到byte数组的结尾
     *
     * @param src   byte数组
     * @param start 开始位置
     * @return hex字符串
     */
    public static String toHex(byte[] src, int start) {
        if (src == null || src.length == 0) {
            return null;
        }
        return toHex(src, start, src.length);
    }

    /**
     * 将byte[]数据转换为16进制的字符串，如byte[]{0x11, 0xaa, 0xbb, 0xcc}转换成"11aabbcc"
     * 默认转换出来的字符串全部为小写 从开始位置到结束位置
     *
     * @param src   byte数组
     * @param start 开始位置
     * @param end   结束位置
     * @return hex字符串
     */
    public static String toHex(byte[] src, int start, int end) {
        if (src == null || src.length == 0) {
            return null;
        }
        if (start > end) {
            throw new IllegalArgumentException();
        }
        if (start < 0 || end > src.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            builder.append(String.format("%02x", src[i]));
        }
        return builder.toString();
    }

    /**
     * 将一个int值的前byteLength个字节转换为十六进制的字符，比如0xab转换成"ab"
     *
     * @param src        要转换的int值
     * @param byteLength 转换前几个字节，范围[1,4]，否则会返回null值
     * @return hex字符串
     */
    public static String toHex(int src, int byteLength) {
        switch (byteLength) {
            case 1:
                return String.format("%02x", src & 0xff);
            case 2:
                return String.format("%04x", src & 0xffff);
            case 3:
                return String.format("%06x", src & 0xffffff);
            case 4:
                return String.format("%08x", src & 0xffffffff);
        }
        return null;
    }

    private static final String HEX_STR = "0123456789abcdefABCDEF";

    /**
     * 将16进制的字符串转换为byte[]，如"11aabbcc"转换成byte[]{0x11, 0xaa, 0xbb, 0xcc}
     * 函数会过滤字符串中不合法的字符
     *
     * @param hex 16进制的字符串，不区分大小写
     * @return byte数组结果
     */
    public static byte[] fromHex(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = hex.length(); i < len; i++) {
            char c = hex.charAt(i);
            if (HEX_STR.indexOf(c) != -1) {
                builder.append(c);
            }
        }
        String hexSrc = builder.toString();
        int strLength = hexSrc.length();
        byte[] result = new byte[strLength / 2];
        for (int i = 0, len = strLength / 2; i < len; i++) {
            result[i] = (byte) Integer.parseInt(hexSrc.substring(i * 2, i * 2 + 2), 16);
        }
        return result;
    }

    /**
     * 将short转成2个字节byte[]
     * 如(short)0xabcd转换成byte[]{0xab, 0xcd}
     *
     * @param s short数据
     * @return 转换后的两个字节byte数组
     */
    public static byte[] fromShort(short s) {
        byte[] ret = new byte[2];
        ret[0] = (byte) ((s >> 8) & 0xff);
        ret[1] = (byte) (s & 0xff);
        return ret;
    }

    public static short toShort(byte b1, byte b2) {
        short s1 = (short) ((b1 & 0xff) << 8);
        short s2 = (short) (b2 & 0xff);
        short ret = (short) (s1 | s2);
        return ret;
    }

    /**
     * 将int转换为4个字节byte[]数组
     *
     * @param i integer数据
     * @return byte数组，共四个字节
     */
    public static byte[] fromInt(int i) {
        byte[] ret = new byte[4];
        ret[0] = (byte) ((i >> 24) & 0xff);
        ret[1] = (byte) ((i >> 16) & 0xff);
        ret[2] = (byte) ((i >> 8) & 0xff);
        ret[3] = (byte) (i & 0xff);
        return ret;
    }

    /**
     * 将四个byte组合成一个int值
     *
     * @param b1 byte1
     * @param b2 byte2
     * @param b3 byte3
     * @param b4 byte4
     * @return int值结果
     */
    public static int toInt(byte b1, byte b2, byte b3, byte b4) {
        int ret = (((b1 & 0xff) << 24) | ((b2 & 0xff) << 16) | ((b3 & 0xff) << 8) | (b4 & 0xff));
        return ret;
    }

    /**
     * 将多个参数组合成一个byte[]
     * 现在支持String、Byte、Short、Integer、Long、byte[]
     * <b>注意：Byte、Short、Integer、Long都会被当做byte处理</b>
     *
     * @param args 可以包括byte、int、short、long、byte[]、String
     * @return byte数组结果
     */
    public static byte[] combine(Object... args) {
        if (args == null || args.length == 0) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                continue;
            }
            try {
                if (args[i] instanceof String) {
                    String hex = (String) args[i];
                    byte[] bytearr = fromHex(hex);
                    if (bytearr != null) {
                        baos.write(bytearr);
                    }
                } else if (args[i] instanceof byte[]) {
                    byte[] ba = (byte[]) args[i];
                    baos.write(ba);
                } else if (args[i] instanceof Byte) {
                    Byte b = (Byte) args[i];
                    int num = b & 0xff;
                    baos.write(num);
                } else if (args[i] instanceof Short) {
                    Short s = (Short) args[i];
                    int num = s & 0xff;
                    baos.write(num);
                } else if (args[i] instanceof Integer) {
                    Integer s = (Integer) args[i];
                    int num = s & 0xff;
                    baos.write(num);
                } else if (args[i] instanceof Long) {
                    Long s = (Long) args[i];
                    int num = (int) (s & 0xff);
                    baos.write(num);
                } else {
                    Log.w(TAG, "不被支持的参数[" + i + "]");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }

    /**
     * String转Byte数组
     *
     * @param str
     * @return
     */
    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }

    /**
     * Byte数组转String
     *
     * @param byteArray
     * @return
     */
    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }

    /**
     * Byte数组转十六进制String
     *
     * @param byteArray
     * @return
     */
    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 十六进制String转Byte数组
     *
     * @param str
     * @return
     */
    public static byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    /**
     * 字符串md5编码
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10)
                    hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return string;
        }
    }

    /**
     * 将int数值转换为占四个字节的Byte数组
     *
     * @param intVal    int 要转换的int值
     * @param byteOrder ByteOrder 大小端模式
     * @return Byte[]
     */
    public static Byte[] int2Bytes(int intVal, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(byteOrder);
        buffer.asIntBuffer().put(intVal);
        byte[] array = buffer.array();
        return bytes2Bytes(array);
    }
    /**
     * 将int数值转换为占四个字节的byte数组
     *
     * @param intVal    int 要转换的int值
     * @param byteOrder ByteOrder 大小端模式
     * @return byte[]
     */
    public static byte[] int2bytes(int intVal, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(byteOrder);
        buffer.asIntBuffer().put(intVal);
        return buffer.array();
    }

    /**
     * 取四个字节的byte数组所代表的int值
     *
     * @param bytes     byte[]
     * @param byteOrder ByteOrder 大小端模式
     * @return int
     */
    private static int bytes2int(byte[] bytes, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(byteOrder);
        return buffer.getInt();
    }

    /**
     * 将short数值转换为占两个字节的Byte数组
     *
     * @param shortVal  short 要转换的short值
     * @param byteOrder ByteOrder 大小端模式
     * @return Byte[]
     */
    public static Byte[] short2Bytes(short shortVal, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(byteOrder);
        buffer.asShortBuffer().put(shortVal);
        byte[] array = buffer.array();
        return bytes2Bytes(array);
    }

    /**
     * 将short数值转换为占两个字节的byte数组
     *
     * @param shortVal  short 要转换的short值
     * @param byteOrder ByteOrder 大小端模式
     * @return byte[]
     */
    public static byte[] short2bytes(short shortVal, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(byteOrder);
        buffer.asShortBuffer().put(shortVal);
        return buffer.array();
    }

    /**
     * 取两个字节的byte数组所代表的short值
     *
     * @param bytes     byte[]
     * @param byteOrder ByteOrder 大小端模式
     * @return short
     */
    public static short bytes2short(byte[] bytes, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(byteOrder);
        return buffer.getShort();
    }

    /**
     * 将byte[]转为Byte[]
     *
     * @param array byte[]
     * @return Byte[]
     */
    public static Byte[] bytes2Bytes(byte[] array) {
        if (null == array) {
            return null;
        }
        Byte[] bytes = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = array[i];
        }
        return bytes;
    }

    /**
     * 将1byte转化为int
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    /**
     * * 低位在前, 高位在后, 将整型数字的每个字节保存到数组中
     * *
     * * @param value
     * * @return
     */
    public static byte[] intToBytes(int value) {
        byte[] des = new byte[4];
        des[0] = (byte) (value & 0xff);  // 低位(右边)的8个bit位
        des[1] = (byte) ((value >> 8) & 0xff); //第二个8 bit位
        des[2] = (byte) ((value >> 16) & 0xff); //第三个 8 bit位
        /**
         * * (byte)((value >> 24) & 0xFF);
         * * value向右移动24位, 然后和0xFF也就是(11111111)进行与运算
         * * 在内存中生成一个与 value 同类型的值
         * * 然后把这个值强制转换成byte类型, 再赋值给一个byte类型的变量 des[3]
         * */
        des[3] = (byte) ((value >> 24) & 0xff); //第4个 8 bit位
        return des;
    }

    /**
     * * 将上面转成的byte数组转换成int原始值
     * * @param des
     * * @param offset
     * * @return
     */
    public static int bytesToInt(byte[] des, int offset) {
        int value;
        value = (int) (des[offset] & 0xff
                | ((des[offset + 1] & 0xff) << 8)
                | ((des[offset + 2] & 0xff) << 16)
                | (des[offset + 3] & 0xff) << 24);
        return value;
    }

    public static void main(String[] args) {
//        byte[] res = intToBytes(30);
//        System.out.println(bytesToInt(res, 0));  //30

        System.out.println("short2bytes:  " + toHex(short2bytes((short) 100, ByteOrder.LITTLE_ENDIAN)));
        System.out.println("bytes2short:  " + bytes2short(fromHex("6400"), ByteOrder.LITTLE_ENDIAN));
    }

}
