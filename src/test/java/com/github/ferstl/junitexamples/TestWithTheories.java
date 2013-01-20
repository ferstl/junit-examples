package com.github.ferstl.junitexamples;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Theories.class)
public class TestWithTheories {

  @DataPoints
  public static final Permission[] dataPoints = Permission.values();

  @Theory
  public void numericValue(Permission user, Permission group, Permission other) {
    String permissionString = user.sPermission + group.sPermission + other.sPermission;
    int expected = 64 * user.iPermission + 8 * group.iPermission + other.iPermission;
    assertThat(UnixFilePermissions.numericValue(permissionString), is(expected));
  }

  @Theory
  public void stringValue(Permission user, Permission group, Permission other) {
    int numericValue = 64 * user.iPermission + 8 * group.iPermission + other.iPermission;
    String expected = user.sPermission + group.sPermission + other.sPermission;
    assertEquals(expected, UnixFilePermissions.stringValue(numericValue));
  }
}
