package ebu9.util;

import java.util.UUID;

public class UUIDUtils {
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        String s = UUIDUtils.randomUUID();
        System.out.println(s);
        String s1 ="818D1424-6C70-4BDA-8DC5-81B279D110D2";
        System.out.println(s1);
    }
}

