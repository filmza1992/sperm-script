package com.clean_sperm.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Data {

    private static Connection connection;

    public Data() {
    }

    // test
    static int count = 0;

    public static ArrayList<String[]> getAllDairySperm() {

        ArrayList<String[]> data = new ArrayList<>();
        String query = "SELECT * from tbd_sperm";
        try {
            openDatabaseVpnConnection();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                int column = statement.getMetaData().getColumnCount();
                while (resultSet.next()) {
                    if (count == 50) {
                        break;
                    }
                    String[] row = new String[column];
                    for (int i = 1; i <= column; i++) {
                        row[i - 1] = resultSet.getString(i);
                    }
                    data.add(row);
                    System.out.println(row[3]);

                }
            }
            closeDatabaseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static HashSet<String[]> getTzSpermByDairySperm(ArrayList<String[]> dataDairy) {

        HashSet<String[]> data = new HashSet<>();
        boolean found = false;
        try {
            openDatabaseVpnConnection();
            for (String[] d : dataDairy) {
                String query = "SELECT * FROM dip_tz WHERE tzdad_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, d[3]);
                    ResultSet resultSet = statement.executeQuery();
                    int column = statement.getMetaData().getColumnCount();
                    while (resultSet.next()) {

                        String[] row = new String[column];
                        for (int i = 1; i <= column; i++) {
                            row[i - 1] = resultSet.getString(i);
                        }
                        for(String[] da : data){
                            if(da[0].equals(row[0])){
                                found = true;
                            }
                        }
                        if(!found){
                            data.add(row);
                        }else{
                            found = false;
                        }
                    }
                    System.out.print("\rrow in dairy " + count);
                    count++;
                }
            }

            closeDatabaseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static ArrayList<String[]> getDairySpermByTzSperm(HashSet<String[]> dataTz) {

        ArrayList<String[]> data = new ArrayList<>();
        try {
            openDatabaseVpnConnection();
            for (String[] d : dataTz) {
                String query = "SELECT * FROM tbd_sperm WHERE sperm_code = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    System.out.println(d[0]);
                    statement.setString(1, d[0]);
                    ResultSet resultSet = statement.executeQuery();
                    int column = statement.getMetaData().getColumnCount();
                    while (resultSet.next()) {
                        String[] row = new String[column];
                        for (int i = 1; i <= column; i++) {
                            row[i - 1] = resultSet.getString(i);
                        }
                        data.add(row);
                        System.out.println(row[0] + " " + row[1] + " " + row[3]);
                    }
                }
            }
            closeDatabaseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static int updateDairySpermByTzSperm(HashSet<String[]> dataTz) {
        int rowsAffected = 0;
        try {
            openDatabaseLocalTestConnection();
            for (String[] d : dataTz) {
                String query = "UPDATE tbd_sperm " +
                        "SET cow_fa_code = ?, " +
                        "cow_fa_name = ?, " +
                        "cow_ma_code = ?, " +
                        "cow_ma_name = ? " +
                        "WHERE sperm_code = ?";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    System.out.println(d[0]);
                    statement.setString(1, d[2]);
                    statement.setString(2, d[3]);
                    statement.setString(3, d[4]);
                    statement.setString(4, d[5]);
                    statement.setString(5, d[0]);

                    rowsAffected += statement.executeUpdate();
                }
            }
            closeDatabaseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsAffected;
    }

    private static void openDatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mariadb://54.251.168.197:6667/zyanwoadev_test",
                "summer2023",
                "Summ3r!@MISL$$23");
    }

    private static void openDatabaseVpnConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mariadb://10.0.0.2:3306/zyanwoadevDIP3",
                "admin",
                "misl");
    }

    private static void openDatabaseLocalTestConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/zyanwoadevDIP3",
                "root",
                "");
    }

    private static void closeDatabaseConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}
