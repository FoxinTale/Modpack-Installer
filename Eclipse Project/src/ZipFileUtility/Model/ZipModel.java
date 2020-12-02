package ZipFileUtility.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZipModel implements Cloneable {

    private List<LocalFileHeader> localFileHeaders = new ArrayList<>();
    private CentralDirectory centralDirectory = new CentralDirectory();
    private EndOfCentralDirectoryRecord endOfCentralDirectoryRecord = new EndOfCentralDirectoryRecord();
    private Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator = new Zip64EndOfCentralDirectoryLocator();
    private Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = new Zip64EndOfCentralDirectoryRecord();

    private boolean splitArchive;
    private File zipFile;
    private boolean isZip64Format = false;
    private long start;
    private long end;

    public ZipModel() {
    }

    public List<LocalFileHeader> getLocalFileHeaders() {
        return localFileHeaders;
    }

    public void setLocalFileHeaders(List<LocalFileHeader> localFileHeaderList) {
        this.localFileHeaders = localFileHeaderList;
    }

    public CentralDirectory getCentralDirectory() {
        return centralDirectory;
    }

    public void setCentralDirectory(CentralDirectory centralDirectory) {
        this.centralDirectory = centralDirectory;
    }

    public EndOfCentralDirectoryRecord getEndOfCentralDirectoryRecord() {
        return endOfCentralDirectoryRecord;
    }

    public void setEndOfCentralDirectoryRecord(EndOfCentralDirectoryRecord endOfCentralDirectoryRecord) {
        this.endOfCentralDirectoryRecord = endOfCentralDirectoryRecord;
    }

    public boolean isSplitArchive() {
        return splitArchive;
    }

    public void setSplitArchive(boolean splitArchive) {
        this.splitArchive = splitArchive;
    }

    public File getZipFile() {
        return zipFile;
    }

    public void setZipFile(File zipFile) {
        this.zipFile = zipFile;
    }

    public Zip64EndOfCentralDirectoryLocator getZip64EndOfCentralDirectoryLocator() {
        return zip64EndOfCentralDirectoryLocator;
    }

    public void setZip64EndOfCentralDirectoryLocator(
            Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator) {
        this.zip64EndOfCentralDirectoryLocator = zip64EndOfCentralDirectoryLocator;
    }

    public Zip64EndOfCentralDirectoryRecord getZip64EndOfCentralDirectoryRecord() {
        return zip64EndOfCentralDirectoryRecord;
    }

    public void setZip64EndOfCentralDirectoryRecord(
            Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord) {
        this.zip64EndOfCentralDirectoryRecord = zip64EndOfCentralDirectoryRecord;
    }

    public boolean isZip64Format() {
        return isZip64Format;
    }

    public void setZip64Format(boolean isZip64Format) {
        this.isZip64Format = isZip64Format;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
