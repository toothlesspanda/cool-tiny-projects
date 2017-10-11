package presentation.rest.security;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Priority;
import javax.naming.InitialContext;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import facade.handlers.IAppTokens;

@Priority(Priorities.AUTHORIZATION)
class RolesAllowedRequestFilter implements ContainerRequestFilter {

	private final boolean denyAll;
	private final boolean permitAll;
	private final List<String> rolesAllowed;

	public RolesAllowedRequestFilter() {
		denyAll = false;
		permitAll = false;
		rolesAllowed = null;
	}

	RolesAllowedRequestFilter(boolean denyAll) {
		this.denyAll = denyAll;
		this.permitAll = !denyAll;
		this.rolesAllowed = null;
	}

	RolesAllowedRequestFilter(String[] rolesAllowed) {
		this.denyAll = false;
		this.permitAll = false;
		this.rolesAllowed = (rolesAllowed != null) ? 
				Arrays.asList(rolesAllowed) : new ArrayList<String>();
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Acknowledge any pre-flight test from browsers before validating the headers (CORS stuff)
		if ("OPTIONS".equals(requestContext.getRequest().getMethod())) {
			requestContext.abortWith( Response.status(Response.Status.OK).build() );
			return;
		}

		// needs to verify the token
		if (!denyAll) { 
			try {
				IAppTokens appTokens = InitialContext.doLookup("java:app/sale-sys-business/Tokens!facade.handlers.IAppTokens");
				String token = requestContext.getHeaderString("Authorization").replaceFirst("Bearer ", "");
				Map<String, Object> payload = appTokens.validateAndDecodeLoginToken(token);
				String userRole = (String) payload.get("rol");
				if (permitAll || rolesAllowed.contains(userRole)) {
					requestContext.setSecurityContext(new SecurityContextImpl((String) payload.get("usr"), userRole));
				} else {
					System.out.println("###############################################################");
					requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
				}
				return;
			} catch(Exception e) { 
			}
		}
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
	}

	private static class SecurityContextImpl implements SecurityContext {

		private Principal principal;
		private String role;

		public SecurityContextImpl(final String user, String role) {
			this.principal = new Principal() {
				@Override
				public String getName() {
					return user;
				}
			};
			this.role = role;
		}

		@Override
		public Principal getUserPrincipal() {
			return principal;
		}

		@Override
		public boolean isUserInRole(String role) {
			return role.equals(this.role);
		}

		@Override
		public boolean isSecure() {
			return false;
		}

		@Override
		public String getAuthenticationScheme() {
			return "JWT";
		}
	}
}