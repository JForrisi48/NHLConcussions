import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;  // Add this import
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class GetPlayerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get player name or other filter from request parameters
        String playerName = request.getParameter("player");

        try {
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/NHL_Concussion_Data", "pace", "123456");

            // Prepare the SQL query to get player data
            String sql = "SELECT * FROM injuries";
            if (playerName != null && !playerName.isEmpty()) {
                sql += " WHERE Player = ?";
            }
            
            // Create statement and execute query
            PreparedStatement stmt = conn.prepareStatement(sql);
            if (playerName != null && !playerName.isEmpty()) {
                stmt.setString(1, playerName);
            }

            ResultSet rs = stmt.executeQuery();

            // Display the results
            out.println("<h1>Player Injury Data</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Season</th><th>Team</th><th>Position</th><th>Player</th><th>Injury Type</th><th>Cap Hit</th><th>Chip</th><th>Games Missed</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("Season") + "</td>");
                out.println("<td>" + rs.getString("Team") + "</td>");
                out.println("<td>" + rs.getString("Position") + "</td>");
                out.println("<td>" + rs.getString("Player") + "</td>");
                out.println("<td>" + rs.getString("Injury_Type") + "</td>");
                out.println("<td>" + rs.getString("Cap_Hit") + "</td>");
                out.println("<td>" + rs.getString("Chip") + "</td>");
                out.println("<td>" + rs.getString("Games_Missed") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            // Close the resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            out.close();
        }
    }
}

