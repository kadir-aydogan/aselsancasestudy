package tr.com.aselsankadir.casestudy.infrastructure.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import tr.com.aselsankadir.casestudy.domain.common.IAselLogger;

import java.util.Arrays;

@Aspect
@Component
public class AselLoggingAspect {
    private final IAselLogger logger;

    public AselLoggingAspect(IAselLogger logger) {
        this.logger = logger;
    }

    @Pointcut("execution(* tr.com.aselsankadir.casestudy.application..*(..))")
    public void applicationMethods() {}

    @Around("applicationMethods()")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().toShortString();

        Object[] args = joinPoint.getArgs();

        logger.info("LOG: INFO | Servisi/Use Case başladı: {} Parametreler: {}", methodName, Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();
            logger.info("LOG: INFO | Servis/Use Case bitti: {} Return: {}", methodName, result);
            return result;
        } catch (Throwable ex) {
            logger.error("LOG: ERROR | Hata oluştu: {} Mesaj: {}", methodName, ex.getMessage(), ex);
            throw ex;
        }
    }

}
