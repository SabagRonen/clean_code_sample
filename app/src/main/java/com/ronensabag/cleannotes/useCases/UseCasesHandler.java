package com.ronensabag.cleannotes.useCases;

import com.ronensabag.cleannotes.pluginInterfaces.UseCasesRunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ronensabag on 12/12/2016.
 */

public class UseCasesHandler {

    private Map<Class, UseCase> mUseCaseMap;

    public UseCasesHandler() {
        mUseCaseMap = new ConcurrentHashMap<>();
    }

    public <Request> void handleUseCaseRequest(Request request,
                                               Class<? extends UseCase> useCaseClass,
                                               UseCasesRunner.ResponseHandler responseHandler,
                                               UseCasesRunner useCasesRunner) {
        UseCase useCase = mUseCaseMap.get(request.getClass().getEnclosingClass());
        if (null == useCase) {
            try {
                useCase = useCaseClass.newInstance();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            mUseCaseMap.put(request.getClass().getEnclosingClass(), useCase);
        }
        useCasesRunner.runUseCase(request, useCase, responseHandler);
    }
}
