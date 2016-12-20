/*
 * (C) 2016. Aleksey Eremin
 * Resource.java created by ae on 20.12.16 11:54
 */

package jpgkillae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Created by ae on 20.12.2016.
 * Загрузка строк и картинок из ресурсов приложения JAR
 */
public class Resource {
  
  // загрузить текстовый ресурс с кодовой страницей "Cp1251"
  public String getResourceString(String nameResource)
  {
    return getResourceString(nameResource, "Cp1251");
  }
  
  // загрузить текстовый ресурс с кодовой страницей codePage ("Cp1251", ...)
  public String getResourceString(String nameResource, String codePage)
  {
    StringBuilder sb = new StringBuilder();
    try {
      InputStream is = getClass().getResourceAsStream(nameResource);  // Имя ресурса
      BufferedReader br = new BufferedReader(new InputStreamReader(is, "Cp1251")); //* кодовая страница ресурса
      while (true) {
        String line = br.readLine();
        if (line == null)
          break;
        sb.append(line).append("\n");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return sb.toString();
  }
  
  /*
  исходный код - http://skipy-ru.livejournal.com/5343.html
  private String loadText() {
    StringBuilder sb = new StringBuilder();
    try {
      InputStream is = getClass().getResourceAsStream("/res/text.txt");  // Имя ресурса
      BufferedReader br = new BufferedReader(new InputStreamReader(is, "Cp1251"));
      while (true) {
        String line = br.readLine();
        if (line == null)
          break;
        sb.append(line).append("\n");
      }
    } catch (IOException ex) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      ex.printStackTrace(pw);
      pw.flush();
      pw.close();
      sb.append("Error while loading text: ").append("\n\n");
      sb.append(sw.getBuffer().toString());
    }
    return sb.toString();
  }
  */
  
  
}
