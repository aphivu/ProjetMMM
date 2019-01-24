package com.example.phi.projetmmm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phi.projetmmm.model.Evenement;
import com.example.phi.projetmmm.model.Note;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvenementDetailsFragment extends Fragment {

    private Evenement mEvenement;

    @BindView(R.id.details_title_value) TextView mTitle;

    @BindView(R.id.details_description_value) TextView mDescription;

    //@BindView(R.id.details_identifiant_value) TextView mIdentifiant;
    @BindView(R.id.ratingBarMean)
    RatingBar mRatingBarMean;

    @BindView(R.id.evenement_image)
    ImageView mImageView;

    @BindView(R.id.ratingBarUser)
    RatingBar mRatinBarUser;

    @BindView(R.id.button_rate)
    Button mbuttonRate;

    @BindView(R.id.textView_nVotes)
    TextView mTextViewNVotes;

    public EvenementDetailsFragment() {
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

        View view = inflater.inflate(R.layout.fragment_evenement_details, container, false);
        ButterKnife.bind(this,view);
        // Inflate the layout for this fragment

        if (getArguments() != null) {

            mEvenement = getArguments().getParcelable("evenement");
        } else {
            mEvenement = new Evenement();
        }


        mTitle.setText(mEvenement.getTitre());
        mDescription.setText(mEvenement.getDescription());
        //mIdentifiant.setText(mEvenement.getId());
        if (mEvenement.getUrlImage() != null){
            Picasso.get().load(mEvenement.getUrlImage()).fit().centerCrop().into(mImageView);
        }

        mRatingBarMean.setRating(mEvenement.getNote().getMean());
        mTextViewNVotes.setText(mEvenement.getNote().getNumber() + " votes");


        mbuttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Note note = mEvenement.getNote();
                note.setMean((mRatinBarUser.getRating() + note.getMean() * note.getNumber()) / (note.getNumber() + 1));
                note.setNumber(note.getNumber() + 1);
                mEvenement.setNote(note);
                EvenementsActivity evenementsActivity = (EvenementsActivity) getActivity();
                if (evenementsActivity != null){
                    evenementsActivity.setRating(mEvenement);
                }
            }
        });

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_fav);
        item.setVisible(true);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                /*String text= (mEvenement != null ) ? mEvenement.getTitre() : "even null";
                Toast.makeText(getActivity(),text,Toast.LENGTH_LONG).show();*/

                EvenementsActivity evenementsActivity = (EvenementsActivity) getActivity();
                if (evenementsActivity != null){
                    //evenementsActivity.setRating(mEvenement);
                    evenementsActivity.insertEvenement(mEvenement);
                }

                return false;
            }
        });
    }

    public Evenement getEvenement(){
        return mEvenement;
    }

}
