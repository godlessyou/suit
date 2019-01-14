package com.yootii.bdy.util;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yootii.bdy.common.Constant;
import com.yootii.bdy.ipservice.model.PlatformService;

@Component
public class UploadUtil {
	@Value("${fileUrl}")  
	private String fileUrl;
	public static UploadUtil uploadUtil;
	@PostConstruct
    public void init() {    
		uploadUtil = this;
    } 
	// 上传用户头像
	public static Map<String, String> uploadUserIcon(HttpServletRequest request,
			String username) throws Exception {
		MultipartFile mf=upload2(request);
		Map<String, String> userIcons = saveFile(mf,username);
		return userIcons;
	}
	//上传平台服务logo
	public static PlatformService uploadServiceLogo(HttpServletRequest request, int serviceId) throws Exception {
		MultipartFile mf=upload2(request);		
		return saveServiceLogoFile(mf, serviceId);
	}
	public static MultipartFile upload2(HttpServletRequest request){
		
		// 转型为MultipartHttpRequest
		MultipartHttpServletRequest multipartRequest = null;
		try{
			multipartRequest = (MultipartHttpServletRequest) request;
		}catch(Exception e){
//			e.printStackTrace();
			return null;
		}
		List<MultipartFile> fileList = multipartRequest.getFiles("file");
		for (MultipartFile mf : fileList) {
			if (!mf.isEmpty()) {				
				return mf;
			}
		}
		return null;
	}
	
	public static Map<String, String> saveFile(MultipartFile mf, String username) throws Exception{
		
		Map<String, String> userIcons = new HashMap<String, String>();
	
		if (mf==null){
			return userIcons;
		}		
		
		String fileName = mf.getOriginalFilename();		
		
		// 临时保存图片的工作目录
		//String savePath = Constant.upload_dir+Constant.platformServiceLogo;
		//将文件保存到D:/apache-tomcat-8.5.9-1/biaodeyi
		String savePath =uploadUtil.fileUrl+"/"+"upload"+Constant.userIcon;
		savePath = savePath.replace('\\', '/');		

		FileUtil.createFolderIfNotExists(savePath);
		
		String filePath=savePath +  "/"  + fileName;				
		
		int pos=fileName.lastIndexOf(".");		
		if (pos>-1){
			fileName=fileName.substring(0,pos)+"_1"+fileName.substring(pos);
		}		
		String destFile=savePath +  "/"  + fileName;
		
		//转存文件
		mf.transferTo(new File(filePath));		
		
		ImageUtils.cutImage(filePath, destFile);
		
		//对图片进行处理
		String base64Image=ImageUtils.encodeImgageToBase64(destFile);					
		String	photo = "data:image/png;base64," + base64Image;		
		
		if (username!=null && !username.equals("")){
			userIcons.put(username, photo);
		}
		return userIcons;
	}
	/**
	 * 保存平台服务Logo
	 * @param mf
	 * @param serviceId
	 * @return
	 * @throws Exception
	 */
	public static PlatformService saveServiceLogoFile(MultipartFile mf, int serviceId) throws Exception{
		
		if (mf==null){
			return null;
		}		
		
		String fileName = mf.getOriginalFilename();		
		
		// 临时保存图片的工作目录
		//String savePath = Constant.upload_dir+Constant.platformServiceLogo;
		//将文件保存到D:/apache-tomcat-8.5.9-1/biaodeyi
		String savePath =uploadUtil.fileUrl+"/"+"upload"+Constant.platformServiceLogo;
		savePath = savePath.replace('\\', '/');		

		FileUtil.createFolderIfNotExists(savePath);
		
		fileName=serviceId+"_"+"src.jpg";//原始图片
		
		String filePath=savePath + fileName;	
		
		fileName=serviceId+".jpg";
		
		String destFile=savePath +  fileName;		
		//转存文件
		mf.transferTo(new File(filePath));		
		ImageUtils.cutImage(filePath, destFile);
		
		PlatformService platformService=new PlatformService();
		platformService.setServiceId(serviceId);
		String serviceLogo = "/"+Constant.uploadDir+Constant.platformServiceLogo+fileName;
		platformService.setServiceLogo(serviceLogo);
		return platformService;
	}
}
