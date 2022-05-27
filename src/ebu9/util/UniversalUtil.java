package ebu9.util;

import com.huawei.shade.org.apache.http.client.ClientProtocolException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocServiceImpl;
import weaver.email.EmailWorkRunnable;
import weaver.file.Prop;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.hrm.resource.ResourceComInfo;
import weaver.workflow.request.WFPathUtil;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * 通用工具类
 */
public class UniversalUtil {
    static BaseBean log = new BaseBean();
    //log类
    public static String getToken() {
        try {
            log.writeLog("开始获取token..........");
            //获取接口IP地址
            String baseUrl = Prop.getPropValue("NCC", "URL");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("grant_type", "client_credentials");
            paramMap.put("client_id", "OA");
            paramMap.put("client_secret", "Nf%2FNUxnaMTT7BYtOHBQs%2BmZ%2Fvp0D1wh8nMyUaSGxfeyVy1x4P7HmBG2ELk%2FYq2Ruqlc70%2B9zYU25%0D%0Akvwa0OH3q508LDNxkcrqLHYnupWKsoiQU%2FVaOGo2kMg8EQGqIL%2FDhTsamyEcaVIJuIRViZ3h05we%0D%0A5FZUfvFFOMETk0Y%2FSnQ%3D%0D%0A");

            paramMap.put("biz_center", "G1");
            paramMap.put("dsname", "NCCJY");
            paramMap.put("signature", "e786b064ce896096141bb9f5102167534d569d324b60cf98e56924b9926a5963");

            String url = baseUrl + "/nccloud/opm/accesstoken";
            String mediaType = "application/x-www-form-urlencoded";
            String token = doPost(url, paramMap, mediaType, null, "");
            log.writeLog("获取token成功SUCCESS." + token);
            return token;
        } catch (Exception e) {
            log.writeLog("获取token失败ERROR：" + e.getMessage());
            return "";
        }

    }

    /**
     * @param baseUrl   请求路径
     * @param paramMap  参数映射
     * @param mediaType 参数类型
     * @param headers   请求头
     * @param json
     * @return
     */
    public static String doPost(String baseUrl, Map<String, String> paramMap, String mediaType, Map<String, String> headers, String json) {

        HttpURLConnection urlConnection = null;
        InputStream in = null;
        OutputStream out = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(baseUrl);
            if (paramMap != null) {
                sb.append("?");
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    sb.append(key + "=" + value).append("&");
                }
                baseUrl = sb.toString().substring(0, sb.toString().length() - 1);
            }

