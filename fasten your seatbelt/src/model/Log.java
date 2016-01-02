/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Calendar;
import static model.User.Rol.values;

/**
 *
 * @author jandorresteijn
 */
public class Log extends DataEntity implements Tabel{

    
    
    public enum UpdateType {

        INSERT(1, "Insert"),
        UPDATE(2, "Update"),
        DELETE(3, "Delete");

        private final int id;

        private final String description;

        UpdateType(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public String getDescription(int id) {
            Log.UpdateType[] types = values();
            for (Log.UpdateType type : types) {
                if (type.id == id) {
                    return description;
                }
            }
            return "Unknown";
        }      
    }
    
    @Column(dataType = DataType.ID)
    public int id;
    @Column(dataType = DataType.INT)
    public int userid;
    @Column(dataType = DataType.INT)
    public int updatetype;
    @Column(dataType = DataType.DATETIME)
    public Calendar logdate;
    @Column(dataType = DataType.STRING)
    public String memo;
    
    @Override
    public String getTable() {
        return "log";
    }
    
    
    
    
}
