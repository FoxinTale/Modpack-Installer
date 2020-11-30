package ZipFileUtility.Util;

import java.io.File;
import java.nio.charset.Charset;

public final class InternalZipConstants {

  private InternalZipConstants() {

  }

  public static final int ENDHDR = 22;
  public static final int STD_DEC_HDR_SIZE = 12;
  public static final int AES_AUTH_LENGTH = 10;
  public static final int AES_BLOCK_SIZE = 16;
  public static final String AES_MAC_ALGORITHM = "HmacSHA1";
  public static final String AES_HASH_CHARSET = "ISO-8859-1";
  public static final int AES_HASH_ITERATIONS = 1000;
  public static final int AES_PASSWORD_VERIFIER_LENGTH = 2;
  public static final int MIN_SPLIT_LENGTH = 65536;
  public static final long ZIP_64_SIZE_LIMIT = 4294967295L;
  public static final int ZIP_64_NUMBER_OF_ENTRIES_LIMIT = 65535;
  public static final int BUFF_SIZE = 1024 * 4;
  public static final int UPDATE_LFH_CRC = 14;
  public static final String ZIP_STANDARD_CHARSET = "Cp437";
  public static final String FILE_SEPARATOR = File.separator;
  public static final String ZIP_FILE_SEPARATOR = "/";
  public static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
  public static final String SEVEN_ZIP_SPLIT_FILE_EXTENSION_PATTERN = ".zip.001";
}
