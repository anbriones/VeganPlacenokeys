package com.example.veganplace.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelmapas.Result;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;


public class HomeFragment extends Fragment {
    private static final int LOCATION_REQUEST_CODE = 1;
    private HomeViewModel homeViewModel;
    private MapView mMapView;
    private AppContainer appContainer;
    private HomeViewModelFactory factory;
    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_home, container, false);


        factory = InjectorUtils.provideMainActivityViewModelFactoryrestaurantes(this.getActivity().getApplicationContext());
        appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
        homeViewModel = new ViewModelProvider(this, appContainer.factoryrestaurantes).get(HomeViewModel.class);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                MapsInitializer.initialize(getContext());


                LatLngBounds spainbounds = new LatLngBounds(
                        new LatLng(40, -4), // SW bounds
                        new LatLng(40, 2)  // NE bounds
                );
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spainbounds.getCenter(), 5));


                homeViewModel.getlocalizaciones().observe(getActivity(), localizaciones -> {
                    // LatLng posicion = new LatLng(39.4762, -6.37076);
                    for (int i = 0; i < localizaciones.size(); i++) {
                        LatLng posicion = new LatLng(localizaciones.get(i).getLat(), localizaciones.get(i).getLng());
                        mMap.addMarker(new MarkerOptions().position(posicion).title(localizaciones.get(i).getAdress_rest()));
                    }
                });

                mMap.setOnMarkerClickListener(marker -> {
                    String address = marker.getTitle();
                    homeViewModel.setfiltrores(address);
                   homeViewModel.getRestaurante().observe(getActivity(), restaurante -> {


                       Result res = restaurante;
                        if (res != null) {
                            Intent intentdetalles = new Intent(getActivity(), Restaurant_detail.class);
                            intentdetalles.putExtra("restaurantedetalle", (Serializable) res);
                            startActivity(intentdetalles);
                        }
                    });

                    return true;
                });

            }
        });

                return inflatedView;
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    mMap.setOnMyLocationButtonClickListener((GoogleMap.OnMyLocationButtonClickListener) getContext());
                    mMap.setOnMyLocationClickListener((GoogleMap.OnMyLocationClickListener) getContext());

                    return;
                }

            } else {
                Toast.makeText(getActivity(), "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }
}



