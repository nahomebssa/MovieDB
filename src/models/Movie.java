package models;

import java.sql.PreparedStatement;

public class Movie implements models.IModel {
    private static final String INSERT_FORMAT = "INSERT INTO Movie VALUES(?, ?, ?, ?)";
    private static final int NUMBER_OF_COLUMNS = 4;
    private char[] Movie_ID = new char[10];
    private char[] Movie_name = new char[50];
    private int Movie_year;
    private char[] Producer = new char[30];
    private float Avg_rating; // CHECK(Avg_rating >= 1 AND Avg_rating <= 5),

    @Override
    public String insertFormat() {
        // TODO Auto-generated method stub
        return INSERT_FORMAT;
    }

    @Override
    public void insertRecord(PreparedStatement pstmt, String[] values) {
        // TODO Auto-generated method stub
        if (values.length != NUMBER_OF_COLUMNS) {
            System.err.println("Movie.insertRecord: only " + NUMBER_OF_COLUMNS + " values needed");
            return;
        }

        try {
            pstmt.setString(1, values[0]);
            pstmt.setString(2, values[1]);
            pstmt.setInt(3, Integer.parseInt(values[2]));
            pstmt.setString(4, values[3]);
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
