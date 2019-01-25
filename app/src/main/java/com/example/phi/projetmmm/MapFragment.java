package com.example.phi.projetmmm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.phi.projetmmm.model.Evenement;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowCloseListener,
        OnMapReadyCallback,
        View.OnClickListener {

    @BindView(R.id.mapView)
    MapView mMapView;

    @BindView(R.id.map_description_button)
    ImageButton mDescriptionButton;

    @BindView(R.id.map_itinary_button)
    ImageButton mItinaryButton;

    @BindView(R.id.map_fav_button)
    ImageButton mFavButton;

    ArrayList<Evenement> mEvenements;

    private GoogleMap googleMap;

    private Marker mSelected;

    List<Marker> mMarkers;
    List<Marker> mFavorites;

    boolean isFav = false;
    Polyline polyline;

    public MapFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEvenements = new ArrayList<>();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        ButterKnife.bind(this,rootView);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mDescriptionButton.setOnClickListener(this);
        mItinaryButton.setOnClickListener(this);
        mDescriptionButton.setEnabled(false);
        mItinaryButton.setEnabled(false);

        mFavButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        mSelected = marker;

        mItinaryButton.setEnabled(true);
        mDescriptionButton.setEnabled(true);

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng france = new LatLng(48, 2);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(france).zoom(6).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        Toast.makeText(getActivity(),"Map is ready",Toast.LENGTH_LONG).show();

        if (getArguments() != null) {
            mEvenements = getArguments().getParcelableArrayList("evenement_list");
        }
        addCityMarker();

        this.googleMap.setOnMarkerClickListener(this);
        this.googleMap.setOnInfoWindowCloseListener(this);

    }

    public void setEvenements(ArrayList<Evenement> evenements){
        mEvenements = evenements;
        addCityMarker();
    }

    public void addCityMarker(){

        if (mMapView != null) {
            mMarkers = new ArrayList<>();
            mFavorites = new ArrayList<>();
            googleMap.clear();
            PolylineOptions optionsPoly = new PolylineOptions().clickable(false);
            for (Evenement e : mEvenements) {
                LatLng marker = new LatLng(e.getLieu().getLatitude(), e.getLieu().getLongitude());
                MarkerOptions options = new MarkerOptions().position(marker).title(e.getTitre());
                EvenementsActivity activity = (EvenementsActivity) getActivity();
                if (activity.isFavorite(e)) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .zIndex(1.0f);
                    mFavorites.add(googleMap.addMarker(options));
                    optionsPoly.add(options.getPosition());
                } else {
                    mMarkers.add(googleMap.addMarker(options));
                }


            }

            polyline = googleMap.addPolyline(optionsPoly);
            mFavButton.setImageResource(R.drawable.icons8_star_filled_48);
            displayUnFav();
        }

    }

    @Override
    public void onInfoWindowClose(Marker marker) {

        mSelected = null;
        mItinaryButton.setEnabled(false);
        mDescriptionButton.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.map_itinary_button:

                Uri itiTarget =
                        Uri.parse("geo:"+ mSelected.getPosition().latitude+","+mSelected.getPosition().longitude
                        + "?q=" + mSelected.getPosition().latitude + "," + mSelected.getPosition().longitude + "("
                        +mSelected.getTitle()+")");

                Intent intent = new Intent(Intent.ACTION_VIEW,itiTarget);
                startActivity(intent);
                return;


            case R.id.map_description_button:

                //Toast.makeText(getActivity(),"Test",Toast.LENGTH_LONG).show();
                ((EvenementsActivity) getActivity()).setViewPager(mSelected.getTitle());

                return;


            case R.id.map_fav_button:
                if(isFav){
                    mFavButton.setImageResource(R.drawable.icons8_star_filled_48);
                    displayUnFav();
                    isFav = !isFav;
                    return;
                }


                mFavButton.setImageResource(R.drawable.icons8_star_48);
                displayFav();
                isFav = !isFav;
                return;

        }
    }

    private void displayUnFav(){
        for(Marker m : mFavorites){
            m.setVisible(false);
        }
        polyline.setVisible(false);
        for(Marker m : mMarkers){
            m.setVisible(true);
        }
    }

    private void displayFav(){

        for(Marker m : mMarkers){
            m.setVisible(false);
        }

        for(Marker m : mFavorites){
            m.setVisible(true);
        }
        polyline.setVisible(true);

    }
}
