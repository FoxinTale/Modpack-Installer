package ZipFileUtility.Crypto;

public interface PRF {
    void init(byte[] P);
    byte[] doFinal(byte[] M);
    int getHLen();
}
