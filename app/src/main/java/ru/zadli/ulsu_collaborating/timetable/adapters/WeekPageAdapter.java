package ru.zadli.ulsu_collaborating.timetable.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ru.zadli.ulsu_collaborating.timetable.fragments.WeekFragment;

public class WeekPageAdapter extends FragmentPagerAdapter {

    public WeekPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public int getCount() {
        return (6);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return (WeekFragment.newInstance(position));
    }

}