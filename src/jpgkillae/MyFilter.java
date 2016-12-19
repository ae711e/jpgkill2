/*
 * (C) 2016. Aleksey Eremin
 */

package jpgkillae;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ae on 07.09.2016.
 */
// класс для фильтрации списка файлов, выдаваемых File.list
public class MyFilter implements FilenameFilter {
    //String strMask;
    Pattern pat;

    // maskName - regexp маска имени файлов для фильтра
    public MyFilter(String maskName) {
        //strMask=maskName;
        pat=Pattern.compile(maskName);
    }

    @Override
    public boolean accept(File dir, String name) {
        // TODO Auto-generated method stub
        Matcher m = pat.matcher(name);
        return m.matches();
    }

}