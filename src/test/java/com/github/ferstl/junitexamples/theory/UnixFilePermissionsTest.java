package com.github.ferstl.junitexamples.theory;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.github.ferstl.junitexamples.theory.UnixFilePermissions;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Theories.class)
public class UnixFilePermissionsTest {

  @DataPoint
  public static final Permission ___ = Permission.create(0, "---");
  @DataPoint
  public static final Permission __X = Permission.create(1, "--x");
  @DataPoint
  public static final Permission _W_ = Permission.create(2, "-w-");
  @DataPoint
  public static final Permission _WX = Permission.create(3, "-wx");
  @DataPoint
  public static final Permission R__ = Permission.create(4, "r--");
  @DataPoint
  public static final Permission R_X = Permission.create(5, "r-x");
  @DataPoint
  public static final Permission RW_ = Permission.create(6, "rw-");
  @DataPoint
  public static final Permission RWX = Permission.create(7, "rwx");


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


  static class Permission {
    private final int iPermission;
    private final String sPermission;

    public static Permission create(int iPermission, String sPermission) {
       return new Permission(iPermission, sPermission);
    }

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
