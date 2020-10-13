package ru.zadli.ulsu_collaborating.timetable.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.zadli.ulsu_collaborating.timetable.MainActivity;
import ru.zadli.ulsu_collaborating.timetable.R;

public class LaunchRVAdapter extends RecyclerView.Adapter<LaunchRVAdapter.ViewHolder> {
    int[] myImageList = new int[]{R.drawable.first_week, R.drawable.second_week, R.drawable.events, R.drawable.settings};
    Context context;

    public LaunchRVAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LaunchRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.picture.setImageResource(myImageList[position]);
    }

    @Override
    public int getItemCount() {
        return myImageList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.rv_grid_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent start_main = new Intent(context, MainActivity.class);
                    start_main.putExtra("position", getAdapterPosition());
                    context.startActivity(start_main);
                }
            });
        }
    }

}
