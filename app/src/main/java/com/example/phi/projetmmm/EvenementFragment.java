package com.example.phi.projetmmm;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phi.projetmmm.model.Evenement;

import java.util.ArrayList;

public class EvenementFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private ArrayList<Evenement> mEvenements;

    private View view;
    private MyEvenementRecyclerViewAdapter adapter;

    public EvenementFragment() {

    }

    public static EvenementFragment newInstance(int columnCount) {
        EvenementFragment fragment = new EvenementFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mEvenements = getArguments().getParcelableArrayList("evenements_liste");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_evenement_list, container, false);

        setupRecyclerView();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(Evenement evenement);
    }

    private void setupRecyclerView(){

        adapter = new MyEvenementRecyclerViewAdapter(getArguments().<Evenement>getParcelableArrayList("evenement_list"),mListener);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);

        }

    }


    public void setEvenements(ArrayList<Evenement> evenements){
        this.mEvenements = evenements;
        adapter.setEvenements(this.mEvenements);
        adapter.notifyDataSetChanged();
        System.out.println("notifyChanged");
    }
}
