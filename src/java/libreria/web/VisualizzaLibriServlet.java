/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package libreria.web;

import common.Libro;
import java.io.*;
import java.net.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;




public class VisualizzaLibriServlet extends HttpServlet 
{
   
   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    {
        PreparedStatement pstmt = null;
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        Vector libri = null;

        try {
            // connessione jdbc al database
            Class.forName("com.mysql.jdbc.Driver");
            String database = "jdbc:mysql://localhost/libreria";
            String user = "root";
            String password = "admin";
            conn = DriverManager.getConnection(database, user, password);

            //query selezione libri
            String queryLibri = "SELECT * FROM libro";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(queryLibri);

            //query selezione autori
            String queryAutori = "SELECT autore.nome FROM scrittura , autore " +
                    "WHERE scrittura.autore = autore.codice AND " +
                    "scrittura.libro = ?";

            while (rs.next()) 
            {
                if (libri == null) 
                {
                    libri = new Vector();
                }
                String isbn = rs.getString("isbn");
                String titolo = rs.getString("titolo");
                String casaEditrice = rs.getString("casaEditrice");
                float prezzo = rs.getFloat("prezzo");

                pstmt = conn.prepareStatement(queryAutori);
                pstmt.setString(1, isbn);
                rs2 = pstmt.executeQuery();

                Vector autori = new Vector();
                while (rs2.next()) 
                {
                    autori.add(rs2.getString("nome"));
                }

                Libro l = new Libro(isbn, titolo, casaEditrice, prezzo, autori);

                libri.add(l);
            }

            // inserimento oggetto nella sessione
            HttpSession sessione = request.getSession(true);
            sessione.setAttribute("libri", libri);
            response.sendRedirect("jsp/visualizzaLibri.jsp");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            response.sendRedirect("index.html");
        } 
        finally      
        {
            if (rs != null) 
            {
                try 
                {
                    rs.close();
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
                rs = null;
            }
            if (rs2 != null) 
            {
                try 
                {
                    rs2.close();
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
                rs2 = null;
            }
            if (stmt != null)
            {
                try 
                {
                    stmt.close();
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
                stmt = null;
            }
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
