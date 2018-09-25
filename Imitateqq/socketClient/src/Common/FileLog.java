package Common;

public class FileLog {
	
	String fileName,path;
	long fileSize,breakPoint;
	public FileLog(String fileName,long fileSize,String path,long breakPoint) {
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.path = path;
		this.breakPoint = breakPoint;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public long getSize() {
		return fileSize;
	}
	
	public String getPath() {
		return path;
	}
	
	public long getBreakPoint() {
		return breakPoint;
	}
	
	public void setBreakPoint(long breakPoint) {
		this.breakPoint = breakPoint;
	}
}
