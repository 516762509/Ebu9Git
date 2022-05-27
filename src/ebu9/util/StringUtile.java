package ebu9.util;

import java.math.BigDecimal;

public class StringUtile {
    /**
     * 字符串的相加
     * @param a
     * @param b
     * @return
     */
    public static String floatAdd(String a, String b) {
        a=a.equals("")?"0":a;
        b=b.equals("")?"0":b;
        BigDecimal bg1 = new BigDecimal(a);
        BigDecimal bg2 = new BigDecimal(b);
        BigDecimal bd = bg1.add(bg2);
        return bd.toString();
    }
    /**
     * 字符串的相减
     * @param a
     * @param b
     * @return
     */
    public static String floatSubtract(String a, String b) {
        a=a.equals("")?"0":a;
        b=b.equals("")?"0":b;
        BigDecimal bg1 = new BigDecimal(a);
        BigDecimal bg2 = new BigDecimal(b);
        BigDecimal bd = bg1.subtract(bg2);
        return bd.toString();
    }

    /**
     * 字符串相除 ，保留两位小数
     * @param a
     * @param b
     * @return
     */
    public static String floatDivide(String a, String b) {
        a=a.equals("")?"0":a;
        b=b.equals("")?"0":b;
        BigDecimal bg1 = new BigDecimal(a);
        BigDecimal bg2 = new BigDecimal(b);
        BigDecimal bd = bg1.divide(bg2, 2, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }

    /**
     * 字符串相除，保留任意位数
     * @param a
     * @param b
     * @param n
     * @return
     */
    public static String floatDivide(String a, String b, int n) {
        a=a.equals("")?"0":a;
        b=b.equals("")?"0":b;
        BigDecimal bg1 = new BigDecimal(a);
        BigDecimal bg2 = new BigDecimal(b);
        BigDecimal bd = bg1.divide(bg2, n, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }

    /**
     * 字符串想乘，保留两位小数
     * @param a
     * @param b
     * @return
     */
    public static String floatMultiply(String a, String b) {
        a=a.equals("")?"0":a;
        b=b.equals("")?"0":b;
        BigDecimal bg1 = new BigDecimal(a);
        BigDecimal bg2 = new BigDecimal(b);
        BigDecimal bd = bg1.multiply(bg2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
