package hawkes.fmc.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

import hawkes.fmc.R;
import hawkes.fmc.model.Model;
import hawkes.model.Event;
import hawkes.model.Person;

import static java.security.AccessController.getContext;

public class PersonActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    LinearLayoutManager mLinearLayoutManager;

    ItemAdapter mTestAdapter;

    private Person personOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personOfInterest = (Person) getIntent().getSerializableExtra("personOfInterest");

        setContentView(R.layout.activity_person);

//        String toastMessage = personOfInterest.getFirstName();
//        Toast.makeText(getBaseContext(), toastMessage, Toast.LENGTH_SHORT).show();

        // Get Recycler View by id from layout file
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_events);

        // Create Linear Layout Manager which defines how it will be shown on the screen
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Set Layout Manager in the RecyclerView
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Create Adapter object from the data by calling default Constructor
        mTestAdapter = new ItemAdapter(getPersonLifeEvents());

        // Set RecyclerView Adapter
        mRecyclerView.setAdapter(mTestAdapter);

        // Done! Hooray !!
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
            //public IconTextView mIcon;
            public TextView topText;
            public TextView bottomText;

            public ListItemViewHolder(View itemView) {
                super(itemView);

                //mIcon = (ImageView) itemView.findViewById(R.id.child_list_item_icon);
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

//            public void bind(ListChild listChild) {
//                mTopText.setText(listChild.getTopText());
//                mBottomText.setText(listChild.getBottomText());
//            }
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

}
