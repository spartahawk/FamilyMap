package hawkes.fmc.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeSet;

import hawkes.fmc.R;
import hawkes.fmc.model.FamilyMember;
import hawkes.fmc.model.Model;
import hawkes.model.Event;
import hawkes.model.Person;

public class PersonActivity extends AppCompatActivity {

    TextView mFirstName;
    TextView mLastName;
    TextView mGender;

    RecyclerView mEventsRecyclerView;
    RecyclerView mFamilyRecyclerView;

    LinearLayoutManager mEventsLinearLayoutManager;
    LinearLayoutManager mFamilyLinearLayoutManager;

    ItemAdapter mEventsTestAdapter;
    FamilyItemAdapter mFamilyTestAdapter;

    private Person personOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personOfInterest = (Person) getIntent().getSerializableExtra("personOfInterest");

        setContentView(R.layout.activity_person);

        mFirstName = (TextView) findViewById(R.id.firstNameText);
        mLastName = (TextView) findViewById(R.id.lastNameText);
        mGender = (TextView) findViewById(R.id.genderText);

        mFirstName.setText(personOfInterest.getFirstName());
        mLastName.setText(personOfInterest.getLastName());
        if (personOfInterest.getGender() == 'm') {
            mGender.setText("Male");
        }
        else {
            mGender.setText("Female");
        }

        // Get Recycler View by id from layout file
        mEventsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_events);
        mFamilyRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_family);

        // Create Linear Layout Manager which defines how it will be shown on the screen
        mEventsLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mEventsLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mFamilyLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mFamilyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Set Layout Manager in the RecyclerView
        mEventsRecyclerView.setLayoutManager(mEventsLinearLayoutManager);
        mFamilyRecyclerView.setLayoutManager(mFamilyLinearLayoutManager);

        // Create Adapter object from the data by calling default Constructor
        mEventsTestAdapter = new ItemAdapter(getPersonLifeEvents());
        mFamilyTestAdapter = new FamilyItemAdapter(getImmediateFamily());

        // Set RecyclerView Adapter
        mEventsRecyclerView.setAdapter(mEventsTestAdapter);
        mFamilyRecyclerView.setAdapter(mFamilyTestAdapter);
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ListItemViewHolder> {

        private ArrayList<Event> lifeEventsList;

        public ItemAdapter(ArrayList<Event> lifeEventsList) {
            this.lifeEventsList = lifeEventsList;

        }

        @Override
        public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.recyclerview_viewholder, parent, false);

            return new ListItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListItemViewHolder holder, int position) {
            Event event = lifeEventsList.get(position);

            holder.icon.setImageResource(R.drawable.locationpin);

            String topText = event.getEventType()
                    + ": " + event.getCity()
                    + ", " + event.getCountry()
                    + " (" + event.getYear() + ")";
            holder.topText.setText(topText);

            holder.bottomText.setText(personOfInterest.getFirstName() + " "
                                    + personOfInterest.getLastName());
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return lifeEventsList.size();
        }

        public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView icon;
            public TextView topText;
            public TextView bottomText;

            public ListItemViewHolder(View itemView) {
                super(itemView);

                icon = (ImageView) itemView.findViewById(R.id.child_list_item_icon);
                topText = (TextView) itemView.findViewById(R.id.child_list_item_top_textview);
                bottomText = (TextView) itemView.findViewById(R.id.child_list_item_bottom_textview);

                itemView.setOnClickListener(this);
            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Event clickedEvent = lifeEventsList.get(getAdapterPosition());
                Model model = Model.getModel();
                model.setSelectedEvent(clickedEvent); // is this event sufficient or is it out of context?

                Intent intent = new Intent(PersonActivity.this, MapActivity.class);
                //intent.putExtra("personOfInterest", model.getPersons().get(selectedEvent.getPersonID()));
                startActivity(intent);
            }
        }
    }

    private ArrayList<Event> getPersonLifeEvents() {

        Model model = Model.getModel();
        TreeSet<Event> orderedEvents = new TreeSet<>();

        for (Event e : model.getFilteredEvents()) {
            if (e.getPersonID().equals(personOfInterest.getPersonID())) {
                orderedEvents.add(e);
            }
        }

        ArrayList<Event> eventsInOrder = new ArrayList<>();
        for (Event e : orderedEvents) {
            eventsInOrder.add(e);
        }
        return eventsInOrder;
    }

    private class FamilyItemAdapter extends RecyclerView.Adapter<FamilyItemAdapter.FamilyListItemViewHolder> {

        private ArrayList<FamilyMember> FamilyMembersList;

        public FamilyItemAdapter(ArrayList<FamilyMember> FamilyMembersList) {
            this.FamilyMembersList = FamilyMembersList;
        }

        @Override
        public FamilyListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.recyclerview_viewholder, parent, false);

            return new FamilyListItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FamilyListItemViewHolder holder, int position) {
            FamilyMember familyMember = FamilyMembersList.get(position);
            String topText = familyMember.getFirstName()
                    + " " + familyMember.getLastName();
            holder.topText.setText(topText);
            if (familyMember.getGender() == 'm') {
                holder.icon.setImageResource(R.drawable.maleicon);
            }
            else {
                holder.icon.setImageResource(R.drawable.femaleicon);
            }

            holder.bottomText.setText(familyMember.getRelationship());
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return FamilyMembersList.size();
        }

        public class FamilyListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView icon;
            public TextView topText;
            public TextView bottomText;

            public FamilyListItemViewHolder(View itemView) {
                super(itemView);

                icon = (ImageView) itemView.findViewById(R.id.child_list_item_icon);
                topText = (TextView) itemView.findViewById(R.id.child_list_item_top_textview);
                bottomText = (TextView) itemView.findViewById(R.id.child_list_item_bottom_textview);

                itemView.setOnClickListener(this);
            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Person clickedPerson = FamilyMembersList.get(getAdapterPosition());
                //todo: could be a problem if being a FamilyMember instead of the person reference breaks things
                Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                intent.putExtra("personOfInterest", clickedPerson);
                startActivity(intent);
            }
        }
    }

    private ArrayList<FamilyMember> getImmediateFamily() {
        ArrayList<FamilyMember> familyMembers = new ArrayList<>();
        Model model = Model.getModel();

        // Parents and Spouse
        try {
            if (model.getPersons().get(personOfInterest.getFather()) != null) {
                FamilyMember father = new FamilyMember(model.getPersons().get(personOfInterest.getFather()));
                father.setRelationship("Father");
                familyMembers.add(father);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (model.getPersons().get(personOfInterest.getMother()) != null) {
                FamilyMember mother = new FamilyMember(model.getPersons().get(personOfInterest.getMother()));
                mother.setRelationship("Mother");
                familyMembers.add(mother);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (model.getPersons().get(personOfInterest.getSpouse()) != null) {
                FamilyMember spouse = new FamilyMember(model.getPersons().get(personOfInterest.getSpouse()));
                spouse.setRelationship("Spouse");
                familyMembers.add(spouse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Child
        try {
            for (Person p : model.getPersons().values()) {
                if (p.getFather().equals(personOfInterest.getPersonID())) {
                    FamilyMember child = new FamilyMember(p);
                    child.setRelationship("Child");
                    familyMembers.add(child);
                }
                if (p.getMother().equals(personOfInterest.getPersonID())) {
                    FamilyMember child = new FamilyMember(p);
                    child.setRelationship("Child");
                    familyMembers.add(child);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return familyMembers;
    }
}
