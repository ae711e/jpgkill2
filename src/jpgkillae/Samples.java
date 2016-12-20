/*
 * (C) 2016. Aleksey Eremin
 */

package jpgkillae;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ae on 09.09.2016.
 */
// класс примеров для сравнения
public class Samples {
    protected Map<String,String> f_samples;

    public Map<String,String> getSamples()
        {
            return f_samples;
        }

    // сделать набор примеров на основе каталога dirOfSamples
    public int makeSamples(String dirOfSamples)
        {
            return 0;
        }

    // Записать список в текстовый файл
    // на основе http://www.cyberforum.ru/java-j2se/thread862328.html
    public int write2file(String fileOut)
    {
        int cnt=0;
        if(f_samples != null) {
            try {
                Writer wrt = new FileWriter(fileOut);
                String key, val, str;
            /*
            for(Iterator it=f_samples.keySet().iterator(); it.hasNext();) {
                key = (String) it.next();
                val = f_samples.get(key);
                str = key + "#" + val;
                wrt.write(str);
                wrt.write(System.getProperty("line.separator"));
                cnt++;
            }
            */
                // по мотивам:
                // http://info.javarush.ru/translation/2014/02/11/9-%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D1%8B%D1%85-%D0%B2%D0%BE%D0%BF%D1%80%D0%BE%D1%81%D0%BE%D0%B2-%D0%BE-Map-%D0%B2-Java.html
                // https://user12vv.wordpress.com/2013/10/30/java-%D1%81%D0%BF%D0%BE%D1%81%D0%BE%D0%B1%D1%8B-%D0%BF%D0%B5%D1%80%D0%B5%D0%B1%D0%BE%D1%80%D0%B0-%D0%BA%D0%BE%D0%BB%D0%BB%D0%B5%D0%BA%D1%86%D0%B8%D0%B8-map/
                // http://www.seostella.com/ru/article/2012/08/09/kollekcii-collections-v-java-map.html
                for (Map.Entry<String, String> ent : f_samples.entrySet()) {
                    key = ent.getKey();
                    val = ent.getValue();
                    str = key + "#" + val;
                    wrt.write(str);
                    wrt.write(System.getProperty("line.separator"));
                    cnt++;
                }

                wrt.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cnt;
    }

    // прочитать список из текстового файла
    public int read4file(String fileInput)
    {
        int cnt=0;
        if(f_samples != null) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(fileInput));
                String str, key, val;
                int i;
                while ((str = in.readLine()) != null) {
                    // прочитали строку
                    i = str.indexOf('#');
                    if (i > 1) {
                        key = str.substring(0, i - 1);  // ключ - имя файла образца
                        val = str.substring(i + 1);    // значение - хэш
                        f_samples.put(key, val);
                        cnt++;
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cnt;
    }


}
