package com.ronensabag.cleannotes.useCases;


public interface UseCase <Request, Response> {

    Response handleRequest(Request request);
}
