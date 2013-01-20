package com.github.ferstl.junitexamples.theory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;


public class RuleExampleTest {

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void testNull() {
    this.exceptionRule.expect(NullPointerException.class);
    UnixFilePermissions.fromString(null);
  }

  @Test
  public void testInvalidPermissionString() {
    String invalidArgument = "xxx---xxx";

    this.exceptionRule.expect(IllegalArgumentException.class);
    this.exceptionRule.expectMessage(containsString(invalidArgument));
    UnixFilePermissions.fromString(invalidArgument);
  }
}
