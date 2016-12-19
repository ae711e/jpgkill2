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
// ����� ��� ���������� ������ ������, ���������� File.list
public class MyFilter implements FilenameFilter {
    //String strMask;
    Pattern pat;

    // maskName - regexp ����� ����� ������ ��� �������
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