            URL urlObj = new URL(baseUrl);
            urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.addRequestProperty("content-type", mediaType);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    urlConnection.addRequestProperty(key, headers.get(key));
                }
            }
            out = urlConnection.getOutputStream();
            out.write(json.getBytes("utf-8"));
            out.flush();
            int resCode = urlConnection.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK || resCode == HttpURLConnection.HTTP_CREATED || resCode == HttpURLConnection.HTTP_ACCEPTED) {
                in = urlConnection.getInputStream();
            } else {
                in = urlConnection.getErrorStream();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            String ecod = urlConnection.getContentEncoding();
            if (ecod == null) {
                ecod = Charset.forName("utf-8").name();
            }
            result = new String(temp.toString().getBytes("utf-8"), ecod);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            urlConnection.disconnect();
        }
        return result;
    }


    public static String doGet(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            //通过默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            //创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            //httpGet.addHeader("Connection", "keep-alive");
            //设置请求头信息
            httpGet.addHeader("Accept", "application/json");
            //配置请求参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000) //设置连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)//设置请求超时时间
                    .setSocketTimeout(60000)//设置数据读取超时时间
                    .build();
            //为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            //执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            //通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            //通过EntityUtils中的toString方法将结果转换为字符串，后续根据需要处理对应的reponse code
            result = EntityUtils.toString(entity);
            System.out.println(result);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }
    /**
     * 解压文件
     * @param fileRealPath
     * @param IMAGEFILENAME
     * @return
     */
    public static String unzip(String fileRealPath,String IMAGEFILENAME){
        int count = -1;
        String resulturl = "";
        int index = fileRealPath.lastIndexOf(".");
        if(index>0){
            resulturl = fileRealPath.split("\\.")[0]+"."+IMAGEFILENAME.split("\\.")[1];
        }
        BufferedOutputStream bos = null;
        java.util.zip.ZipEntry entry = null;
        FileInputStream fis;
        try {
            fis = new FileInputStream(fileRealPath);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            while ((entry = zis.getNextEntry()) != null) {
                byte[] data = new byte[2048];
                File f = new File(resulturl);
                f.createNewFile();
                FileOutputStream fos = new FileOutputStream(f);
                bos = new BufferedOutputStream(fos, 2048);
                while ((count = zis.read(data, 0, 2048)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
            }
            zis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resulturl;
    }



    /**
     * http接口调用
     * @param path
     * @return
     */
    public static String commonSendPost(String path) {
        /** 网络的url地址 */
        URL url = null;
        /** 输入流 */
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(path);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {

            }
        }
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     * @return
     * @throws SocketException
     */
    public static String getCurrentIpAddress() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        }else{
            return localip;
        }
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
    /**
     * 发送邮件
     */
    public void sendMailForPwd(){
        try {
            String oaurl = Util.null2String(log.getPropValue("PassMailprop", "oaurl"));
            ResourceComInfo resourceComInfo = new ResourceComInfo();
            RecordSet rs = new RecordSet();
            RecordSet rs1 = new RecordSet();
            rs.execute("select yh,loginid,password from uf_csmmsj where lower(loginid) like '%@sumec.com.cn' order by loginid");
            while(rs.next()) {
                String hrmid =  Util.null2String(rs.getString("yh"));
                String lastname =  Util.null2String(resourceComInfo.getLastname(hrmid));
                String login = "";
                if(!"1".equals(hrmid)) {
                    login = Util.null2String(resourceComInfo.getLoginID(hrmid));
                }
                String mailtoaddress = Util.null2String(rs.getString("loginid"));
                String password = Util.null2String(rs.getString("password"));
                String mailobject = "OA协同办公系统欢迎"+lastname+"登录系统";
                String mailrequestname=lastname+",您好!   "+System.getProperty("line.separator")+"   欢迎使用OA协同办公系统!   "+System.getProperty("line.separator")+"您的用户名为:"+login+""+System.getProperty("line.separator")+"您的初始密码:"+password+"您可以使用浏览器连接至"+oaurl+""+System.getProperty("line.separator")+"登录系统。 " +
                        ""+System.getProperty("line.separator")+"备注：1、登录成功后，请根据系统提示，及时修改账户密码，完成后切换到集团办公门户，点击'完善个人履历'，及时复核并完善个人详细人事信息；2、PC端操作手册及培训视频，详见：OA系统集团办公门户-常用下载-培训资料；3、移动端APP下载与设置，详见信息系统登录手册，请点击链接查阅："+System.getProperty("line.separator")+"https://dwz.cn/Z69CchJQ"+System.getProperty("line.separator")+";4、集团和技贸的员工，请先根据每刻账户激活邮件指引激活账户。";
                SendRemindMail(mailtoaddress, mailobject, mailrequestname);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void SendRemindMail(String mailtoaddress,String mailobject,String mailrequestname){
        //将邮件提醒抛出线程处理
        //new Thread(new EmailWorkRunnable(mailtoaddress, mailobject, mailrequestname)).start();
        new WFPathUtil().getFixedThreadPool().execute(new EmailWorkRunnable(mailtoaddress, mailobject, mailrequestname));
    }
    /**
     * 去除html标签
     * @param str
     * @return
     */
    public static String stripHtml(String str) {
        str = str.replace("'", "\"");
        /** 删除普通标签  */
        str = str.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
        /** 删除转义字符 */
        str = str.replaceAll("&.{2,6}?;", "");
        return str;
    }

    /**
     * 获取类的属性名，
     * 传参为User.class
     * @param clazzT
     * @param <T>
     * @return
     */
    public static <T> List<String> getObjectAttributeNames(Class<T> clazzT) {
        List<String> list = new LinkedList<>();
        try {
            //根据类名获得其对应的Class对象 写上你想要的类名就是了 注意是全名 如果有包的话要加上 比如java.Lang.String
            Class clazz = Class.forName(clazzT.getName());
            //根据Class对象获得属性 私有的也可以获得
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                list.add(f.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }




}

