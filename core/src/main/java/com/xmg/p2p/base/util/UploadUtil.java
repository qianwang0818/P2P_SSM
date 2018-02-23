package com.xmg.p2p.base.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传工具
 * day06_05
 */
@Slf4j
public class UploadUtil {

	/**
	 * 处理文件上传
	 * @param file
	 * @param basePath 存放文件的目录的绝对路径 servletContext.getRealPath("/upload")
	 * @return
	 */
	public static String upload(MultipartFile file, String basePath) {
		String orgFileName = file.getOriginalFilename();
		String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(orgFileName);
		try {
			File targetFile = new File(basePath, fileName);						//生成一个新的目标File用于接收文件
			FileUtils.writeByteArrayToFile(targetFile, file.getBytes());		//拷贝文件到目标File
			
			//把文件同步到公共文件夹
//			File publicFile=new File(BidConst.PUBLIC_IMG_SHARE_PATH,fileName);	//生成一个新的目标File用于接收文件
//			FileUtils.writeByteArrayToFile(publicFile, file.getBytes());		//拷贝文件到目标File
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return fileName;
	}
}
























