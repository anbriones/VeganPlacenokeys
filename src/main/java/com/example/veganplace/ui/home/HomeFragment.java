package com.example.veganplace.ui.home;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelmapas.Result;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;


public class HomeFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,  ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_REQUEST_CODE = 1;
    private HomeViewModel homeViewModel;
    private AppContainer appContainer;
    GoogleMap map;
    private String busqueda;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private HomeViewModelFactory factory;
    Context context;

    /**
     * Flag indicating whether a requested permission has been denied after returning in {@link
     * #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;


    private static final String LOG_TAG = HomeFragment.class.getSimpleName();


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

            map=googleMap;
                MapsInitializer.initialize(getActivity().getApplicationContext());
                EditText bus = inflatedView.findViewById(R.id.busquedaciudad);
                Button searchButton = inflatedView.findViewById(R.id.butonlupa);
                LatLngBounds spainbounds = new LatLngBounds(
                        new LatLng(40, -4), // SW bounds
                        new LatLng(40, 2)  // NE bounds
                );
                //Se muestra el mapa del mundo centrado en las coordenadas que comprenden España
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spainbounds.getCenter(), 5));
            // Activo el marcador de la posición actual
                    enableMyLocation();

                searchButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        googleMap.clear();
                        busqueda = bus.getText().toString();
                        homeViewModel.setbusqueda(busqueda);
                    homeViewModel.onRefresh();
                        homeViewModel.getlocalizaciones().observe(getActivity(), localizaciones -> {
                              for (int j = 0; j < localizaciones.size(); j++) {
                                LatLng posicion = new LatLng(localizaciones.get(j).getLat(), localizaciones.get(j).getLng());
                                Log.d(LOG_TAG,"dir loca"+localizaciones.get(j).getAdress_rest() );

                                  googleMap.addMarker(new MarkerOptions().position(posicion).title(localizaciones.get(j).getAdress_rest()));
                                //este código solo funciona cuando no se ha dibujado nada en el mapa, en el momento que el mapa ha mostrado una vez los iconos deja de funcionar.
                              /* googleMap.addMarker(new MarkerOptions().position(posicion).title(localizaciones.get(j).getAdress_rest())
                                        .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_baseline_eco_24)));*/

                            }



                        });

                    }
                });

                googleMap.setOnMarkerClickListener(marker -> {
                    String address = marker.getTitle();
                    homeViewModel.setfiltrores(address);
                    homeViewModel.getRestaurante().observe(getActivity(), restaurante -> {
                        Intent intentdetalles = new Intent(getActivity(), Restaurant_detail.class);
                        Result res = restaurante;
                        if (restaurante != null) {
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


    public void mostrarmarkers(GoogleMap mMap) {
        mMap.clear();
        homeViewModel.getlocalizaciones().observe(getActivity(), localizaciones -> {
            for (int j = 0; j < localizaciones.size(); j++) {
                LatLng posicion = new LatLng(localizaciones.get(j).getLat(), localizaciones.get(j).getLng());
                Log.d(LOG_TAG,"dir loca"+localizaciones.get(j).getAdress_rest() );
                mMap.addMarker(new MarkerOptions()
                        .position(posicion));

                mMap.addMarker(new MarkerOptions().position(posicion).title(localizaciones.get(j).getAdress_rest())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_eco_24)));
            }



        });
        mMap.clear();
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            return;
        }


    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        enableMyLocation();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}