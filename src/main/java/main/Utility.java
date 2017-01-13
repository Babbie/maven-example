package main;

/**
 * Collection of static utility methods.
 */
public abstract class Utility {
    public static boolean isValidIP(String ip) {
        if (ip.equals("localhost")) {
            return true;
        }

        if (ip.isEmpty()) {
            return false;
        }

        if (ip.endsWith(".")) {
            return false;
        }

        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        try {
            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean isValidPort(String port) {
        try {
            int i = Integer.parseInt(port);
            if ((i < 1024) || (i > 65535)) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
