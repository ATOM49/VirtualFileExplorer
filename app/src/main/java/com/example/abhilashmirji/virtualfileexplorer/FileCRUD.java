package com.example.abhilashmirji.virtualfileexplorer;

/**
 * Created by abhilashmirji on 27/04/17.
 */

public interface FileCRUD {
    void createNewFolder(String root, String newFolder);

    void renameFolder(String oldName, String newName);

    void deleteFolder(String folderName);

}
