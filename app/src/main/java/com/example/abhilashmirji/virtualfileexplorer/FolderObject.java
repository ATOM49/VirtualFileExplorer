package com.example.abhilashmirji.virtualfileexplorer;

/**
 * Created by abhilashmirji on 27/04/17.
 */

public class FolderObject {
    public String fileName;
    public FolderObject parentFile;

    public FolderObject(String fileName, FolderObject parentFile) {
        this.fileName = fileName;
        this.parentFile = parentFile;
    }

    public boolean isRoot() {
        return this.parentFile == null;
    }
}
