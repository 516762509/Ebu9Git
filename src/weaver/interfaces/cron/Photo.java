package weaver.interfaces.cron;

import org.apache.commons.codec.binary.Base64;
import weaver.general.BaseBean;
import weaver.interfaces.schedule.BaseCronJob;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Photo extends BaseCronJob {
    private static int BUFFER = 1024;
    private String filePath = "/usr/weaver/ecology/filesystem/202205/J/94c88adc-aeb5-4d72-abe6-c1bcea914093";

    // private String filePath="e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip";

    private String zipDir = "/usr/weaver/ecology/filesystem/202205/J/94c88adc-aeb5-4d72-abe6-c1bcea914093";
    String imgName3 = zipDir.substring(zipDir.lastIndexOf("/") + 1);


    //  private String zipDir="e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip";
    public static void main(String[] args) {
        String zipDir = "/usr/weaver/ecology/filesystem/202205/B/94c88adc-aeb5-4d72-abe6-c1bcea914093";
        String imgName3 = zipDir.substring(zipDir.lastIndexOf("/") + 1);
        System.out.println(imgName3);
    }

    @Override
    public void execute() {


        new BaseBean().writeLog("执行解压任务");

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
                    System.out.println(name);
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

        new BaseBean().writeLog("读取文件开始..........");
        String s = zipDir;
        byte[] fileByte = null;
        try {
            File file = new File(s);
            fileByte = Files.readAllBytes(file.toPath());
            System.out.println("data:image/png;base64," + Base64.encodeBase64String(fileByte));
            new BaseBean().writeLog("data:image/png;base64," + Base64.encodeBase64String(fileByte));
        } catch (IOException e) {
            e.printStackTrace();
            new BaseBean().writeLog("出错..............");
        }

    }

}
