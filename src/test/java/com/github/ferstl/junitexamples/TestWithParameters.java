package com.github.ferstl.junitexamples;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestWithParameters {

  @Parameters(name = "{0}{1}{2} -> {3}")
  public static Iterable<Object[]> data() {
    Collection<Object[]> data = new ArrayList<>(512);
    for (Permission user : Permission.values()) {
      for (Permission group : Permission.values()) {
        for (Permission other : Permission.values()) {
          int expected = 64 * user.iPermission + 8 * group.iPermission + other.iPermission;
          data.add(new Object[] {user.sPermission, group.sPermission, other.sPermission, expected});
        }
      }
    }
    return data;
  }

  private final String userPermission;
  private final String groupPermission;
  private final String otherPermission;
  private final int numeric;


  public TestWithParameters(String userPermission, String groupPermission, String otherPermission, int numeric) {
    this.userPermission = userPermission;
    this.groupPermission = groupPermission;
    this.otherPermission = otherPermission;
    this.numeric = numeric;
  }


  @Test
  public void numericValue() {
    int permission = UnixFilePermissions.numericValue(
        this.userPermission + this.groupPermission + this.otherPermission);
    assertEquals(this.numeric, permission);
  }

  @Test
  public void stringValue() {
    String expected = this.userPermission + this.groupPermission + this.otherPermission;
    assertEquals(expected, UnixFilePermissions.stringValue(this.numeric));
  }

}
