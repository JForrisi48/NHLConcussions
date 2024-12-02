import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddPlayerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String season = request.getParameter("season");
        String team = request.getParameter("team");
        String position = request.getParameter("position");
        String player = request.getParameter("player");
        String injuryType = request.getParameter("injuryType");
        String capHit = request.getParameter("capHit");
        String chip = request.getParameter("chip");
        String gamesMissed = request.getParameter("gamesMissed");

        if (season == null || team == null || position == null || player == null ||
            injuryType == null || capHit == null || chip == null || gamesMissed == null) {
            out.println("<p>All fields are required.</p>");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");


            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/NHL_Concussion_Data", "pace", "123456");


            String sql = "INSERT INTO injuries (Season, Team, Position, Player, Injury_Type, Cap_Hit, Chip, Games_Missed) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, season);
            stmt.setString(2, team);
            stmt.setString(3, position);
            stmt.setString(4, player);
            stmt.setString(5, injuryType);
            stmt.setString(6, capHit);
            stmt.setString(7, chip);
            stmt.setString(8, gamesMissed);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<p>Player added successfully!</p>");
            } else {
                out.println("<p>Failed to add player.</p>");
            }

        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                out.println("<p>Error closing resources: " + e.getMessage() + "</p>");
            }
            out.close();
        }
    }
}

