package com.example.phi.projetmmm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phi.projetmmm.Room.EvenementViewModel;
import com.example.phi.projetmmm.model.Evenement;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class ProfilFragment extends Fragment {

    @BindView(R.id.profil_text)
    TextView profilText;

    @BindView(R.id.recycler_profil)
    RecyclerView mRecyclerView;
    private ProfilAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //private List<Evenement> mFavorites;

    private EvenementViewModel mViewModel;


    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        ButterKnife.bind(this,view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ProfilAdapter(new ArrayList<Evenement>(),mListener);

        mViewModel = ViewModelProviders.of(getActivity()).get(EvenementViewModel.class);
        mViewModel.getEvenements().observe(this, new Observer<List<Evenement>>() {
            @Override
            public void onChanged(@Nullable List<Evenement> evenements) {
                mAdapter.setEvenements(evenements);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_fav);
        item.setVisible(false);
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Evenement evenement);
    }

    public void insert(Evenement evenement){

        this.mViewModel.insert(evenement);
    }

    public void delete(Evenement evenement){
        this.mViewModel.delete(evenement);
    }

    public Boolean isPresent(Evenement evenement){
        return mViewModel.isPresent(evenement);
    }
}
