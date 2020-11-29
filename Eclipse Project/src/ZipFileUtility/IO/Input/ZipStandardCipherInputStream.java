package ZipFileUtility.IO.Input;

import ZipFileUtility.Crypto.StandardDecrypter;
import ZipFileUtility.Model.LocalFileHeader;
import ZipFileUtility.Util.InternalZipConstants;
import ZipFileUtility.ZipException;

import java.io.IOException;

class ZipStandardCipherInputStream extends CipherInputStream<StandardDecrypter> {

  public ZipStandardCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader, char[] password) throws IOException, ZipException {
    super(zipEntryInputStream, localFileHeader, password);
  }

  @Override
  protected StandardDecrypter initializeDecrypter(LocalFileHeader localFileHeader, char[] password) throws ZipException, IOException {
    return new StandardDecrypter(password, localFileHeader.getCrcRawData(), getStandardDecrypterHeaderBytes());
  }

  private byte[] getStandardDecrypterHeaderBytes() throws IOException {
    byte[] headerBytes = new byte[InternalZipConstants.STD_DEC_HDR_SIZE];
    readRaw(headerBytes);
    return headerBytes;
  }
}
