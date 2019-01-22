package com.example.phi.projetmmm;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.view.View;

import com.example.phi.projetmmm.model.Evenement;
import com.example.phi.projetmmm.model.Lieu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvenementsActivity extends AppCompatActivity
        implements ProfilFragment.OnFragmentInteractionListener,
        EvenementFragment.OnListFragmentInteractionListener,
        SearchView.OnQueryTextListener {


    //Bind activity layout
    @BindView(R.id.eventtoolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.eventtabs)
    TabLayout tabLayout;

    //Ref the fragment in tabs
    private ProfilFragment profilFragment;
    private EvenementFrameLayoutFragment containerEvenement;
    private MapFragment mapFragment;

    //Evenements data
    private ArrayList<Evenement> mEvenements;

    //Firebase ref
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenements);

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        handleIntent(getIntent());

        ButterKnife.bind(this);

        initData();

        setViewPager(viewPager);

    }

    /**
     * In Order to avoid Failed Binder Transaction exception
     * when starting Map application
     * We dont keep saved instance state
     * @param outState to clear
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }


    /**
     * Initialisation du view pager avec les tabs désirées
     * Init d'un fragment par page
     */
    private void setViewPager(ViewPager viewPager){
        setSupportActionBar(toolbar);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        containerEvenement = new EvenementFrameLayoutFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("evenement_list",mEvenements);
        containerEvenement.setArguments(args);
        profilFragment = new ProfilFragment();
        mapFragment = new MapFragment();
       // mapFragment.setArguments(args);

        adapter.addFragment(profilFragment,"Profil");
        adapter.addFragment(containerEvenement,"Evènements");
        adapter.addFragment(mapFragment,"Map");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.baseline_account_circle_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.baseline_list_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.baseline_map_white_24dp);

    }


    /**
     * Classe adapter pour le page viewer
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
        }
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Evenement evenement) {

        containerEvenement.onListFragmentInteraction(evenement);
    }

    /**
     * Creation de la bar de menu dans l'actitivy
     * @param menu layout du menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_actions, menu);

        /**
         * Ajout d'un champ de recherche par séquence de charactères
         */
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        return true;

    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Management des actions de la bare d'action
     * @param item du menu correspondant à une action
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout_action:

                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

                return true;


            case R.id.filter_action:

                displayFilterDialog();

                return true;

        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println(query);

            onQueryTextChange(query);
        }
    }


    /**
     * Initialisation du jeux de données
     * Récupération de la liste des évènements disponnibles sur firebase
     */
    private void initData(){

        mEvenements = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        databaseReference.limitToFirst(1000).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                   /* String title = snapshot.child("fields/titre_fr").getValue().toString();
                    String description = snapshot.child("fields/description_fr").getValue().toString();
                    String id = snapshot.child("fields/identifiant").getValue().toString();
                    String adresse = snapshot.child("fields/adresse").getValue().toString();


                    String ville = snapshot.hasChild("fields/ville") ?
                        snapshot.child("fields/ville").getValue().toString() : "inconnu";

                    String departement = snapshot.hasChild("fields/departement") ?
                        snapshot.child("fields/departement").getValue().toString() : "inconnu";

                    String region = snapshot.hasChild("fields/region") ?
                        snapshot.child("fields/region").getValue().toString() : "inconnu";

                    String image = snapshot.hasChild("fields/image") ?
                            snapshot.child("fields/image").getValue().toString() : "";

                    double lat = (double) snapshot.child("fields/geolocalisation/0").getValue();
                    double longi = (double) snapshot.child("fields/geolocalisation/1").getValue();

                    Lieu lieu = new Lieu(ville,departement,region,longi,lat);
                    Evenement evenement = new Evenement(title,description,id,image);
                    evenement.setLieu(lieu);*/

                    mEvenements.add(snapshotToEvenement(snapshot));
                }

                containerEvenement.setDataFetched(mEvenements);
                mapFragment.setEvenements(mEvenements);

                //mapFragment.addCityMarker();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Test", "Failed to read value.", error.toException());
            }
        });
    }

    private Evenement snapshotToEvenement(DataSnapshot snapshot){

        String title = snapshot.child("fields/titre_fr").getValue().toString();
        String description = snapshot.child("fields/description_fr").getValue().toString();
        String id = snapshot.child("fields/identifiant").getValue().toString();
        String adresse = snapshot.child("fields/adresse").getValue().toString();


        String ville = snapshot.hasChild("fields/ville") ?
                snapshot.child("fields/ville").getValue().toString() : "inconnu";

        String departement = snapshot.hasChild("fields/departement") ?
                snapshot.child("fields/departement").getValue().toString() : "inconnu";

        String region = snapshot.hasChild("fields/region") ?
                snapshot.child("fields/region").getValue().toString() : "inconnu";

        String image = snapshot.hasChild("fields/image") ?
                snapshot.child("fields/image").getValue().toString() : "";

        double lat = (double) snapshot.child("fields/geolocalisation/0").getValue();
        double longi = (double) snapshot.child("fields/geolocalisation/1").getValue();

        Lieu lieu = new Lieu(ville,departement,region,longi,lat);
        Evenement evenement = new Evenement(title,description,id,image);
        evenement.setLieu(lieu);

        return evenement;
    }

    /**
     * MAJ du jeux de données par une search query
     * @param query donnée dans la searchview
     */
    private void searchFilterData(String query){

    }

    private void displayFilterDialog(){

        FragmentManager fm = getSupportFragmentManager();
        FilterFragment filterFragment = FilterFragment.newInstance();
        filterFragment.show(fm,"filter_fragment");

    }


    public void filterData(String region, String date) {
        //Toast.makeText(this,"Region: " +region + " date: "+ date,Toast.LENGTH_LONG).show();

        containerEvenement.setIndeterminate();

        Query query = databaseReference.orderByChild("fields/region").equalTo(region);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //List<String> value = new ArrayList<>();
                    ArrayList<Evenement> filterEvenement = new ArrayList<>();
                    for(DataSnapshot issue :dataSnapshot.getChildren()){
                        //value.add(issue.child("fields/region").getValue().toString());
                        filterEvenement.add(snapshotToEvenement(issue));
                    }
                    System.out.println("Size Test: " + filterEvenement.size());
                    mEvenements = filterEvenement;
                    containerEvenement.setDataFetched(mEvenements);
                    mapFragment.setEvenements(mEvenements);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Test", "Failed to read value.", databaseError.toException());
            }
        });
    }

    public void setViewPager(String titre){
        viewPager.setCurrentItem(1);
        for(Evenement e : mEvenements){
            if (e.getTitre().equals(titre)){
                onListFragmentInteraction(e);
                return;
            }
        }

    }
}
