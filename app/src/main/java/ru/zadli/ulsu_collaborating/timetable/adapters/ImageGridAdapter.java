package ru.zadli.ulsu_collaborating.timetable.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import ru.zadli.ulsu_collaborating.timetable.MainActivity;
import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.view.SquareImageView;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.GridItemViewHolder> {

    private List<String> imageList;

    private Context c;

    public static class GridItemViewHolder extends RecyclerView.ViewHolder {
        SquareImageView siv;

        public GridItemViewHolder(View view) {
            super(view);
            siv = view.findViewById(R.id.siv);
        }
    }

    public ImageGridAdapter(Context c, List imageList) {
        this.c = c;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public GridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, parent, false);

        return new GridItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GridItemViewHolder holder, final int position) {
        final String path = imageList.get(position);

        Picasso.get()
                .load(path)
                .into(holder.siv);

        holder.siv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start_main = new Intent(c, MainActivity.class);
                start_main.putExtra("position", position);
                c.startActivity(start_main);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}