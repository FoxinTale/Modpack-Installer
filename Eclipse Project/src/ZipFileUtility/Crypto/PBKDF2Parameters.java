package ZipFileUtility.Crypto;

public class PBKDF2Parameters {

    protected byte[] salt;
    protected int iterationCount;
    protected String hashAlgorithm;
    protected String hashCharset;
    protected byte[] derivedKey;

    public PBKDF2Parameters(String hashAlgorithm, String hashCharset, byte[] salt, int iterationCount) {
        this(hashAlgorithm, hashCharset, salt, iterationCount, null);
    }

    public PBKDF2Parameters(String hashAlgorithm, String hashCharset, byte[] salt, int iterationCount,
                            byte[] derivedKey) {
        this.hashAlgorithm = hashAlgorithm;
        this.hashCharset = hashCharset;
        this.salt = salt;
        this.iterationCount = iterationCount;
        this.derivedKey = derivedKey;
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getDerivedKey() {
        return derivedKey;
    }

    public void setDerivedKey(byte[] derivedKey) {
        this.derivedKey = derivedKey;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public String getHashCharset() {
        return hashCharset;
    }

    public void setHashCharset(String hashCharset) {
        this.hashCharset = hashCharset;
    }
}
