package ZipFileUtility.Model;

import java.util.ArrayList;
import java.util.List;

public class CentralDirectory {

  private List<FileHeader> fileHeaders = new ArrayList<>();
  private DigitalSignature digitalSignature = new DigitalSignature();

  public List<FileHeader> getFileHeaders() {
    return fileHeaders;
  }

  public void setFileHeaders(List<FileHeader> fileHeaders) {
    this.fileHeaders = fileHeaders;
  }

  public DigitalSignature getDigitalSignature() {
    return digitalSignature;
  }

  public void setDigitalSignature(DigitalSignature digitalSignature) {
    this.digitalSignature = digitalSignature;
  }


}
