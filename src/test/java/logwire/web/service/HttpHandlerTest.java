package logwire.web.service;

import logwire.core.resource.bean.HttpHandlerProvider;
import org.springframework.jdbc.core.CallableStatementCreator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class HttpHandlerTest implements HttpHandlerProvider {
    public void doDocGet(logwire.web.service.ActionContext $, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //处理逻辑
        response.setHeader("","");
        response.getWriter().print("");

    }
}
