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
    private MultiSelector mMultiSelector = new SingleSelector();
    private ArrayList<String> folders = new ArrayList<>();
    private FolderInterface folderInterface;

    public FoldersAdapter(Context context, ArrayList<String> folders, FolderInterface folderInterface) {
        this.context = context;
        this.folderInterface = folderInterface;
        this.folders = folders;
    }

    public void populateFolders(ArrayList<String> folders) {
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
        holder.folderNameText.setText(folders.get(position));
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public void itemAdded(String newFolderName) {
        folders.add(newFolderName);
        notifyItemInserted(getItemCount());
    }


    public class FolderViewHolder extends SwappingHolder implements View.OnLongClickListener, View.OnClickListener {

        private final TextView folderNameText;

        public FolderViewHolder(View itemView) {
            super(itemView, mMultiSelector);
            folderNameText = (TextView) itemView.findViewById(R.id.folder_item_name_text);
            itemView.setLongClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (!mMultiSelector.isSelectable()) {
                mMultiSelector.setSelectable(true);
                mMultiSelector.setSelected(FolderViewHolder.this, true);
                folderInterface.changeFabAction("delete");
                return true;
            } else {
                folderInterface.changeFabAction("add");
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            String folderName = ((TextView) v.findViewById(R.id.folder_item_name_text)).getText().toString();
            folderInterface.openFolder(folderName);
        }
    }

    public interface FolderInterface {
        void openFolder(String name);

        void changeFabAction(String action);
    }
}
