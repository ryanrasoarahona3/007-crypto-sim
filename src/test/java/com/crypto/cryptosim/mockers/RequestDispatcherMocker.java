package com.crypto.cryptosim.mockers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class RequestDispatcherMocker implements RequestDispatcher {

    public boolean forwarded = false;
    public boolean included = false;
    public String resource = null;


    public RequestDispatcherMocker(String resource) {
        this.resource = resource;
    }

    @Override
    public void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        forwarded = true;
        included = false;
    }

    @Override
    public void include(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        included = true;
        forwarded = false;
    }
}
