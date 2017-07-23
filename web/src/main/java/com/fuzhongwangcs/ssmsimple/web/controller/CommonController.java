package com.fuzhongwangcs.ssmsimple.web.controller;

import com.fuzhongwangcs.ssmsimple.core.upload.FileCommon;
import com.fuzhongwangcs.ssmsimple.core.upload.FileEntity;
import com.fuzhongwangcs.ssmsimple.core.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: lazyeclipse
 * @Description: 公共视图控制器
 * @Date: 2017/5/31 11:54
 */
@Controller
public class CommonController {
    /**
     * 首页
     *
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request) {
        return "login";
    }

    @RequestMapping(value = "/ajaxFileUpload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ajaxFileUpload(HttpServletRequest request) {
        List<FileEntity> resultList = FileCommon.doSuperUpFile(request, "png");
        String resultJson = JsonUtils.toJsonString(resultList);
        return resultJson;
    }
}