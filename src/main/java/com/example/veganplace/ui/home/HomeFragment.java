package com.example.veganplace.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.veganplace.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public  class HomeFragment extends Fragment implements OnMapReadyCallback {
    private HomeViewModel homeViewModel;
    private MapView mMapView;
    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

    View inflatedView = inflater.inflate(R.layout.fragment_home, container, false);

        return inflatedView;



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng caceres = new LatLng(39.4762, -6.37076);
        mMap.addMarker(new MarkerOptions().position(caceres).title("Marker in Cáceres"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(caceres));

    }
}









