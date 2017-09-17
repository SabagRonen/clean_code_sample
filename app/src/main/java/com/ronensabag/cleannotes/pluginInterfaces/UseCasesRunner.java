package com.ronensabag.cleannotes.pluginInterfaces;

import com.ronensabag.cleannotes.useCases.UseCase;

/**
 * Created by ronensabag on 12/12/2016.
 */

public interface UseCasesRunner {
    public interface ResponseHandler<Response> {
        public void handleResponse(Response response);
    }

    public <Request> void runUseCase(Request request, UseCase<Request, Object> useCase,
                                     ResponseHandler<Object> responseHandler);

    void cancelUseCaseRunner();
}
