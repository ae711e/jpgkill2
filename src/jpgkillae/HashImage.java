
/*
 * (C) 2016. Aleksey Eremin
 * HashImage.java created by ae on 07.09.16 15:21
 */

package jpgkillae;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Created by ae on 07.09.2016.
 */
public class HashImage {

    private int    f_hashImageSize;  // размер изображения для вычисления хэш-функции
    private File   fileImage;       // файл изображения
    private String hashImage;     // хэш изображения

    HashImage(File imageFile)
    {
        fileImage=imageFile;
        hashImage="";
        f_hashImageSize=8;  // размер изображения 8x8
    }

    // задать нестандарный размер изображения для вычисления хэша
    HashImage(File imageFile, int hashImageSize)
    {
        fileImage=imageFile;
        hashImage="";
        if(hashImageSize<8) hashImageSize=8;    // меньше 8 нельзя
        f_hashImageSize=hashImageSize;  // размер изображения hSxhS
    }

    // вернуть хэш изображения, если еще не считали - рассчитать
    public String getHash()
    {
        if(hashImage.length()>1) {
            return hashImage;
        }
        return calcHash();
    }

    // расчет хэша изображения
    public String calcHash()
    {
        // начнем развивать тему hash
        BufferedImage img;
        try {
            img = ImageIO.read(fileImage);
        } catch (IOException e) {
            e.printStackTrace();
            return hashImage;
        }
        //
        img=cropPicture(img);
        if(img!=null) {
          hashImage = calcHashImage(img);
        }
        return hashImage;
    }

    // Обрезать изображение
    private BufferedImage cropPicture(BufferedImage image)
    {
      int dx, dy;
      dx=image.getWidth();  // ширина
      dy=image.getHeight(); // высота
      // надо отрезать снизу текстовую примочку на высоту bottomCrop пиксел
      int r=dy-R.bottomCrop;
      if(r<1) return null;
      BufferedImage cropImage;
      cropImage=image.getSubimage(0,0, dx,r);
      return cropImage;
    }
    
    // рассчитать хэш:  (https://habrahabr.ru/post/120562/)
    // 1. уменьшить размер
    // 2. сделать серую картинку
    // 3. найти среднее
    // 4. цепочка бит. заменим пикселы на 0 (если меньше среднего) или 1 (если больше, равно) заменим бит
    // 5. вытянуть все бит в строку HEX - теперь не вытягиваю ибо нафиг, мой файл-мои правила!
    // от опытов осталось: final static char[] charHex={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private String calcHashImage(BufferedImage image)
    {
        ///   writeImage2File(image,"c:\\tmp\\a0.png");
        // 1. уменьшаем размер и убираем нижнюючасть картинки (там служебные надписи c 21/12/2016)
        BufferedImage im=toNewSize(image, f_hashImageSize, f_hashImageSize);
        ///   writeImage2File(im,"c:\\tmp\\a1.png");
        // 2. убрать цвет - сделать серой и заодно рассчитаем среднее
        int isr;  // среднее картинки
        isr=toGrayImageAndAverage(im);
        ///   writeImage2File(im,"c:\\tmp\\a2.png");
        // размеры изображения
        int sx=im.getWidth();
        int sy=im.getHeight();
        // 3. строим цепочку бит от уровня среднего
        StringBuffer shash=new StringBuffer(sx*sy);
        WritableRaster raster = im.getRaster();
        int[] data=new int[4];
        int x,y;
        ////int base = 1;  // база двоичного числа в последовательности бит-пиксела
        ////int symb = 0;  //
        char c;
        for (x = 0; x < sx; x++) {
            for (y = 0; y < sy; y++) {
                data = raster.getPixel(x, y, data);
                c='0';
                if(data[0] > isr) {
                    ////symb = symb + base;
                    c='1';
                }
                shash.append(c);
                ////base = base * 2;
                ////if(base > 8) {
                ////shash.append(charHex[symb]);
                ////base = 1;
                ////symb = 0;
                ////}
            }
        }
        //
        return new String(shash);
    }


