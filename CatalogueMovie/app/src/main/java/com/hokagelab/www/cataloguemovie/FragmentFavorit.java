package com.hokagelab.www.cataloguemovie;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

//import com.hokagelab.www.cataloguemovie.adapter.MovieAdapter;
import com.hokagelab.www.cataloguemovie.adapter.MovieAdapterSql;
import com.hokagelab.www.cataloguemovie.db.MovieHelper;
//import com.hokagelab.www.cataloguemovie.model.Movie;

//import java.util.ArrayList;

//import static com.hokagelab.www.cataloguemovie.DetailMovies.TAG;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavorit extends Fragment {
    RecyclerView recyclerViewFav;
    MovieAdapterSql movieAdapter;
    MovieHelper movieHelper;
    ProgressBar progressBar;

    private Cursor list;


    public FragmentFavorit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_favorit, container, false);

        recyclerViewFav = view.findViewById(R.id.recyclerViewFav);
        recyclerViewFav.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progressbar);

        movieHelper = new MovieHelper(getActivity());
        movieAdapter = new MovieAdapterSql(getContext());
        movieAdapter.setListNotes(list);

        recyclerViewFav.setAdapter(movieAdapter);


        //getData();

        new LoadMovieAsync().execute();

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync().execute();
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI, null, null, null, null); }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            progressBar.setVisibility(View.GONE);

            list = cursor;
            movieAdapter.setListNotes(list);
            movieAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
