package hawkes.fmc.model;

/**
 * Created by yo on 6/16/17.
 */

public class Settings {

    private String lifeStoryLinesColor;
    private String familyTreeLinesColor;
    private String spouseLinesColor;
    private String mapType;


    public Settings() {
        lifeStoryLinesColor = "Red";
        familyTreeLinesColor = "Green";
        spouseLinesColor = "Blue";
        mapType = "Normal";
    }

    public String getLifeStoryLinesColor() {
        return lifeStoryLinesColor;
    }

    public void setLifeStoryLinesColor(String lifeStoryLinesColor) {
        this.lifeStoryLinesColor = lifeStoryLinesColor;
    }

    public String getFamilyTreeLinesColor() {
        return familyTreeLinesColor;
    }

    public void setFamilyTreeLinesColor(String familyTreeLinesColor) {
        this.familyTreeLinesColor = familyTreeLinesColor;
    }

    public String getSpouseLinesColor() {
        return spouseLinesColor;
    }

    public void setSpouseLinesColor(String spouseLinesColor) {
        this.spouseLinesColor = spouseLinesColor;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }
}
