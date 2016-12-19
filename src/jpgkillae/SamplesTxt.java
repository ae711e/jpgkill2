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

// хэш картинок из текстового файла, каждый хэш в отдельной строке
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
        // маска regexp для фильтра файлов
        String JpgMask = ".+txt$";
        // начнем проходить по каталогу картинок jpg
        // получим путь директории,где файлы лежат
        String DirFi = mydir.getAbsolutePath() + System.getProperty("file.separator");  // добавим разделитель
        // получим список файлов в каталоге согласно фильтра
        String list[] = mydir.list(new MyFilter(JpgMask));
        n = list.length; // длина списка
        f_samples = new HashMap<String,String>();
        for (i = 0; i < n; i++) {
            String fname = DirFi + list[i];  // имя файла jpg в каталоге
            cnt += readSamples(fname, f_samples);
        }
        return cnt;
    }

    // прочитать из файла в список
    public static int readSamples(String fileInput, Map<String,String> map)
    {
        int cnt=0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileInput));
            map=new HashMap<String,String>();
            String str, key,val;
            int i;

            while ((str=in.readLine()) != null) {
                // прочитали строку
                i=str.indexOf('#');
                if(i>1) {
                    key=str.substring(0,i-1);  // ключ - имя файла образца
                    val=str.substring(i+1);    // значение - хэш
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
