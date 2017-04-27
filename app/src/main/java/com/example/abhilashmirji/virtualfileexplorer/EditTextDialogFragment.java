package com.example.abhilashmirji.virtualfileexplorer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by abhilashmirji on 27/04/17.
 */

public class EditTextDialogFragment extends DialogFragment implements View.OnClickListener {

    private static FileCRUD mFileCRUD;
    private static FolderObject mParentFolder;
    private TextInputEditText mEditText;
    private TextInputLayout mEditTextLayout;

    public EditTextDialogFragment() {
    }

    public static EditTextDialogFragment newInstance(String title, FileCRUD fileCRUD, FolderObject tag) {
        EditTextDialogFragment frag = new EditTextDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        mFileCRUD = fileCRUD;
        mParentFolder = tag;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_text, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (TextInputEditText) view.findViewById(R.id.folder_name_edit_text);
        mEditTextLayout = (TextInputLayout) view.findViewById(R.id.folder_name_layout);
        Button mOKButton = (Button) view.findViewById(R.id.ok);
        mOKButton.setOnClickListener(this);
        Button mCancelButton = (Button) view.findViewById(R.id.cancel);
        mCancelButton.setOnClickListener(this);
        String title = getArguments().getString("title", "Create Folder");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                String newFolderName = mEditText.getText().toString();
                if (!TextUtils.isEmpty(newFolderName)) {
                    mFileCRUD.createNewFolder(new FolderObject(mEditText.getText().toString(), mParentFolder));
                    dismiss();
                } else {
                    mEditTextLayout.setError("Enter a folder name");
                }
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
