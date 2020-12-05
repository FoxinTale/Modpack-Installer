package SetUtils.Base;

import SetUtils.Annotations.GwtCompatible;
import SetUtils.Annotations.GwtIncompatible;

import java.util.Arrays;
import java.util.BitSet;

@GwtCompatible(emulated = true)
public abstract class CharMatcher implements Predicate<Character> {
    /*
     *           N777777777NO
     *         N7777777777777N
     *        M777777777777777N
     *        $N877777777D77777M
     *       N M77777777ONND777M
     *       MN777777777NN  D777
     *     N7ZN777777777NN ~M7778
     *    N777777777777MMNN88777N
     *    N777777777777MNZZZ7777O
     *    DZN7777O77777777777777
     *     N7OONND7777777D77777N
     *      8$M++++?N???$77777$
     *       M7++++N+M77777777N
     *        N77O777777777777$                              M
     *          DNNM$$$$777777N                              D
     *         N$N:=N$777N7777M                             NZ
     *        77Z::::N777777777                          ODZZZ
     *       77N::::::N77777777M                         NNZZZ$
     *     $777:::::::77777777MN                        ZM8ZZZZZ
     *     777M::::::Z7777777Z77                        N++ZZZZNN
     *    7777M:::::M7777777$777M                       $++IZZZZM
     *   M777$:::::N777777$M7777M                       +++++ZZZDN
     *     NN$::::::7777$$M777777N                      N+++ZZZZNZ
     *       N::::::N:7$O:77777777                      N++++ZZZZN
     *       M::::::::::::N77777777+                   +?+++++ZZZM
     *       8::::::::::::D77777777M                    O+++++ZZ
     *        ::::::::::::M777777777N                      O+?D
     *        M:::::::::::M77777777778                     77=
     *        D=::::::::::N7777777777N                    777
     *       INN===::::::=77777777777N                  I777N
     *      ?777N========N7777777777787M               N7777
     *      77777$D======N77777777777N777N?         N777777
     *     I77777$$$N7===M$$77777777$77777777$MMZ77777777N
     *      $$$$$$$$$$$NIZN$$$$$$$$$M$$7777777777777777ON
     *       M$$$$$$$$M    M$$$$$$$$N=N$$$$7777777$$$ND
     *      O77Z$$$$$$$     M$$$$$$$$MNI==$DNNNNM=~N
     *   7 :N MNN$$$$M$      $$$777$8      8D8I
     *     NMM.:7O           777777778
     *                       7777777MN
     *                       M NO .7:
     *                       M   :   M
     *                            8
     */
// This was in the original file, and I'm leaving it here.

    public static CharMatcher any() {
        return Any.INSTANCE;
    }

    public static CharMatcher none() {
        return None.INSTANCE;
    }

