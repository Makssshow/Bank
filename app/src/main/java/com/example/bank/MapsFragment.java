package com.example.bank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    GoogleMap map;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
//            for (String item : coordinatesArray) {
//                String[] numbers = item.split(", ");
//                LatLng place = new LatLng(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
//                googleMap.addMarker(new MarkerOptions()
//                        .position(place)
//                        .title(item));
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));
//
//            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(callback);
//        }
    }

    public void addMarkers(ArrayList<String> array) {
        for (String item: array) {
            String[] numbers =  item.split(",");
            LatLng place = new LatLng(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
            map.addMarker(new MarkerOptions()
                    .position(place));
//                .title("Marker in Sydney"));
            map.moveCamera(CameraUpdateFactory.newLatLng(place));

        }
    }
}
