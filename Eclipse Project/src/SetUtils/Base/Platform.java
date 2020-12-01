package SetUtils.Base;

import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.Nullable;

import java.util.logging.Logger;
import java.util.regex.Pattern;


@GwtCompatible(emulated = true)
final class Platform {
  private static final Logger logger = Logger.getLogger(Platform.class.getName());
  private static final PatternCompiler patternCompiler = loadPatternCompiler();

  private Platform() {}

  static boolean stringIsNullOrEmpty(@Nullable String string) {
    return string == null || string.isEmpty();
  }

  static String nullToEmpty(@Nullable String string) {
    return (string == null) ? "" : string;
  }

  static String emptyToNull(@Nullable String string) {
    return stringIsNullOrEmpty(string) ? null : string;
  }

  static boolean patternCompilerIsPcreLike() {
    return patternCompiler.isPcreLike();
  }

  private static PatternCompiler loadPatternCompiler() {
    return new JdkPatternCompiler();
  }

  private static final class JdkPatternCompiler implements PatternCompiler {
    @Override
    public CommonPattern compile(String pattern) {
      return new JdkPattern(Pattern.compile(pattern));
    }

    @Override
    public boolean isPcreLike() {
      return true;
    }
  }

  static void checkGwtRpcEnabled() {
    String propertyName = "guava.gwt.emergency_reenable_rpc";

    if (!Boolean.parseBoolean(System.getProperty(propertyName, "false"))) {
      throw new UnsupportedOperationException(
          Strings.lenientFormat(
              "We are removing GWT-RPC support for Guava types. You can temporarily reenable"
                  + " support by setting the system property %s to true. For more about system"
                  + " properties, see %s. For more about Guava's GWT-RPC support, see %s.",
              propertyName,
              "https://stackoverflow.com/q/5189914/28465",
              "https://groups.google.com/d/msg/guava-announce/zHZTFg7YF3o/rQNnwdHeEwAJ"));
    }
    logger.log(
        java.util.logging.Level.WARNING,
        "Later in 2020, we will remove GWT-RPC support for Guava types. You are seeing this"
            + " warning because you are sending a Guava type over GWT-RPC, which will break. You"
            + " can identify which type by looking at the class name in the attached stack trace.",
        new Throwable());

  }
}
