package id.co.myproject.madefinal.view;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.adapter.TvShowAdapter;
import id.co.myproject.madefinal.model.TvShow;
import id.co.myproject.madefinal.model.TvShowResults;
import id.co.myproject.madefinal.viewmodel.TvShowViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {

    private RecyclerView recyclerView;
    private TvShowAdapter adapter;
    private List<TvShow> tvShowList;
    private TvShowViewModel tvShowViewModel;
    private ProgressDialog progressDialog;

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvShowList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_tv);
        progressDialog = new ProgressDialog(getActivity());
        adapter = new TvShowAdapter(getActivity());
        progressDialog.setMessage("cek");
        progressDialog.show();

        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.mLiveDataTv().removeObservers(this);
        tvShowViewModel.mLiveDataTv().observe(this, new Observer<TvShowResults>() {
            @Override
            public void onChanged(TvShowResults tvShowResults) {
                progressDialog.dismiss();
                recyclerView.setVisibility(View.VISIBLE);
                List<TvShow> models = tvShowResults.getTvShowModels();
                tvShowList.addAll(models);
                adapter.setTvShows(tvShowList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
