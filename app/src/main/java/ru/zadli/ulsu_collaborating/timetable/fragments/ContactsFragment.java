package ru.zadli.ulsu_collaborating.timetable.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

import ru.zadli.ulsu_collaborating.timetable.R;
import ru.zadli.ulsu_collaborating.timetable.activities.MainActivity;
import ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapters.ContactsRVAdapter;
import ru.zadli.ulsu_collaborating.timetable.adapters.RVAdapters.SettingsRVAdapter;

public class ContactsFragment extends Fragment {

    public ContactsFragment() {
    }

    public static ContactsFragment newInstance(final int page) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View result = inflater.inflate(R.layout.contacts_layout, container, false);
        final RecyclerView contacts_rv = result.findViewById(R.id.rv_contacts);
        MainActivity activity = (MainActivity) getActivity();
        DataSnapshot students_contacts = Objects.requireNonNull(activity).get_students_contacts();
        contacts_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ContactsRVAdapter adapter = new ContactsRVAdapter(getActivity(), students_contacts);
        contacts_rv.setAdapter(adapter);
        return result;
    }
}
