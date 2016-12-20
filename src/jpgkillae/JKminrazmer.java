
/*
 * (C) 2016. Aleksey Eremin
 */

package jpgkillae;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by ae on 07.09.2016.
 */


public class JKminrazmer extends JpgKill {
    private int f_minSize; // минимальные размеры, после которых выбраковывается

    JKminrazmer()
    {
        f_minSize = 120;
    }

    JKminrazmer(int minSize)
    {
        f_minSize = minSize;
    }

    // тестирование картинки по размеру
    @Override
    public boolean Test(File ifile)
    {
        BufferedImage img;
        try {
            img = ImageIO.read(ifile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // определим размеры изображения
        int x=img.getWidth();
        int y=img.getHeight();
        boolean flag;
        flag= x < f_minSize || y < f_minSize;
        // Log(ifile.getName() + "   " + x + "x" + y + "  ? " +flag);
        return flag;
    }


}