    // записать изображение в файл
    protected static boolean writeImage2File(BufferedImage img, String fout)
    {
        File out=new File(fout);
        try {
            ImageIO.write((RenderedImage)img,"png",out);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //
        return true;
    }

    // сделать изображение серым и вернуть среднее значение
    private static int toGrayImageAndAverage(BufferedImage image)
    {
        // делаем картинку серой
        WritableRaster raster = image.getRaster();
        int[] data = new int[4];
        int sx=image.getWidth();
        int sy=image.getHeight();
        int rgb;
        // и рассчитаем среднее значение
        // найдем среднее
        double sr=0; // сумма
        int sn=0; // кол-во
        //
        for (int x = 0; x < sx; x++) {
            for (int y = 0; y < sy; y++) {
                data = raster.getPixel(x, y, data); // new int[4]
                // серый пиксел (data[0]-red  data[1]-green data[2]-blue data[3]-alpha)
                rgb = (int) (((double) data[0] * 0.299) + ((double) data[1] * 0.587) + ((double) data[2] * 0.114));
                if (rgb > 255) {
                    rgb = 255;
                }
                data[0]=data[1]=data[2]=rgb;
                sr += (double)rgb;
                sn++;
                raster.setPixel(x, y, data);
            }
        }
        //
        sr = sr/(double)sn;
        return (int)sr;
    }
    
    // изменить размер изображение до newX newY
    // уменьшить размер, отрезать нижнюю часть
    // использует разные функции
    private static BufferedImage toNewSize(BufferedImage originalImage, int newX, int newY)
    {
      BufferedImage img, imgcrop, imgret;
      img = toNewSize3(originalImage, 960, 600); // привести к стандартному размеру 960x600 для отрезания подписи
      imgcrop = img.getSubimage(0,0,  960, 550);  // отрезать подпись (она внизу картинки)
      imgret= toNewSize3(imgcrop, newX, newY);
        ////writeImage2File(originalImage,"c:\\tmp\\ai0.png");
        ////writeImage2File(img,"c:\\tmp\\ai1.png");
        ////writeImage2File(imgcrop,"c:\\tmp\\ai2.png");
        ////writeImage2File(imgret,"c:\\tmp\\ai3.png");
      return imgret;
    }
  
    // изменить размер изображение до newX newY
    private static BufferedImage toNewSize1(BufferedImage originalImage, int newX, int newY)
    {
        BufferedImage scaled = new BufferedImage(newX, newY, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(originalImage, 0, 0, newX, newY, null);
        g.dispose();
        return scaled;
    }
  
    // изменить размер изображение до newX newY
    private static BufferedImage toNewSize2(BufferedImage image, int newX, int newY)
    {

        ColorModel cm = image.getColorModel();

        WritableRaster raster = cm.createCompatibleWritableRaster(newX, newY);

        boolean isRasterPremultiplied = cm.isAlphaPremultiplied();

        BufferedImage target = new BufferedImage(cm, raster, isRasterPremultiplied, null);
        Graphics2D g2 = target.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        double scalex = (double) target.getWidth()/ image.getWidth();
        double scaley = (double) target.getHeight()/ image.getHeight();

        AffineTransform xform = AffineTransform.getScaleInstance(scalex, scaley);
        g2.drawRenderedImage(image, xform);
        g2.dispose();
        return target;
    }

    // функция из Интернета
    private static BufferedImage toNewSize3(BufferedImage img, int targetWidth, int targetHeight)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        // Используем многошаговую технику: начинаем с оригинального размера, затем
        // уменьшаем несколько раз многократным вызовом drawImage()
        // до тех пор, пока достигнем нужного размера
        w = img.getWidth();
        h = img.getHeight();
        do {
            if (w > targetWidth)  { w /= 2;           }
            if (w < targetWidth)  { w = targetWidth;  }
            if (h > targetHeight) { h /= 2;           }
            if (h < targetHeight) { h = targetHeight; }
            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();
            ret = tmp;
        } while (w != targetWidth || h != targetHeight);
        return ret;
    }
    
    // вырезает в оригинальном изображении прямоугольник от x0, y0 шириной sizeX и высотой sizeY
    // http://blog.dimmduh.com/2011/05/crop-image-java.html
    private static BufferedImage cropImage(BufferedImage originalImage, int x0, int y0, int sizeX, int sizeY)
    {
      BufferedImage img;
      img=originalImage.getSubimage(x0,y0,sizeX,sizeY);
      return img;
    }

    /**  https://community.oracle.com/docs/DOC-983611
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     * in pixels
     * @param targetHeight the desired height of the scaled instance,
     * in pixels
     * @param hint one of the rendering hints that corresponds to
     * {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     * {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     * {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     * {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     * scaling technique that provides higher quality than the usual
     * one-step technique (only useful in downscaling cases, where
     * {@code targetWidth} or {@code targetHeight} is
     * smaller than the original dimensions, and generally only when
     * the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@code BufferedImage}
     *  /
    public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth(); h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth; h = targetHeight;
        }
        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) { w = targetWidth; }
            }
            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) { h = targetHeight; }
            }
            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();
            ret = tmp;
        } while (w != targetWidth || h != targetHeight);
        return ret;
    }
    */




} // end class HashImage
