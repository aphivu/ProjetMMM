package com.example.phi.projetmmm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phi.projetmmm.model.Evenement;

import java.util.ArrayList;


public class EvenementFrameLayoutFragment extends Fragment
        implements EvenementFragment.OnListFragmentInteractionListener {

    private EvenementFragment evenementFragment;
    private EvenementDetailsFragment evenementDetailsFragment;

    public EvenementFrameLayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_evenement_frame_layout, container, false);

        if (view.findViewById(R.id.evenement_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return view;
            }


            setIndeterminate();
        }

        return view;
    }

    @Override
    public void onListFragmentInteraction(Evenement evenement) {

        evenementDetailsFragment = new EvenementDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("evenement",evenement);
        evenementDetailsFragment.setArguments(args);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.evenement_container, evenementDetailsFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    public void setIndeterminate(){
        IndeterminateFragment indeterminateFragment = new IndeterminateFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.evenement_container,indeterminateFragment).commit();

    }


    public void setDataFetched(ArrayList<Evenement> evenements){

        if ( evenementFragment != null){
            evenementFragment.setEvenements(evenements);


        }
        else {
            evenementFragment = new EvenementFragment();

            evenementFragment.setArguments(getActivity().getIntent().getExtras());
            evenementFragment.setArguments(getArguments());
        }


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.evenement_container, evenementFragment,"evenement_fragment_tag").commit();

        //evenementFragment.setEvenements(evenements);

    }

    public Evenement getEvenement(){

        if (evenementDetailsFragment != null){
            //System.out.println("Container getEvenement");
            evenementDetailsFragment.getEvenement();
        }

        return null;
    }


}
