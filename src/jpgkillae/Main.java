/*
 * (C) 2016. Aleksey Eremin
 */


package jpgkillae;

import java.io.File;

public class Main {

    public static void main(String[] args) {
	// write your code here
        if(args.length < 1) {
            System.out.println("(C) 2016 ������� ������");
            System.out.println("JPGKILL v.2.06(130px, 8x8<5, 32x32<3) 30.09.2016");
            System.out.println("���������� �������� � DirJpg � ���������� x\\, �������:");
            System.out.println("a) ���������;");
            System.out.println("b) ����� ������ �� ������� � DirSampl;");
            System.out.println("c) ����� ������ �� ������� � DirSampleHD.");
            System.out.println(">java -jar jpgkill.jar DirJpg [DirSample [DirSampleHD]]");
            System.out.println("DirJpg      - ������� � �������������� ���������� jpg");
            System.out.println("DirSample   - ������� � ����������-��������� jpg (������)");
            System.out.println("DirSampleHD - ������� � ����������-��������� jpg (������)");
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
        // ��������� ��������
        int a;
        // �������� �������� �������� (������ - �� ������)
        JKminrazmer jr = new JKminrazmer(130);
        System.out.println("Begin work [" + strDirJpg + "]");
        a = jr.Run(strDirJpg);  //  ���������� ���-�� "������" ��������
        System.out.println("RESULT: " + a);
        // ������ ��������� ��������
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
            //smljpg.write2file(strDirSample + "\\hash.txt"); // ������� ��� � ���� (�� ������ ������! :-)
            //SamplesTxt smltxt = new SamplesTxt();
            //smltxt.readSamples(hashTxtFileName);
            jk.setSamples(sampl.getSamples());
            a = jk.Run(strDirJpg);  //  ���������� ���-�� "������" ��������
            System.out.println("RESULT: " + a);
        }
        // ������ ��������� ��������
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
            //smljpg.write2file(strDirSampleHD + "\\hashhd.txt"); // ������� ��� � ���� (�� ������ ������! :-)
            jk.setSamples(sampl.getSamples());
            a = jk.Run(strDirJpg);  //  ���������� ���-�� "������" ��������
            System.out.println("RESULT: " + a);
        }
        //
    }
}
