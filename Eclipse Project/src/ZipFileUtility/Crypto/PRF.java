package ZipFileUtility.Crypto;

interface PRF {
    void init(byte[] P);
    byte[] doFinal(byte[] M);
    int getHLen();
}
