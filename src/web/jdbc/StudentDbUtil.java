package web.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.sql.DataSource;
import javax.xml.ws.Response;

import org.apache.jasper.tagplugins.jstl.core.Out;

public class StudentDbUtil {

	private static DataSource dataSource;
	
	public StudentDbUtil(DataSource thedatSource) {
		dataSource = thedatSource;
		
	}
		
		
	public List<Student> getStudents()throws Exception{
		
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		String sql = "select * from student";
		try {
		myConn = dataSource.getConnection();
		myStmt = myConn.createStatement();
		myRs = myStmt.executeQuery(sql);
		while (myRs.next()) {
			int id;
			String firstname,lastname,email;
			id = myRs.getInt("id");
			firstname = myRs.getString("first_name");
			lastname = myRs.getString("last_name");
			email = myRs.getString("email");
			Student s = new Student(id, firstname, lastname, email);
			students.add(s);	
			
		}
		return students;	
		}finally {
			close(myConn, myStmt, myRs);
		}
		
		
		
		
		
		
	}

	


	public static void addStudent(Student s) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = dataSource.getConnection();
			String sql = "insert into student"
						+"(first_name, last_name, email)"
						+"values(?, ?, ?)";
			myStmt= myConn.prepareStatement(sql);
			myStmt.setString(1, s.getFirstname());
			myStmt.setString(2, s.getLastname());
			myStmt.setString(3, s.getEmail());
			
			myStmt.execute();
		} finally {
			close(myConn, myStmt, null);
		}
		
	}
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if(myRs != null) myRs.close();
			if(myStmt != null) myStmt.close();
			if(myConn != null) myConn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public static Student getStudent(String string) throws Exception {
		Student s = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId ;
		
		try {
			studentId = Integer.parseInt(string);
			myConn = dataSource.getConnection();
			String sql = "select * from student where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1,studentId);
			myRs = myStmt.executeQuery();
			if(myRs.next()) {
				String firstname, lastname,email;
				firstname = myRs.getString("first_name");
				lastname = myRs.getString("last_name");
				email = myRs.getString("email");
				s = new Student(studentId, firstname, lastname, email);
				
			}
			else {
				throw new Exception("Could not find student id: " + studentId);
			}				
			
			return s;
		
		} finally {
			close(myConn, myStmt, null);
		}
	}


	public static void updateStudent(Student s) {
		Connection myConn = null;
		PreparedStatement myStmt=null;
		try {
			myConn = dataSource.getConnection();
			String sql = "update student "
					+ "set first_name=?, last_name=?, email=? "
					+ "where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, s.getFirstname());
			myStmt.setString(2, s.getLastname());
			myStmt.setString(3, s.getEmail());
			myStmt.setInt(4, s.getId());
			
			myStmt.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(myConn, myStmt, null);
		}
				

	}


	public static void deleteStudent(String studentId) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			int id = Integer.parseInt(studentId);
			
			myConn = dataSource.getConnection();
						
			String sql = "delete from student where id=?";
						
			myStmt = myConn.prepareStatement(sql);
						
			myStmt.setInt(1, id);
			
			myStmt.execute();
		}
		finally {
			
			close(myConn, myStmt, null);
		}	
	}

		
	}	

