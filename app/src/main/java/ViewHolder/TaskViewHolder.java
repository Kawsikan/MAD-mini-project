package ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalogue.R;

public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    public TextView text_taskTitle, text_taskDescription, text_taskDate, text_taskTime;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);

        text_taskTitle = itemView.findViewById(R.id.text_taskTitle);
        text_taskDescription = itemView.findViewById(R.id.text_taskDescription);
        text_taskDate = itemView.findViewById(R.id.text_taskDate);
        text_taskTime = itemView.findViewById(R.id.text_taskTime);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select menu");
        contextMenu.add(0, 0, getAdapterPosition(), "Update");
        contextMenu.add(0, 1, getAdapterPosition(), "Delete");
    }
}
