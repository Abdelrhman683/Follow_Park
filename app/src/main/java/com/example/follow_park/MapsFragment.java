package com.example.follow_park;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.follow_park.databinding.ActivityHomeBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

public class MapsFragment extends Fragment {
    Button btn;
    double latitude;
    double longitude;
    TextView titles;
    GoogleMap mMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        private ActivityHomeBinding binding;



        @Override
        public void onMapReady(GoogleMap googleMap) {
            GPSTracker gg = new GPSTracker(getActivity());
            //      gpsTracker = new GPSTracker(MainActivity.this);
            Location location = gg.getLocation();

            // Eger konum bilgisi alinabiliyorsa ekranda goruntulenir
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            } else {
                // Konum bilgisi alinamiyorsa mesaj kutusunu goster
                gg.showSettingsAlert();
            }
            mMap = googleMap;

            // Add a marker in Sydney and move the camera
            LatLng markar = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(markar).title("Marker in your Loction"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markar, 14));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull @NotNull Marker marker) {
                    View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
                    BottomSheetDialog dialog = new BottomSheetDialog(getActivity());

                    titles  =dialogView.findViewById(R.id.button6);

                    titles.setText("Directins");

                    dialog.setContentView(dialogView);
                    dialog.show();



                    return false;
                }
            });

            int strokeColor = 0xffff0000;
            int shadeColor = 0x44ff0000;
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(latitude, longitude))
                    .radius(200).strokeColor(strokeColor).fillColor(shadeColor));

         /*   LatLng markarr = new LatLng(35, 153);
            mMap.addMarker(new MarkerOptions().position(markarr).title("Marker in your Loction"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markarr, 14));*/

        }


    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_maps, container, false);
        titles = view.findViewById(R.id.button6);
        btn=view.findViewById(R.id.buttona);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LatLng markar = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(markar).title("Marker in your Loction"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markar,14));





            }


        });
        return view;
    }








    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}