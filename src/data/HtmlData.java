package data;

import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.List;

public class HtmlData extends Data {
    @Override
    public List<String> read(List<String> dataPaths) {
        List<String> readResults = new ArrayList<>(dataPaths.size());
        StringBuffer sb;
        String s;

        for (String path : dataPaths) {
            s = "";
            sb = urlOpen(path);
            if (sb != null) {
                s = sb.toString();
                s = parse(s);
            }

            readResults.add(s);

        }
        return readResults;
    }

    private StringBuffer urlOpen(String url) {
        try {
            URL u = new URL(url);
            StringBuffer sb = new StringBuffer();
            BufferedReader in;
            in = new BufferedReader(
                    new InputStreamReader(u.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
                sb.append('\n');
            }
            in.close();
            return sb;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String parse(String s) {
        // TODO: Implement later. Do nothing for now.
        return s;
    }
}
