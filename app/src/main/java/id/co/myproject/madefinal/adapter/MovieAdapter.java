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
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.MovieResults;
import id.co.myproject.madefinal.util.CustomOnItemClickListener;
import id.co.myproject.madefinal.view.DetailActivity;

import static id.co.myproject.madefinal.view.DetailActivity.EXTRAS_DETAIL_MOVIE;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewModel> {

    private List<Movie> movies = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovieModelList(List<Movie> movieModelList) {
        if (movieModelList.size() > 0){
            this.movies.clear();
        }
        this.movies.addAll(movieModelList);
        notifyDataSetChanged();
    }

    public List<Movie> getMovieModelList() {
        return movies;
    }



    @NonNull
    @Override
    public MovieAdapter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewModel holder, int position) {
        Movie model = movies.get(position);
        holder.tvFilm.setText(model.getTitle());
        Glide.with(context).load(BuildConfig.BASE_URL_IMG+model.getPosterPath()).into(holder.ivFilm);
        holder.cdFilm.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id_movie", model.getId());
                intent.putExtra("detail", EXTRAS_DETAIL_MOVIE);
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return movies.size();
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
