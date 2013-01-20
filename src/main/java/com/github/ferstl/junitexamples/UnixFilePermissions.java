package com.github.ferstl.junitexamples;

import java.util.regex.Pattern;

/**
 * Utility class to convert unix file permission strings (like {@code rwxr-xr--}) into their numeric
 * representation (like {@code 0754}) and vice versa.
 */
public final class UnixFilePermissions {

  private static final char[] CHAR_POSITIONS = { 'r', 'w', 'x' };
  private static final int VALID_PERMISSION_MASK = 0xFF_FF_FE_00;
  private static final Pattern PERMISSION_PATTERN = Pattern.compile("([r-][w-][x-]){3}");

  /**
   * Transforms a Unix file permission string (e.g. {@code rwxr-xr--}) into its numeric
   * representation (e.g. {@code 0754}).
   * @param permissionString Permission string.
   * @return The numeric representation.
   */
  public static int numericValue(String permissionString) {
    if (permissionString == null) {
      throw new NullPointerException("Permission string must not be null");
    }
    if (!PERMISSION_PATTERN.matcher(permissionString).matches()) {
      throw new IllegalArgumentException(
          "Invalid permission string: " + permissionString + ". Must match " + PERMISSION_PATTERN);
    }

    char[] chars = permissionString.toCharArray();
    int permission = 0;
    for (int i = 0; i < chars.length; i++) {
      permission <<= 1;
      if (chars[i] != '-') {
        permission |= 1;
      }
    }
    return permission;
  }

  /**
   * Transforms the numeric representation of a Unix file permission (e.g. {@code 0754}) into its
   * string representation (e.g. {@code rwxr-xr--}).
   * @param permission Numeric permission.
   * @return The string representation.
   */
  public static String stringValue(int permission) {
    if ((permission & VALID_PERMISSION_MASK) != 0) {
      throw new IllegalArgumentException("Invalid permission: " + permission);
    }

    StringBuilder sb = new StringBuilder();
    int mask = 0400;
    for(int i = 0; i < 9; i++) {
      if ((permission & mask) != 0) {
        sb.append(CHAR_POSITIONS[i % 3]);
      } else {
        sb.append('-');
      }
      mask >>= 1;
    }
    return sb.toString();
  }

  private UnixFilePermissions() {
    throw new AssertionError("not instantiatable");
  }
}
