package com.example.phi.projetmmm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phi.projetmmm.EvenementFragment.OnListFragmentInteractionListener;
import com.example.phi.projetmmm.dummy.DummyContent.DummyItem;
import com.example.phi.projetmmm.model.Evenement;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEvenementRecyclerViewAdapter extends RecyclerView.Adapter<MyEvenementRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;

    private List<String> mTitles;
    private List<Evenement> mEvenements;
    private final OnListFragmentInteractionListener mListener;

    public MyEvenementRecyclerViewAdapter(List<Evenement> evenements,
            OnListFragmentInteractionListener listener) {
        mEvenements = evenements;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_cards, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mCardText.setText(mEvenements.get(position).getTitre());
        holder.mEvenement = mEvenements.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mEvenement);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEvenements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public Evenement mEvenement;

        @BindView(R.id.card_text) TextView mCardText;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
            mView = view;
        }

    }
}
