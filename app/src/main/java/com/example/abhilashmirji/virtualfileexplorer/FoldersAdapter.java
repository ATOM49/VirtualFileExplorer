package com.example.abhilashmirji.virtualfileexplorer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;

/**
 * Created by abhilashmirji on 27/04/17.
 */

public class FoldersAdapter extends RecyclerView.Adapter<FoldersAdapter.FolderViewHolder> {

    private final Context context;
    private final FolderObject parentFolderObject;
    private MultiSelector mMultiSelector = new SingleSelector();
    private ArrayList<FolderObject> folders = new ArrayList<>();
    private FolderInterface folderInterface;

    public FoldersAdapter(Context context, ArrayList<FolderObject> folders, FolderInterface folderInterface, FolderObject parentFolderObject) {
        this.context = context;
        this.folderInterface = folderInterface;
        this.folders = folders;
        this.parentFolderObject = parentFolderObject;
    }

    public void populateFolders(ArrayList<FolderObject> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }


    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FolderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(FolderViewHolder holder, int position) {
        holder.folderNameText.setText(folders.get(position).fileName);
        holder.itemView.setTag(folders.get(position));
    }


    @Override
    public int getItemCount() {
        return folders.size();
    }

    public void itemAdded(FolderObject newFolderName) {
        folders.add(newFolderName);
        notifyItemInserted(folders.size() - 1);
    }

    public void itemRemoved(FolderObject folderName) {
        folders.remove(folderName);
        //This can be improved by having a reference to the position of the deleted value
        notifyDataSetChanged();
    }


    public class FolderViewHolder extends SwappingHolder implements View.OnLongClickListener, View.OnClickListener {

        private final TextView folderNameText;

        public FolderViewHolder(View itemView) {
            super(itemView, mMultiSelector);
            folderNameText = (TextView) itemView.findViewById(R.id.folder_item_name_text);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (!mMultiSelector.isSelectable()) {
                mMultiSelector.setSelectable(true);
                mMultiSelector.setSelected(FolderViewHolder.this, true);
                folderInterface.changeFabAction("delete", (FolderObject) view.getTag());
                return true;
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            folderInterface.openFolder((FolderObject) v.getTag());
        }
    }

    public interface FolderInterface {
        void openFolder(FolderObject name);

        void changeFabAction(String action, FolderObject tag);
    }
}
