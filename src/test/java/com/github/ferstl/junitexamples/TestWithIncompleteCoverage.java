package com.github.ferstl.junitexamples;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
@Ignore
public class TestWithIncompleteCoverage {

  @ClassRule
  public static final InvocationCountRule invocationCountRule = new InvocationCountRule();

  @Parameters(name = "{index}: {0}{1}{2} -> {3}")
  public static Iterable<Object[]> data() {
    Collection<Object[]> data = new ArrayList<>(512);
    for (Permission user : Permission.values()) {
      for (Permission group : Permission.values()) {
        // Oops, not all combinations of group permissions are covered!
        int expected = 64 * user.iPermission + 8 * group.iPermission + Permission.___.iPermission;
        data.add(new Object[]{user.sPermission, group.sPermission, Permission.___.sPermission, expected});
      }
    }
    return data;
  }

  @BeforeClass
  public static void init() {
    invocationCountRule.register("numericValue", 512);
  }

  private final String userPermission;
  private final String groupPermission;
  private final String otherPermission;
  private final int numeric;


  public TestWithIncompleteCoverage(String userPermission, String groupPermission, String otherPermission, int numeric) {
    this.userPermission = userPermission;
    this.groupPermission = groupPermission;
    this.otherPermission = otherPermission;
    this.numeric = numeric;
  }


  // Fails because not all possible permission combinations are tested.
  @Test
  public void numericValue() {
    invocationCountRule.countDown("numericValue");
    int permission = UnixFilePermissions.numericValue(
        this.userPermission + this.groupPermission + this.otherPermission);
    assertEquals(this.numeric, permission);
  }

}
