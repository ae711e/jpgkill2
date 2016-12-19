package jpgkillae;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ae on 09.09.2016.
 */

// ��� �������� �� ���������� �����, ������ ��� � ��������� ������
public class SamplesTxt extends Samples {
    @Override
    public int makeSamples(String dirOfSamples)
    {
        int i, n;
        int cnt = 0;
        File mydir = new File(dirOfSamples);
        if (!mydir.isDirectory() || !mydir.exists()) {
            System.out.println("?-ERROR-Directory not exists: " + dirOfSamples);
            return 0;
        }
        //
        f_samples = new HashMap<String,String>();
        // ����� regexp ��� ������� ������
        String JpgMask = ".+txt$";
        // ������ ��������� �� �������� �������� jpg
        // ������� ���� ����������,��� ����� �����
        String DirFi = mydir.getAbsolutePath() + System.getProperty("file.separator");  // ������� �����������
        // ������� ������ ������ � �������� �������� �������
        String list[] = mydir.list(new MyFilter(JpgMask));
        n = list.length; // ����� ������
        f_samples = new HashMap<String,String>();
        for (i = 0; i < n; i++) {
            String fname = DirFi + list[i];  // ��� ����� jpg � ��������
            cnt += readSamples(fname, f_samples);
        }
        return cnt;
    }

    // ��������� �� ����� � ������
    public static int readSamples(String fileInput, Map<String,String> map)
    {
        int cnt=0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileInput));
            map=new HashMap<String,String>();
            String str, key,val;
            int i;

            while ((str=in.readLine()) != null) {
                // ��������� ������
                i=str.indexOf('#');
                if(i>1) {
                    key=str.substring(0,i-1);  // ���� - ��� ����� �������
                    val=str.substring(i+1);    // �������� - ���
                    map.put(key,val);
                    cnt++;
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cnt;
    }
}
