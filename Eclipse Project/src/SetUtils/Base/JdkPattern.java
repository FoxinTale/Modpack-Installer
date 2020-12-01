package SetUtils.Base;

import SetUtils.Annotations.GwtIncompatible;

import java.io.Serializable;
import java.util.regex.Pattern;


@GwtIncompatible
final class JdkPattern extends CommonPattern implements Serializable {
  private final Pattern pattern;

  JdkPattern(Pattern pattern) {
    this.pattern = Preconditions.checkNotNull(pattern);
  }

  @Override
  public String pattern() {
    return pattern.pattern();
  }

  @Override
  public int flags() {
    return pattern.flags();
  }

  @Override
  public String toString() {
    return pattern.toString();
  }

  private static final long serialVersionUID = 0;
}
