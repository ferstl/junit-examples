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
}
