package com.yootii.bdy.aop;

import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.translation.service.TranslationService;

@Aspect
public class AopTranslation {
	
	@Resource
	protected TranslationService translationService;	
	
	
	AopTranslation(){
		
	}

	

	@Around("execution(* com..*.*Controller.*(..))")
	public Object process(ProceedingJoinPoint point) throws Throwable {
		Boolean internal = false;
		try {
         
		HttpServletRequest request =null;
        for(Object a:point.getArgs()) {
        	if(a instanceof HttpServletRequest) {
        		request = (HttpServletRequest) a;
        		break;
        	}
        }
        
        if(request!=null) {
        	if(request.getParameter("internal")!=null)
        	internal = request.getParameter("internal").equals("yes");
        	
        }
        
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
        Object returnValue = point.proceed();
        if(returnValue == null) return returnValue;
//        System.out.println("内部接口："+internal.toString());
//        System.out.println("输出语音为："+com.yootii.bdy.common.Globals.getLanguage());
//        System.out.println("文件类型为："+returnValue.getClass().getName());
        if(internal) return returnValue;
        return translationService.translationObject((ReturnInfo)returnValue, "cn", com.yootii.bdy.common.Globals.getLanguage());
    }
}
