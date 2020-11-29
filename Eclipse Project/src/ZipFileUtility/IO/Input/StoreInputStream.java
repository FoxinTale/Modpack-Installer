package ZipFileUtility.IO.Input;

class StoreInputStream extends DecompressedInputStream {

  public StoreInputStream(CipherInputStream cipherInputStream) {
    super(cipherInputStream);
  }
}
