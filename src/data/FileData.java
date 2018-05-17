package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileData extends Data {
    @Override
    public List<String> read(List<String> dataPaths) {

        List<String> readResults = new ArrayList<>(dataPaths.size());
        StringBuffer sb;
        BufferedReader br;

        for (String path : dataPaths) {
            File file = new File(path);
            sb = new StringBuffer();
            try {
                br = new BufferedReader(new FileReader(file));
                String str;
                while ((str = br.readLine()) != null){
                    sb.append(str);
                    sb.append('\n');
                }
                readResults.add(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return readResults;
    }
}
