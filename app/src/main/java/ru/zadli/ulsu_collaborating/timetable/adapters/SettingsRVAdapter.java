package ru.zadli.ulsu_collaborating.timetable.adapters;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import ru.zadli.ulsu_collaborating.timetable.R;

public class SettingsRVAdapter extends RecyclerView.Adapter<SettingsRVAdapter.ViewHolder> {
    Context context;
    String[] name = new String[]{"OTA Обновление", "Выход из аккаунта"};

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
        holder.name.setText(name[position]);
    }

    private void runEnterAnimation(View view) {
        view.setTranslationY((float) Resources.getSystem().getDisplayMetrics().heightPixels);
        view.animate().translationY(0.0f).setInterpolator(new DecelerateInterpolator(3.0f)).setDuration(700).start();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (getAdapterPosition()) {
                        case 0:
                            Intent start_browser = new Intent(Intent.ACTION_VIEW);
                            start_browser.setData(Uri.parse("https://timetable.zadli.ru/timetable/app-release.apk"));
                            context.startActivity(start_browser);
                            Runtime.getRuntime().exit(0);
                        case 1:
                            FirebaseAuth.getInstance().signOut();
                            try {
                                Thread.sleep(100);
                                PackageManager packageManager = context.getPackageManager();
                                Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
                                ComponentName componentName = intent.getComponent();
                                Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                                context.startActivity(mainIntent);
                                Runtime.getRuntime().exit(0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }

                }
            });
        }
    }
}
