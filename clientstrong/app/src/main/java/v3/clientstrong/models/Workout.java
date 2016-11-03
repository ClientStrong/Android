package v3.clientstrong.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by runquest.
 * Date: 2016-10-26
 */
public class Workout implements Serializable {

    public String id;
    public String name;
    public String scheduled_date;
    public String level;
    public String description;
    public ArrayList<Element> elements;

    public class Element {
        public String type;
        public String description;
        public int[] exercise_ids;
    }
}
