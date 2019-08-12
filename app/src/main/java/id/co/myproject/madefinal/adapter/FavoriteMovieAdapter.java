package id.co.myproject.madefinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.util.CustomOnItemClickListener;
import id.co.myproject.madefinal.view.DetailActivity;

import static id.co.myproject.madefinal.view.DetailActivity.EXTRAS_DETAIL_MOVIE;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {

    private List<Movie> movieModelList = new ArrayList<>();
    private Context context;

    public FavoriteMovieAdapter(Context context) {
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
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.ViewHolder holder, int position) {
        Movie model = movieModelList.get(position);
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
