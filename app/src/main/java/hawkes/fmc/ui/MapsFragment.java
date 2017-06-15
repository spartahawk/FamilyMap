package hawkes.fmc.ui;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import hawkes.fmc.R;
import hawkes.fmc.model.Model;
import hawkes.model.Event;
import hawkes.model.Person;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private View view;
    private LinearLayout mInfoWindow;
    private TextView mInfoWindowUpperText;
    private TextView mInfoWindowLowerText;
    private ImageView mGenderImageView;  // removed "Final" so it's changeable.
    //private Drawable mAndroidGenderIcon;
    //private Drawable mMaleGenderIcon;
    //private Drawable mFemaleGenderIcon;

    private HashMap<Marker, Event> markerToEventMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_maps);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_maps, container, false);

        //android icon
        mGenderImageView = (ImageView) view.findViewById(R.id.mapGenderIcon);
        Drawable androidGenderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_android)
                .colorRes(R.color.androidColor).sizeDp(40);

        mGenderImageView.setImageDrawable(androidGenderIcon);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapIDunique);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                createMarker(googleMap);

            }
        });

        mInfoWindow = (LinearLayout) view.findViewById(R.id.infoWindow);

        mInfoWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoWindowClicked();
            }
        });

        return view;

    }

    private void infoWindowClicked() {
        //Toast.makeText(getContext(), "Info Window Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_maps, menu);

        //toolbar icon(s)
        // Set an icon in the ActionBar
        menu.findItem(R.id.searchMenuItem).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
                        .colorRes(R.color.toolbarIcon)
                        .actionBarSize());

        menu.findItem(R.id.filterMenuItem).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_filter)
                        .colorRes(R.color.toolbarIcon)
                        .actionBarSize());

        menu.findItem(R.id.settingsMenuItem).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear)
                        .colorRes(R.color.toolbarIcon)
                        .actionBarSize());

//        Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
//                colorRes(R.color.male_icon).sizeDp(40);
//        genderImageView.setImageDrawable(genderIcon);

//        menu.findItem(R.id.item_settings).setIcon(
//                new IconDrawable(this, FontAwesomeIcons.fa_cog)
//                        .colorRes(R.color.your_color)
//                        .actionBarSize());

    }

    private void createMarker(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        ArrayList<Event> events = Model.getModel().getEvents();
        markerToEventMap = Model.getModel().getMarkerToEventMap();

        for (Event event : events) {

            double lat = Double.parseDouble(event.getLatitude());
            double lng = Double.parseDouble(event.getLongitude());
            LatLng latLng = new LatLng(lat, lng);

            float color = determineColor(event.getEventType());

            // there is a .tag or something I could add for the IDs if that's easier than the map
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(color)));
            markerToEventMap.put(marker, event);

        }

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    private final float determineColor(String eventType) {
        switch (eventType) {
            case "Birth":  return HUE_BLUE;
            case "Baptism": return HUE_GREEN;
            case "Marriage": return HUE_ORANGE;
            case "Graduation": return HUE_YELLOW;
            case "Death": return HUE_RED;
            default:
                //should not occur
                System.out.println("Error in getting event type");
                return HUE_VIOLET;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) { //todo: consider making it final Marker marker
        //Toast.makeText(getContext(), "Marker Clicked", Toast.LENGTH_SHORT).show();
        Event event = markerToEventMap.get(marker);
        // get the associated person for the event



        mInfoWindowUpperText = (TextView) view.findViewById(R.id.infoWindowUpperText);
        mInfoWindowLowerText = (TextView) view.findViewById(R.id.infoWindowLowerText);

        // update event details
        String eventDetails = event.getEventType()
                                + ": " + event.getCity()
                                + ", " + event.getCountry()
                                + " (" + event.getYear() + ")";
        mInfoWindowLowerText.setText(eventDetails);

        Person p = Model.getModel().getPersonByEventID(event.getPersonID());

        //update name
        String fullName = p.getFirstName() + " " + p.getLastName();
        mInfoWindowUpperText.setText(fullName);



        Drawable maleGenderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male)
                .colorRes(R.color.maleColor).sizeDp(40);
        Drawable femaleGenderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female)
                .colorRes(R.color.femaleColor).sizeDp(40);







        // update gender icon
        if (p != null) {
            if (p.getGender() == 'm') mGenderImageView.setImageDrawable(maleGenderIcon);
            else mGenderImageView.setImageDrawable(femaleGenderIcon);
        }
        else {
            Toast.makeText(getContext(), "Error! Person not found by ID!", Toast.LENGTH_SHORT).show();
        }

        //eventview.settext

        // this seems to control whether the map centers over the marker. return false does.
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
