/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yago
 */
@WebServlet(urlPatterns = {"/FormServlet"})
public class FormServlet extends HttpServlet {    
    Connection con;
    
    /**
     *
     * @param config
     */
    @Override
    public void init(ServletConfig config){
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            try {//con = DriverManager.getConnection("jdbc:hsqldb:hsql://10.65.215.17:9001/xdb", "sa", "");
                con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
            } catch (SQLException ex) {
                Logger.getLogger(FormServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FormServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        
        try (Statement stmt = con.createStatement();
                PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code.
            * CREATE TABLE messages ( id integer identity, name varchar (14), message varchar (144), date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
            * INSERT INTO messages ( name, message)  VALUES ('Gabriel', 'oi');
            * INSERT INTO messages ( name, message, date)  VALUES ('Gabriel1', 'oi1', now());
            * SELECT * FROM messages
            * DROP TABLE messages
            * */            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Web messages</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<H2 align= \"center\" >MESSAGES</H2><HR><br><form method=\"POST\" action=\"FormServlet\">");
            String value="'"+request.getParameter("name")+"','"+request.getParameter("message")+"', now()";
            String insert = "INSERT INTO messages (name, message, date) VALUES ("+value+")";
            //out.println (insert);
            stmt.executeQuery(insert);            
            out.println("Name: <br>");
            out.println("<input type=\"text\" size=\"14\" name=\"name\"><br><br>");
            out.println("Message: <br>");
            out.println("<textarea rows = \"3\" cols=50 type= \"text\" size= \"144\" name= \"message\" >Type your massage...</textarea><br><br>");
            out.println("<input type= \"submit\" value= \"Send\" >");
            //out.println("<h1>Servlet FormServlet at " + request.getContextPath() + "</h1>");
            out.println("<h1>MESSAGES</h1>");            
            ResultSet rs = stmt.executeQuery("select NAME, MESSAGE, DATE from messages ORDER BY DATE DESC");
            while(rs.next()){
                out.println("<br>");
                String name  = rs.getString("NAME");
                String message  = rs.getString("MESSAGE");
                Date date  = rs.getDate("DATE");
                
                out.println("Name: " + name + "<br>");
                out.println("Comentário: " + message + "<br>");
                out.println("Date: " + date.toString() + "<br>");
            }            
            stmt.close();
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FormServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FormServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
