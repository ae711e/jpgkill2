
/*
 * (C) 2016. Aleksey Eremin
 */

package jpgkillae;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ae on 07.09.2016.
 */
// сравнение на основе hash
public class JKhash extends JKminrazmer {
    // образцы хэш подходящих изображений
    private Map<String,String> f_samples;
    private int f_hashImageSize;  // размер хэша изображения
    private int f_minHamDist;     // минимально-допустимая дистанция Хамминга для сравнения

    JKhash()
    {
        f_hashImageSize = 0;  // (будет 8)
        f_minHamDist = 5;
    }

    JKhash(int hashImageSize)
    {
        f_hashImageSize = hashImageSize;  // размер хэша изображения
        f_minHamDist = 5;
    }

    JKhash(int hashImageSize, int minHammingDistance)
    {
        f_hashImageSize = hashImageSize;  // размер хэша изображения
        f_minHamDist = minHammingDistance;
    }


    public void setSamples(Map<String,String> samples)
    {
        f_samples=samples;
    }


    // тестирование картинки по хэш-коду
    @Override
    public boolean Test(File ifile) {
        int hd;
        HashImage hu=new HashImage(ifile, f_hashImageSize);
        String hi=hu.getHash();
        String key,val;
        for(Map.Entry<String,String> ent: f_samples.entrySet()) {
            key = ent.getKey();
            val = ent.getValue();
            // явное равнение или дистанция Хэмминга (по-символьно)
            hd=HammingDistance(hi,val);
            if(hd<f_minHamDist) {  // литература советует 5 на 8x8
                // сравнение совпало
                LogFile(key + " ~ " + ifile.getName() + " / " + hi );
                return true;
            }
        }

        return false;

    }

    ////private static final int[] fs_charhd={0,1,1,2,1,2,2,3,1,2,2,3,2,3,3,4};
    public static int HammingDistance(String hash1, String hash2)
    {
        int i,n,l,i1,i2;
        char c1,c2;
        int hD=0;
        n=hash1.length();
        i=hash2.length();
        if(n>i) {
            String tmp;
            tmp=hash1;
            hash1=hash2;
            hash2=tmp;
        }
        hD = (n-i); //*4;
        if (hD<0) hD = -hD;
        n=hash1.length();    // сравниваем по минимальной строке, а в hash1 меньшая строка
        //
        for(i=0; i<n; i++) {
            c1=hash1.charAt(i);
            c2=hash2.charAt(i);
            if(c1 != c2) {
                hD++;
            }
        }
        return hD;
    }

    private final static String LogFileName="c:\\tmp\\_rev\\log.txt";

    private static void LogFile(String message)
    {
        try {
            Writer wrt = new FileWriter(LogFileName,true);
            wrt.write(message);
            wrt.write(System.getProperty("line.separator"));
            wrt.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
