package com.ronensabag.cleannotes.useCases;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.ronensabag.cleannotes.pluginInterfaces.UseCasesRunner;

public class BoundUseCaseRunner implements LoaderManager.LoaderCallbacks, UseCasesRunner {

    private final LoaderManager mLoaderManager;
    private final Context mContext;
    private UseCase mUseCase;
    private UseCasesRunner.ResponseHandler<Object> mResponseHandler;
    private Object mRequest;
    private int loaderId;

    public BoundUseCaseRunner(Context context, LoaderManager loaderManager) {
        mContext = context;
        mLoaderManager = loaderManager;
    }

    @Override
    public void cancelUseCaseRunner() {
        if (mLoaderManager.getLoader(loaderId) != null) {
            mLoaderManager.destroyLoader(loaderId);
        }
    }

    @Override
    public <Request> void runUseCase(Request request, UseCase<Request, Object> useCase,
                                     ResponseHandler<Object> responseHandler) {
        mRequest = request;
        mUseCase = useCase;
        mResponseHandler = responseHandler;
        Bundle bundle = new Bundle();
        loaderId = getLoaderId();
        mLoaderManager.restartLoader(loaderId, bundle, this);
    }

    private int getLoaderId() {
        int id = 0;
        while (mLoaderManager.getLoader(id) != null) {
            id++;
        }
        return id;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new MyAsyncTaskLoader(mContext, mUseCase, mRequest);
    }

    @Override
    public void onLoadFinished(Loader loader, Object response) {
        mLoaderManager.destroyLoader(loader.getId());
        LoaderResponse loaderResponse = (LoaderResponse) response;
        mResponseHandler.handleResponse(loaderResponse.getData());
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private static class MyAsyncTaskLoader extends AsyncTaskLoader {

        private final UseCase mUseCase;
        private final Object mRequest;

        MyAsyncTaskLoader(Context context, UseCase useCase, Object request) {
            super(context);
            mUseCase = useCase;
            mRequest = request;
        }

        @Override
        public LoaderResponse loadInBackground() {
            LoaderResponse loaderResponse =  new LoaderResponse();
            loaderResponse.setData(mUseCase.handleRequest(mRequest));
            return loaderResponse;
        }
    }
}
