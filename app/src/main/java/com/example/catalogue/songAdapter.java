  package com.example.catalogue;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;


public class songAdapter extends RecyclerView.Adapter<songAdapter.songHolder> {
    private Context mContext;
    private OnItemClickListener mListener;
    private List<Song> mSongs;

    public songAdapter(Context context, List<Song> songs){
        mContext = context;
        mSongs = songs;

    }


    public songHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songrow, parent, false);

        return new songHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull songHolder holder, int position) {

        Song songCurrent = mSongs.get(position);
        holder.songName.setText(songCurrent.getSongName());
        holder.songArtist.setText(songCurrent.getArtist());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }


    public class songHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener , MenuItem.OnMenuItemClickListener {

        TextView songName, songArtist;
        CardView c;

        public songHolder(@NonNull final View itemView) {
            super(itemView);
            songName = (TextView) itemView.findViewById(R.id.song_row_name);
            songArtist = (TextView) itemView.findViewById(R.id.song_row_artist);
            c = (CardView)itemView.findViewById(R.id.song_card);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position !=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            MenuItem dowhatever = contextMenu.add(contextMenu.NONE,2,2,"Edit");
            MenuItem delete = contextMenu.add(contextMenu.NONE,1,1,"Delete");

                dowhatever.setOnMenuItemClickListener(this);
                delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position !=RecyclerView.NO_POSITION){

                    switch(menuItem.getItemId()){

                        case 1:
                            mListener.onDeleteClick(position);
                            return true;
                        case 2:
                            mListener.onWhateverClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);

        void onWhateverClick(int position);

        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){

        mListener = listener;
    }
}