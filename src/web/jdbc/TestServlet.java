package web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.cj.protocol.Resultset;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		Connection myconn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myconn = dataSource.getConnection();
			String sql = "select * from student";
			myStmt = myconn.createStatement();
			myRs = myStmt.executeQuery(sql);
			List<Student> student_list = new ArrayList<>();
			while (myRs.next()) {
				int id;
				String firstname,lastname,email;
				id = myRs.getInt("id");
				firstname = myRs.getString("first_name");
				lastname = myRs.getString("last_name");
				email = myRs.getString("email");
				Student s = new Student(id, firstname, lastname, email);
				student_list.add(s);
				
			}
			out.println(student_list);
			
		} catch (SQLException sql) {
			out.println(sql);
		}
		
		
		
		
		
		
		
		
	}

}
