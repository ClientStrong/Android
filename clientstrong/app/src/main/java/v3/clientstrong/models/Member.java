package v3.clientstrong.models;

/**
 * Created by runquest.
 * Date: 2016-10-18
 */
public class Member {

    public String id;
    public String email;
    public String password;
    public String first_name;
    public String last_name;
    public String address;
    public String mobile;
    public String birthday;
    public boolean favorite;

    public String getFullName() {
        return first_name + " " + last_name;
    }

    public String getFirstNameLetter() {
        return String.valueOf(first_name.charAt(0)).toUpperCase();
    }

    public boolean isFavorite() {
        return getFirstNameLetter().equals("S");
    }
}
