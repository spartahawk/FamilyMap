package doughawkes.fmserver.services.fromJSON;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by yo on 5/31/17.
 */

public class DataPool {
    private Random random;
    private LocationData locationData;
    private FemaleNameData femaleNameData;
    private MaleNameData maleNameData;
    private SurnameData surnameData;

    public DataPool() {
        loadAll();
        random = new Random();
    }

    private void loadAll() {
        Gson gson = new Gson();

        try {
//            File file = new File("server/data/json/fnames.json");
//            FileInputStream fis = new FileInputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            Scanner scanner = new Scanner(bis);
//            StringBuilder sb = new StringBuilder();
//            while (scanner.hasNext()) {
//                sb.append(scanner.next());
//            }
//            femaleNameData = gson.fromJson(sb.toString(), FemaleNameData.class);
//
//            file = new File("server/data/json/mnames.json");
//            fis = new FileInputStream(file);
//            bis = new BufferedInputStream(fis);
//            scanner = new Scanner(bis);
//            sb = new StringBuilder();
//            while (scanner.hasNext()) {
//                sb.append(scanner.next());
//            }
//            maleNameData = gson.fromJson(sb.toString(), MaleNameData.class);
//
//            file = new File("server/data/json/snames.json");
//            fis = new FileInputStream(file);
//            bis = new BufferedInputStream(fis);
//            scanner = new Scanner(bis);
//            sb = new StringBuilder();
//            while (scanner.hasNext()) {
//                sb.append(scanner.next());
//            }
//            surnameData = gson.fromJson(sb.toString(), SurnameData.class);
//
//            file = new File("server/data/json/locations.json");
//            fis = new FileInputStream(file);
//            bis = new BufferedInputStream(fis);
//            scanner = new Scanner(bis);
//            sb = new StringBuilder();
//            while (scanner.hasNext()) {
//                sb.append(scanner.next());
//            }
//            locationData = gson.fromJson(sb.toString(), LocationData.class);

            Reader reader = new FileReader("server/data/json/fnames.json");
            femaleNameData = gson.fromJson(reader, FemaleNameData.class);

            reader = new FileReader("server/data/json/mnames.json");
            maleNameData = gson.fromJson(reader, MaleNameData.class);

            reader = new FileReader("server/data/json/snames.json");
            surnameData = gson.fromJson(reader, SurnameData.class);

            reader = new FileReader("server/data/json/locations.json");
            locationData = gson.fromJson(reader, LocationData.class);

        } catch (FileNotFoundException e) {
            System.out.println("A json file was not found at the specified path.");
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** generates random integer from 0 (inclusive) to femaleNameData array length (exclusive)
    * effectively selecting one femaleName index for use.
     * @return the female name String
     */
    public String getRandomFemaleName() {
        int index = random.nextInt(femaleNameData.data.length);
        return femaleNameData.data[index];
    }

    /** generates random integer from 0 (inclusive) to maleNameData array length (exclusive)
     * effectively selecting one maleName index for use.
     * @return the male name String
     */
    public String getRandomMaleName() {
        int availableNamesLength = maleNameData.data.length;
        int index = random.nextInt(maleNameData.data.length);
        return maleNameData.data[index];
    }

    /** generates random integer from 0 (inclusive) to surnameData array length (exclusive)
     * effectively selecting one surname index for use.
     * @return the surnname String
     */
    public String getRandomSurname() {
        int index = random.nextInt(surnameData.data.length);
        return surnameData.data[index];
    }

    /** generates random integer from 0 (inclusive) to locationData array length (exclusive)
     * effectively selecting one location index for use.
     * @return the location object
     */
    public Location getRandomLocation() {
        int index = random.nextInt(locationData.data.length);
        return locationData.data[index];
    }

}
