package com.hokagelab.www.cataloguemovie;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hokagelab.www.cataloguemovie.adapter.MovieAdapterGrid;
import com.hokagelab.www.cataloguemovie.adapter.MovieAdapterMulti;
import com.hokagelab.www.cataloguemovie.api.ApiClient;
import com.hokagelab.www.cataloguemovie.api.MovieClient;
import com.hokagelab.www.cataloguemovie.model.MovieResponse;
import com.hokagelab.www.cataloguemovie.model.Result;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentComing extends Fragment {
    private final static String TAG = "MainActivity";
    private final static String API_KEY = "007c868395e80dc2e4a833416b24efa5";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView soonRecyclerView;



    public FragmentComing() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_coming, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        soonRecyclerView = view.findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        soonRecyclerView.setNestedScrollingEnabled(false);
        soonRecyclerView.setHasFixedSize(true);
        layoutManager.scrollToPositionWithOffset(0, 0);

        soonRecyclerView.setLayoutManager(layoutManager);

        getNowComing();
    }

    private void getNowComing(){
        MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
        Call<MovieResponse> call = apiService.reposUpComing(API_KEY);

        // panggil progressdialog biar enak kayak nunggu jodoh
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("Harap Bersabar XD");
//        progressDialog.setTitle("Catalogue Movie");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();

                List<Result> MovieList = response.body().getResults();
                Log.d(TAG, "Jumlah data Movie: " + String.valueOf(MovieList.size()));
                //lempar data ke adapter
                //mRecyclerView.setAdapter(mAdapter);
                mAdapter = new MovieAdapterGrid(MovieList);
                soonRecyclerView.setAdapter(mAdapter);

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
