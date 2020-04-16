package com.hokagelab.www.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.hokagelab.www.cataloguemovie.R;
import com.hokagelab.www.cataloguemovie.model.Movie;
import com.hokagelab.www.cataloguemovie.model.Result;

import static android.support.constraint.Constraints.TAG;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.CONTENT_URI;

public class MovieAdapterSql extends RecyclerView.Adapter<MovieAdapterSql.viewHolder> {
//    private ArrayList<Movie> mData = new ArrayList<>();
//    private LayoutInflater mInflater;

    private Cursor listNotes;
    private Context context;

    public MovieAdapterSql(Context context) {
        this.context = context;
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setListNotes(Cursor listNotes) {
        this.listNotes = listNotes;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new viewHolder(mView);

    }


    @Override
    public void onBindViewHolder(@NonNull final MovieAdapterSql.viewHolder holder, int position) {
        final Movie movies = getItem(position);

//        Log.i(TAG, "DATA APA = "+movies.getBackdrop());

        String imgPath = "http://image.tmdb.org/t/p/w185/"+movies.getBackdrop();

        if (movies.getBackdrop() == null) {
            holder.imagePoster.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        }else{
            Glide.with(context)
                    .load(imgPath)
                    .into(holder.imagePoster);
        }

        holder.tv_title.setText(movies.getTitle());
        holder.tv_overview.setText(movies.getDescription());
        holder.tv_release.setText(movies.getDate());
//        holder.tv_genre.setText(movies.getGenre());
//        holder.tv_rating.setText(movies.getRating());
//        holder.tv_website.setText(movies.getWebsite());
//        holder.tv_company.setText(movies.getCompany());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), DetailMovies.class);

                Uri uri = Uri.parse(CONTENT_URI+"/"+movies.getId());
                i.setData(uri);
                i.putExtra("backdrop", movies.getBackdrop());
                i.putExtra("title", movies.getTitle());
                i.putExtra("overview", movies.getDescription());
                i.putExtra("release_date", movies.getDate());
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listNotes == null){
            return 0;
        }
        return listNotes.getCount();
    }

    private Movie getItem(int position){
        if (!listNotes.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(listNotes);
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_overview, tv_release, tv_genre, tv_rating, tv_website, tv_company;
        ImageView imagePoster;

        viewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            tv_release = itemView.findViewById(R.id.tv_releasedate);
            tv_genre = itemView.findViewById(R.id.tv_genre);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_website = itemView.findViewById(R.id.tv_website);
            tv_company = itemView.findViewById(R.id.tv_company);
            imagePoster = itemView.findViewById(R.id.imgPoster);
        }
    }
}

