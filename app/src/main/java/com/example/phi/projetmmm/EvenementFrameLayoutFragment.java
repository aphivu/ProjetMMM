package com.example.phi.projetmmm;

import android.content.Context;
import android.icu.text.SearchIterator;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phi.projetmmm.model.Evenement;


public class EvenementFrameLayoutFragment extends Fragment
        implements EvenementFragment.OnListFragmentInteractionListener {

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

            System.out.println("Evenement List");
            // Create a new Fragment to be placed in the activity layout
            EvenementFragment evenementFragment = new EvenementFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            evenementFragment.setArguments(getActivity().getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.evenement_container, evenementFragment).commit();
        }

        return view;
    }

    @Override
    public void onListFragmentInteraction(Evenement evenement) {

        EvenementDetailsFragment evenementDetailsFragment = new EvenementDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("evenement",evenement);
        evenementDetailsFragment.setArguments(args);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.evenement_container, evenementDetailsFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();

    }


}
