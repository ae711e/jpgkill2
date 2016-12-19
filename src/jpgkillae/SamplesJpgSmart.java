/*
 * (C) 2016. Aleksey Eremin
 * SamplesJpgSmart.java created by ae on 20.09.16 9:58
 */

package jpgkillae;

import java.io.File;
import java.util.HashMap;

/**
 * Created by ae on 20.09.2016.
 */
public class SamplesJpgSmart extends SamplesJpg {
    public static final String hashFileName = "hash.txt";

    SamplesJpgSmart()
    {

    }

    SamplesJpgSmart(int hashImageSize)
    {
        f_hashImageSize=hashImageSize;
    }

    @Override
    public int makeSamples(String dirOfSamples)
    {
        int cnt=0;
        File mydir = new File(dirOfSamples);
        if(!mydir.isDirectory() || !mydir.exists()) {
            System.out.println("?-ERROR-Directory not exists: " + dirOfSamples);
            return 0;
        }

        long timeMyDir = mydir.lastModified(); // дата модификации каталога
        String fullHashFileName = dirOfSamples + s_fileSeparator +hashFileName;
        File myhash = new File (fullHashFileName);
        long timeHashFile=0;
        // если файл с хэшами существует, то используем его дату
        if(myhash.exists()) {
            timeHashFile = myhash.lastModified();  // время модификации файла хэша
        }
        // если время изменения каталога больше текстового файла, то перестроим файл
        if(timeMyDir > timeHashFile) {
            cnt = super.makeSamples(dirOfSamples);
            super.write2file(fullHashFileName);
        } else {
            f_samples = new HashMap<String,String>();
            cnt = super.read4file(fullHashFileName);
        }

        return cnt;
    }


} // end class
