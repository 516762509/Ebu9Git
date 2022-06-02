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
    private String filePath = "/usr/weaver/ecology/filesystem/202206/H/4a8ba5b8-ae2f-4f94-b631-8042c1db983a";

    // private String filePath="e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip";

    private String zipDir = "/usr/weaver/ecology/filesystem/202205/J/94c88adc-aeb5-4d72-abe6-c1bcea914093";
    String imgName3 = zipDir.substring(zipDir.lastIndexOf("/") + 1);


    //  private String zipDir="e:\\WEAVER\\ecology\\filesystem\\202205\\J\\94c88adc-aeb5-4d72-abe6-c1bcea914093.zip";
    public static void main(String[] args) {
        String zipDir = "/usr/weaver/ecology/filesystem/202206/H/4a8ba5b8-ae2f-4f94-b631-8042c1db983a";
        String imgName3 = zipDir.substring(zipDir.lastIndexOf("/") + 1);
        System.out.println(imgName3);
    }

    @Override
    public void execute() {
        new BaseBean().writeLog("执行解压任务");


        new BaseBean().writeLog("读取文件开始..........");

        byte[] fileByte = null;
        try {
            File file = new File(filePath);
            new BaseBean().writeLog(file.exists());
            fileByte = Files.readAllBytes(file.toPath());
            System.out.println("data:image/png;base64," + Base64.encodeBase64String(fileByte));
            new BaseBean().writeLog("获取的字节码"+Base64.encodeBase64String(fileByte));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