    @Deprecated
    public static CharMatcher digit() {
        return Digit.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaDigit() {
        return JavaDigit.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLetter() {
        return JavaLetter.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLetterOrDigit() {
        return JavaLetterOrDigit.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaUpperCase() {
        return JavaUpperCase.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLowerCase() {
        return JavaLowerCase.INSTANCE;
    }


    @Deprecated
    public static CharMatcher invisible() {
        return Invisible.INSTANCE;
    }

    @Deprecated
    public static CharMatcher singleWidth() {
        return SingleWidth.INSTANCE;
    }

    public static CharMatcher is(final char match) {
        return new Is(match);
    }

    public static CharMatcher isNot(final char match) {
        return new IsNot(match);
    }

    public static CharMatcher anyOf(final CharSequence sequence) {
        switch (sequence.length()) {
            case 0:
                return none();
            case 1:
                return is(sequence.charAt(0));
            case 2:
                return isEither(sequence.charAt(0), sequence.charAt(1));
            default:
                return new AnyOf(sequence);
        }
    }

    public static CharMatcher noneOf(CharSequence sequence) {
        return anyOf(sequence).negate();
    }

    protected CharMatcher() {
    }

    public abstract boolean matches(char c);

    @Override
    public CharMatcher negate() {
        return new Negated(this);
    }

    public CharMatcher and(CharMatcher other) {
        return new And(this, other);
    }

    public CharMatcher or(CharMatcher other) {
        return new Or(this, other);
    }

    private static final int DISTINCT_CHARS = Character.MAX_VALUE - Character.MIN_VALUE + 1;


    @GwtIncompatible
    CharMatcher precomputedInternal() {
        final BitSet table = new BitSet();
        setBits(table);
        int totalCharacters = table.cardinality();
        if (totalCharacters * 2 <= DISTINCT_CHARS) {
            return precomputedPositive(totalCharacters, table, toString());
        } else {
            table.flip(Character.MIN_VALUE, Character.MAX_VALUE + 1);
            int negatedCharacters = DISTINCT_CHARS - totalCharacters;
            String suffix = ".negate()";
            final String description = toString();
            String negatedDescription =
                    description.endsWith(suffix)
                            ? description.substring(0, description.length() - suffix.length())
                            : description + suffix;
            return new NegatedFastMatcher(
                    precomputedPositive(negatedCharacters, table, negatedDescription)) {
                @Override
                public String toString() {
                    return description;
                }
            };
        }
    }

    @GwtIncompatible
    private static CharMatcher precomputedPositive(
            int totalCharacters, BitSet table, String description) {
        switch (totalCharacters) {
            case 0:
                return none();
            case 1:
                return is((char) table.nextSetBit(0));
            case 2:
                char c1 = (char) table.nextSetBit(0);
                char c2 = (char) table.nextSetBit(c1 + 1);
                return isEither(c1, c2);
            default:
                return isSmall(totalCharacters, table.length())
                        ? SmallCharMatcher.from(table, description)
                        : new BitSetMatcher(table, description);
        }
    }

    @GwtIncompatible
    private static boolean isSmall(int totalCharacters, int tableLength) {
        return totalCharacters <= SmallCharMatcher.MAX_SIZE
                && tableLength > (totalCharacters * 4 * Character.SIZE);
    }

    @GwtIncompatible
    void setBits(BitSet table) {
        for (int c = Character.MAX_VALUE; c >= Character.MIN_VALUE; c--) {
            if (matches((char) c)) {
                table.set(c);
            }
        }
    }

    public boolean matchesAllOf(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (!matches(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesNoneOf(CharSequence sequence) {
        return indexIn(sequence) == -1;
    }


    public int indexIn(CharSequence sequence) {
        return indexIn(sequence, 0);
    }

    public int indexIn(CharSequence sequence, int start) {
        int length = sequence.length();
        Preconditions.checkPositionIndex(start, length);
        for (int i = start; i < length; i++) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }


    public int lastIndexIn(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int countIn(CharSequence sequence) {
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (matches(sequence.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public String removeFrom(CharSequence sequence) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }

        char[] chars = string.toCharArray();
        int spread = 1;

        OUT:
        while (true) {
            pos++;
            while (true) {
                if (pos == chars.length) {
                    break OUT;
                }
                if (matches(chars[pos])) {
                    break;
                }
                chars[pos - spread] = chars[pos];
                pos++;
            }
            spread++;
        }
        return new String(chars, 0, pos - spread);
    }

    public String replaceFrom(CharSequence sequence, char replacement) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        chars[pos] = replacement;
        for (int i = pos + 1; i < chars.length; i++) {
            if (matches(chars[i])) {
                chars[i] = replacement;
            }
        }
        return new String(chars);
    }


    public String replaceFrom(CharSequence sequence, CharSequence replacement) {
        int replacementLen = replacement.length();
        if (replacementLen == 0) {
            return removeFrom(sequence);
        }
        if (replacementLen == 1) {
            return replaceFrom(sequence, replacement.charAt(0));
        }

        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }

        int len = string.length();
        StringBuilder buf = new StringBuilder((len * 3 / 2) + 16);

        int oldpos = 0;
        do {
            buf.append(string, oldpos, pos);
            buf.append(replacement);
            oldpos = pos + 1;
            pos = indexIn(string, oldpos);
        } while (pos != -1);

        buf.append(string, oldpos, len);
        return buf.toString();
    }


    public String collapseFrom(CharSequence sequence, char replacement) {
        // This implementation avoids unnecessary allocation.
        int len = sequence.length();
        for (int i = 0; i < len; i++) {
            char c = sequence.charAt(i);
            if (matches(c)) {
                if (c == replacement && (i == len - 1 || !matches(sequence.charAt(i + 1)))) {
                    // a no-op replacement
                    i++;
                } else {
                    StringBuilder builder = new StringBuilder(len).append(sequence, 0, i).append(replacement);
                    return finishCollapseFrom(sequence, i + 1, len, replacement, builder, true);
                }
            }
        }
        // no replacement needed
        return sequence.toString();
    }


    public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
        // This implementation avoids unnecessary allocation.
        int len = sequence.length();
        int first = 0;
        int last = len - 1;

        while (first < len && matches(sequence.charAt(first))) {
            first++;
        }

        while (last > first && matches(sequence.charAt(last))) {
            last--;
        }

        return (first == 0 && last == len - 1)
                ? collapseFrom(sequence, replacement)
                : finishCollapseFrom(
                sequence, first, last + 1, replacement, new StringBuilder(last + 1 - first), false);
    }

    private String finishCollapseFrom(
            CharSequence sequence,
            int start,
            int end,
            char replacement,
            StringBuilder builder,
            boolean inMatchingGroup) {
        for (int i = start; i < end; i++) {
            char c = sequence.charAt(i);
            if (matches(c)) {
                if (!inMatchingGroup) {
                    builder.append(replacement);
                    inMatchingGroup = true;
                }
            } else {
                builder.append(c);
                inMatchingGroup = false;
            }
        }
        return builder.toString();
    }

    @Deprecated
    @Override
    public boolean apply(Character character) {
        return matches(character);
    }

    @Override
    public String toString() {
        return super.toString();
    }


    private static String showCharacter(char c) {
        String hex = "0123456789ABCDEF";
        char[] tmp = {'\\', 'u', '\0', '\0', '\0', '\0'};
        for (int i = 0; i < 4; i++) {
            tmp[5 - i] = hex.charAt(c & 0xF);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(tmp);
    }

    // Fast matchers

    /**
     * A matcher for which precomputation will not yield any significant benefit.
     */
    abstract static class FastMatcher extends CharMatcher {

        @Override
        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }
    }

    /**
     * {@link FastMatcher} which overrides {@code toString()} with a custom name.
     */
    abstract static class NamedFastMatcher extends FastMatcher {

        private final String description;

        NamedFastMatcher(String description) {
            this.description = Preconditions.checkNotNull(description);
        }

        @Override
        public final String toString() {
            return description;
        }
    }

    /**
     * Negation of a {@link FastMatcher}.
     */
    static class NegatedFastMatcher extends Negated {

        NegatedFastMatcher(CharMatcher original) {
            super(original);
        }

    }

    /**
     * Fast matcher using a {@link BitSet} table of matching characters.
     */
    @GwtIncompatible // used only from other GwtIncompatible code
    private static final class BitSetMatcher extends NamedFastMatcher {

        private final BitSet table;

        private BitSetMatcher(BitSet table, String description) {
            super(description);
            if (table.length() + Long.SIZE < table.size()) {
                table = (BitSet) table.clone();
            }
            this.table = table;
        }

        @Override
        public boolean matches(char c) {
            return table.get(c);
        }

        @Override
        void setBits(BitSet bitSet) {
            bitSet.or(table);
        }
    }

    private static final class Any extends NamedFastMatcher {

        static final Any INSTANCE = new Any();

        private Any() {
            super("CharMatcher.any()");
        }

        @Override
        public boolean matches(char c) {
            return true;
        }

        @Override
        public int indexIn(CharSequence sequence) {
            return (sequence.length() == 0) ? -1 : 0;
        }

        @Override
        public int indexIn(CharSequence sequence, int start) {
            int length = sequence.length();
            Preconditions.checkPositionIndex(start, length);
            return (start == length) ? -1 : start;
        }

        @Override
        public int lastIndexIn(CharSequence sequence) {
            return sequence.length() - 1;
        }

        @Override
        public boolean matchesAllOf(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return true;
        }

        @Override
        public boolean matchesNoneOf(CharSequence sequence) {
            return sequence.length() == 0;
        }

        @Override
        public String removeFrom(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return "";
        }

        @Override
        public String replaceFrom(CharSequence sequence, char replacement) {
            char[] array = new char[sequence.length()];
            Arrays.fill(array, replacement);
            return new String(array);
        }

        @Override
        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            return String.valueOf(replacement).repeat(Math.max(0, sequence.length()));
        }

        @Override
        public String collapseFrom(CharSequence sequence, char replacement) {
            return (sequence.length() == 0) ? "" : String.valueOf(replacement);
        }

        @Override
        public int countIn(CharSequence sequence) {
            return sequence.length();
        }

        @Override
        public CharMatcher and(CharMatcher other) {
            return Preconditions.checkNotNull(other);
        }

        @Override
        public CharMatcher or(CharMatcher other) {
            Preconditions.checkNotNull(other);
            return this;
        }

        @Override
        public CharMatcher negate() {
            return none();
        }
    }

    private static final class None extends NamedFastMatcher {

        static final None INSTANCE = new None();

        private None() {
            super("CharMatcher.none()");
        }

        @Override
        public boolean matches(char c) {
            return false;
        }

        @Override
        public int indexIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return -1;
        }

        @Override
        public int indexIn(CharSequence sequence, int start) {
            int length = sequence.length();
            Preconditions.checkPositionIndex(start, length);
            return -1;
        }

        @Override
        public int lastIndexIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return -1;
        }

        @Override
        public boolean matchesAllOf(CharSequence sequence) {
            return sequence.length() == 0;
        }

        @Override
        public boolean matchesNoneOf(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return true;
        }

        @Override
        public String removeFrom(CharSequence sequence) {
            return sequence.toString();
        }

        @Override
        public String replaceFrom(CharSequence sequence, char replacement) {
            return sequence.toString();
        }

        @Override
        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            Preconditions.checkNotNull(replacement);
            return sequence.toString();
        }

        @Override
        public String collapseFrom(CharSequence sequence, char replacement) {
            return sequence.toString();
        }

        @Override
        public int countIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return 0;
        }

        @Override
        public CharMatcher and(CharMatcher other) {
            Preconditions.checkNotNull(other);
            return this;
        }

        @Override
        public CharMatcher or(CharMatcher other) {
            return Preconditions.checkNotNull(other);
        }

        @Override
        public CharMatcher negate() {
            return any();
        }
    }

    private static class RangesMatcher extends CharMatcher {

        private final String description;
        private final char[] rangeStarts;
        private final char[] rangeEnds;

        RangesMatcher(String description, char[] rangeStarts, char[] rangeEnds) {
            this.description = description;
            this.rangeStarts = rangeStarts;
            this.rangeEnds = rangeEnds;
            Preconditions.checkArgument();
            for (int i = 0; i < rangeStarts.length; i++) {
                Preconditions.checkArgument();
                if (i + 1 < rangeStarts.length) {
                    Preconditions.checkArgument();
                }
            }
        }

        @Override
        public boolean matches(char c) {
            int index = Arrays.binarySearch(rangeStarts, c);
            if (index >= 0) {
                return true;
            } else {
                index = ~index - 1;
                return index >= 0 && c <= rangeEnds[index];
            }
        }

        @Override
        public String toString() {
            return description;
        }
    }

    private static final class Digit extends RangesMatcher {
        private static final String ZEROES =
                "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0de6"
                        + "\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1a80\u1a90\u1b50\u1bb0"
                        + "\u1c40\u1c50\ua620\ua8d0\ua900\ua9d0\ua9f0\uaa50\uabf0\uff10";

        private static char[] zeroes() {
            return ZEROES.toCharArray();
        }

        private static char[] nines() {
            char[] nines = new char[ZEROES.length()];
            for (int i = 0; i < ZEROES.length(); i++) {
                nines[i] = (char) (ZEROES.charAt(i) + 9);
            }
            return nines;
        }

        static final Digit INSTANCE = new Digit();

        private Digit() {
            super("CharMatcher.digit()", zeroes(), nines());
        }
    }

    private static final class JavaDigit extends CharMatcher {

        static final JavaDigit INSTANCE = new JavaDigit();

        @Override
        public boolean matches(char c) {
            return Character.isDigit(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaDigit()";
        }
    }

    private static final class JavaLetter extends CharMatcher {

        static final JavaLetter INSTANCE = new JavaLetter();

        @Override
        public boolean matches(char c) {
            return Character.isLetter(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaLetter()";
        }
    }

    private static final class JavaLetterOrDigit extends CharMatcher {

        static final JavaLetterOrDigit INSTANCE = new JavaLetterOrDigit();

        @Override
        public boolean matches(char c) {
            return Character.isLetterOrDigit(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaLetterOrDigit()";
        }
    }

    private static final class JavaUpperCase extends CharMatcher {

        static final JavaUpperCase INSTANCE = new JavaUpperCase();

        @Override
        public boolean matches(char c) {
            return Character.isUpperCase(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaUpperCase()";
        }
    }

    private static final class JavaLowerCase extends CharMatcher {

        static final JavaLowerCase INSTANCE = new JavaLowerCase();

        @Override
        public boolean matches(char c) {
            return Character.isLowerCase(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaLowerCase()";
        }
    }

    private static final class Invisible extends RangesMatcher {
        private static final String RANGE_STARTS =
                "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u08e2\u1680\u180e\u2000\u2028\u205f\u2066"
                        + "\u3000\ud800\ufeff\ufff9";
        private static final String RANGE_ENDS = // inclusive ends
                "\u0020\u00a0\u00ad\u0605\u061c\u06dd\u070f\u08e2\u1680\u180e\u200f\u202f\u2064\u206f"
                        + "\u3000\uf8ff\ufeff\ufffb";

        static final Invisible INSTANCE = new Invisible();

        private Invisible() {
            super("CharMatcher.invisible()", RANGE_STARTS.toCharArray(), RANGE_ENDS.toCharArray());
        }
    }

    private static final class SingleWidth extends RangesMatcher {

        static final SingleWidth INSTANCE = new SingleWidth();

        private SingleWidth() {
            super(
                    "CharMatcher.singleWidth()",
                    "\u0000\u05be\u05d0\u05f3\u0600\u0750\u0e00\u1e00\u2100\ufb50\ufe70\uff61".toCharArray(),
                    "\u04f9\u05be\u05ea\u05f4\u06ff\u077f\u0e7f\u20af\u213a\ufdff\ufeff\uffdc".toCharArray());
        }
    }

    private static class Negated extends CharMatcher {

        final CharMatcher original;

        Negated(CharMatcher original) {
            this.original = Preconditions.checkNotNull(original);
        }

        @Override
        public boolean matches(char c) {
            return !original.matches(c);
        }

        @Override
        public boolean matchesAllOf(CharSequence sequence) {
            return original.matchesNoneOf(sequence);
        }

        @Override
        public boolean matchesNoneOf(CharSequence sequence) {
            return original.matchesAllOf(sequence);
        }

        @Override
        public int countIn(CharSequence sequence) {
            return sequence.length() - original.countIn(sequence);
        }

        @GwtIncompatible
        @Override
        void setBits(BitSet table) {
            BitSet tmp = new BitSet();
            original.setBits(tmp);
            tmp.flip(Character.MIN_VALUE, Character.MAX_VALUE + 1);
            table.or(tmp);
        }

        @Override
        public CharMatcher negate() {
            return original;
        }

        @Override
        public String toString() {
            return original + ".negate()";
        }
    }

    private static final class And extends CharMatcher {

        final CharMatcher first;
        final CharMatcher second;

        And(CharMatcher a, CharMatcher b) {
            first = Preconditions.checkNotNull(a);
            second = Preconditions.checkNotNull(b);
        }

        @Override
        public boolean matches(char c) {
            return first.matches(c) && second.matches(c);
        }

        @GwtIncompatible
        @Override
        void setBits(BitSet table) {
            BitSet tmp1 = new BitSet();
            first.setBits(tmp1);
            BitSet tmp2 = new BitSet();
            second.setBits(tmp2);
            tmp1.and(tmp2);
            table.or(tmp1);
        }

        @Override
        public String toString() {
            return "CharMatcher.and(" + first + ", " + second + ")";
        }
    }

    private static final class Or extends CharMatcher {

        final CharMatcher first;
        final CharMatcher second;

        Or(CharMatcher a, CharMatcher b) {
            first = Preconditions.checkNotNull(a);
            second = Preconditions.checkNotNull(b);
        }

        @GwtIncompatible
        @Override
        void setBits(BitSet table) {
            first.setBits(table);
            second.setBits(table);
        }

        @Override
        public boolean matches(char c) {
            return first.matches(c) || second.matches(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.or(" + first + ", " + second + ")";
        }
    }

    private static final class Is extends FastMatcher {

        private final char match;

        Is(char match) {
            this.match = match;
        }

        @Override
        public boolean matches(char c) {
            return c == match;
        }

        @Override
        public String replaceFrom(CharSequence sequence, char replacement) {
            return sequence.toString().replace(match, replacement);
        }

        @Override
        public CharMatcher and(CharMatcher other) {
            return other.matches(match) ? this : none();
        }

        @Override
        public CharMatcher or(CharMatcher other) {
            return other.matches(match) ? other : super.or(other);
        }

        @Override
        public CharMatcher negate() {
            return isNot(match);
        }

        @GwtIncompatible // used only from other GwtIncompatible code
        @Override
        void setBits(BitSet table) {
            table.set(match);
        }

        @Override
        public String toString() {
            return "CharMatcher.is('" + showCharacter(match) + "')";
        }
    }

    private static final class IsNot extends FastMatcher {

        private final char match;

        IsNot(char match) {
            this.match = match;
        }

        @Override
        public boolean matches(char c) {
            return c != match;
        }

        @Override
        public CharMatcher and(CharMatcher other) {
            return other.matches(match) ? super.and(other) : other;
        }

        @Override
        public CharMatcher or(CharMatcher other) {
            return other.matches(match) ? any() : this;
        }

        @GwtIncompatible
        @Override
        void setBits(BitSet table) {
            table.set(0, match);
            table.set(match + 1, Character.MAX_VALUE + 1);
        }

        @Override
        public CharMatcher negate() {
            return is(match);
        }

        @Override
        public String toString() {
            return "CharMatcher.isNot('" + showCharacter(match) + "')";
        }
    }

    private static CharMatcher.IsEither isEither(char c1, char c2) {
        return new CharMatcher.IsEither(c1, c2);
    }

    private static final class IsEither extends FastMatcher {

        private final char match1;
        private final char match2;

        IsEither(char match1, char match2) {
            this.match1 = match1;
            this.match2 = match2;
        }

        @Override
        public boolean matches(char c) {
            return c == match1 || c == match2;
        }

        @GwtIncompatible // used only from other GwtIncompatible code
        @Override
        void setBits(BitSet table) {
            table.set(match1);
            table.set(match2);
        }

        @Override
        public String toString() {
            return "CharMatcher.anyOf(\"" + showCharacter(match1) + showCharacter(match2) + "\")";
        }
    }

    private static final class AnyOf extends CharMatcher {

        private final char[] chars;

        public AnyOf(CharSequence chars) {
            this.chars = chars.toString().toCharArray();
            Arrays.sort(this.chars);
        }

        @Override
        public boolean matches(char c) {
            return Arrays.binarySearch(chars, c) >= 0;
        }

        @Override
        @GwtIncompatible
        void setBits(BitSet table) {
            for (char c : chars) {
                table.set(c);
            }
        }

        @Override
        public String toString() {
            StringBuilder description = new StringBuilder("CharMatcher.anyOf(\"");
            for (char c : chars) {
                description.append(showCharacter(c));
            }
            description.append("\")");
            return description.toString();
        }
    }

}
