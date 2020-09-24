package ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalogue.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    public TextView text_taskTitle, text_taskDescription;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);

        text_taskTitle = itemView.findViewById(R.id.text_taskTitle);
        text_taskDescription = itemView.findViewById(R.id.text_taskDescription);
    }
}
