import java.io.*;
import java.util.ArrayList;

import static constants.Messages.*;


public class FileReader {

    public static ArrayList<String> readFile(String path){
        FileInputStream inputStream =null;
        BufferedReader buffer =null;
        InputStreamReader inputStreamReader=null;

        try {
            inputStream = new FileInputStream(new File(path));
            inputStreamReader = new InputStreamReader(inputStream);
            buffer =new BufferedReader(inputStreamReader);
        }
        catch (FileNotFoundException e){
            System.out.println(FILE_NOT_FOUND + path);
            e.printStackTrace();
        }

        ArrayList<String> result = new ArrayList();
        String temp;

        try {
            while ((temp=buffer.readLine())!=null){
                result.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
