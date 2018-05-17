package data;

import java.util.ArrayList;
import java.util.List;

public abstract class Data {

    /*
    @dataAddress contains the location of the data. This can be filename or links.
     */
    public abstract List<String> read(List<String> dataPaths);


}
