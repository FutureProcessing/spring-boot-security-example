package com.futureprocessing.spring.infrastructure;

import com.futureprocessing.spring.infrastructure.externalwebservice.AuthenticatedExternalServiceProvider;
import com.futureprocessing.spring.infrastructure.externalwebservice.ExternalWebServiceStub;

public abstract class ServiceGatewayBase {
    private AuthenticatedExternalServiceProvider authenticatedExternalServiceProvider;

    public ServiceGatewayBase(AuthenticatedExternalServiceProvider authenticatedExternalServiceProvider) {
        this.authenticatedExternalServiceProvider = authenticatedExternalServiceProvider;
    }

    protected ExternalWebServiceStub externalService() {
        return authenticatedExternalServiceProvider.provide().getExternalWebService();
    }
}
