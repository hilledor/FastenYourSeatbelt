/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.FouteStatic;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.User.Rol.values;
import utils.DateUtils;
import utils.Utils;

/**
 *
 * @author jandorresteijn
 */
public class Log extends DataEntity implements Tabel {

    

    public Log() {
        getNew();
    }

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

        public static String getDescription(int id) {
            Log.UpdateType[] types = values();
            for (Log.UpdateType type : types) {
                if (type.id == id) {
                    return type.description;
                }
            }
            return "Unknown";
        }
    }

    @Column(dataType = DataType.ID)
    public int id;
    @Column(dataType = DataType.INT)
    public int user_id;
    @Column(dataType = DataType.INT)
    public int updatetype;
    @Column(dataType = DataType.DATETIME)
    public Calendar logdate;
    @Column(dataType = DataType.STRING)
    public String tablename;
    @Column(dataType = DataType.INT)
    public int tableid;
    @Column(dataType = DataType.STRING)
    public String memo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserDisplay(){
        try {
            User user = new User();
            user.load(getUser_id());
            String userDisplay = Utils.glue(user.getFirstname(), user.getMiddlename(), " ");
            userDisplay = Utils.glue(userDisplay, user.getLastname(), " ");
            return userDisplay;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Unknown ";
    }
    
    public int getUpdatetype() {
        return updatetype;
    }
    
    public String getUpdatetypeDisplay(){
        return UpdateType.getDescription(getUpdatetype());
    }

    public void setUpdatetype(int updatetype) {
        this.updatetype = updatetype;
    }

    public Calendar getLogdate() {
        return logdate;
    }

    public void setLogdate(Calendar logdate) {
        this.logdate = logdate;
    }

    public String getLogdateDisplay(){
        return DateUtils.toString(getLogdate(), DateUtils.DMYHMS);
    }
    
    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public int getTableid() {
        return tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Log(int id, int user_id, Calendar logdate, String tablename, int tableid, int updatetype, String memo) {
        this.id = id;
        this.user_id = user_id;
        this.updatetype = updatetype;
        Calendar c = Calendar.getInstance();
        this.logdate = logdate;
        this.tablename = tablename;
        this.tableid = tableid;
        this.memo = memo;
    }

    @Override
    public String getTable() {
        return "log";
    }

    
    
    public static <T extends DataEntity> void logData(T oldEntity, T newEntity) {
        try {
            Log log = new Log();
            // Set tablename
            log.setTablename( newEntity.getTable());
            // Set id
            Field newIdField = oldEntity.getIdField();
            int idNew = newIdField.getInt(newEntity);
            log.setTableid(idNew);
            
            // set updatetype
            Field oldIdField = oldEntity.getIdField();
            int idOld = oldIdField.getInt(oldEntity);  
            // Check if it is a new record
            if (idOld == -1){
                // Zet empty values
                log.setUpdatetype(1);  // Insert
            } else {
                log.setUpdatetype(2); // Update
            }    
            
            // Set user
            log.setUser_id(FouteStatic.theUser.getId());
            // Set date
            log.setLogdate(Calendar.getInstance());
            
            // Set memo
            String memo = "";

            Class cls = newEntity.getClass();       
            for (Field field : cls.getDeclaredFields()) {
                for (Annotation anno : field.getAnnotations()) {
                    if (anno instanceof Column) {
                         Column col = (Column) anno;
                        DataType dt = col.dataType();
                        switch (dt) {
                            case ID:
                                break;
                            case STRING:
                                String sOld =  field.get(oldEntity).toString();
                                String sNew =  field.get(newEntity).toString();
                                if ( !sOld.equals(sNew) ){
                                    memo = Utils.glue(memo , field.getName()+" "+ sOld +" >> "+sNew, "\n");
                                }
                                break;
                            case INT:
                                int iOld =  field.getInt(oldEntity);
                                int iNew =  field.getInt(newEntity);
                                if ( iOld != iNew ){
                                    memo = Utils.glue(memo , field.getName()+" "+ iOld +" >> "+iNew, "\n");
                                }
                                 break;
                            case DATETIME:
                                Calendar cOld = (Calendar) field.get(oldEntity);
                                Calendar cNew = (Calendar) field.get(newEntity);
                                if ( cOld.equals(cNew) ){
                                    memo = Utils.glue(memo , field.getName()+" "+ cOld +" >> "+cNew, "\n");
                                }
                                break;
                        }
                    }
                }
            }
           
            log.setMemo(memo);
            
            log.save();
            
 
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
}
