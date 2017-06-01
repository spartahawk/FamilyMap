package doughawkes.fmserver.services.fromJSON;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by yo on 5/31/17.
 */

public class AllJsonIntoMemory {
    private LocationData locationData;
    private FirstNameData firstNameData;
    private MiddleNameData middleNameData;
    private SurnameData surnameData;

    public AllJsonIntoMemory() {
        loadAll();
    }

    private void loadAll() {
        Gson gson = new Gson();

        try {
            Reader reader = new FileReader("server/data/json/locations.json");
            LocationData locationData = gson.fromJson(reader, LocationData.class);

            reader = new FileReader("server/data/json/fnames.json");
            FirstNameData firstNameData = gson.fromJson(reader, FirstNameData.class);

            reader = new FileReader("server/data/json/mnames.json");
            MiddleNameData middleNameData = gson.fromJson(reader, MiddleNameData.class);

            reader = new FileReader("server/data/json/snames.json");
            SurnameData surnameData = gson.fromJson(reader, SurnameData.class);
        } catch (FileNotFoundException e) {
            System.out.println("A json file was not found at the specified path.");
            e.printStackTrace();
        }
    }

    public LocationData getLocationData() {
        return locationData;
    }

    public FirstNameData getFirstNameData() {
        return firstNameData;
    }

    public MiddleNameData getMiddleNameData() {
        return middleNameData;
    }

    public SurnameData getSurnameData() {
        return surnameData;
    }
}
