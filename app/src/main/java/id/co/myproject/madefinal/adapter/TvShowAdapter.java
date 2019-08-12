package id.co.myproject.madefinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.model.TvShow;
import id.co.myproject.madefinal.util.CustomOnItemClickListener;
import id.co.myproject.madefinal.view.DetailActivity;

import static id.co.myproject.madefinal.view.DetailActivity.EXTRAS_DETAIL_TV;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewModel> {

    private List<TvShow> tvShows = new ArrayList<>();
    private Context context;

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public List<TvShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<TvShow> tvShows) {
        if (tvShows.size()>0){
            this.tvShows.clear();
        }
        this.tvShows.addAll(tvShows);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowAdapter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.ViewModel holder, int position) {
        TvShow model = tvShows.get(position);
        holder.tvFilm.setText(model.getName());
        Glide.with(context).load(BuildConfig.BASE_URL_IMG+model.getPosterPath()).into(holder.ivFilm);
        holder.cdFilm.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id_tv", model.getId());
                intent.putExtra("detail", EXTRAS_DETAIL_TV);
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder{
        private TextView tvFilm;
        private ImageView ivFilm;
        private CardView cdFilm;
        public ViewModel(@NonNull View itemView) {
            super(itemView);
            tvFilm = itemView.findViewById(R.id.tv_film);
            ivFilm = itemView.findViewById(R.id.iv_film);
            cdFilm = itemView.findViewById(R.id.card);
        }
    }
}
