package ZipFileUtility.IO.Input;

public class StoreInputStream extends DecompressedInputStream{
    public StoreInputStream(CipherInputStream cipherInputStream) {
        super(cipherInputStream);
    }
}
