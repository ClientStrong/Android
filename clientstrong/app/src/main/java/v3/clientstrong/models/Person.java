package v3.clientstrong.models;

/**
 * Created by runquest.
 * Date: 2016-10-18
 */
public class Person {

    public String id;
    public String email;
    public String password;
    public String first_name;
    public String last_name;
    public String address;
    public String mobile;
    public String birthday;

    public Person(String id, String email, String password, String first_name, String last_name, String address, String mobile, String birthday) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.mobile = mobile;
        this.birthday = birthday;
    }
}
