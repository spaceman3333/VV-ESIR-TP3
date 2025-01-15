package fr.istic.vv;

public class StringUtils {

    private StringUtils() {}

    public static boolean isBalanced(String str) {
        String prev;
        do {
            prev = str;
            str = str.replace("()", "").replace("[]", "").replace("{}", "");
        } while (!str.equals(prev));
        return str.isEmpty();
    }

}
