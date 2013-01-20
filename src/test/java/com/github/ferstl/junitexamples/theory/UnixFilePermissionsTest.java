package com.github.ferstl.junitexamples.theory;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Theories.class)
public class UnixFilePermissionsTest {

  @DataPoints
  public static final Permission[] dataPoints = Permission.values();

  @Theory
  public void fromString(Permission user, Permission group, Permission other) {
    String permissionString = user.sPermission + group.sPermission + other.sPermission;
    int expected = 100 * user.iPermission + 10 * group.iPermission + other.iPermission;
    assertThat(UnixFilePermissions.fromString(permissionString), is(expected));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidFormat() {
    UnixFilePermissions.fromString("invalid");
  }

  @Test(expected = NullPointerException.class)
  public void nullTest() {
    UnixFilePermissions.fromString(null);
  }


  static enum Permission {

    ___(0, "---"),
    __X(1, "--x"),
    _W_(2, "-w-"),
    _WX(3, "-wx"),
    R__(4, "r--"),
    R_X(5, "r-x"),
    RW_(6, "rw-"),
    RWX(7, "rwx");

    private final int iPermission;
    private final String sPermission;

    private Permission(int iPermission, String sPermission) {
      this.iPermission = iPermission;
      this.sPermission = sPermission;
    }

    @Override
    public String toString() {
      return this.sPermission;
    }
  }
}
