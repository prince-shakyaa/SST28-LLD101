public class StringNormalizer {
    public static String truncate(String s, int max) {
        if (s == null)
            return s;
        return s.length() > max ? s.substring(0, max) : s;
    }
}
