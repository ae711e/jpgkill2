/*
 * (C) 2016. Aleksey Eremin
 */


package jpgkillae;

import java.io.File;
/*
 Проверяет на матрице 16х16 только один каталог SAMPLE
 20 января 2017
 */

public class Main {

    public static void main(String[] args) {
	// write your code here
        if(args.length < 1) {
            // выведем подсказку из ресурсного файла
            String str;
            Resource res=new Resource();
            str = res.getResourceString("/res/readme.txt");
            System.out.println(str);
            return;
        }
        //
        String strDirJpg =args[0];
        String strDirSample = (args.length<2)? null: args[1];
        String strDirSampleHD = (args.length<3)? null: args[2];
        File fd=new File(strDirJpg);
        if(!fd.isDirectory() || !fd.exists()) {
            System.out.println("?-ERROR-Directory not exists: " + strDirJpg);
            return;
        }
        // обработка каталога
        int a;
        // проверка размеров картинки (меньше - на выброс)
        JKminrazmer jr = new JKminrazmer(130);
        System.out.println("Begin work [" + strDirJpg + "]");
        a = jr.Run(strDirJpg);  //  возвращает кол-во "убитых" картинок
        System.out.println("RESULT: " + a);
        // сравнение картинок
        if                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         (strDirSample != null) {
            File fs=new File(strDirSample);
            if(!fs.isDirectory() || !fs.exists()) {
                System.out.println("?-ERROR-Directory not exists: " + strDirSample);
                return;
            }
            JKhash jk = new JKhash(16, 4); // размеры
            SamplesJpg sampl = new SamplesJpgSmart(16);
            System.out.println("image compare");
            sampl.makeSamples(strDirSample);
            jk.setSamples(sampl.getSamples());
            a = jk.Run(strDirJpg);  //  возвращает кол-во "убитых" картинок
            System.out.println("RESULT: " + a);
        }
        //
    }
}
