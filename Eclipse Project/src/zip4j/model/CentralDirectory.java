package zip4j.model;

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


}
