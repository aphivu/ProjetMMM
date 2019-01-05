package com.example.phi.projetmmm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phi.projetmmm.model.Evenement;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvenementDetailsFragment extends Fragment {

    private Evenement mEvenement;

    @BindView(R.id.details_title_value) TextView mTitle;

    @BindView(R.id.details_description_value) TextView mDescription;

    @BindView(R.id.details_identifiant_value) TextView mIdentifiant;

    public EvenementDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mEvenement = getArguments().getParcelable("evenement");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_evenement_details, container, false);
        ButterKnife.bind(this,view);
        // Inflate the layout for this fragment


        mTitle.setText(mEvenement.getTitre());
        mDescription.setText(mEvenement.getDescription());
        mIdentifiant.setText(mEvenement.getId());

        return view;
    }




}
