package ebu9.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author HY
 */
public class UnZipPic {

    private static int BUFFER = 1024;

    public static void main(String[] args) {

    //    String unzip = unzip("e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip", "e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip");
     //   System.out.printf(unzip);
        String imgPath = "";
        String image = imgPath.substring(imgPath.lastIndexOf("\\")+1);
        String imgName2 = image.substring(0, image.indexOf(".",image.indexOf(".")));
        System.out.println(imgName2);

    }

    public  static String unzip(String filePath,String zipDir) {
        String name = "";
        try {
            BufferedOutputStream dest = null;
            BufferedInputStream is = null;
            ZipEntry entry;
            ZipFile zipfile = new ZipFile(filePath);

            Enumeration dir = zipfile.entries();
            while (dir.hasMoreElements()){
                entry = (ZipEntry) dir.nextElement();
                if( entry.isDirectory()){
                    name = entry.getName();
                    System.out.println(name);
                    name = name.substring(0, name.length() - 1);
                    File fileObject = new File(zipDir+name);
                    fileObject.mkdir();
                }
            }

            Enumeration e = zipfile.entries();
            while (e.hasMoreElements()) {
                entry = (ZipEntry) e.nextElement();
                if( entry.isDirectory()){
                    continue;
                }else{
                    is = new BufferedInputStream(zipfile.getInputStream(entry));
                    int count;
                    byte[] dataByte = new byte[BUFFER];
                    FileOutputStream fos = new FileOutputStream(zipDir+entry.getName());
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = is.read(dataByte, 0, BUFFER)) != -1) {
                        dest.write(dataByte, 0, count);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  name;
    }


}