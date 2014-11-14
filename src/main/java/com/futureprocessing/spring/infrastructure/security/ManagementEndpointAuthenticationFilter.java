package com.futureprocessing.spring.infrastructure.security;

import com.futureprocessing.spring.api.ApiController;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ManagementEndpointAuthenticationFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(ManagementEndpointAuthenticationFilter.class);
    private AuthenticationManager authenticationManager;
    private Set<String> managementEndpoints;

    public ManagementEndpointAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        prepareManagementEndpointsSet();
    }

    private void prepareManagementEndpointsSet() {
        managementEndpoints = new HashSet<>();
        managementEndpoints.add(ApiController.AUTOCONFIG_ENDPOINT);
        managementEndpoints.add(ApiController.BEANS_ENDPOINT);
        managementEndpoints.add(ApiController.CONFIGPROPS_ENDPOINT);
        managementEndpoints.add(ApiController.ENV_ENDPOINT);
        managementEndpoints.add(ApiController.MAPPINGS_ENDPOINT);
        managementEndpoints.add(ApiController.METRICS_ENDPOINT);
        managementEndpoints.add(ApiController.SHUTDOWN_ENDPOINT);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);

        Optional<String> username = Optional.fromNullable(httpRequest.getHeader("X-Auth-Username"));
        Optional<String> password = Optional.fromNullable(httpRequest.getHeader("X-Auth-Password"));

        String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);

        try {
            if (postToManagementEndpoints(resourcePath)) {
                logger.debug("Trying to authenticate user {} for management endpoint by X-Auth-Username method", username);
                processManagementEndpointUsernamePasswordAuthentication(username, password);
            }

            logger.debug("ManagementEndpointAuthenticationFilter is passing request down the filter chain");
            chain.doFilter(request, response);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        }
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    private boolean postToManagementEndpoints(String resourcePath) {
        return managementEndpoints.contains(resourcePath);
    }

    private void processManagementEndpointUsernamePasswordAuthentication(Optional<String> username, Optional<String> password) throws IOException {
        Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username, password);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryToAuthenticateWithUsernameAndPassword(Optional<String> username, Optional<String> password) {
        BackendAdminUsernamePasswordAuthenticationToken requestAuthentication = new BackendAdminUsernamePasswordAuthenticationToken(username, password);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Backend Admin for provided credentials");
        }
        logger.debug("Backend Admin successfully authenticated");
        return responseAuthentication;
    }
}
