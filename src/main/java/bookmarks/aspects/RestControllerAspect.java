package bookmarks.aspects;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerAspect {

	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void controller() {
	}

	@Pointcut("execution(* *.*.*(..))")
	protected void allMethod() {
	}
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *)")
	public void requestMapping() {
	}
	
	
	@AfterReturning("controller() && allMethod() && requestMapping()")
	public void afterControllerMethod(JoinPoint joinPoint) {
		        System.out.println("Finished: " + niceName(joinPoint));
		    }


	@Before("controller() && allMethod() && requestMapping()")
	public void aroundControllerMethod(JoinPoint joinPoint) throws Throwable {
	        System.out.println("Invoked: " + niceName(joinPoint));
		    }


	private String niceName(JoinPoint joinPoint) {
		        return joinPoint.getTarget().getClass()
		                + "#" + joinPoint.getSignature().getName()
               + "\targs:" + Arrays.toString(joinPoint.getArgs());

		    }



	}