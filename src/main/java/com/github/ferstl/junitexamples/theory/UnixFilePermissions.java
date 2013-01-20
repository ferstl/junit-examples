package com.github.ferstl.junitexamples.theory;

import java.util.regex.Pattern;


public final class UnixFilePermissions {

  private static final Pattern PERMISSION_PATTERN = Pattern.compile("([r-][w-][x-]){3}");

  public static int fromString(String permissionString) {
    if (permissionString == null) {
      throw new NullPointerException("Permission string must not be null");
    }
    if (!PERMISSION_PATTERN.matcher(permissionString).matches()) {
      throw new IllegalArgumentException(
          "Invalid permission string: " + permissionString + ". Must match " + PERMISSION_PATTERN);
    }

    int permission = 0;
    char[] chars = permissionString.toCharArray();
    for (int i = 0; i < chars.length; i += 3) {
      int group = 0;
      for (int j = i; j < i + 3; j++) {
        if (chars[j] != '-') {
          group |= (1 << (2 - j + i));
        }
      }
      permission += group * Math.pow(10, 2 - i / 3);
    }
    return permission;
  }

  private UnixFilePermissions() {
    throw new AssertionError("not instantiatable");
  }
}
