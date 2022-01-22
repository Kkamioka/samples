package com.example.todolist.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // HttpServletRequest/Response は ServletRequest/Response のサブクラス
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        String uri = req.getRequestURI();
        if (uri.startsWith("/todo") || uri.startsWith("/task")) {
            // sessionが存在するか？
            HttpSession session = req.getSession(false);
            if (session == null) {
                // session無し -> Login画面へリダイレクト
                res.sendRedirect("/login");

            } else {
                // sessionにaccountIdが存在するか(=loginしたか?)
                Integer accountId = (Integer)session.getAttribute("accountId");
                if (accountId == null) {
                    // accountId無し -> Loginしていない -> Login画面へリダイレクト
                    res.sendRedirect("/login");

                } else {
                    // Loginしている -> コントローラーへリクエストを渡す
                    chain.doFilter(request, response);
                }
            }

        } else {
            // check対象外 -> コントローラーへリクエストを渡す
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
