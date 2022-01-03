package com.poly.payload;

import lombok.Data;

@Data
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileViewUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileViewUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileViewUri = fileViewUri;
        this.fileType = fileType;
        this.size = size;
    }

    public UploadFileResponse() {
    }
}
