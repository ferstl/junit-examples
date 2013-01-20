package com.github.ferstl.junitexamples.theory;

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
          int expected = 100 * user.iPermission + 10 * group.iPermission + other.iPermission;
          data.add(new Object[] {user.sPermission, group.sPermission, other.sPermission, expected});
        }
      }
    }
    return data;
  }

  private final String userPermission;
  private final String groupPermission;
  private final String otherPermission;
  private final int expected;


  public TestWithParameters(String userPermission, String groupPermission, String otherPermission, int expected) {
    this.userPermission = userPermission;
    this.groupPermission = groupPermission;
    this.otherPermission = otherPermission;
    this.expected = expected;
  }


  @Test
  public void fromString() {
    int permission = UnixFilePermissions.fromString(this.userPermission + this.groupPermission + this.otherPermission);
    assertEquals(this.expected, permission);
  }

}
