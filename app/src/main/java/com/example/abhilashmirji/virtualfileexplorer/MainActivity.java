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

public class MainActivity extends AppCompatActivity implements FileCRUD, FoldersAdapter.FolderInterface {

    private static final String FOLDER_STRUCTURE = "folder_structure";
    // The folders are maintained in a ordered list in which the hasmap defines the folder name and
    // the Arraylist of all subfolders
    HashMap<FolderObject, ArrayList<FolderObject>> folderStructure = new HashMap<>();
    FolderObject activeFolderObject;
    private Toolbar mToolbar;
    private FoldersAdapter mFoldersAdapter;
    private FloatingActionButton mFolderAction;
    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openFolder(activeFolderObject.parentFile);
        }
    };
    private boolean isInDeleteMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize the root directory
        FolderObject rootFileObject = new FolderObject("root", null);
        activeFolderObject = rootFileObject;
        folderStructure.put(rootFileObject, new ArrayList<FolderObject>());
        setupViews();
    }

    private void setupViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_folder_open);
        mToolbar.setTitle("root");
        setSupportActionBar(mToolbar);
        mFolderAction = (FloatingActionButton) findViewById(R.id.folder_action);
        mFolderAction.setTag(null);
        mFolderAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getTag()!=null) {
                    deleteFolder((FolderObject) view.getTag());
                } else {
                    EditTextDialogFragment editTextDialogFragment
                            = EditTextDialogFragment.newInstance("Create Folder", MainActivity.this, activeFolderObject);
                    editTextDialogFragment.show(getSupportFragmentManager(), "FragmentAddFolder");
                }
            }
        });
        RecyclerView fileExplorer = (RecyclerView) findViewById(R.id.file_explorer);
        fileExplorer.setLayoutManager(new LinearLayoutManager(this));
        mFoldersAdapter = new FoldersAdapter(this, folderStructure.get(activeFolderObject), this, activeFolderObject);
        fileExplorer.setAdapter(mFoldersAdapter);
    }

    @Override
    public void createNewFolder(FolderObject newFolderObject) {
        ArrayList<FolderObject> subFolders = folderStructure.get(activeFolderObject);
        subFolders.add(newFolderObject);
        folderStructure.put(newFolderObject, new ArrayList<FolderObject>());
        mFoldersAdapter.itemAdded(newFolderObject);
    }

    @Override
    public void deleteFolder(FolderObject folderName) {
        ArrayList<FolderObject> subFolders = folderStructure.get(activeFolderObject);
        subFolders.remove(folderName);
        folderStructure.remove(folderName);
        mFoldersAdapter.itemRemoved(folderName);
        changeFabAction("add", null);
    }

    @Override
    public void openFolder(FolderObject name) {
        if (name.isRoot()) {
            mToolbar.setNavigationIcon(R.drawable.ic_folder_open);
            mToolbar.setNavigationOnClickListener(null);
        } else {
            mToolbar.setNavigationIcon(android.R.drawable.ic_media_previous);
            mToolbar.setNavigationOnClickListener(backClickListener);
        }
        ArrayList<FolderObject> newFolders = folderStructure.get(name);
        mFoldersAdapter.populateFolders(newFolders);
        activeFolderObject = name;
        mToolbar.setTitle(name.fileName);
    }

    @Override
    public void changeFabAction(String action, FolderObject tag) {
        if (action == "delete") {
            mFolderAction.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_delete));
            mFolderAction.setTag(tag);
        } else {
            mFolderAction.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_create_new_folder));
            mFolderAction.setTag(null);
        }

    }
}
