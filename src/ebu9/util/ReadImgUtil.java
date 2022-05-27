package ebu9.util;


import org.apache.commons.codec.binary.Base64;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;


public class ReadImgUtil {
    public static void main(String[] args) {
        //String image = readImage("e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip", "94c88adc-aeb5-4d72-abe6-c1bcea914093");
        //System.out.println(image);
        String s = "e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip";
        String image = s.substring(s.lastIndexOf("\\")+1);
        String imgPath = image.substring(0, image.indexOf(".",image.indexOf(".")));
        System.out.println(imgPath);
        String END = readImage("e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip", imgPath);
    }

    /**
     * @Description: 图片转base64编码格式
     * @Auther: pss
     * @Date: 2019/9/6 19:39
     */
    public static String readImage(String filePath, String name) {
        UnZipPic.unzip(filePath, filePath);
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
            new BaseBean().writeLog("获取到的数据....." + imgName);
            new BaseBean().writeLog("获取到的数据....." + imgPath);

            String image = imgPath.substring(imgPath.lastIndexOf("\\")+1);
            new BaseBean().writeLog("截取获取的数据");
            String imgName2 = image.substring(0, image.indexOf(".",image.indexOf(".")));
            imgBase64 = readImage(imgPath, imgName2);
        }
        return imgBase64;
    }
}
