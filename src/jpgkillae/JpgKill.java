
/*
 * (C) 2016. Aleksey Eremin
 */

package jpgkillae;

import java.io.File;

/**
 * Created by ae on 07.09.2016.
 */

// базовый класс обработки каталога
public class JpgKill {
    final static String s_subdir=System.getProperty("file.separator")+
                                 "x"+System.getProperty("file.separator"); // подкаталог, куда перемещать похожие файлы

    public static void Log(String str) {
        System.out.println(str);
    }

    public int Run(String dir)
    {
        int i,n;
        int cnt=0;
        File mydir=new File(dir);
        if(!mydir.isDirectory() || !mydir.exists()) {
            Log("?-ERROR-Directory not exists: " + dir);
            return 0;
        }
        // маска regexp для фильтра файлов
        String JpgMask=".+jpg$";
        // начнем проходить по каталогу картинок jpg
        // получим путь директории,где файлы лежат
        String DirFi=mydir.getAbsolutePath() + System.getProperty("file.separator");  // добавим разделитель
        // получим список файлов в каталоге согласно фильтра
        String list[]=mydir.list(new MyFilter(JpgMask));
        n=list.length; // длина списка
        for(i=0; i<n; i++) {
            String fname=DirFi+list[i];  // имя файла jpg в каталоге
            //Log(fname);
            // проверим - соответствует ли файл условию?
            File ifile=new File(fname);
            if(Test(ifile)) {
                cnt++;
                // "убьем" файл
                Kill(ifile);
            }
        }
        //
        return cnt;
    }

    // проверяет условия проверки для файла jpg
    // если файл соответствует условию - возвращает TRUE, иначе - FALSE
    public boolean Test(File ifile)
    {
        return false;
    }

    // убивает файл
    // если убил - возвращает TRUE
    public boolean Kill(File ifile)
    {
        String path=ifile.getParent();
        String name=ifile.getName();
        String outname;
        outname=path+s_subdir+name;
        File fn=new File(outname);
        if(!ifile.renameTo(fn)) {
            File wd=new File(path+s_subdir);
            wd.mkdir();
            if(!ifile.renameTo(fn)) {
                Log("?-ERROR-File don't move: " + outname);
            }
        }
        //
        Log(outname);
        //
        return true;
    }

}
