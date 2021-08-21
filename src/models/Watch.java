package models;

import java.sql.PreparedStatement;

public class Watch implements models.IModel {
    private static final String INSERT_FORMAT = "INSERT INTO Watch VALUES(?, ?, ?, ?)";
    private static final int NUMBER_OF_COLUMNS = 4;
    private char[] Member_ID = new char[10];
    private char[] Profile_name = new char[15];
    private char[] Movie_ID = new char[10];
    private float rating;

    // CHECK(rating >= 1 AND rating <= 5),
    @Override
    public String insertFormat() {
        // TODO Auto-generated method stub
        return INSERT_FORMAT;
    }

    @Override
    public void insertRecord(PreparedStatement pstmt, String[] values) {
        // TODO Auto-generated method stub
        if (values.length != NUMBER_OF_COLUMNS) {
            System.err.println("Watch.insertRecord: only " + NUMBER_OF_COLUMNS + " values needed");
            return;
        }

        try {
            for (int i = 0; i < values.length; i++) {
                pstmt.setString(i + 1, values[i]);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
    }

    @Override
    public String tableName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String primaryKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteRecord(PreparedStatement pstmt, String primaryKey) {
        // TODO Auto-generated method stub

    }
}
