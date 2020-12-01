package SetUtils.Base;

import SetUtils.Annotations.GwtIncompatible;

@GwtIncompatible
interface PatternCompiler {

  CommonPattern compile(String pattern);
  boolean isPcreLike();
}
