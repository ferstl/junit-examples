package com.github.ferstl.junitexamples;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.ferstl.junitexamples.UnixFilePermissions;

import static org.hamcrest.Matchers.containsString;


public class TestWithRules {

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void testNull() {
    this.exceptionRule.expect(NullPointerException.class);
    UnixFilePermissions.numericValue(null);
  }

  @Test
  public void testInvalidPermissionString() {
    String invalidArgument = "xxx---xxx";

    this.exceptionRule.expect(IllegalArgumentException.class);
    this.exceptionRule.expectMessage(containsString(invalidArgument));
    UnixFilePermissions.numericValue(invalidArgument);
  }
}
