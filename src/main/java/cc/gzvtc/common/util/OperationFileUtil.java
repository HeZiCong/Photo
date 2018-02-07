package cc.gzvtc.common.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @Description 操作文件工具类
 * @author LJ
 * @Date 2016年6月8日 上午11:42:44
 * @Version v1.0
 */
public final class OperationFileUtil {
    private static final String ENCODING = "utf-8";

    /**
     * 文件下载
     * 
     * @param filePath
     *            文件路径
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static ResponseEntity<byte[]> download(String filePath) throws UnsupportedEncodingException, IOException {
        String fileName = FilenameUtils.getName(filePath);
        return downloadAssist(filePath, fileName);
    }

    /**
     * 文件下载
     * 
     * @param filePath
     *            文件路径
     * @param fileName
     *            文件名
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static ResponseEntity<byte[]> download(String filePath, String fileName) throws UnsupportedEncodingException, IOException {
        return downloadAssist(filePath, fileName);
    }

    /**
     * 文件下载辅助
     * 
     * @param filePath
     *            文件路径
     * @param fileName
     *            文件名
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private static ResponseEntity<byte[]> downloadAssist(String filePath, String fileName) throws UnsupportedEncodingException, IOException {
        File file = new File(filePath);
        if (!file.isFile() || !file.exists()) {
            throw new IllegalArgumentException("filePath 参数必须是真实存在的文件路径:" + filePath);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, ENCODING));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    /**
     * 多文件上传
     * 
     * @param request
     *            当前上传的请求
     * @param basePath
     *            保存文件的路径
     * @throws IOException
     * @throws IllegalStateException
     * @return Map<String, String> 返回上传文件的保存路径 以文件名做map的key;文件保存路径作为map的value
     */
    public static Map<String, String> multiFileUpload(HttpServletRequest request, String basePath) throws IllegalStateException, IOException {
        if (!(new File(basePath).isDirectory())) {
            throw new IllegalArgumentException("basePath 参数必须是文件夹路径"+basePath);
        }
        return multifileUploadAssist(request, basePath, null);
    }

    /**
     * 多文件上传
     * 
     * @param request
     *            当前上传的请求
     * @param basePath
     *            保存文件的路径
     * @param exclude
     *            排除文件名字符串,以逗号分隔的,默认无可传null
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static Map<String, String> multiFileUpload(HttpServletRequest request, String basePath, String exclude) throws IllegalStateException, IOException {
        if (!(new File(basePath).isDirectory())) {
            throw new IllegalArgumentException("basePath 参数必须是文件夹路径"+basePath);
        }

        return multifileUploadAssist(request, basePath, exclude);
    }

    /**
     * 多文件上传辅助
     * 
     * @param request
     *            当前上传的请求
     * @param basePath
     *            保存文件的路径
     * @param exclude
     *            排除文件名字符串,以逗号分隔的,默认无可传null
     * @return
     * @throws IOException
     */
    private static Map<String, String> multifileUploadAssist(HttpServletRequest request, String basePath, String exclude) throws IOException {
        exclude = exclude == null ? "" : exclude;

        Map<String, String> filePaths = new HashMap<String, String>();
        File file = null;
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // get the parameter names of the MULTIPART files contained in this request
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                List<MultipartFile> multipartFiles = multiRequest.getFiles(iter.next());
                for (MultipartFile multipartFile : multipartFiles) {
                    String fileName = multipartFile.getOriginalFilename();
                    if (StringUtils.isNotEmpty(fileName) && (!exclude.contains(fileName))) {
                        file = new File(basePath + changeFilename2UUID(fileName));
                        filePaths.put(fileName, file.getPath());
                        multipartFile.transferTo(file);
                    }
                }
            }
        }
        return filePaths;
    }

    /**
     * 将文件名转变为UUID命名的 ,保留文件后缀
     * 
     * @param filename
     * @return
     */
    public static String changeFilename2UUID(String filename) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + FilenameUtils.getExtension(filename);
    }

    /**
     * 删除文件
     * 
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}