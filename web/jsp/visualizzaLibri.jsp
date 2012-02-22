<%@ page import="java.util.*"%>
<%@ page import="libreria.web.*"%>
<%@ page import="common.*"%>

<html>
    
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Libri presenti</title>
        
    </head>
    
    <body>
        <%
            Vector libri = (Vector) session.getAttribute("libri");

            if (libri == null) 
            {
                out.println("<H3>Nessun libro presente!</H3><BR><BR>");
            } 
            else 
            {
                out.println("<H3>Libri presenti:</H3><BR><BR>");
                Iterator it = libri.iterator();
                while (it.hasNext()) 
                {
                    Libro l = (Libro) it.next();
                    String libro = "";
                    libro += "ISBN : " + l.getIsbn() + "<BR>";
                    libro += "Titolo : " + l.getTitolo() + "<BR>";
                    libro += "Autori : ";
                    Iterator it2 = l.getAutori().iterator();
                    while (it2.hasNext()) 
                    {
                        libro += it2.next() + " ";
                    }
                    libro += "<BR>Casa Editrice : " + l.getCasaEditrice();
                    libro += "<BR>Prezzo : " + l.getPrezzo();

                    out.println("<HR><br>" + libro);
        %>
        
        <FORM METHOD="POST" ACTION="../CancellaLibroServlet">
            <BR>
            <INPUT TYPE="hidden" NAME="isbn" VALUE="<%=l.getIsbn() %>" SIZE=40>
            <INPUT TYPE=submit NAME="Submit" VALUE="Rimuovi Libro">
            <BR>
        </FORM>
        <%
                }
                out.println("<HR>");
            } 
        %>
        <BR>
        <BR>
        
        <A HREF="../index.html">Home</A>
        
    </body>
    
</html>
