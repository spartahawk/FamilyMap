package hawkes.fmc.ui;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.TreeMap;

import hawkes.fmc.R;
import hawkes.fmc.model.Model;
import hawkes.model.Event;

public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_maps);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapIDunique);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                ArrayList<Event> events = Model.getModel().getEvents();
                TreeMap<Marker, Event> markerToEventMap = Model.getModel().getMarkerToEventMap();

                for (Event event : events) {

                    double lat = Double.parseDouble(event.getLatitude());
                    double lng = Double.parseDouble(event.getLongitude());

                    LatLng latLng = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(latLng));
                }
                
            }
        });

        return view;

    }

    private void createMarker(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnMarkerClickListener(this);

        ArrayList<Event> events = Model.getModel().getEvents();
        TreeMap<Marker, Event> markerToEventMap = Model.getModel().getMarkerToEventMap();

        for (Event event : events) {

            double lat = Double.parseDouble(event.getLatitude());
            double lng = Double.parseDouble(event.getLongitude());
            LatLng latLng = new LatLng(lat, lng);

            // there is a .tag or something I could add for the IDs if that's easier than the map
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
            markerToEventMap.put(marker, event);

        }

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public boolean onMarkerClick(Marker marker) { //todo: consider making it final Marker marker
        // bring up the marker's event info in the linear/relative layout
        return false;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
}
