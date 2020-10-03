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


public class diaryAdapter extends RecyclerView.Adapter<diaryAdapter.diaryHolder> {
    private Context mContext;
    private OnItemClickListener mListener;
    private List<Diary> mDiary;

    public diaryAdapter(Context context, List<Diary> diary){
        mContext = context;
        mDiary = diary;

    }


    public diaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_custom, parent, false);

        return new diaryHolder(view);
    }

    public void onBindViewHolder(@NonNull diaryHolder holder, int position) {

        Diary diaryCurrent = mDiary.get(position);
        holder.diaryTitle.setText(diaryCurrent.getTitle());
        holder.diaryTime.setText(diaryCurrent.getTime());
    }

    @Override
    public int getItemCount() {
        return mDiary.size();
    }




    public class diaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener , MenuItem.OnMenuItemClickListener {

        TextView diaryTitle, diaryTime;
        CardView c;

        public diaryHolder(@NonNull final View itemView) {
            super(itemView);
            diaryTitle = (TextView) itemView.findViewById(R.id.diaryshowtxt1);
            diaryTime = (TextView) itemView.findViewById(R.id.diaryshowtxt2);


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