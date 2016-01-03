package model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Client extends DataEntity implements Tabel {

    /*
    public enum Rol {

        ADMIN(1, "Admin"),
        MANAGER(2, "Manager"),
        EMPLOYEE(3, "Employee");

        private final int id;

        private final String description;

        Rol(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public static Rol getRol(String description) {
            System.out.println("description " + description);
            Rol[] rols = values();
            for (Rol rol : rols) {
                if (rol.description.equals(description)) {
                    return rol;
                }
            }
            return null;
        }

        public static Rol getRol(int id) {
            Rol[] rols = values();
            for (Rol rol : rols) {
                if (rol.id == id) {
                    return rol;
                }
            }
            return getRol(1);
        }
    }
    */
    
    @Column(dataType = DataType.ID)
    public int id;
    @Column(dataType = DataType.STRING)
    public String firstname;
    @Column(dataType = DataType.STRING)
    public String middlename;
    @Column(dataType = DataType.STRING)
    public String lastname;
    @Column(dataType = DataType.STRING)
    public String email;
    @Column(dataType = DataType.STRING)
    public String phonenumber;
    @Column(dataType = DataType.STRING)
    public String street;
    @Column(dataType = DataType.STRING)
    public String streetnumber;
    @Column(dataType = DataType.STRING)
    public String zipcode;
    @Column(dataType = DataType.STRING)
    public String city;
    @Column(dataType = DataType.STRING)
    public String country;

    public Client(ResultSet rs) {
        try {
            getNew();
            if (rs.next()) {
                rsToEntity(rs);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Client() {

    }

    public Client(int id, String firstname, String middlename, String lastname, String email, String phonenumber, String street, String streetnumber, String zipcode, String city, String country) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.street = street;
        this.streetnumber = streetnumber;
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    /*
     private final SimpleIntegerProperty id;
     private final SimpleStringProperty firstname;
     private final SimpleStringProperty middlename;
     private final SimpleStringProperty lastname;
     private final SimpleStringProperty email;
     private final SimpleIntegerProperty role;
     private String gggg;
     public User (int id, String firstname, String middlename, String lastname, String email, int role){
     this.id = new SimpleIntegerProperty(id);
     this.firstname = new SimpleStringProperty(firstname);
     this.middlename = new SimpleStringProperty(middlename);
     this.lastname = new SimpleStringProperty(lastname);
     this.email = new SimpleStringProperty(email);
     this.role = new SimpleIntegerProperty(role);
     }
     */
    public static void main(String[] arg) throws IllegalArgumentException {
        Client client = new Client();
        client.setFirstname("jan");
        client.setLastname("Dorr");
        Class cls = client.getClass();
        System.out.println("Check Colums");
        for (Field field : cls.getDeclaredFields()) {
            Class type = field.getType();
            String name = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();
            System.out.println("Naam " + name + " type =" + type + "  ");
            for (Annotation anno : field.getAnnotations()) {
                if (anno instanceof Column) {
                    Column col = (Column) anno;
                    System.out.println("col " + col.dataType());
                    if (col.dataType().equals(DataType.STRING)) {
                        try {
                            if (field.get(client) != null) {
                                String val = field.get(client).toString();

                                System.out.println("value " + val);
                            }
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
        }
    }

    public void getNew() {
        super.getNew();

    }

    @Override
    public String getTable() {
        return "client";
    }
}
