package com.clean_sperm.data;

import java.util.ArrayList;
import java.util.HashSet;
public class Dashboard {

    public Dashboard(){

        //get all dairy sperm
        System.out.println("Running!!!");
        System.out.println("Get Sperm in Dairy");
        ArrayList<String[]> dataDairy = Data.getAllDairySperm();
        System.out.println("Get Sperm dairy Done!");
        
        System.out.println("=====================");
        
        //get all tz have in dairy
        System.out.println("Get Sperm in Tz By Dairy");
        HashSet<String[]> dataTz = Data.getTzSpermByDairySperm(dataDairy);
        System.out.println(setToString(dataTz));
        System.out.println("Get Sperm in Tz By Dairy Done!");
        
        System.out.println("=====================");
        
        System.out.println("Get Sperm Dairy By Tz");
        ArrayList<String[]> filterDataDairyByDataTz = Data.getDairySpermByTzSperm(dataTz);
        System.out.println("Rows : " +filterDataDairyByDataTz.size());
        System.out.println("Successful operation Get Sperm Dairy By Tz");

        System.out.println("====================="); 

        System.out.println("Update Sperm Dairy By Tz");
        int resultUpdate = Data.updateDairySpermByTzSperm(dataTz);
        System.out.println("Rows affected: " + resultUpdate);
        System.out.println("Successful operation Update Sperm Dairy By Tz");
        
        System.out.println("=====================");

    }

    public static String setToString(HashSet<String[]> set) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String[] arr : set) {
            sb.append("[");
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]);
                if (i < arr.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            sb.append(", ");
        }
        if (!set.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
}
