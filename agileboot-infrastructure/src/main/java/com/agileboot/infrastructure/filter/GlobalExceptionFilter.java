package com.agileboot.infrastructure.filter;


import cn.hutool.json.JSONUtil;
import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.exception.error.ErrorCode.Internal;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;


/**
 * 异常过滤器，因为配置的全局异常捕获器只能捕获MVC框架的异常
 * 不能捕获filter的异常
 * @author valarchie
 */
@Slf4j
@WebFilter(filterName = "ExceptionFilter", urlPatterns = "/*")
public class GlobalExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("global filter exceptions", e);
            e.printStackTrace();
            String resultJson = JSONUtil.toJsonStr(ResponseDTO.fail(Internal.INTERNAL_ERROR, e));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resultJson);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}