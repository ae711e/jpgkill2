/*
 * (C) 2016. Aleksey Eremin
 */


package jpgkillae;

import java.io.File;

public class Main {

    public static void main(String[] args) {
	// write your code here
        if(args.length < 1) {
            System.out.println("(C) 2016 Алексей Еремин");
            System.out.println("JPGKILL v.2.06(130px, 8x8<5, 32x32<3) 30.09.2016");
            System.out.println("Перемещает картинки в DirJpg в подкаталог x\\, которые:");
            System.out.println("a) маленькие;");
            System.out.println("b) грубо похожи на образцы в DirSampl;");
            System.out.println("c) точно похожи на образцы в DirSampleHD.");
            System.out.println(">java -jar jpgkill.jar DirJpg [DirSample [DirSampleHD]]");
            System.out.println("DirJpg      - каталог с анализируемыми картинками jpg");
            System.out.println("DirSample   - каталог с картинками-образцами jpg (грубые)");
            System.out.println("DirSampleHD - каталог с картинками-образцами jpg (точные)");
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
        // грубое сравнение картинок
        if(strDirSample != null) {
            File fs=new File(strDirSample);
            if(!fs.isDirectory() || !fs.exists()) {
                System.out.println("?-ERROR-Directory not exists: " + strDirSample);
                return;
            }
            JKhash jk = new JKhash();
            SamplesJpg sampl = new SamplesJpgSmart();
            System.out.println("image compare");
            sampl.makeSamples(strDirSample);
            //smljpg.write2file(strDirSample + "\\hash.txt"); // запишем хэш в фйал (на всякий случай! :-)
            //SamplesTxt smltxt = new SamplesTxt();
            //smltxt.readSamples(hashTxtFileName);
            jk.setSamples(sampl.getSamples());
            a = jk.Run(strDirJpg);  //  возвращает кол-во "убитых" картинок
            System.out.println("RESULT: " + a);
        }
        // точное сравнение картинок
        if(strDirSampleHD != null) {
            File fs=new File(strDirSampleHD);
            if(!fs.isDirectory() || !fs.exists()) {
                System.out.println("?-ERROR-Directory not exists: " + strDirSampleHD);
                return;
            }
            JKhash jk = new JKhash(32, 3);
            SamplesJpg sampl = new SamplesJpgSmart(32);
            System.out.println("image HD compare");
            sampl.makeSamples(strDirSampleHD);
            //smljpg.write2file(strDirSampleHD + "\\hashhd.txt"); // запишем хэш в фйал (на всякий случай! :-)
            jk.setSamples(sampl.getSamples());
            a = jk.Run(strDirJpg);  //  возвращает кол-во "убитых" картинок
            System.out.println("RESULT: " + a);
        }
        //
    }
}
