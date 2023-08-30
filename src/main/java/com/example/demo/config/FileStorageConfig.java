package com.example.demo.config;

public class FileStorageConfig {

	private String uploadDir;

	public FileStorageConfig(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
}