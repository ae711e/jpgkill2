
/*
 * (C) 2016. Aleksey Eremin
 */

package jpgkillae;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ae on 08.09.2016.
 */
// создание коллекции образцов
public class SamplesJpg extends Samples {
    public static final String s_fileSeparator = System.getProperty("file.separator");
    protected int f_hashImageSize;  // размер изображения хэша

    SamplesJpg()
    {
        f_hashImageSize=0;  // (будет 8)
    }

    SamplesJpg(int hashImageSize)
    {
        f_hashImageSize=hashImageSize;
    }

    // сделать набор примеров на основе каталога dirOfSamples
    public int makeSamples(String dirOfSamples)
    {
        int i,n;
        int cnt=0;
        File mydir=new File(dirOfSamples);
        if(!mydir.isDirectory() || !mydir.exists()) {
            System.out.println("?-ERROR-Directory not exists: " + dirOfSamples);
            return 0;
        }
        //
        f_samples=new HashMap<String,String>();
        // маска regexp для фильтра файлов
        String JpgMask=".+jpg$";
        // начнем проходить по каталогу картинок jpg
        // получим путь директории,где файлы лежат
        String DirFi=mydir.getAbsolutePath() + s_fileSeparator;  // добавим разделитель
        // получим список файлов в каталоге согласно фильтра
        String list[]=mydir.list(new MyFilter(JpgMask));
        n=list.length; // длина списка
        for(i=0; i<n; i++) {
            String fname=DirFi+list[i];  // имя файла jpg в каталоге
            File ifile=new File(fname);
            HashImage hi=new HashImage(ifile,f_hashImageSize);
            String hash=hi.getHash();
            f_samples.put(list[i],hash);
            cnt++;
        }
        return cnt;
    }
}
