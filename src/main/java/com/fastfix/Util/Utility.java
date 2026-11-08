package com.fastfix.Util;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {
    public static String servletRequest(HttpServletRequest request){
        String siteUrl = request.getRequestURL().toString();
        return  siteUrl.replace(request.getServletPath(),"");
    }
}


