package ge.bog.hw22.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ge.bog.hw22.modules.User;
import ge.bog.hw22.modules.Task;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* ge.bog.hw22.services.*.*(..))")
    private void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        logger.info("Entering method: {}", joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        switch (methodName) {
            case "userCreatorService":
                if (args.length > 0 && args[0] instanceof User) {
                    User user = (User) args[0];
                    logger.info("Creating user with details: Name - {}, LastName - {}", user.getName(), user.getLastName());
                }
                break;
            case "userUpdateService":
                if (args.length > 1 && args[1] instanceof User) {
                    Long userId = (Long) args[0];
                    User user = (User) args[1];
                    logger.info("Updating user with id: {} and new details: Name - {}, LastName - {}", userId, user.getName(), user.getLastName());
                }
                break;
            case "deleteUser":
                if (args.length > 0 && args[0] instanceof Long) {
                    Long userId = (Long) args[0];
                    logger.info("Deleting user with id: {}", userId);
                }
                break;
            case "createTask":
                if (args.length > 1 && args[0] instanceof Long && args[1] instanceof Task) {
                    Long userId = (Long) args[0];
                    Task task = (Task) args[1];
                    logger.info("Creating task for user with id: {}. Task details: Name - {}, Description - {}", userId, task.getName(), task.getDescription());
                }
                break;
        }
    }

    @After("serviceMethods()")
    public void logAfterServiceMethods(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        switch (methodName) {
            case "userCreatorService":
                if (args.length > 0 && args[0] instanceof User) {
                    User user = (User) args[0];
                    logger.info("User creation completed for user: Name - {}, LastName - {}", user.getName(), user.getLastName());
                }
                break;
            case "userUpdateService":
                if (args.length > 1 && args[1] instanceof User) {
                    Long userId = (Long) args[0];
                    User user = (User) args[1];
                    logger.info("User update completed for user with id: {}. New details: Name - {}, LastName - {}", userId, user.getName(), user.getLastName());
                }
                break;
            case "deleteUser":
                if (args.length > 0 && args[0] instanceof Long) {
                    Long userId = (Long) args[0];
                    logger.info("User deletion completed for user with id: {}", userId);
                }
                break;
            case "createTask":
                if (args.length > 1 && args[0] instanceof Long && args[1] instanceof Task) {
                    Long userId = (Long) args[0];
                    Task task = (Task) args[1];
                    logger.info("Task creation completed for user with id: {}. Task details: Name - {}, Description - {}", userId, task.getName(), task.getDescription());
                }
                break;
        }
    }
}
