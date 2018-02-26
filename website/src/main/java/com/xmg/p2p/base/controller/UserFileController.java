package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.UploadUtil;
import com.xmg.p2p.base.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 风控资料相关控制层
 * @Author: Squalo
 * @Date: 2018/2/25 - 20:19     day07_02
 */
@Controller
@Slf4j
public class UserFileController {

    @Autowired
    private IUserFileService userFileService;

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @Autowired
    private ServletContext servletContext;  //全局的对象,不能通过方法参数方式注入

    /**打开风控照片中心*/
    @RequireLogin
    @RequestMapping("userFile")
    public String userFile(Model model, HttpServletRequest request){
        //将jsessionId放到页面便于Flash上传时能找到Session
        model.addAttribute("sessionId", request.getSession().getId());
        //列出该用户所有的未选择材料类型的认证材料
        Long applierId = UserContext.getCurrent().getId();
        List<UserFile> unTypeFiles = userFileService.listFilesByHasType(applierId,false);
        if(unTypeFiles.size()<=0){      //如果没有材料待选择类型,跳转到展示页面
            //列出该用户所有的已选择材料类型的认证材料
            model.addAttribute("userFiles",userFileService.listFilesByHasType(applierId,true));
            return "userFiles";
        }else{                          //如果有材料为待选择类型,跳转到选择类型并提交页面
            model.addAttribute("userFiles",unTypeFiles);
            model.addAttribute("fileTypes",systemDictionaryService.selectItemByParentSn("userFileType"));
            return "userFiles_commit";
        }
    }

    /**处理认证材料上传*/
    @RequireLogin
    @RequestMapping("userFile_upload")
    @ResponseBody
    public void userFileUpload(MultipartFile file){
        String basePath = servletContext.getRealPath("/upload");
        String fileName = UploadUtil.upload(file, basePath);
        //上传地址 D:/Document/JavaDocument/JavaEE_Stage3/P2P_SSM/website/target/website/upload
        fileName = "/upload/"+fileName;
        userFileService.save(fileName);
    }

    /**处理用户给风控材料选择材料类型*/
    @RequestMapping("userFile_selectType")
    @ResponseBody
    public JSONResult userFilesSelectType(Long[] ids, Long[] fileTypes){
        if(ids!=null && fileTypes!=null && ids.length==fileTypes.length){
            userFileService.batchUpdateFileType(ids,fileTypes);
        }
        return new JSONResult();
    }


}
