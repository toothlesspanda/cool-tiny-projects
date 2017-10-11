package presentation.rest.security;

import java.lang.reflect.Method;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityFilterDynamicFeature implements DynamicFeature {

    @Override
    public void configure(final ResourceInfo resourceInfo, final FeatureContext configuration) {
        Method am = resourceInfo.getResourceMethod();
        // DenyAll on the method take precedence over RolesAllowed and PermitAll
        if (am.isAnnotationPresent(DenyAll.class)) {              
            configuration.register(new RolesAllowedRequestFilter(true));
        } else {                                                    
            // RolesAllowed on the method takes precedence over PermitAll
            RolesAllowed ra = am.getAnnotation(RolesAllowed.class);
            if (ra != null) {
                configuration.register(new RolesAllowedRequestFilter(ra.value()));
                // PermitAll takes precedence over RolesAllowed on the class
            } else if (am.isAnnotationPresent(PermitAll.class)) {     

                configuration.register(new RolesAllowedRequestFilter(false));
            } else {      
                // RolesAllowed on the class takes precedence over PermitAll
                ra = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
                if (ra != null) {
                    configuration.register(new RolesAllowedRequestFilter(ra.value()));
                }
            }
        }
    }
}