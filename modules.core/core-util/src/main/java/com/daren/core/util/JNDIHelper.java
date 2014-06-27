package com.daren.core.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 2014-3-23
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class JNDIHelper {

    public static final <T> T getJNDIServiceForName(String serviceName) throws IOException {
        try {
            InitialContext ic = new InitialContext();

            return (T) ic.lookup("osgi:service/"
                    + serviceName);
        } catch (NamingException e) {
            e.printStackTrace();
            IOException ioe = new IOException(
                    "service resolution failed");
            ioe.initCause(e);
            throw ioe;
        }
    }

    public static final <T> T getJNDIService(String serviceName) throws IOException {
        try {
            InitialContext ic = new InitialContext();

            return (T) ic.lookup(serviceName);
        } catch (NamingException e) {
            e.printStackTrace();
            IOException ioe = new IOException(
                    "service resolution failed");
            ioe.initCause(e);
            throw ioe;
        }
    }
}
