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
import id.co.myproject.favoritecatalogue.model.TvShow;
import id.co.myproject.favoritecatalogue.util.CustomOnItemClickListener;

import static id.co.myproject.favoritecatalogue.DetailActivity.EXTRAS_DETAIL_TV;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    List<TvShow> tvShowList = new ArrayList<>();
    Context context;

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<TvShow> getTvShowModelList() {
        ArrayList<TvShow> models = new ArrayList<>();
        models.addAll(tvShowList);
        return models;
    }

    public void setTvShowModelList(List<TvShow> tvShowModelList) {
        if (tvShowModelList.size() > 0){
            this.tvShowList.clear();
        }
        this.tvShowList.addAll(tvShowModelList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fav, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.ViewHolder holder, int position) {
        final TvShow model = tvShowList.get(position);
        holder.tvTitle.setText(model.getName());
        Glide.with(context).load(BuildConfig.BASE_URL_IMG+model.getPosterPath()).into(holder.ivFilm);
        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
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
        return tvShowList.size();
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
