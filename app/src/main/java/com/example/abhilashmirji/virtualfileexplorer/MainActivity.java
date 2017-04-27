package com.example.abhilashmirji.virtualfileexplorer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FileCRUD, FoldersAdapter.FolderInterface {

    private static final String FOLDER_STRUCTURE = "folder_structure";
    // The folders are maintained in a ordered list in which the hasmap defines the folder name and
    // the Arraylist of all subfolders
    List<HashMap<String, ArrayList<String>>> folderStructure = new ArrayList<>();
    int activePosition = 0;
    private Toolbar mToolbar;
    private FoldersAdapter mFoldersAdapter;
    private FloatingActionButton mFolderAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize the root directory
        HashMap<String, ArrayList<String>> rootDirectory = new HashMap<>();
        rootDirectory.put("root", new ArrayList<String>());
        folderStructure.add(rootDirectory);
        setupViews();
        createNewFolder("root", "Test");
    }

    private void setupViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mFolderAction = (FloatingActionButton) findViewById(R.id.folder_action);
        mFolderAction.setTag("root");
        mFolderAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextDialogFragment editTextDialogFragment
                        = EditTextDialogFragment.newInstance("Create Folder", MainActivity.this, (String) view.getTag());
                editTextDialogFragment.show(getSupportFragmentManager(), "FragmentAddFolder");

            }
        });
        RecyclerView fileExplorer = (RecyclerView) findViewById(R.id.file_explorer);
        fileExplorer.setLayoutManager(new LinearLayoutManager(this));
        mFoldersAdapter = new FoldersAdapter(this, folderStructure.get(activePosition).get("root"), this);
        fileExplorer.setAdapter(mFoldersAdapter);
    }

    @Override
    public void createNewFolder(String root, String newFolderName) {
        HashMap<String, ArrayList<String>> activeFolder = folderStructure.get(activePosition);
        if (activeFolder.containsKey(root)) {
            ArrayList<String> subFolders = activeFolder.get(root);
            //update subdirectories
            subFolders.add(newFolderName);
            mFoldersAdapter.itemAdded(newFolderName);
            //Update the present folder structure
            HashMap<String, ArrayList<String>> newEntry = new HashMap<>();
            newEntry.put(newFolderName, new ArrayList<String>());
            folderStructure.add(newEntry);
        }
    }

    @Override
    public void renameFolder(String oldName, String newName) {

    }

    @Override
    public void deleteFolder(String folderName) {

    }

    @Override
    public void openFolder(String name) {
        for (int i = 0; i < folderStructure.size(); i++) {
            if (folderStructure.get(i).keySet().contains(name)) {
                activePosition = i;
                mFoldersAdapter.populateFolders(folderStructure.get(i).get(name));
            }
        }
        HashMap<String, ArrayList<String>> activeFolder = folderStructure.get(activePosition);
        mFolderAction.setTag(name);
        mToolbar.setTitle(name);
    }

    @Override
    public void changeFabAction(String action) {
        if (action == "delete") {
            mFolderAction.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_delete));
        } else {
            mFolderAction.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_create_new_folder));
        }

    }
}
