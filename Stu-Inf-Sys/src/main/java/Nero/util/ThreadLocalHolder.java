package Nero.util;

public class ThreadLocalHolder {
    private static ThreadLocal<Integer> TOTAL = new ThreadLocal<>();

    public static ThreadLocal<Integer> getTOTAL() {
        return TOTAL;
    }

   /* public static void setTOTAL(ThreadLocal<Integer> TOTAL) {
        ThreadLocalHolder.TOTAL = TOTAL;
    }*/
}
