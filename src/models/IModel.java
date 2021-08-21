package models;

import java.sql.PreparedStatement;

public interface IModel {

    public String tableName();
    public String primaryKey();
    
    public String insertFormat();

    public void insertRecord(PreparedStatement pstmt, String[] values);
    public void deleteRecord(PreparedStatement pstmt, String primaryKey);
    
    public static void deleteRecordStatic(PreparedStatement pstmt, String primaryKey) {
        
    }
}
