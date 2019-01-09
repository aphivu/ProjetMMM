package com.example.phi.projetmmm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

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

    ArrayList<Evenement> mEvenements;

    private GoogleMap googleMap;

    private Marker mSelected;

    public MapFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEvenements = new ArrayList<>();

        if (getArguments() != null) {
            mEvenements = getArguments().getParcelableArrayList("evenements_liste");
        }

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

        //addCityMarker();

        this.googleMap.setOnMarkerClickListener(this);
        this.googleMap.setOnInfoWindowCloseListener(this);
    }

    public void setEvenements(ArrayList<Evenement> evenements){
        mEvenements = evenements;
        addCityMarker();
    }

    public void addCityMarker(){

        if (mMapView != null) {
            for (Evenement e : mEvenements) {
                LatLng marker = new LatLng(e.getLieu().getLatitude(), e.getLieu().getLongitude());
                //System.out.println("lat: " + e.getLieu().getLatitude());
                googleMap.addMarker(new MarkerOptions().position(marker).title(e.getTitre()));
            }
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

        }
    }
}
