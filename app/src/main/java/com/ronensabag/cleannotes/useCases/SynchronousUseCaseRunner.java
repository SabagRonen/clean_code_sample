package com.ronensabag.cleannotes.useCases;

import com.ronensabag.cleannotes.pluginInterfaces.UseCasesRunner;

public class SynchronousUseCaseRunner implements UseCasesRunner {

    @Override
    public void cancelUseCaseRunner() {
    }

    @Override
    public <Request> void runUseCase(Request request, UseCase<Request, Object> useCase,
                                     UseCasesRunner.ResponseHandler<Object> responseHandler) {
        responseHandler.handleResponse(useCase.handleRequest(request));
    }
}
