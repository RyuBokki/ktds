package com.ktds.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoParamAop {
	
	private Logger logger = LoggerFactory.getLogger(DaoParamAop.class);
	
	// 먼저 설정 필요
	// applicationContext가서 aop추가
	// nameSpace에서 aop추가 후 source로 넘어감
	// bean을 만들어주어야함.
	
	
	// 어떤 것을 return할지 모르기 때문에
	// Around일때는 ProceedingJoinPoint가 필요함
	
	// JoinPoint는 클래스의 정보만 주는 것
	// before에 사용
	public Object getParam(ProceedingJoinPoint pjp) {
		
		String classAndMethod = pjp.getSignature().toShortString();
		
		logger.debug(classAndMethod);
		
		// before
		// parameter 가져오기
		Object[] paramArray = pjp.getArgs();
		
		for ( Object param : paramArray ) {
			
			logger.debug(classAndMethod + " = " + param.toString());
		}
				
		//기존의 메소드 실행
		Object result = null;
		
		try {
			//before
			logger.debug("Before");
			result = pjp.proceed();
			// after-returning
			logger.debug(classAndMethod + " = Result : " + result.toString());
		}
		
		catch(Throwable e) {
			// after-throwing
			
			// getCause는 throwable이 발생한 이유
			logger.debug(classAndMethod + " = Exception : " + e.getCause() + ", " + e.getMessage());
			// 위에 까지 쓰면 우리가 만든 global 이 무용지물
			// dao단계에서 오류를 잡으므로
			
			// 따라서 예외를 runtimeException에게 던져주어야함.
			throw new RuntimeException(e.getMessage(), e);	
		}
		return result;
	}
}
