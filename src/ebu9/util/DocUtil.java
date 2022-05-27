package ebu9.util;

import weaver.conn.RecordSet;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocServiceImpl;
import weaver.general.Util;
import weaver.hrm.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DocUtil {
    /**
     * 创建文档
     * @param user
     * @param fileid
     * @param mlid
     * @param ip
     * @param port
     * @return
     */
    public static int CreateDoc(User user, int fileid, String mlid, String ip, int port){
        RecordSet rs = new RecordSet();
        int docid = 0;
        String nameSql = "select filerealpath,iszip,imagefilename,imagefile from imagefile where imagefileid="+fileid;
        rs.execute(nameSql);
        if(rs.next()){
            String filePath = Util.null2String(rs.getString("filerealpath"));
            String fileName = Util.null2String(rs.getString("imagefilename"));
            String locapath = "";
            int index = 0;
            index = filePath.lastIndexOf(".");
            if(index>0){
                locapath = filePath.substring(0, index); //无后缀路径
            }
            DocServiceImpl serviceImpl = new DocServiceImpl(); //创建文档服务
            try {
                String session1 = serviceImpl.login(user.getLoginid(), user.getPwd(), 0, "127.0.0.1");
                DocInfo doc = new DocInfo();
                DocAttachment da = new DocAttachment();
                da.setDocid(0);
                da.setImagefileid(0);
                da.setFilename(fileName);
                da.setFilerealpath(filePath);
                da.setIszip(0);
                da.setImagefilesize(0);
                String DocSubject = "";
                if(fileName.indexOf(".")>0){
                    DocSubject = fileName.substring(0,fileName.indexOf("."));
                }else{
                    DocSubject = fileName;
                }
                doc.setId(0);
                doc.setDocSubject(DocSubject);
                doc.setDoccontent("附件");
                doc.setAttachments(new DocAttachment[]{da});
                doc.setSeccategory(Integer.parseInt(mlid));
                docid = serviceImpl.createDoc(doc, session1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return docid;
    }

    /**
     * 将输入流生成本地文件
     * @param urls
     * @param input
     * @throws IOException
     */
    private static void getWriteLocal(String urls, InputStream input) throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(urls);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        input.close();
    }
}
