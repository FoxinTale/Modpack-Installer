package ZipFileUtility.Util;

public class BitUtils {
    public static boolean isBitSet(byte b, int pos) {
        return (b & (1L << pos)) != 0;
    }
    public static byte setBit(byte b, int pos) {
        return (byte) (b | 1 << pos);
    }
}
