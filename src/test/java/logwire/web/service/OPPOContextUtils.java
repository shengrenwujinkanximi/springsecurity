package logwire.web.service;

import java.io.PrintStream;

import logwire.core.exceptions.ApplicationException;
import logwire.core.tenant.TenantProject;
import logwire.web.mobile.*;
import logwire.web.security.SecurityUtil;
import logwire.web.security.TenantUser;
import logwire.web.security.jwt.JwtUtil;
import logwire.web.security.jwt.LogwireUsernamePasswordAuthenticationToken;
import logwire.web.service.event.EventService;
import logwire.web.service.query.QueryService;
import logwire.web.service.report.ReportService;
import logwire.web.service.role.RoleService;
import logwire.web.service.task.TaskService;
import logwire.web.tenant.TenantProjectService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class OPPOContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        OPPOContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    private static void assertApplicationContext() {
        if (OPPOContextUtils.applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }

    private static TenantProject getCurrentProject() {
        return applicationContext.getBean(TenantProjectService.class).getCurrentProject();
    }

    private static EventService getEventService() {
        return applicationContext.getBean(EventService.class);
    }

    private static QueryService getQueryService() {
        return applicationContext.getBean(QueryService.class);
    }

    private static RoleService getRoleService() {
        return applicationContext.getBean(RoleService.class);
    }

    private static TaskService getTaskService() {
        return applicationContext.getBean(TaskService.class);
    }

    private static ReportService getReportService() {
        return applicationContext.getBean(ReportService.class);
    }

    public static ActionContext build(String userId) {
        UserDetailsService userDetailsService = applicationContext.getBean(UserDetailsService.class);
        JwtUtil jwtUtil = applicationContext.getBean(JwtUtil.class);
        TenantUser user = (TenantUser)userDetailsService.loadUserByUsername(userId);

        try {
            SecurityUtil.setCurrentUser(user);
            jwtUtil.refreshUserExt(user);
        } finally {
            SecurityUtil.resetCurrentUser();
        }

        return build(user);
    }

    public static ActionContext build() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof TenantUser) {
            return build((TenantUser)principal);
        } else {
            throw new ApplicationException("Accessed by anonymous, please login");
        }
    }

    public static ActionContext build(TenantUser user) {
        return new ActionContext(applicationContext, user);
    }
}