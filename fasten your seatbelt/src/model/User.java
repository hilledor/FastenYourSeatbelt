package model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import utils.Utils;

public class User extends DataEntity implements Tabel {

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
    @Column(dataType = DataType.INT)
    public int rol;
    @Column(dataType = DataType.STRING)
    public String password;
    @Column(dataType = DataType.INT)
    public int inactive;

    public User(ResultSet rs) {
        try {
            getNew();
            if (rs.next()) {
                rsToEntity(rs);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User() {

    }

    public User(int id, String firstname, String middlename, String lastname, String email, int rol, String password) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.email = email;
        this.rol = rol;
        this.password = password;
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

    public int getRol() {
        return rol;
    }

    public String getRolDisplay() {
        return Rol.getRol(getRol()).getDescription();
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getInactive() {
        return inactive;
    }

    public void setInactive(int inactive) {
        this.inactive = inactive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        User user = new User();
        user.setFirstname("jan");
        user.setLastname("Dorr");
        Class cls = user.getClass();
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
                            if (field.get(user) != null) {
                                String val = field.get(user).toString();

                                System.out.println("value " + val);
                            }
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
        }
    }

    public void getNew() {
        super.getNew();
        setRol(3);
    }

    public String getTable() {
        return "user";
    }
    
    public String toString(){
        String name = getFirstname();
        name = Utils.glue(name, getMiddlename(), " ");
        name = Utils.glue(name, getLastname(), " ");
        return name;
    }
}
