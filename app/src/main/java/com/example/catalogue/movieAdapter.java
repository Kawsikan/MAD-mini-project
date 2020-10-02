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

public class movieAdapter  extends RecyclerView.Adapter<movieAdapter.movieHolder> {
    private Context mContext;
    private movieAdapter.OnItemClickListener mListener;
    private List<Movie> mMovies;

    public movieAdapter(Context context, List<Movie> movies){
        mContext = context;
        mMovies = movies;

    }


    public movieAdapter.movieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movierow, parent, false);

        return new movieAdapter.movieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieAdapter.movieHolder holder, int position) {

        Movie movieCurrent = mMovies.get(position);
        holder.movieName.setText(movieCurrent.getMovieName());
        holder.movieWriter.setText("YEAR :" +movieCurrent.getWriterName());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


public class movieHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnCreateContextMenuListener , MenuItem.OnMenuItemClickListener {

    TextView movieName, movieWriter;
    CardView c;

    public movieHolder(@NonNull final View itemView) {
        super(itemView);
        movieName = (TextView) itemView.findViewById(R.id.movie_row_name);
        movieWriter = (TextView) itemView.findViewById(R.id.movie_row_writer);
        c = (CardView)itemView.findViewById(R.id.movie_card);

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
    public void setOnItemClickListener(movieAdapter.OnItemClickListener listener){

        mListener = listener;
    }
}