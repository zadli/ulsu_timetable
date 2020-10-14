package ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;


import java.util.Objects;
import java.util.Random;

import ru.zadli.ulsu_collaborating.timetable.R;

public class ContactsRVAdapter extends RecyclerView.Adapter<ContactsRVAdapter.ViewHolder> {
    DataSnapshot contacts;
    Context context;

    public ContactsRVAdapter(Context context, DataSnapshot students_contacts) {
        this.context = context;
        this.contacts = students_contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.contacts_rv_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Random rand = new Random();
        int r = rand.nextInt();
        int g = rand.nextInt();
        int b = rand.nextInt();
        int randomColor = Color.rgb(r, g, b);
        holder.fio.setTextColor(randomColor);
        holder.fio.setText(Objects.requireNonNull(contacts.child(String.valueOf(position)).child("fio").getValue()).toString());
        holder.phone_number.setText(Objects.requireNonNull(contacts.child(String.valueOf(position)).child("phone_number").getValue()).toString());
        holder.email.setText(Objects.requireNonNull(contacts.child(String.valueOf(position)).child("email").getValue()).toString());

    }

    @Override
    public int getItemCount() {
        return (int) contacts.getChildrenCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fio;
        TextView phone_number;
        TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fio = itemView.findViewById(R.id.fio);
            phone_number = itemView.findViewById(R.id.phone_number);
            email = itemView.findViewById(R.id.email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Позвонить, написать смс или написать на email?")
                            .setPositiveButton("Позвонить", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.fromParts("tel",
                                                    (String.valueOf(contacts.child(String.valueOf(getAdapterPosition())).child("phone_number").getValue())),
                                                    null)));
                                }
                            })
                            .setNeutralButton("Отправить Email", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.fromParts("mailto",
                                                    (String.valueOf(contacts.child(String.valueOf(getAdapterPosition())).child("email").getValue())),
                                                    null)));
                                }
                            })
                            .setNegativeButton("Отправить СМС", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.fromParts("sms",
                                                    (String.valueOf(contacts.child(String.valueOf(getAdapterPosition())).child("phone_number").getValue())),
                                                    null)));
                                }
                            });
                    final AlertDialog dialog = builder.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            Random rand = new Random();
                            int r = rand.nextInt();
                            int g = rand.nextInt();
                            int b = rand.nextInt();
                            int randomColor = Color.rgb(r, g, b);
                            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                            positiveButtonLL.gravity = Gravity.CENTER;
                            positiveButton.setLayoutParams(positiveButtonLL);
                            Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                            LinearLayout.LayoutParams neutralButtonLL = (LinearLayout.LayoutParams) neutralButton.getLayoutParams();
                            neutralButtonLL.gravity = Gravity.CENTER;
                            neutralButton.setLayoutParams(neutralButtonLL);
                            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                            LinearLayout.LayoutParams negativeButtonLL = (LinearLayout.LayoutParams) negativeButton.getLayoutParams();
                            negativeButtonLL.gravity = Gravity.CENTER;
                            negativeButton.setLayoutParams(negativeButtonLL);
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(randomColor);
                            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(randomColor);
                            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(randomColor);
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}
