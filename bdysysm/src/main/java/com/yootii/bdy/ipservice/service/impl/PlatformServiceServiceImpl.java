package com.yootii.bdy.ipservice.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yootii.bdy.common.Constant;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.ipservice.dao.PlatformServiceMapper;
import com.yootii.bdy.ipservice.model.PlatformService;
import com.yootii.bdy.ipservice.service.PlatformServiceService;
import com.yootii.bdy.util.UploadUtil;

@Service
public class PlatformServiceServiceImpl implements PlatformServiceService{
	@Resource
	private PlatformServiceMapper platformServiceMapper;
	@Value("${fileUrl}")  
	private String fileUrl;
	
	/**
	 * 创建一条服务时，要先创建其  父节点  
	 */
	@Override
	public ReturnInfo createPlatformService(HttpServletRequest request,PlatformService platformService) {
		ReturnInfo info = new ReturnInfo();
		if(platformService.getServiceName()==null){
			info.setSuccess(false);
			info.setMessage("请填写服务名称");
			return info;
		}
		if(platformService.getCaseType()==null){
			info.setSuccess(false);
			info.setMessage("请填写案件类型");
			return info;
		}
		if(platformService.getParent()==null){
			platformService.setParent(0);
		}
		//插入父节点
		int id = platformServiceMapper.insertSelective(platformService);
		Integer serviceId = platformService.getServiceId();
		//插入子服务
		platformService.setParent(serviceId);
		
		platformServiceMapper.insert(platformService);
		
		PlatformService ps = null;
		try {
			ps = uploadLogo(request,serviceId);
		} catch (Exception e) {
			e.printStackTrace();
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage("logo上传异常：" + e.getMessage());
			return info;
		}
		if(ps!=null){
			String logo = ps.getServiceLogo();
			if(logo==null||"".equals(logo)){
				info.setSuccess(false);
				info.setMessageType(-4);
				info.setMessage("图片文件上传失败");
				return info;
			}
			platformServiceMapper.updateByPrimaryKeySelective(ps);
		}else{
			//没有上传图片
		}
		info.setSuccess(true);
		info.setMessage("创建成功");
		return info;
	}
	
	
	
	@Override
	public ReturnInfo deletePlatformService(PlatformService platformService) {
		ReturnInfo info = new ReturnInfo();
		Integer serviceId = platformService.getServiceId();
		if(serviceId==null){
			info.setSuccess(false);
			info.setMessage("服务ID不能为空");
			return info;
		}
		platformServiceMapper.deleteByPrimaryKey(serviceId);
		info.setSuccess(true);
		info.setMessage("删除成功");
		return info;
	}

	@Override
	public ReturnInfo modifyPlatformService(HttpServletRequest request,PlatformService platformService) {
		ReturnInfo info = new ReturnInfo();
		Integer serviceId = platformService.getServiceId();
		if(serviceId==null){
			info.setSuccess(false);
			info.setMessage("服务ID不能为空");
			return info;
		}
		PlatformService ps = null;
		try {
			ps = uploadLogo(request, serviceId);
		} catch (Exception e) {
			e.printStackTrace();
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage("logo上传异常：" + e.getMessage());
			return info;
		}
		if(ps!=null){
			String logo = ps.getServiceLogo();
			if(logo==null||"".equals(logo)){
				info.setSuccess(false);
				info.setMessageType(-4);
				info.setMessage("图片文件上传失败");
				return info;
			}
			platformService.setServiceLogo(logo);
		}
		platformServiceMapper.updateByPrimaryKeySelective(platformService);
		info.setSuccess(true);
		info.setMessage("修改成功");
		return info;
	}

	@Override
	public ReturnInfo queryPlatformServiceList(PlatformService platformService,
			GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		List<PlatformService> platformServices =platformServiceMapper.selectByPlatformService(platformService, gcon);
		Long count = platformServiceMapper.selectByPlatformServiceCount(platformService, gcon);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setData(platformServices);
		info.setTotal(count);
		info.setMessage("查询成功");
		return info;
	}

	@Override
	public ReturnInfo queryPlatformServiceDetail(PlatformService platformService) {
		ReturnInfo info = new ReturnInfo();
		Integer serviceId = platformService.getServiceId();
		if(serviceId==null){
			info.setSuccess(false);
			info.setMessage("服务ID不能为空");
			return info;
		}
		platformService = platformServiceMapper.selectByPrimaryKey(serviceId);
		info.setSuccess(true);
		info.setData(platformService);
		info.setMessage("查询成功");
		return info;
	}

	@Override
	public ReturnInfo queryPlatformServices() {
		ReturnInfo info = new ReturnInfo();
		//查询1级平台服务
		Integer parent = Integer.valueOf(0);
		List<PlatformService> platformServices = platformServiceMapper.queryPlatformService(parent);
		for(PlatformService platformService : platformServices){
			parent = platformService.getServiceId();
			List<PlatformService> platformServices2 = platformServiceMapper.queryPlatformService(parent); //0
			platformService.setData(platformServices2);
		}
		info.setData(platformServices);
		info.setSuccess(true);
		info.setMessage("平台服务查询成功");
		return info;
	}

	@Override
	public PlatformService uploadLogo(HttpServletRequest request,
			Integer serviceId) throws Exception {
		PlatformService platformService =null;
		platformService = UploadUtil.uploadServiceLogo(request,serviceId);
		return platformService;
	}



	@Override
	public ReturnInfo upLoad(HttpServletRequest request, Integer serviceId) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		PlatformService platformService = new PlatformService();
		
		//String filePath = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFiles("file").get(0);
		String filePath = null;
		if(file.getSize() !=0){
			platformService = UploadUtil.saveServiceLogoFile(file, serviceId);
			/*filePath = uploadFile(platformService,file,returnInfo);
			platformService.setServiceLogo(filePath);
			if(returnInfo.getSuccess()){
				//更新
				platformService.setServiceId(serviceId);*/
				platformServiceMapper.upLoad(platformService);
		//	}
			//再次查询
				PlatformService platformService2 = platformServiceMapper.selectById(serviceId);
				returnInfo.setData(platformService2);
		}
		return returnInfo;
	}
	
	private String uploadFile(PlatformService platformService,MultipartFile uploadFile,ReturnInfo rInfo){
		
		String filePath = null;
		if(uploadFile !=null){
			try{
				String fileName = uploadFile.getOriginalFilename();
				String savePath = fileUrl+Constant.platformServiceLogo;
				filePath = Constant.platformServiceLogo;
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdirs();
					uploadFile.transferTo(new File(savePath+fileName));
					rInfo.setSuccess(true);
					rInfo.setMessage("文件上传成功");
				}
			}catch (Exception e) {
				e.printStackTrace();
				rInfo.setSuccess(false);
				rInfo.setMessage("文件上传失败,失败原因："+e.getMessage());
			}
		}
		return filePath;
	}
	
}