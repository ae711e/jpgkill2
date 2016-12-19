
/*
 * (C) 2016. Aleksey Eremin
 */

package jpgkillae;

import java.io.File;

/**
 * Created by ae on 07.09.2016.
 */

// ������� ����� ��������� ��������
public class JpgKill {
    final static String s_subdir=System.getProperty("file.separator")+
                                 "x"+System.getProperty("file.separator"); // ����������, ���� ���������� ������� �����

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
        // ����� regexp ��� ������� ������
        String JpgMask=".+jpg$";
        // ������ ��������� �� �������� �������� jpg
        // ������� ���� ����������,��� ����� �����
        String DirFi=mydir.getAbsolutePath() + System.getProperty("file.separator");  // ������� �����������
        // ������� ������ ������ � �������� �������� �������
        String list[]=mydir.list(new MyFilter(JpgMask));
        n=list.length; // ����� ������
        for(i=0; i<n; i++) {
            String fname=DirFi+list[i];  // ��� ����� jpg � ��������
            //Log(fname);
            // �������� - ������������� �� ���� �������?
            File ifile=new File(fname);
            if(Test(ifile)) {
                cnt++;
                // "�����" ����
                Kill(ifile);
            }
        }
        //
        return cnt;
    }

    // ��������� ������� �������� ��� ����� jpg
    // ���� ���� ������������� ������� - ���������� TRUE, ����� - FALSE
    public boolean Test(File ifile)
    {
        return false;
    }

    // ������� ����
    // ���� ���� - ���������� TRUE
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
