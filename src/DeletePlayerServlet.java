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

public class DeletePlayerServlet extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String player = request.getParameter("player");

        if (player == null || player.isEmpty()) {
            out.println("<p>Player name is required to delete the record.</p>");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/NHL_Concussion_Data", "pace", "123456");

            String sql = "DELETE FROM injuries WHERE Player = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, player);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                out.println("<p>Player " + player + " deleted successfully!</p>");
            } else {
                out.println("<p>Failed to delete player " + player + " or player not found.</p>");
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            out.close();
        }
    }
}

