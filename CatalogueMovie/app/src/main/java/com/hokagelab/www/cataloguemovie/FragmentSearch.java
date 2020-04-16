package com.hokagelab.www.cataloguemovie;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hokagelab.www.cataloguemovie.adapter.MovieAdapterFragment;
import com.hokagelab.www.cataloguemovie.api.ApiClient;
import com.hokagelab.www.cataloguemovie.api.MovieClient;
import com.hokagelab.www.cataloguemovie.model.MovieDetail;
import com.hokagelab.www.cataloguemovie.model.MovieResponse;
import com.hokagelab.www.cataloguemovie.model.Result;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearch extends Fragment{
    private final static String TAG = "MainActivity";
    private final static String API_KEY = "007c868395e80dc2e4a833416b24efa5";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mrecyclerView;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public FragmentSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_search, container, false);


        mrecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(layoutManager);

        setHasOptionsMenu(true);//Make sure you have this line of code.

        getAllMovie();

        return view;


    }

    private void getAllMovie() {
        MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
        Call<MovieResponse> call = apiService.reposMovieList(API_KEY);

        // panggil progressdialog biar enak kayak nunggu jodoh
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(50);
        progressDialog.setMessage("Harap Bersabar XD");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();

                List<Result> MovieList = response.body().getResults();
                Log.d(TAG, "Jumlah data Movie: " + String.valueOf(MovieList.size()));
                //lempar data ke adapter
                mAdapter = new MovieAdapterFragment(MovieList);
                mrecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(getActivity(), "koneksi error :(", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchId);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            assert searchManager != null;
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    String EXTRAS_MOVIES = query;
                    if (EXTRAS_MOVIES.isEmpty()){
                        EXTRAS_MOVIES = "tidak ada";
                        Log.e(TAG,"Data = "+EXTRAS_MOVIES);

                        Toast.makeText(getActivity(), "Ada filmnya gak ? "+EXTRAS_MOVIES, Toast.LENGTH_SHORT).show();
                        getSearchMovie(EXTRAS_MOVIES);

                    }else {
                        Log.e(TAG,"Data = "+EXTRAS_MOVIES);
                        Toast.makeText(getActivity(), "Ada filmnya gak ? ada ini, semua film "+EXTRAS_MOVIES, Toast.LENGTH_SHORT).show();
                        getSearchMovie(EXTRAS_MOVIES);
                    }
                    return true;
                }
            };

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    Log.i("SearchView:", "onClose");
                    searchView.onActionViewCollapsed();
                    return false;
                }
            });

            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void getSearchMovie(String EXTRAS_MOVIES){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(50);
        progressDialog.setMessage("Harap Bersabar XD");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
        Call<MovieResponse> call = apiService.reposSearch(API_KEY, EXTRAS_MOVIES);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                List<Result> MovieList = response.body().getResults();
                Log.d(TAG, "Jumlah data Movie: " + String.valueOf(MovieList.size()));
                //lempar data ke adapter
                mAdapter = new MovieAdapterFragment(MovieList);
                mrecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(getActivity(), "koneksi error :(", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
