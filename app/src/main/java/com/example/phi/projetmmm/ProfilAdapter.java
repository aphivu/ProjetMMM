package com.example.phi.projetmmm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import com.example.phi.projetmmm.model.Evenement;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfilAdapter extends RecyclerView.Adapter<ProfilAdapter.ProfilViewHolder> {

    private List<Evenement> mEvenements;
    private ProfilFragment.OnFragmentInteractionListener mListener;

    public static class ProfilViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public Evenement mEvenement;
        @BindView(R.id.card_text) TextView mCardText;

        public ProfilViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
            mView = v;
        }
    }

    public ProfilAdapter(List<Evenement> evenements,
                         ProfilFragment.OnFragmentInteractionListener listener){
        mEvenements = evenements;
        mListener = listener;
    }

    @Override
    public ProfilAdapter.ProfilViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType){
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_cards,parent,false);

        ProfilViewHolder vh = new ProfilViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ProfilViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mView.setText(mEvenements.get(position).getTitre());
        holder.mEvenement = mEvenements.get(position);
        holder.mCardText.setText(holder.mEvenement.getTitre());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFragmentInteraction(holder.mEvenement);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mEvenements.size();
    }

    public void setEvenements(List<Evenement> evenements){
        System.out.println("Fav set *********************************");
        mEvenements = evenements;
        notifyDataSetChanged();
    }


}
