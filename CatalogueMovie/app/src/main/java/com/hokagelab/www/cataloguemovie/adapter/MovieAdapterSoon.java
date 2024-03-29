package com.hokagelab.www.cataloguemovie.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hokagelab.www.cataloguemovie.DetailMovies;
import com.hokagelab.www.cataloguemovie.DetailMoviesLengkap;
import com.hokagelab.www.cataloguemovie.R;
import com.hokagelab.www.cataloguemovie.model.Result;

import java.util.List;

//By Aris Kurniawan

public class MovieAdapterSoon extends RecyclerView.Adapter<MovieAdapterSoon.ViewHolder>{
    private static final String TAG = "MovieAdapter";
    private List<Result> mMovieList;

    public MovieAdapterSoon(List <Result> MovieList){
        this.mMovieList = MovieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_soon, parent, false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final int pos = position;
        final Result movies = mMovieList.get(position);
        String imgPath = "http://image.tmdb.org/t/p/w185/"+movies.getPoster_path();

        if (movies.getPoster_path() == null) {
            holder.imagePoster.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        }else{
            Glide.with(holder.itemView.getContext())
                    .load(imgPath) //url
                    .into(holder.imagePoster);
        }

        holder.tv_title.setText(mMovieList.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "posisi dimana nih = " + pos);
                Result data = mMovieList.get(pos);
                Intent i = new Intent(holder.itemView.getContext(), DetailMoviesLengkap.class);
                i.putExtra("id_movie", data.getId());
                i.putExtra("title", data.getTitle());
                i.putExtra("overview", data.getOverview());
                i.putExtra("release_date", data.getRelease_date());
                i.putExtra("backdrop", data.getBackdrop_path());
                i.putExtra("poster", data.getPoster_path());

                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_overview, tv_releasedate, tv_voteAverage;
        ImageView imagePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            tv_releasedate = itemView.findViewById(R.id.tv_releasedate);
            imagePoster = itemView.findViewById(R.id.imgPoster);
        }
    }
}
