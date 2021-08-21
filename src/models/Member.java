package models;

import java.sql.PreparedStatement;
import java.util.Arrays;

/**
 * Member
 */
public class Member implements models.IModel {

    /**
     *
     */
    private static final String INSERT_INTO_MEMBERS_VALUES = "INSERT INTO Members VALUES(?, ?, ?, ?, ?)";
    private static final int NUMBER_OF_COLUMNS = 5;
    private char[] Member_ID = new char[10];
    private char[] First_name = new char[20];
    private char[] Last_name = new char[20];
    private char[] Card_number = new char[16];
    private char[] exp_dat = new char[5];

    private static final String INSERT_FORMAT = INSERT_INTO_MEMBERS_VALUES;

    @Override
    public String insertFormat() {
        return INSERT_FORMAT;
    }

    @Override
    public void insertRecord(PreparedStatement pstmt, String[] values) {
        // TODO Auto-generated method stub

        if (values.length != 5) {
            System.err.println("Member.insertRecord: not enough values");
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
    public void deleteRecord(PreparedStatement pstmt, String primaryKey) {
        // TODO Auto-generated method stub



    }

    @Override
    public String tableName() {
        // TODO Auto-generated method stub
        return "members";
    }

    @Override
    public String primaryKey() {
        // TODO Auto-generated method stub
        return "member_id";
    }

}