package com.fuzhongwangcs.ssmsimple.core.upload;

import com.fuzhongwangcs.ssmsimple.core.util.CustomDateSerializer;
import com.fuzhongwangcs.ssmsimple.core.util.ImageUtils;
import com.fuzhongwangcs.ssmsimple.core.util.SystemPropertiesUtil;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 文件上传公共类!
 */
public class FileCommon {
    /**
     * 创建文件夹
     *
     * @param dir
     */
    private static void doDir(String dir) {
        File file = new File(dir);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 文件上传，支持单个和多个
     *
     * @param request
     * @param format  格式规定，例如：gif,jpg,jpeg,png,bmp
     * @return
     */
    public static List<FileEntity> doSuperUpFile(HttpServletRequest request, String format) {
        List<FileEntity> resultList = new ArrayList<FileEntity>();
        // TODO 本地测试
        // String rootPath = request.getServletContext().getRealPath("/");
        String rootPath = SystemPropertiesUtil.getProperty("root.file.path");
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("format", format);
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            MultiValueMap<String, MultipartFile> iter = multiRequest.getMultiFileMap();
            for (Map.Entry<String, List<MultipartFile>> entry : iter.entrySet()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(entry.getKey());
                if (format != null) {
                    if (!Arrays.<String>asList(extMap.get("format").split(",")).contains(
                            file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1))) {
                        FileEntity fileEntity = new FileEntity();
                        fileEntity.setStatus(0);
                        fileEntity.setMessage("上传文件扩展名只允许" + extMap.get("format") + "格式");
                        resultList.add(fileEntity);
                        return resultList;
                    }
                }
                String fileName = FileCommon.doUpFile(rootPath, file);
                if (!"".equals(fileName)) {
                    FileEntity fileEntity = new FileEntity();
                    fileEntity.setStatus(1);
                    fileEntity.setMessage("上传成功");
                    fileEntity.setPath(fileName);
                    resultList.add(fileEntity);
                }
            }
        }
        return resultList;
    }

    /**
     * 保存图片
     *
     * @param oldImg 原图路径
     * @param top    选择框的左边y坐标
     * @param left   选择框的左边x坐标
     * @param width  选择框宽度
     * @param height 选择框高度
     * @return
     * @throws IOException
     */
    public static FileEntity saveImage(String oldImg, int top, int left, int width, int height) throws IOException {
        String rootFilePath = SystemPropertiesUtil.getProperty("root.file.path");
        String newImg = oldImg.substring(0, oldImg.lastIndexOf("."));
        String newImgSuf = ImageUtils.getExtension(oldImg).toLowerCase();
        String newImgPathNotRoot = newImg + "_cut." + newImgSuf;
        String newImgPath = rootFilePath + newImgPathNotRoot;
        String oldImgPath = rootFilePath + oldImg;
        File fileDest = new File(newImgPath);
        if (!fileDest.getParentFile().exists())
            fileDest.getParentFile().mkdirs();
        String ext = ImageUtils.getExtension(newImgPath).toLowerCase();
        BufferedImage bi = (BufferedImage) ImageIO.read(new File(oldImgPath));
        height = Math.min(height, bi.getHeight());
        width = Math.min(width, bi.getWidth());
        if (height <= 0)
            height = bi.getHeight();
        if (width <= 0)
            width = bi.getWidth();
        top = Math.min(Math.max(0, top), bi.getHeight() - height);
        left = Math.min(Math.max(0, left), bi.getWidth() - width);
        BufferedImage bi_cropper = bi.getSubimage(left, top, width, height);
        boolean flag = ImageIO.write(bi_cropper, ext, fileDest);
        if (flag) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setPath(newImgPathNotRoot);
            fileEntity.setMessage("上传成功");
            fileEntity.setStatus(1);
            return fileEntity;
        } else {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setMessage("上传失败");
            fileEntity.setStatus(0);
            return fileEntity;
        }
    }

    /**
     * 文件上传规则
     *
     * @param rootPath
     * @param file
     * @return
     */
    private static String doUpFile(String rootPath, MultipartFile file) {
        if (file != null) {
            // 取得当前上传文件的文件名称
            String myFileName = file.getOriginalFilename();
            // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
            if (myFileName.trim() != "") {
                // 该项目的文件路径
                String filePath = SystemPropertiesUtil.getProperty("file.path");
                // 文件的相对路径
                String relativeFilePath = filePath + "/" + CustomDateSerializer.getCurDateFormat("yyyyMMdd");
                // 文件的绝对路径
                String absoluteFilePath = rootPath + relativeFilePath;
                // 创建文件夹
                doDir(absoluteFilePath);
                // 重命名文件名
                String fileName = System.currentTimeMillis() + "." + ImageUtils.getExtension(myFileName);
                // 上传文件绝对路径
                String path = absoluteFilePath + "/" + fileName;
                File localFile = new File(path);
                try {
                    file.transferTo(localFile);
                    return relativeFilePath + "/" + fileName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
