package ebu9.util;


import org.apache.commons.codec.binary.Base64;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static test.gs.TraceApplication.BUFFER;


public class ReadImgUtil {
    public static void main(String[] args) {
        //String image = readImage("e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip", "94c88adc-aeb5-4d72-abe6-c1bcea914093");
        //System.out.println(image);
        String s = "usr\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip";
        String image = s.substring(s.lastIndexOf("\\") + 1);
        String imgPath = image.substring(0, image.indexOf(".", image.indexOf(".")));
        System.out.println(imgPath);
        String END = readImage("usr\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip", imgPath);

    }

    /**
     * @Description: 图片转base64编码格式
     * @Auther: pss
     * @Date: 2019/9/6 19:39
     */
    public static String readImage(String filePath, String name) {
        unzip(filePath, filePath);
        String s = filePath + name;
        byte[] fileByte = null;
        try {
            File file = new File(s);
            fileByte = Files.readAllBytes(file.toPath());
            System.out.println("data:image/png;base64," + Base64.encodeBase64String(fileByte));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(fileByte);
    }


    public static String getImg(Integer id) {
        String docsubject = null;
        RecordSet recordSet = new RecordSet();
        String sql1 = "select docsubject from docdetail where id = ?";
        recordSet.executeQuery(sql1, id);
        if (recordSet.next()) {
            docsubject = recordSet.getString("docsubject");
        }
        new BaseBean().writeLog("docsubject......" + docsubject);
        String sql2 = "select imagefilename,filerealpath from imagefile where imagefilename like '%" + docsubject + "%' order by imagefileid desc";

        new BaseBean().writeLog("执行获取图片.....sql" + sql2);
        RecordSet rsd = new RecordSet();

        rsd.execute(sql2);
        String imgBase64 = "";
        if (rsd.next()) {
            new BaseBean().writeLog("开始i获取数据........");
            String imgName = rsd.getString("imagefilename");
            String imgPath = rsd.getString("filerealpath");
            //   String imgPath = rsd.getString("e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip");
            new BaseBean().writeLog("获取到的数据....." + imgName);
            new BaseBean().writeLog("获取到的数据....." + imgPath);

            String image = imgPath.substring(imgPath.lastIndexOf("\\") + 1);
            new BaseBean().writeLog("截取获取的数据");
            String imgName2 = image.substring(0, image.indexOf(".", image.indexOf(".")));
            imgBase64 = readImage(imgPath, imgName2);
        }
        return imgBase64;
    }

    public static String unzip(String filePath, String zipDir) {
        String name = "";
        try {
            BufferedOutputStream dest = null;
            BufferedInputStream is = null;
            ZipEntry entry;
            ZipFile zipfile = new ZipFile(filePath);

            Enumeration dir = zipfile.entries();
            while (dir.hasMoreElements()) {
                entry = (ZipEntry) dir.nextElement();

                if (entry.isDirectory()) {
                    name = entry.getName();
                    name = name.substring(0, name.length() - 1);
                    File fileObject = new File(zipDir + name);
                    fileObject.mkdir();
                }
            }

            Enumeration e = zipfile.entries();
            while (e.hasMoreElements()) {
                entry = (ZipEntry) e.nextElement();
                if (entry.isDirectory()) {
                    continue;
                } else {
                    is = new BufferedInputStream(zipfile.getInputStream(entry));
                    int count;
                    byte[] dataByte = new byte[BUFFER];
                    FileOutputStream fos = new FileOutputStream(zipDir + entry.getName());
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
        return name;
    }

}
