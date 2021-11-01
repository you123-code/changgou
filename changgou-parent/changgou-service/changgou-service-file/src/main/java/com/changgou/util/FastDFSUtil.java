package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author youwei
 * @version 1.0
 * @date 2021/10/25 18:21
 */
public class FastDFSUtil {
    /**
     * 加载Tracker链接信息
     */

    static{
        try {
            //查找classPath下的文件路径
            String fileName = new ClassPathResource("fdfs_client.conf").getPath();
            //加载tracker链接信息
            ClientGlobal.init(fileName);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }
    /**
     * 文件上传
     * @param fastDFSFile
     * 上传的文件信息的封装
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws  Exception {
        //附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author",fastDFSFile.getAuthor());
        TrackerServer trackerServer = getTrackerServer();
        //通过TrackerServer的连接信息可以获取storage的连接信息，创建storageClient对象存储storage的连接信息
        StorageClient storageClient = getStorageClient(trackerServer);
        /**
         * 通过storageClient访问Storage，实现文件上传，并且获取文件上传后的存储信息
         * 1.上传文件的字节数组
         * 2.文件的扩展名，jpg
         * 3.附件参数，如：拍摄地址等
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        return uploads;
    }
    /**
     * 获取文件信息
     * @param groupName：文件的组名
     * @param remoteFileName：文件的存储路径名字
     */
    public static FileInfo getFile(String groupName, String remoteFileName) throws  Exception{
        TrackerServer trackerServer = getTrackerServer();
        //通过TrackerServer获取storage信息，创建storageClient存储Storage信息
        StorageClient storageClient = getStorageClient(trackerServer);
        //获取文件信息
        return storageClient.get_file_info(groupName,remoteFileName);
    }

    /**
     * 文件下载
     *   * @param groupName：文件的组名
     *      * @param remoteFileName：文件的存储路径名字
     */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception {
        TrackerServer trackerServer = getTrackerServer();
        //通过TrackerServer获取storage信息，创建storageClient存储Storage信息
        StorageClient storageClient = getStorageClient(trackerServer);
        //文件下载
        byte[] downloadFile = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(downloadFile);
    }

    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        TrackerServer trackerServer = getTrackerServer();
        //通过TrackerServer获取storage信息，创建storageClient存储Storage信息
        StorageClient storageClient = getStorageClient(trackerServer);
        //文件删除
        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 获取storage信息
     * @throws  Exception
     */
    public static StorageServer getStorages() throws Exception {
        //创建一个TrackerClient对象,通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取storage信息
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 获取storage的IP和端口信息
     * @param groupName
     * @param remoteFileName
     * @throws Exception
     * @return
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception {
        //创建一个TrackerClient对象,通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取storage的IP和端口信息
        return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
    }

    //获取Tracker的信息
    public static String getTrackerInfo() throws Exception {
        TrackerServer trackerServer = getTrackerServer();
        //tracker的IP,http端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int g_tracker_http_port = ClientGlobal.getG_tracker_http_port();
        String url = "http://"+ip+":"+g_tracker_http_port;
        return url;
    }

    /**
     * 获取TrackerServer
     * @return
     * @throws Exception
     */
    public static TrackerServer getTrackerServer() throws Exception {
        //创建一个TrackerClient对象,通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /**
     * 获取TrackerClient
     * @return
     */
    public static StorageClient getStorageClient(TrackerServer trackerServer){
        //通过TrackerServer的连接信息可以获取storage的连接信息，创建storageClient对象存储storage的连接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }
    public static void main(String[] args) throws Exception {
        //获得文件信息
        //FileInfo fileInfo = getFile("group1", "M00/00/00/wKjThGF3b_WAUfnnAACQTYsp7XA161.jpg");
        //System.out.println(fileInfo.getSourceIpAddr());
        //System.out.println(fileInfo.getFileSize());

        //下载文件
        //InputStream is = downloadFile("group1", "M00/00/00/wKjThGF3b_WAUfnnAACQTYsp7XA161.jpg");
        //将文件写入磁盘
        //FileOutputStream os = new FileOutputStream("D:/a.jpg");
        //定义一个缓冲区
        //byte[] buffer = new byte[1024];
        //  while (is.read(buffer) != -1){
        //  os.write(buffer);
        //        }
        //        os.flush();
        //        os.close();
        //        is.close();

        //删除文件
        //deleteFile("group1", "M00/00/00/wKjThGF3y7OAYKwrAAMQv8Vs1wY399.jpg");
        //StorageServer storages = getStorages();
        //System.out.println(storages.getStorePathIndex());
        //System.out.println(storages.getInetSocketAddress().getAddress());

        //获取storage的IP和端口信息
        //ServerInfo[] groups = getServerInfo("group1", "M00/00/00/wKjThGF3a9qAIi7SAACQTYsp7XA034.jpg");
        //for (ServerInfo group : groups) {
        //    System.out.println(group.getIpAddr());
        //    System.out.println(group.getPort());
        //}

        //获取tracker信息
        String trackerInfo = getTrackerInfo();
        System.out.println(trackerInfo);
    }
}
