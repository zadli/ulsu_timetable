package ru.zadli.ulsu_collaborating.timetable.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Objects;
import ru.zadli.ulsu_collaborating.timetable.MainActivity;
import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapter;

public class WeekFragment extends Fragment {

    private int pageNumber;

    public static WeekFragment newInstance(final int page) {
        WeekFragment fragment = new WeekFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public WeekFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        JSONArray[] data = Objects.requireNonNull(activity).get_data();
        final View result=inflater.inflate(R.layout.page_layout, container, false);
        final RecyclerView monday = result.findViewById(R.id.rv_monday);
        final TextView monday_text = result.findViewById(R.id.monday);
        ImageView weekend = result.findViewById(R.id.weekend);
        monday.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (pageNumber == 0){
            weekend.setVisibility(View.INVISIBLE);
            JSONObject couples;
            JSONArray[] couple;
            try {
                couples = data[0].getJSONObject(0);
                couple = new JSONArray[couples.length()];
                for(int q = 0; q < couple.length; q++){
                    couple[q] = couples.getJSONArray(String.valueOf(q+1));
                }
                RVAdapter monday_adapter = new RVAdapter(
                        getActivity(), couple, couples.length());
                monday.setNestedScrollingEnabled(false);
                monday_text.setText(getResources().getString(R.string.day_monday));
                monday.setAdapter(monday_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (pageNumber == 1){
            weekend.setVisibility(View.INVISIBLE);
            JSONObject couples;
            JSONArray[] couple;
            try {
                couples = data[1].getJSONObject(0);
                couple = new JSONArray[couples.length()];
                for(int q = 0; q < couple.length; q++){
                    couple[q] = couples.getJSONArray(String.valueOf(q+1));
                }
                RVAdapter monday_adapter = new RVAdapter(
                        getActivity(), couple, couples.length());
                monday.setNestedScrollingEnabled(false);
                monday_text.setText(getResources().getString(R.string.day_tuesday));
                monday.setAdapter(monday_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (pageNumber == 2){
            weekend.setVisibility(View.INVISIBLE);
            JSONObject couples;
            JSONArray[] couple;
            try {
                couples = data[2].getJSONObject(0);
                couple = new JSONArray[couples.length()];
                for(int q = 0; q < couple.length; q++){
                    couple[q] = couples.getJSONArray(String.valueOf(q+1));
                }
                RVAdapter monday_adapter = new RVAdapter(
                        getActivity(), couple, couples.length());
                monday.setNestedScrollingEnabled(false);
                monday_text.setText(getResources().getString(R.string.day_wednesday));
                monday.setAdapter(monday_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (pageNumber == 3){
            weekend.setVisibility(View.INVISIBLE);
            JSONObject couples;
            JSONArray[] couple;
            try {
                couples = data[3].getJSONObject(0);
                couple = new JSONArray[couples.length()];
                for(int q = 0; q < couple.length; q++){
                    couple[q] = couples.getJSONArray(String.valueOf(q+1));
                }
                RVAdapter monday_adapter = new RVAdapter(
                        getActivity(), couple, couples.length());
                monday.setNestedScrollingEnabled(false);
                monday_text.setText(getResources().getString(R.string.day_thursday));
                monday.setAdapter(monday_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (pageNumber == 4) {
            weekend.setVisibility(View.INVISIBLE);
            JSONObject couples;
            JSONArray[] couple;
            try {
                couples = data[4].getJSONObject(0);
                couple = new JSONArray[couples.length()];
                for(int q = 0; q < couple.length; q++){
                    couple[q] = couples.getJSONArray(String.valueOf(q+1));
                }
                RVAdapter monday_adapter = new RVAdapter(
                        getActivity(), couple, couples.length());
                monday.setNestedScrollingEnabled(false);
                monday_text.setText(getResources().getString(R.string.day_friday));
                monday.setAdapter(monday_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (pageNumber == 5){
            monday_text.setText("Ну и нахера ты сюда зашел(шла), иди бухай, будь студентом");
            weekend.setVisibility(View.VISIBLE);
        }
        return result;
    }
}