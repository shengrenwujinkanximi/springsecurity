package logwire.web.service;

import java.util.Iterator;
import java.util.List;

import logwire.core.meta.Tenant;
import logwire.core.tenant.TenantManager;
import logwire.web.security.SecurityUtil;
import logwire.web.security.TenantUser;
import logwire.web.service.ActionContext;
import logwire.web.service.ActionContextBuilder;
import logwire.web.tenant.TenantContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class OPPOActionContextUtil {
    public static ActionContext getActionContextUtil(String userXid) {
        List<Tenant> tenants = OPPOContextUtils.getBean(TenantManager.class).getTenants();
        Iterator it = tenants.iterator();
        ActionContext $ = null;
        while (it.hasNext()) {
            Tenant tenant = (Tenant) it.next();
            TenantContextHolder.resetTenant();
            TenantContextHolder.setTenant(tenant.getId());
            SecurityUtil.setAdminUserAsCurrentUser();
            UserDetails userDetails = OPPOContextUtils.getBean(UserDetailsService.class).loadUserByUsername(userXid);
            SecurityUtil.setCurrentUser((TenantUser) userDetails);
            $ = OPPOContextUtils.getBean(ActionContextBuilder.class).build();
        }
        return $;
    }
}
