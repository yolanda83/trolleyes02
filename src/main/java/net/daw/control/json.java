package net.daw.control;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.daw.bean.ReplyBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;
import net.daw.service.TipousuarioService;

/**
 * Servlet implementation class json
 */
public class json extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public json() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        String strJson = "";
        String strOb = request.getParameter("ob");
        String strOp = request.getParameter("op");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            strJson = "{\"status\":500,\"msg\":\"jdbc driver not found\"}";
        }

        if (strOp != null && strOb != null) {
            if (!strOp.equalsIgnoreCase("") && !strOb.equalsIgnoreCase("")) {
                if (strOb.equalsIgnoreCase("tipousuario")) {
                    if (strOp.equalsIgnoreCase("get")) {

                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.get();
                            strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":\"" + oReplyBean.getJson()
                                    + "\"}";

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    
                     if (strOp.equalsIgnoreCase("getCount")) {

                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.get();
                            strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":\"" + oReplyBean.getJson()
                                    + "\"}";

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    
                    
                    if (strOp.equalsIgnoreCase("remove")) {

                        TipousuarioService oService = new TipousuarioService(request);
                        try {
                            ReplyBean oReplyBean = oService.remove();
                            strJson = "{\"status\":" + oReplyBean.getStatus() + ",\"message\":\"" + oReplyBean.getJson()
                                    + "\"}";

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

             
                if (strOb.equalsIgnoreCase("usuario")) {
                    if (strOp.equalsIgnoreCase("connect")) {

                        try {
                            ConnectionInterface oConnectionPool = ConnectionFactory
                                    .getConnection(ConnectionConstants.connectionPool);
                            Connection oConnection = oConnectionPool.newConnection();
                            // servir la petición utilizando oConnection
                            oConnectionPool.disposeConnection();
                            strJson = "{\"status\":200,\"msg\":\"Connection OK\"}";
                        } catch (Exception ex) {
                            strJson = "{\"status\":500,\"msg\":\"Bad Connection: "
                                    + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())) + "\"}";
                        }

                    }

                    // http://localhost:8081/authentication/json?op=login&user=nombre&pass=password
                    // http://localhost:8081/authentication/json?op=check
                    // http://localhost:8081/authentication/json?op=logout
                    response.setContentType("application/json;charset=UTF-8");
                    HttpSession oSession = request.getSession();

                    if (strOp.equalsIgnoreCase("login")) {
                        String strUser = request.getParameter("user");
                        String strPass = request.getParameter("pass");
                        if (strUser.equals("rafa") && strPass.equals("thebest")) {
                            oSession.setAttribute("daw", strUser);
                            strJson = "{\"status\":200,\"msg\":\"" + strUser + "\"}";
                        } else {
                            strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
                        }
                    }
                    if (strOp.equalsIgnoreCase("logout")) {
                        oSession.invalidate();
                        strJson = "{\"status\":200,\"msg\":\"Session is closed\"}";
                    }
                    if (strOp.equalsIgnoreCase("check")) {
                        String strUserName = (String) oSession.getAttribute("daw");
                        if (strUserName != null) {
                            strJson = "{\"status\":200,\"msg\":\"" + strUserName + "\"}";
                        } else {
                            strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
                        }
                    }
                    if (strOp.equalsIgnoreCase("getsecret")) {
                        String strUserName = (String) oSession.getAttribute("daw");
                        if (strUserName != null) {
                            strJson = "{\"status\":200,\"msg\":\"985739847598\"}";
                        } else {
                            strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
                        }
                    }
                }

            } else {
                strJson = "{\"status\":200,\"msg\":\"operation empty\"}";
            }
        } else {
            strJson = "{\"status\":200,\"msg\":\"operation can't be null\"}";
        }

        response.getWriter().append(strJson).close();
    }

}
