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
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN;
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

    ArrayList<Polyline> previousPolylines;

    private GoogleMap mMap;

    private View view;
    private LinearLayout mInfoWindow;
    private TextView mInfoWindowUpperText;
    private TextView mInfoWindowLowerText;
    private ImageView mGenderImageView;

    private HashMap<Marker, Event> markerToEventMap;

    private Event selectedEvent;
    private int familyTreeLinesColorValue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Model.getModel().applyUpdatedFilters();

        previousPolylines = new ArrayList<>();

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
                mMap = googleMap;

                switch (Model.getModel().getSettings().getMapType()) {
                    case "Normal" : mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case "Hybrid" : mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case "Satellite" : mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case "Terrain" : mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    default: mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }

                createMarkers();

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
        if (selectedEvent == null) return;
        //Toast.makeText(getContext(), "Info Window Clicked", Toast.LENGTH_SHORT).show();
        Model model = Model.getModel();

        Intent intent = new Intent(getActivity(), PersonActivity.class);
        //intent.putExtra("personOfInterest", model.getPersons().get(selectedEvent.getPersonID()));
        intent.putExtra("personOfInterest", model.getPersons().get(selectedEvent.getPersonID()));
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

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

    private void createMarkers() {
        mMap.setOnMarkerClickListener(this);

        // Using filteredEvents instead of just events
        HashSet<Event> filteredEvents = Model.getModel().getFilteredEvents();
        markerToEventMap = Model.getModel().getMarkerToEventMap();

        for (Event event : filteredEvents) {

            double lat = Double.parseDouble(event.getLatitude());
            double lng = Double.parseDouble(event.getLongitude());
            LatLng latLng = new LatLng(lat, lng);

            float color = determineColor(Model.getModel().getEventTypeColors().get(event.getEventType()));

            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(color)));
            markerToEventMap.put(marker, event);

        }
    }

    private final float determineColor(String color) {
        switch (color) {
            case "Blue":  return HUE_BLUE;
            case "Green": return HUE_GREEN;
            case "Orange": return HUE_ORANGE;
            case "Yellow": return HUE_YELLOW;
            case "Red": return HUE_RED;
            case "Azure": return HUE_AZURE;
            case "Violet": return HUE_VIOLET;
            default:
                //should not occur
                System.out.println("Error in getting event type");
                return HUE_CYAN;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) { //todo: consider making it final Marker marker
        Event event = markerToEventMap.get(marker);

        selectedEvent = event;
        Model.getModel().setSelectedEvent(event);

        createPolylines();
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

        for(Polyline line : previousPolylines) {
            line.remove();
        }
        previousPolylines.clear();

        Model model = Model.getModel();
        selectedEvent = model.getSelectedEvent();

        RelationshipLines lines = new RelationshipLines(selectedEvent);

        if (model.getSettings().isShowLifeStoryLines() == true) {
            ArrayList<LatLng> lifeStoryCoordList = new ArrayList<>();

            try {
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

                    previousPolylines.add(mMap.addPolyline(polylineOptions));

                    lineWidth = lineWidth * percentage;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (model.getSettings().isShowFamilyTreeLines() == true) {

            String familyTreeLinesColor = model.getSettings().getFamilyTreeLinesColor();
            familyTreeLinesColorValue = Color.YELLOW;
            if (familyTreeLinesColor.equals("Red")) familyTreeLinesColorValue = Color.RED;
            if (familyTreeLinesColor.equals("Green")) familyTreeLinesColorValue = Color.GREEN;
            if (familyTreeLinesColor.equals("Blue")) familyTreeLinesColorValue = Color.BLUE;

            try {
                Person thisPerson = model.getPersons().get(selectedEvent.getPersonID());

                String fatherPersonID = thisPerson.getFather();
                String motherPersonID = thisPerson.getMother();

                float lineWidth = 30;

                drawLineToParent(selectedEvent, fatherPersonID, lineWidth);
                drawLineToParent(selectedEvent, motherPersonID, lineWidth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (model.getSettings().isShowSpouseLines() == true && lines.getSpouseEvents().size() > 0) {

            try {
                Event firstSpouseEvent = lines.getSpouseEvents().first();

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

                previousPolylines.add(mMap.addPolyline(polylineOptions));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("No such event");
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

        previousPolylines.add(mMap.addPolyline(polylineOptions));

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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLatLng, 4));
    }



    public boolean ismIsMainActivity() {
        return mIsMainActivity;
    }

    public void setmIsMainActivity(boolean mIsMainActivity) {
        this.mIsMainActivity = mIsMainActivity;
    }

}
