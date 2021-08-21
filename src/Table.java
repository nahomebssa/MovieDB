import java.util.ArrayList;

public class Table {

    ArrayList<String> columnNames;
    ArrayList<String[]> rows;
    boolean updated;
    String stringCache;

    Table() {
        columnNames = new ArrayList<>();
        rows = new ArrayList<>();
        stringCache = null;
        updated = true;
    }

    void addColumn(String name) {
        columnNames.add(name);
        updated = true;
    }

    void addRow(String[] row) {
        rows.add(row);
        updated = true;
    }

    void printTable() {
        System.out.println(columnNames);
        for (String[] row : rows)
            System.out.println(String.join(" ", row));
    }
    
    @Override
    public String toString() {
        if (!updated) return stringCache;
        StringBuilder sb = new StringBuilder();
        String separator = " ";
        for (String columnName : columnNames) {
            if (columnName.length() > 10)
            sb.append(String.format("%1$-20s", columnName));
            else sb.append(String.format("%1$-10s", columnName));
            sb.append(separator);
        }

        sb.append("\n");
        
        for (String[] row : rows) {
            for (String value : row) {
                if (value.length() > 10)
                    sb.append(String.format("%1$-20s", value));
                else sb.append(String.format("%1$-10s", value));
            }
            sb.append("\n");
        }

        updated = false;
        stringCache = sb.toString();
        return stringCache
        ;
    }
}