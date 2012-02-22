/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package libreria.web;

import java.io.*;
import java.net.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.*;
import javax.servlet.http.*;




public class CancellaLibroServlet extends HttpServlet 
{
   

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    {
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            //recupero parametri dalla richiesta
            String isbn = request.getParameter("isbn");

            //connessione jdbc al database
            Class.forName("com.mysql.jdbc.Driver");
            String database = "jdbc:mysql://localhost:3306/libreria";
            String user = "root";
            String password = "admin";
            conn = DriverManager.getConnection(database, user, password);

            //cancellazione libro
            String deleteLibro = "DELETE FROM Libro WHERE ISBN = ?";

            pstmt = conn.prepareStatement(deleteLibro);
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();

            response.sendRedirect("VisualizzaLibriServlet");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            response.sendRedirect("VisualizzaLibriServlet");
        } 
        finally 
        {
            if (pstmt != null) 
            {
                try 
                {
                    pstmt.close();
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
                pstmt = null;
            }
            if (conn != null) 
            {
                try 
                {
                    conn.close();
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
                conn = null;
            }
        }

    }


}
