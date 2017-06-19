package hawkes.fmc.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import hawkes.fmc.R;
import hawkes.fmc.model.Model;
import hawkes.fmc.model.RelationshipLines;
import hawkes.model.Event;
import hawkes.model.Person;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    private boolean mIsMainActivity;

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

    private Event selectedEvent;
    private int familyTreeLinesColorValue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.fragment_maps);

        //THIS IS NO LONGER NEEDED. THE ACTIVITY THAT OPENS THIS FRAGMENT SETS THE mIsMainActivity boolean
        //get the parent activity from the bundle
//        if (isAdded() && getActivity() instanceof MainActivity){
//            mIsMainActivity = true;
//        }
//        // otherwise it's from the map activity
//        else {
//            mIsMainActivity = false;
//        }
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
                createMarkers(googleMap);

                if (!mIsMainActivity) {
                    createPolylines();
                    centerOverSelectedEvent();
                    updateEventInfoWindow();
                }

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
        Model model = Model.getModel();

        Intent intent = new Intent(getActivity(), PersonActivity.class);
//        intent.putExtra("personOfInterest", model.getPersons().get(selectedEvent.getPersonID()));
        intent.putExtra("personOfInterest", model.getPersons().get(selectedEvent.getPersonID()));
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //maybe make a different menu xml depending on if it's in the map activity,
        //or just don't add the action bar items if not in MainActivity
        if (mIsMainActivity) {
            inflater.inflate(R.menu.fragment_maps, menu);

            //ActionBar icon(s)
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
        }
        else {
            // Todo: instead of this, make a menu with just the up button
            inflater.inflate(R.menu.fragment_maps_map_activity, menu);

//            //ActionBar icon(s)
//            menu.findItem(R.id.searchMenuItem).setIcon(
//                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
//                            .colorRes(R.color.maleColor)
//                            .actionBarSize());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.searchMenuItem:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;

            case R.id.filterMenuItem:
                intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
                return true;

            case R.id.settingsMenuItem:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void createMarkers(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

//        ArrayList<Event> events = Model.getModel().getEvents();
//        markerToEventMap = Model.getModel().getMarkerToEventMap();
//
//        for (Event event : events) {
//
//            double lat = Double.parseDouble(event.getLatitude());
//            double lng = Double.parseDouble(event.getLongitude());
//            LatLng latLng = new LatLng(lat, lng);
//
//            float color = determineColor(event.getEventType());
//
//            // there is a .tag or something I could add for the IDs if that's easier than the map
//            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
//                    .icon(BitmapDescriptorFactory
//                            .defaultMarker(color)));
//            markerToEventMap.put(marker, event);
//
//        }

        // Using filteredEvents instead of just events
        HashSet<Event> filteredEvents = Model.getModel().getFilteredEvents();
        markerToEventMap = Model.getModel().getMarkerToEventMap();

        for (Event event : filteredEvents) {

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

        selectedEvent = event;
        Model.getModel().setSelectedEvent(event);

        updateEventInfoWindow();

        // this seems to control whether the map centers over the marker. return false does.
        return false;
    }

    private void updateEventInfoWindow() {
        // get the associated person for the event

        mInfoWindowUpperText = (TextView) view.findViewById(R.id.infoWindowUpperText);
        mInfoWindowLowerText = (TextView) view.findViewById(R.id.infoWindowLowerText);

        // update event details
        String eventDetails = selectedEvent.getEventType()
                + ": " + selectedEvent.getCity()
                + ", " + selectedEvent.getCountry()
                + " (" + selectedEvent.getYear() + ")";
        mInfoWindowLowerText.setText(eventDetails);

        Person p = Model.getModel().getPersonByEvent(selectedEvent.getPersonID());

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

    }


    public void createPolylines() {
        Model model = Model.getModel();
        selectedEvent = model.getSelectedEvent();

        RelationshipLines lines = new RelationshipLines(selectedEvent);

        if (model.getSettings().isShowLifeStoryLines() == true) {
            ArrayList<LatLng> lifeStoryCoordList = new ArrayList<>();

            for (Event e : lines.getLifeStoryEvents()) {
                lifeStoryCoordList.add(new LatLng(Double.parseDouble(e.getLatitude()),
                        Double.parseDouble(e.getLongitude())));
            }

            String lifeStoryLinesColor = model.getSettings().getLifeStoryLinesColor();
            int lifeStoryLinesColorValue = Color.YELLOW;
            if (lifeStoryLinesColor.equals("Red")) lifeStoryLinesColorValue = Color.RED;
            if (lifeStoryLinesColor.equals("Green")) lifeStoryLinesColorValue = Color.GREEN;
            if (lifeStoryLinesColor.equals("Blue")) lifeStoryLinesColorValue = Color.BLUE;

            float lineWidth = 20;
            float percentage = .75f;

            for (int i = 1; i < lifeStoryCoordList.size(); i++) {

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(lifeStoryCoordList.get(i - 1),
                                    lifeStoryCoordList.get(i))
                               .clickable(false)
                               .color(lifeStoryLinesColorValue)
                               .width(lineWidth);

                mMap.addPolyline(polylineOptions);
                lineWidth = lineWidth * percentage;
            }

//            PolylineOptions polylineOptions = new PolylineOptions();
//            // Create polyline options with existing LatLng ArrayList
//            polylineOptions.addAll(lifeStoryCoordList);
//            polylineOptions
//                    .clickable(false);
//                    //.width(5);
//                    //.color(Color.RED);
//
//            mMap.addPolyline(polylineOptions);
        }

        if (model.getSettings().isShowFamilyTreeLines() == true) {

            String familyTreeLinesColor = model.getSettings().getFamilyTreeLinesColor();
            familyTreeLinesColorValue = Color.YELLOW;
            if (familyTreeLinesColor.equals("Red")) familyTreeLinesColorValue = Color.RED;
            if (familyTreeLinesColor.equals("Green")) familyTreeLinesColorValue = Color.GREEN;
            if (familyTreeLinesColor.equals("Blue")) familyTreeLinesColorValue = Color.BLUE;

            Person thisPerson = model.getPersons().get(selectedEvent.getPersonID());

            String fatherPersonID = thisPerson.getFather();
            String motherPersonID = thisPerson.getMother();

            float lineWidth = 30;

            drawLineToParent(selectedEvent, fatherPersonID, lineWidth);
            drawLineToParent(selectedEvent, motherPersonID, lineWidth);


        }

        if (model.getSettings().isShowSpouseLines() == true && lines.getSpouseEvents() != null) {

            try {
                Event firstSpouseEvent = null;
                firstSpouseEvent = lines.getSpouseEvents().first();

                String spouseLinesColor = model.getSettings().getSpouseLinesColor();
                int spouseLinesColorValue = Color.YELLOW;
                if (spouseLinesColor.equals("Red")) spouseLinesColorValue = Color.RED;
                if (spouseLinesColor.equals("Green")) spouseLinesColorValue = Color.GREEN;
                if (spouseLinesColor.equals("Blue")) spouseLinesColorValue = Color.BLUE;

                float lineWidth = 20;

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(new LatLng(Double.parseDouble(selectedEvent.getLatitude()),
                                               Double.parseDouble(selectedEvent.getLongitude())),
                                    new LatLng(Double.parseDouble(firstSpouseEvent.getLatitude()),
                                               Double.parseDouble(firstSpouseEvent.getLongitude())))
                                .clickable(false)
                                .color(spouseLinesColorValue)
                                .width(lineWidth);

                mMap.addPolyline(polylineOptions);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("No such event perhaps");
                // this is someone's parent, but they don't really exist in the sense
                // that they don't have events. I think.
            }
        }
    }

    public void drawLineToParent(Event event, String parentPersonID, float lineWidth) {
        Model model = Model.getModel();
        TreeSet<Event> parentEvents = new TreeSet<>();

        for (Event e : model.getFilteredEvents()) {
            if (e.getPersonID().equals(parentPersonID)) {
                parentEvents.add(e);
            }
        }

        if (parentEvents.size() == 0) return;
        Event firstParentEvent = parentEvents.first();

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(new LatLng(Double.parseDouble(event.getLatitude()),
                                       Double.parseDouble(event.getLongitude())),
                            new LatLng(Double.parseDouble(firstParentEvent.getLatitude()),
                                       Double.parseDouble(firstParentEvent.getLongitude())))
                        .clickable(false)
                        .color(familyTreeLinesColorValue)
                        .width(lineWidth);

        mMap.addPolyline(polylineOptions);

        Person thisPerson = model.getPersons().get(firstParentEvent.getPersonID());
        String fatherPersonID = thisPerson.getFather();
        String motherPersonID = thisPerson.getMother();

        float percentage = .60f;
        drawLineToParent(firstParentEvent, fatherPersonID, lineWidth * percentage);
        drawLineToParent(firstParentEvent, motherPersonID, lineWidth * percentage);

    }

    private void centerOverSelectedEvent() {
        LatLng eventLatLng = new LatLng(Double.parseDouble(selectedEvent.getLatitude()),
                                        Double.parseDouble(selectedEvent.getLongitude()));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLatLng, 5));
    }



    public boolean ismIsMainActivity() {
        return mIsMainActivity;
    }

    public void setmIsMainActivity(boolean mIsMainActivity) {
        this.mIsMainActivity = mIsMainActivity;
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

}
