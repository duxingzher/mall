/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.macro.mall.controller;

import com.macro.mall.util.OSInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.macro.mall.util.OSInfoUtils.rootPathDir;

@Controller
@RequestMapping(value = "/filemanager")
public class FileManagerController {

    private static final Logger logger = LoggerFactory.getLogger(FileManagerController.class);


    /**
     * 上传文件
     *
     * @param response
     * @param file     上传的文件，支持多文件
     * @throws Exception
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, Object> insert(@RequestParam("dir") String dir, HttpServletResponse response
            , @RequestParam("file") MultipartFile[] file,@RequestParam("key") String uuidName) throws Exception {
        if (file != null && file.length > 0) {
            try {
                for (int i = 0; i < file.length; i++) {
                    if (!file[i].isEmpty()) {
                        uploadFile(dir, file[i], uuidName);
                    }
                }

            } catch (Exception e) {
                logger.error("上传出现异常！e:{}", e);
            }
        }
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "success");
        return result;
    }


    private void uploadFile(String pathDeposit, MultipartFile file, String uuidName) {
        if (file == null) {
            logger.error("上传失败！");
            throw new RuntimeException("上传失败！");
        }

        try {

            String origName = file.getOriginalFilename();// 文件原名称
            logger.info("上传的文件原名称:" + origName);

            // 判断文件类型
            String type = origName.indexOf(".") != -1 ? origName.substring(origName.lastIndexOf(".") + 1, origName.length()) : null;

            String newName = uuidName + "." + type;

            String parentPath = OSInfoUtils.rootPathDir + pathDeposit;

            File parentFileDir = new File(pathDeposit);

            //创建目录
            if (!parentFileDir.exists()) {
                parentFileDir.mkdirs();
            }
            File targetFile = new File(pathDeposit, newName);

            //上传
            file.transferTo(targetFile);
            //完整路径
            logger.info("图片上传成功:{}", parentPath);
        } catch (Exception e) {
            logger.error("上传失败！{}", e);
        }

    }


}