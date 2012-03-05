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
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;




public class AggiungiLibroServlet extends HttpServlet 
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
        ResultSet rs = null;

        try {
            //recupero parametri dalla richiesta
            String isbn = request.getParameter("isbn");
            String titolo = request.getParameter("titolo");
            String autori = request.getParameter("autori");
            String casaEd = request.getParameter("casaEditrice");

            float prezzo = Float.parseFloat(request.getParameter("prezzo"));


            String[] autoriArray = getTokensAutori(autori);

            //connessione jdbc al database
            Class.forName("com.mysql.jdbc.Driver");
            String database = "jdbc:mysql://localhost:3306/nome";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(database, user, password);

            //inserimento libro
            String updateLibro = "INSERT INTO libro ( ISBN , titolo , casaEditrice , prezzo  ) " +
                    "VALUES (?,?,?,?)";
            pstmt = conn.prepareStatement(updateLibro);
            pstmt.setString(1, isbn);
            pstmt.setString(2, titolo);
            pstmt.setString(3, casaEd);
            pstmt.setFloat(4, prezzo);
            pstmt.executeUpdate();

            //inserimento autori e scritture
            String queryAutorePresente = "SELECT codice FROM autore WHERE nome = ?";
            String updateAutore = "INSERT INTO autore ( nome ) VALUES (?)";
            String updateScrittura = "INSERT INTO scrittura ( libro, autore ) VALUES ( ?, ?)";

            for (int i = 0; i < autoriArray.length; i++) 
            {
                String proxAutore = autoriArray[i];

                //verifica presenza autore
                pstmt = conn.prepareStatement(queryAutorePresente);
                pstmt.setString(1, proxAutore);
                rs = pstmt.executeQuery();

                int codiceAutore = 0;
                if (rs.next()) // autore già presente
                {
                    codiceAutore = rs.getInt("codice");
                } 
                else // autore non presente
                {
                    //inserimento autore
                    pstmt = conn.prepareStatement(updateAutore);
                    pstmt.setString(1, proxAutore);
                    pstmt.executeUpdate();

                    //recupero codice autore appena inserito
                    pstmt = conn.prepareStatement(queryAutorePresente);
                    pstmt.setString(1, proxAutore);
                    rs = pstmt.executeQuery();
                    rs.next();
                    codiceAutore = rs.getInt("codice");

                }

                //inserimento scrittura
                pstmt = conn.prepareStatement(updateScrittura);
                pstmt.setString(1, isbn);
                pstmt.setInt(2, codiceAutore);
                pstmt.executeUpdate();
            }

            //torna alla HOME PAGE dopo un inserimento corretto
            response.sendRedirect("index.html");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            response.sendRedirect("aggiungiLibro.html");
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
    
    private String[] getTokensAutori(String s) 
    {
        if (s.equals("")) 
        {
            return null;
        }
        Vector v = new Vector();
        StringTokenizer st = new StringTokenizer(s, ", \t");
        while (st.hasMoreTokens()) 
        {
            String primo = st.nextToken();
            v.add(primo);
        }
        
        v.trimToSize();
        
        String[] autori = new String[v.size()];
        for (int i = 0; i < v.size(); i++) 
        {
            autori[i] = (String) v.get(i);
        }
        
        return autori;
    }

}
