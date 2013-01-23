package com.github.ferstl.junitexamples;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * <p>
 * Rule to count test invocations.
 * </p>
 * <p>
 * This rule was part of a short demo about JUnit rules in a very short presentation with a few
 * other topics. It fulfilled its demo purposes well but it is by far not usable in any other
 * scenario. So please pretend you haven't seen it at all and just don't use it. Oh, and it's not
 * thread-safe as well.
 * </p>
 */
public class InvocationCountRule implements TestRule {

  private final Map<String, Integer> expectedCounters;
  private final Map<String, Integer> actualCounters;


  public InvocationCountRule() {
    this.expectedCounters = new HashMap<>();
    this.actualCounters = new HashMap<>();
  }

  public void register(String key, int expectedInvocations) {
    if (!this.actualCounters.containsKey(key)) {
      this.actualCounters.put(key, expectedInvocations);
      this.expectedCounters.put(key, expectedInvocations);
    }
  }

  public void countDown(String key) {
    if (!this.actualCounters.containsKey(key)) {
      throw new IllegalStateException("Unknown counter: " + key + ". You need to call register() first.");
    }

    int counter = this.actualCounters.get(key);
    counter -= 1;
    this.actualCounters.put(key, counter);
  }

  private void verifyCounters() {
    for (Entry<String, Integer> counter : this.actualCounters.entrySet()) {
      int counterValue = counter.getValue();

      if (counterValue != 0) {
        String key = counter.getKey();
        int expectedValue = this.expectedCounters.get(key);

        throw new AssertionError(
            "Invalid invocation count for key '" + key + "'. Expected "
                + expectedValue + " invocations but got " + (expectedValue - counterValue) + ".", null);
      }
    }
  }


  @Override
  public Statement apply(final Statement base, Description description) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        // Execute the statement.
        base.evaluate();

        // Check the invocations
        verifyCounters();
      }
    };
  }
}
