package com.fuzhongwangcs.ssmsimple.core.upload;

/**
 * Created by lazyeclipse on 2017/6/1.
 * 文件上传返回信息实体
 */
public class FileEntity {
    // 文件上传状态 (1:成功, 0:失败)
    private int status;
    // 文件上传地址
    private String path;
    // 上传状态消息
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
