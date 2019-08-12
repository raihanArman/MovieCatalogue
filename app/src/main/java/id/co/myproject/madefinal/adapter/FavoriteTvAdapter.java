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
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.model.TvShow;
import id.co.myproject.madefinal.util.CustomOnItemClickListener;
import id.co.myproject.madefinal.view.DetailActivity;

import static id.co.myproject.madefinal.view.DetailActivity.EXTRAS_DETAIL_TV;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {

    private List<TvShow> tvShowArrayList = new ArrayList<>();
    private Context context;

    public FavoriteTvAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<TvShow> getTvShowModelArrayList() {
        ArrayList<TvShow> models = new ArrayList<>();
        models.addAll(tvShowArrayList);
        return models;
    }

    public void setTvShowModelArrayList(List<TvShow> tvShowModelArrayList) {
        if (tvShowModelArrayList.size() > 0){
            this.tvShowArrayList.clear();
        }
        this.tvShowArrayList.addAll(tvShowModelArrayList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FavoriteTvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvAdapter.ViewHolder holder, int position) {
        TvShow model = tvShowArrayList.get(position);
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
        return tvShowArrayList.size();
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
