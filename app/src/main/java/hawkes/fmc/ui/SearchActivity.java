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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import hawkes.fmc.R;
import hawkes.fmc.model.FamilyMember;
import hawkes.fmc.model.Model;
import hawkes.fmc.model.SearchResult;
import hawkes.model.Event;
import hawkes.model.Person;

public class SearchActivity extends AppCompatActivity {

    SearchView mSearchView;

    RecyclerView mFamilyRecyclerView;
    RecyclerView mEventsRecyclerView;

    LinearLayoutManager mFamilyLinearLayoutManager;
    LinearLayoutManager mEventsLinearLayoutManager;

    FamilyItemAdapter mFamilyTestAdapter;
    ItemAdapter mEventsTestAdapter;

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchView = (SearchView) findViewById(R.id.search_query_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchResult searchResult = new SearchResult(query);
                refreshAdapters(searchResult);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Get Recycler View by id from layout file
        mFamilyRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view_people);
        mEventsRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view_events);

        // Create Linear Layout Manager which defines how it will be shown on the screen
        mFamilyLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mFamilyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mEventsLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mEventsLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Set Layout Manager in the RecyclerView
        mFamilyRecyclerView.setLayoutManager(mFamilyLinearLayoutManager);
        mEventsRecyclerView.setLayoutManager(mEventsLinearLayoutManager);


//        // Create Adapter object from the data by calling default Constructor
//        mFamilyTestAdapter = new FamilyItemAdapter(getImmediateFamily());
//        mEventsTestAdapter = new ItemAdapter(getPersonLifeEvents());
//
//        // Set RecyclerView Adapter
//        mFamilyRecyclerView.setAdapter(mFamilyTestAdapter);
//        mEventsRecyclerView.setAdapter(mEventsTestAdapter);

        // Done! Hooray !!
    }

    private void refreshAdapters(SearchResult searchResult) {
        // Create Adapter object from the data by calling default Constructor
        mFamilyTestAdapter = new FamilyItemAdapter(searchResult.getMatchingPeople());
        mEventsTestAdapter = new ItemAdapter(searchResult.getMatchingEvents());

        // Set RecyclerView Adapter
        mFamilyRecyclerView.setAdapter(mFamilyTestAdapter);
        mEventsRecyclerView.setAdapter(mEventsTestAdapter);
    }



    private class FamilyItemAdapter extends RecyclerView.Adapter<FamilyItemAdapter.FamilyListItemViewHolder> {

        private ArrayList<Person> matchingPeople;

        public FamilyItemAdapter(ArrayList<Person> matchingPeople) {
            this.matchingPeople = matchingPeople;

        }

        @Override
        public FamilyListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.search_person_recyclerview_viewholder, parent, false);

            return new FamilyListItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FamilyListItemViewHolder holder, int position) {
            Person person = matchingPeople.get(position);
            String fullName = person.getFirstName() + person.getLastName();

            holder.nameText.setText(fullName);

            if (person.getGender() == 'm') {
                holder.icon.setImageResource(R.drawable.maleicon);
            }
            else {
                holder.icon.setImageResource(R.drawable.femaleicon);
            }
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return matchingPeople.size();
        }

        public class FamilyListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView icon;
            public TextView nameText;

            public FamilyListItemViewHolder(View itemView) {
                super(itemView);

                icon = (ImageView) itemView.findViewById(R.id.search_person_icon);
                nameText = (TextView) itemView.findViewById(R.id.search_person_name);

                itemView.setOnClickListener(this);
            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Person clickedPerson = matchingPeople.get(getAdapterPosition());
                //todo: could be a problem if being a FamilyMember instead of the person reference breaks things
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                intent.putExtra("personOfInterest", clickedPerson);
                startActivity(intent);
            }

//            public void bind(ListChild listChild) {
//                mTopText.setText(listChild.getTopText());
//                mBottomText.setText(listChild.getBottomText());
//            }
        }
    }








    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ListItemViewHolder> {

        private ArrayList<Event> matchingEvents;

        public ItemAdapter(ArrayList<Event> matchingEvents) {
            this.matchingEvents = matchingEvents;

        }

        @Override
        public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.search_event_recyclerview_viewholder, parent, false);

            return new ListItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListItemViewHolder holder, int position) {
            Event event = matchingEvents.get(position);

            holder.icon.setImageResource(R.drawable.locationpin);

            String topText = event.getEventType()
                    + ": " + event.getCity()
                    + ", " + event.getCountry()
                    + " (" + event.getYear() + ")";
            holder.topText.setText(topText);

            Person p = Model.getModel().getPersons().get(event.getPersonID());

            holder.bottomText.setText(p.getFirstName() + " "
                    + p.getLastName());

        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return matchingEvents.size();
        }

        public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView icon;
            public TextView topText;
            public TextView bottomText;

            public ListItemViewHolder(View itemView) {
                super(itemView);

                icon = (ImageView) itemView.findViewById(R.id.search_event_icon);
                topText = (TextView) itemView.findViewById(R.id.search_event_info);
                bottomText = (TextView) itemView.findViewById(R.id.search_event_owner_name);

                itemView.setOnClickListener(this);
            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Event clickedEvent = matchingEvents.get(getAdapterPosition());
                Model model = Model.getModel();
                model.setSelectedEvent(clickedEvent); // is this event sufficient or is it out of context?

                Intent intent = new Intent(SearchActivity.this, MapActivity.class);
                //intent.putExtra("personOfInterest", model.getPersons().get(selectedEvent.getPersonID()));
                startActivity(intent);
            }

//            public void bind(ListChild listChild) {
//                mTopText.setText(listChild.getTopText());
//                mBottomText.setText(listChild.getBottomText());
//            }
        }
    }



}
