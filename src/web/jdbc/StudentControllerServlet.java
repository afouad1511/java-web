package web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.tomcat.util.buf.UDecoder;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private StudentDbUtil studentdbutil;
	@Resource(name="jdbc/web_student_tracker")
	DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentdbutil= new StudentDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException();
		}
	}

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		if(command == null) command="LIST";
		switch (command) {
		case "LIST":
			try {
				listStudent(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		 case "ADD":
			try {
				addStudent(request,response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			case "LOAD":
			try {
				loadStudent(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			case"UPDATE":
			try {
				updateStudent(request,response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			case "DELETE":
			try {
				deleteStudent(request,response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		default:
			try {
				listStudent(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
		
		
		
		
	}


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
				String studentId = request.getParameter("studentId");
				StudentDbUtil.deleteStudent(studentId);
				listStudent(request, response);
		
	}


	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		int studenId = Integer.parseInt(request.getParameter("studentId"));
		String firstname,lastname,email;
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		email = request.getParameter("email");
		Student s = new Student(studenId, firstname, lastname, email);
		StudentDbUtil.updateStudent(s);
		listStudent(request, response);
		
		
		
		
		
	}


	private void loadStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String studentId = request.getParameter("studentId");
		Student s = StudentDbUtil.getStudent(studentId);
		request.setAttribute("THE_STUDENT", s);
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/student_update_form.jsp");
		dispatcher.forward(request, response);
		
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firstname,lastname,email;
		firstname = request.getParameter("firstname");
		lastname = request.getParameter("lastname");
		email = request.getParameter("email");
		Student s = new Student(firstname, lastname, email);
		StudentDbUtil.addStudent(s);
		listStudent(request, response);
		
		
	}


	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Student> student_list = studentdbutil.getStudents();
		request.setAttribute("STUDENTS_LIST", student_list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/students_list.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
}
