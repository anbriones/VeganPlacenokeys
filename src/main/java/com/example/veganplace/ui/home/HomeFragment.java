package com.example.veganplace.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelmapas.Photo;
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

    private Photo foto;
    private String busqueda;
    private SwipeRefreshLayout mSwipeRefreshLayout;


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

                MapsInitializer.initialize(getContext());

               EditText bus = inflatedView.findViewById(R.id.busquedaciudad);

                Button searchButton = inflatedView.findViewById(R.id.butonlupa);

                LatLngBounds spainbounds = new LatLngBounds(
                        new LatLng(40, -4), // SW bounds
                        new LatLng(40, 2)  // NE bounds
                );
                //Se muestra el mapa del mundo centrado en las coordenadas que comprenden España
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spainbounds.getCenter(), 5));
                //Se muestran los marcadores de los restaurantes que se encuentran cercanos a nuestra posición
                mostrarmarkers(googleMap);

                searchButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        busqueda = bus.getText().toString();
                        homeViewModel.setbusqueda(busqueda);
                        homeViewModel.onRefresh();
                        mostrarmarkers(googleMap);
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


        public void mostrarmarkers( GoogleMap mMap){
            mMap.clear();
            homeViewModel.getlocalizaciones().observe(getActivity(), localizaciones -> {
                    for (int j=0; j<localizaciones.size();j++){
                           LatLng posicion = new LatLng(localizaciones.get(j).getLat(), localizaciones.get(j).getLng());
                            mMap.addMarker(new MarkerOptions().position(posicion).title(localizaciones.get(j).getAdress_rest()));
                        }

            });
        }
/*
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

 */
    }




