package bookmarks.aspects;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ReFormatAspect {

	
	 @SuppressWarnings("resource")
	@AfterReturning(pointcut = "@annotation(bookmarks.aspects.ReFormat)", returning = "response")
	    public void formatter(JoinPoint joinPoint, Object response) {
		 
			      try {
						PrintWriter logs = new PrintWriter(new BufferedWriter(new FileWriter("logs.txt",true)));
						
					      logs.println("[@AfterReturning] " + ((String) response).toUpperCase()+ " " + new Date());
					     System.out.println("[@AfterReturning] " + ((String) response).toUpperCase()+ " " + new Date());
					     
					     logs.close();
					     
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.err.println("Nie znaleziono pliku");
						e.printStackTrace();
					}
		 
		 

	}

		
}  

