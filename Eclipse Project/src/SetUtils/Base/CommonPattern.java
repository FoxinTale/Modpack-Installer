package SetUtils.Base;

import SetUtils.Annotations.GwtCompatible;

@GwtCompatible
abstract class CommonPattern {

  public abstract String pattern();
  public abstract int flags();

  @Override
  public abstract String toString();

}
