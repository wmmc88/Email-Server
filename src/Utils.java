/**
 * Created by Melvin Wang on 2017-02-23.
 */
public class Utils {
    public static String generateRandomCode(int len) {
        String code = "";
        for (int i = 0; i < len; i++) {
            code += (int) (Math.random() * 10);
        }
        return code;
    }

    public static boolean isANumber(String s) {
        for (int i = 0, j = s.length(); i < j; i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static String leftPad(String text, int desiredLength, char paddingItem) {
        if (text.length() >= desiredLength) {
            return text;
        } else {
            String padding = "";
            for (int i = 0, j = desiredLength - text.length(); i < j; i++) {
                padding += paddingItem;
            }
            return padding + text;
        }
    }

    public static String removeChars(String text, char c) {
        String newText = "";
        for (int i = 0, j = text.length(); i < j; i++) {
            if (text.charAt(i) != c) {
                newText += text.charAt(i);
            }
        }
        return newText;
    }

    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println("Process error in delay method.");
        }
    }
}
