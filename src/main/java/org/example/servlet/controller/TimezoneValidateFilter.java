package org.example.servlet.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
@WebFilter(value = "/api")
public class TimezoneValidateFilter extends HttpFilter {
    //    /api?timezone=Australia/Canberra
    //    /api?timezone=Asia/Aden
    //    /api?timezone=USD
    //    /api?timezone=Etc/GMT-8
    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse resp,
                            FilterChain chain) throws IOException, ServletException {
        resp.setContentType("text/html; charset=utf-8");

        String value = req.getParameter("timezone");

        int priz = 0;
        for (String s: ZoneId.getAvailableZoneIds()) {
            if (s.equals(value)) {
                priz += 1;
              //  System.out.println("value = " + value + " s = " + s);
            }
        }
       // System.out.println(priz);
        if (priz == 1) {
                resp.setStatus(201);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"OK-201\": \"Time zone "+ value +"\"}");
                resp.getWriter().close();
                chain.doFilter(req, resp);
        } else {
                resp.setStatus(401);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"Error-401\": \"Time zone not found\"}");
                resp.getWriter().close();
        }
    }

}
