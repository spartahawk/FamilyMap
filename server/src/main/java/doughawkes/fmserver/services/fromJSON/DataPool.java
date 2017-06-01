package doughawkes.fmserver.services.fromJSON;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yo on 5/31/17.
 */

public class DataPool {
    private Random random;
    private LocationData locationData;
    private FirstNameData firstNameData;
    private MiddleNameData middleNameData;
    private SurnameData surnameData;

    public DataPool() {
        loadAll();
        random = new Random();
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

    /** generates random integer from 0 (inclusive) to firstNameData array length (exclusive)
    * effectively selecting one firstName index for use.
     * @return the first name String
     */
    public String getRandomFirstName() {
        int index = random.nextInt(firstNameData.data.length);
        return firstNameData.data[index];
    }

    /** generates random integer from 0 (inclusive) to middleNameData array length (exclusive)
     * effectively selecting one middleName index for use.
     * @return the first name String
     */
    public String getRandomMiddleName() {
        int index = random.nextInt(middleNameData.data.length);
        return middleNameData.data[index];
    }

    /** generates random integer from 0 (inclusive) to surnameData array length (exclusive)
     * effectively selecting one surname index for use.
     * @return the first name String
     */
    public String getRandomSurname() {
        int index = random.nextInt(surnameData.data.length);
        return surnameData.data[index];
    }

    /** generates random integer from 0 (inclusive) to locationData array length (exclusive)
     * effectively selecting one location index for use.
     * @return the first name String
     */
    public Location getRandomLocation() {
        int index = random.nextInt(locationData.data.length);
        return locationData.data[index];
    }

}
