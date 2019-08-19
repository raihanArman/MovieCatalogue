package id.co.myproject.madefinal.adapter;

import android.content.Context;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.view.FavoriteMovieFragment;
import id.co.myproject.madefinal.view.FavoriteTvFragment;

public class FavoriteViewPager extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    Context context;

    public FavoriteViewPager(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        fragments = new ArrayList<>();
        fragments.add(new FavoriteMovieFragment());
        fragments.add(new FavoriteTvFragment());

        titles = new ArrayList<>();
        titles.add(context.getResources().getString(R.string.film));
        titles.add(context.getResources().getString(R.string.tv));
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
