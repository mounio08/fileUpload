package com.uploadfile.design;

public class Data {
	
	private String fName;
	private long fSize;
	
	
	public String getFileName() {
		return fName;
	}
	
	public void setFileName(String fileName) {
		this.fName = fileName;
	}
	
	public long getFileSize() {
		return fSize;
	}
	
	public void setFileSize(long fileSize) {
		this.fSize = fileSize;
	}
}