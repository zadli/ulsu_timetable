package ru.zadli.ulsu_collaborating.timetable.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.zadli.ulsu_collaborating.timetable.R;

public class SettingsRVAdapter extends RecyclerView.Adapter<SettingsRVAdapter.ViewHolder>{
    Context context;

    public SettingsRVAdapter(final Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.settings_rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        runEnterAnimation(holder.itemView);
        holder.name.setText("OTA Обновление");
    }

    private void runEnterAnimation(View view) {
        view.setTranslationY((float) Resources.getSystem().getDisplayMetrics().heightPixels);
        view.animate().translationY(0.0f).setInterpolator(new DecelerateInterpolator(3.0f)).setDuration(700).start();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent start_browser = new Intent(Intent.ACTION_VIEW);
                    start_browser.setData(Uri.parse("https://zadli.ru/timetable/app-release.apk"));
                    context.startActivity(start_browser);
                }
            });
        }
    }
}
