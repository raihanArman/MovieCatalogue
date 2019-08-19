package id.co.myproject.favoritecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.co.myproject.favoritecatalogue.BuildConfig;
import id.co.myproject.favoritecatalogue.DetailActivity;
import id.co.myproject.favoritecatalogue.R;
import id.co.myproject.favoritecatalogue.model.Movie;
import id.co.myproject.favoritecatalogue.util.CustomOnItemClickListener;

import static id.co.myproject.favoritecatalogue.DetailActivity.EXTRAS_DETAIL_MOVIE;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movieModelList = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getMovieModelList() {
        ArrayList<Movie> models = new ArrayList<>();
        models.addAll(movieModelList);
        return models;
    }

    public void setMovieModelList(List<Movie> movieModelList) {
        if (movieModelList.size() > 0){
            this.movieModelList.clear();
        }
        this.movieModelList.addAll(movieModelList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        final Movie model = movieModelList.get(position);
        holder.tvTitle.setText(model.getTitle());
        Glide.with(context).load(BuildConfig.BASE_URL_IMG+model.getPosterPath()).into(holder.ivFilm);
        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
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
        return movieModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView ivFilm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivFilm = itemView.findViewById(R.id.iv_film);
        }
    }
}
