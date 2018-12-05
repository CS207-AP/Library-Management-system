package servlet;

import java.io.IOException;



import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import dao.DBConnector;
import objects.Book;
import objects.User;


/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DBConnector mydbConnect = new DBConnector();
	Book book=new Book();
	User u = new User();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        chain.doFilter(req, res);
    }

	/**
	 * 
	 * The dopost method of the controller servlet in this project is an HTTP servlet which handles request from the jsp pages and provides response to it. The requests that the controller servlet accepts is:
	 *	<p>
	 *	1.Login: Accepts user details from the JSP page and calls method check Credentials in DBConnector.If the input is valid, then the user gets redirected to the admin_login or member_login page depending on the user type.
	 *	<p>
	 *  2.Logout: Terminates the current session.
	 *  <p>
	 *	3.Create Book: Accepts book details from the JSP page and stores those details in the Book object book. It then calls method addBook in DBConnector, which adds the book in the database. If the add is a success, we set attribute <code>success</code> to <code>addBook</code> and redirect to the admin_login page (only admin can add books to the library).
	 *	<p>
	 *  4.Create User: Accepts user details from the JSP page and stores those details in the User object obj. It then calls method add User in DBConnector, which adds the user in the database. If the add is a success, we set attribute <code>success</code> to <code>addBook</code> and redirect to the admin_login page (only admin can add users to the library).
	 *	<p>
	 *	5.Issue Book: 
	 *	<p>
	 *	6.Return Book	
	 *	<p>
	 *	7.Edit Book: Accepts the book details of the book that needs to be edited, from the JSP page and stores it in Book object book. It then calls method editBook in DBConnector, which edits that particular book in the database. If the edit is a success, we set attribute <code>success</code> to <code>editBook</code> and redirect to the admin_login page (only admin can edit book details).
	 *  <p>
	 *  8.Delete Book: Accepts the book id of the book that needs to be deleted,from the JSP page. It then calls method deleteBook in DBConnector, which deletes that particular book in the database. If the delete is a success, we set attribute <code>success</code> to <code>deleteBook</code> and redirect to the admin_login page (only admin can delete a book).
	 *	<p>
	 *	9.Delete user:  Accepts the user id of the user that needs to be deleted, from the JSP page. It then calls method deleteMember in DBConnector, which deletes that particular member in the database only if the member has no book issued currently or is not on waitlist for any particular book. If the delete is a success, the message <code>Removed User from Database</code> is printed and is redirected to edit_accounts.jsp (only admin can delete a user).
	 *	<p>
	 *	10.Edit User/Admin Details by Admin : Accepts the user details of the user that needs to be edited, from the JSP page and stores it in User object userToEdit. It then calls method editUserDetails in DBConnector, which edits that particular user details in the database. If the edit is a success, we set attribute <code>success</code> to <code>editUser</code> and redirect to the admin_login page (only admin can edit user details).
	 *	<p>
	 *	11.Edit Details by Member: Accepts the three parameters from the JSP page that the member can edit for himself i.e. name,email and password and stores it in User object u. It then calls method editUserDetails in DBConnector, which edits that particular members details in the database. If the edit is a success, the message <code>Edited Details Successfully</code> is printed and the page is redirected to member_login.
	 *	<p>
	 *	12.Browse Books:
	 *	<p>
	 *	13.Add to Waitlist:
	 *	<p>
	 *	14.Remove from Waitlist:
	 * <p>
	 *	15. View your Books:
	 *	<p>
	 *	16.Individual History of the Book:
	 *	<p>
	 *	17.View All Current Issues:
	 * <p>
     *  18.Calling_edit_books:Obtains a list of Books from getAllBooks method in the DBConnector and stores it in booklist. Then it sets attribute booklist to "book_list" and the page is redirected to edit_books.jsp
	 *  19.Calling_edit_accounts: Obtains a list of users from getAllUsers method in the DBConnector and stores it in memberlist.Then it sets attribute memberlist to "users" and the page is redirected to edit_accounts.jsp
	 * 
	 * @param email Contains the email address entered by the user
	 * @param pass Contains the password entered by the user
	 * @param action Contains the action passed by the jsp pages to the Servlet.
	 * @param obj is an object of class User
	 * @param u is an object of class User
	 * @param user_ToEdit is an object of class User
	 * @param book is an object of class Book
	 * @param Title Contains the title of the Book.
	 * @param Author Contains the author of the Book.
	 * @param book_id Contains the id of the Book.
	 * @param ISBN Contains the ISBN of the Book.
	 * @param Publisher Contains the Publisher of the Book.
	 * @param Quantity Contains the quantity of the Book available.
	 * @param Genre Contains the genre of the Book.
	 * @param memberlist Contains the list of members.
	 * @param booklist Contains the list of books.
	 * @param Name Contains the name of a user.
	 * @param user_id Contains the id of a user.
	 * @param issues Contains the list of objects of books.
	 * @param objectlist Contains the list of objects of books.
	 * @param objectlist1 Contains the list of objects of books.
	 * @param fine Contains the fine a user is supposed to pay while returning the book.
	 * @return returns a <code>User</code> object with its appropriate details of the user.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String action = "";
		action=request.getParameter("action");
		
		
		if(action.equalsIgnoreCase("login"))
		{
			
			 String email = request.getParameter("login_email"); 
		     String pass = request.getParameter("login_password");
		     User obj = mydbConnect.checkCredentials(email, pass);
	        	if (obj.getMemId()==0)
	        	{
	           out.println("Invalid Credentials");
	           request.setAttribute("loginResult", "Error");
	           RequestDispatcher rs = request.getRequestDispatcher("loginPage.jsp"); 
	           rs.include(request, response);
	           }
	        	else
	        	{   
	        		u=obj;
	        		String type=obj.getType();
	        		request.setAttribute("memberid", obj.getMemId());
	        		RequestDispatcher rs;
	        		if(type.equalsIgnoreCase("admin"))
	        		{
	        		rs = request.getRequestDispatcher("admin_login.jsp");
	        		}else {
	        		rs = request.getRequestDispatcher("member_login.jsp");
	        		}
	        		rs.forward(request, response);

	        	}
		}
		else if(action.equalsIgnoreCase("create_book"))
		{
			book.setTitle(request.getParameter("title"));
			book.setAuthor(request.getParameter("author"));
			book.setPublisher(request.getParameter("publisher"));
			book.setGenre(request.getParameter("genre"));
			book.setISBN(request.getParameter("isbn"));
			int quantity=Integer.parseInt(request.getParameter("number_of_copies"));
			book.setQuantity(quantity);
			book.setAvailable(quantity);
			System.out.println("INSIDE CREATE_BOOK OF SERVLET: "+book.getTitle());
			try {
			boolean save=mydbConnect.addBook(book);
			if(save==true)
			{
				request.setAttribute("addBook", "success");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response);
			}
			catch (Exception ex) {
			Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		}
		else if(action.equalsIgnoreCase("create_user"))
		{
			User obj = new User();
			String user_type=request.getParameter("type");
			obj.setType(user_type);
			String Name=request.getParameter("name");
			obj.setName(Name);
			String Email=request.getParameter("email");
			obj.setEmail(Email);
			String Password=request.getParameter("password");
			obj.setPassword(Password);
			
			try {
			boolean save;
			save=mydbConnect.addUser(obj);
			if(save==true)
			{
			request.setAttribute("addUser", "success");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //form for member add.
			}
			catch (Exception ex) {
				Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		
		else if(action.equalsIgnoreCase("calling_edit_books")) {
			
			List<Book> booklist = new ArrayList<Book>();
			booklist = mydbConnect.getAllBooks();
			request.setAttribute("book_list",booklist);//set list as attribute
			
			request.getRequestDispatcher("edit_books.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("edit_book")) {
			
			int book_ID=Integer.parseInt(request.getParameter("id"));
			book.setid(book_ID);
			String Title=request.getParameter("title");
			book.setTitle(Title);
			
			String Author=request.getParameter("author");
			book.setAuthor(Author);
			String ISBN=request.getParameter("isbn");
			book.setISBN(ISBN);
			String Publisher=request.getParameter("publisher");
			book.setPublisher(Publisher);
			String Genre=request.getParameter("genre");
			book.setGenre(Genre);
			int quantity=Integer.parseInt(request.getParameter("copies"));
			book.setQuantity(quantity);
			
			boolean save=mydbConnect.editBook(book);
			if(save==true)
			{
				request.setAttribute("editBook", "success");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //wherever it has to get redirected.
			
		}
		else if(action.equalsIgnoreCase("calling_edit_accounts")) {
			System.out.println("Inside edit_user in servlet");
			List<User> memberlist= new ArrayList<User>();
			memberlist=mydbConnect.getAllUsers();
			request.setAttribute("users",memberlist);//set list as attribute
			
			request.getRequestDispatcher("edit_accounts.jsp").include(request, response);
		}
		
		else if(action.equalsIgnoreCase("edit_user")) {
			
			User userToEdit = mydbConnect.getUserDetails(Integer.parseInt(request.getParameter("member-id")));
			
			String user_type=request.getParameter("member-type");
			userToEdit.setType(user_type);
			String Name=request.getParameter("member-name");
			userToEdit.setName(Name);
			
			String Email=request.getParameter("member-email");
			userToEdit.setEmail(Email);
		
			boolean save=mydbConnect.editUserDetails(userToEdit);
			
			if(save==true)
			{
				request.setAttribute("editUser", "success");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_book")) {
			
			String bookid=request.getParameter("bookId");
			int book_id=Integer.parseInt(bookid); 
			
			boolean remove=mydbConnect.deleteBook(book_id);
			if(remove==true)
			{
				request.setAttribute("deleteBook", "success");
			}
			
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_user")) {
			
			String userid =request.getParameter("memId");
			int user_id=Integer.parseInt(userid);
			
			boolean deleted;
			deleted=mydbConnect.deleteMember(user_id);
			if (!deleted)
			{
				out.println("User is has borrowed a book or is in a waitlist, cannot delete");
			}
			else
			{
				out.println("Removed User from Database.");
			}
			request.setAttribute("user", user_id);
			request.getRequestDispatcher("edit_accounts.jsp").include(request, response); //wherever it has to get redirected.

			
		}
		else if(action.equalsIgnoreCase("calling_current_issues")) {
			
			List<Object[]> objectlist = new ArrayList<Object[]>();
			objectlist=mydbConnect.getAllBooksCurrentlyIssued();
			request.setAttribute("currentIssues",objectlist);//set list as attribute
			request.setAttribute("book", new Object[5]);
			request.getRequestDispatcher("current_issues_page.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("calling_past_issues")) {
			
			List<Book> allBooks=new ArrayList<Book>();
			allBooks = mydbConnect.getAllBooks();
			
			request.setAttribute("books",allBooks);//set list as attribute
			request.getRequestDispatcher("view_history.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("calling_individual_view_history")) {
			
			String bookid=request.getParameter("bookid");
			int bookID=Integer.parseInt(bookid);
			String title = request.getParameter("booktitle");
			List<Object[]> issues = new ArrayList<Object[]>();
			issues=mydbConnect.getBookIssueHistory(bookID);
			request.setAttribute("booktitle", title);
			request.setAttribute("history",issues);//set list as attribute
			request.getRequestDispatcher("individual_book_history.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("calling_view_your_books")) {
			
			int memberID=u.getMemId(); 
			List<Object[]> objectlist = new ArrayList<Object[]>();
			objectlist=mydbConnect.getUserCurrentIssue(memberID);
			request.setAttribute("current_issues",objectlist);//set list as attribute
			List<Object[]> objectlist1 = new ArrayList<Object[]>();
			objectlist1=mydbConnect.getUserIssueHistory(memberID);
			request.setAttribute("past_issues",objectlist1);//set list as attribute
			request.getRequestDispatcher("view_your_books.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("edit_your_details")) { 
			

			String Name=request.getParameter("name");
			u.setName(Name);
			String Email=request.getParameter("email");
			u.setEmail(Email);
			String Password=request.getParameter("password");
			u.setPassword(Password);
			boolean save=mydbConnect.editUserDetails(u);
			if(save==true)
			{
				out.println("Edited details successfully.");
			}
			request.getRequestDispatcher("member_login.jsp").include(request, response); 
		}
		
		else if(action.equalsIgnoreCase("calling_edit_your_details"))
		{
			String name=u.getName();
			request.setAttribute("name", name);
			String email=u.getEmail();
			request.setAttribute("email", email);
			request.getRequestDispatcher("edit_your_details.jsp").include(request, response);	
		}
		
		else if(action.equalsIgnoreCase("borrowing_book"))   //user Issue Books
		{
			int userID=u.getMemId();
			
			int bookID=Integer.parseInt(request.getParameter("book-id"));
			
			boolean issue=mydbConnect.borrowBook(userID,bookID);
			if(issue==true)
			{
				request.setAttribute("borrowSuccess", "yes");
			}
			else {
				request.setAttribute("borrowFail", "yes");
			}
			
			RequestDispatcher rs;
			if(u.getType().equalsIgnoreCase("admin"))
			 rs=request.getRequestDispatcher("admin_login.jsp");
			else
				rs=request.getRequestDispatcher("member_login.jsp");
			rs.include(request, response);
		}
		
		else if(action.equalsIgnoreCase("returning_book"))
		{
			System.out.println("Entered returning_book in servlet");
			int userID=u.getMemId();
			
			int bookID = Integer.parseInt(request.getParameter("book-id"));
			
			double fine=mydbConnect.returnBook(userID,bookID);
			if(fine>0)
			{
				request.setAttribute("fine", (double)fine);
			}
			else
			{
				request.setAttribute("returnBook", "success");
			}
			RequestDispatcher rs;
			if(u.getType().equalsIgnoreCase("admin"))
			 rs=request.getRequestDispatcher("admin_login.jsp");
			else
				rs=request.getRequestDispatcher("member_login.jsp");
			rs.include(request, response);
		}
		else if(action.equalsIgnoreCase("logout")) {
			
			HttpSession session=request.getSession();  
			session.invalidate();
			request.setAttribute("loginResult", null);
			RequestDispatcher rd = request.getRequestDispatcher("loginPage.jsp");
            rd.forward(request, response);
			out.close();
			
		}
		else if(action.equalsIgnoreCase("calling_browse_books"))
		{
		
			List<Object[]> objectlist = new ArrayList<Object[]>();
			int user_id=u.getMemId();
			objectlist=mydbConnect.browseBooks(user_id);
			if(u.getType().equalsIgnoreCase("admin"))
				request.setAttribute("adminBrowsing", "yes");
			else
				request.setAttribute("memberBrowsing", "yes");
            request.setAttribute("object_list",objectlist);//set list as attribute
			request.getRequestDispatcher("browse_books.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("add_to_waitlist"))
		{
			int bookid = Integer.parseInt(request.getParameter("book-id"));
			int userid = u.getMemId();
			boolean x = mydbConnect.addtoWaitlist(bookid,userid);
			if(x)
			{
				request.setAttribute("waitlistSuccess", "yes");
			}
			else
			{
				request.setAttribute("waitlistFailure", "yes");
			}
			if(u.getType().equalsIgnoreCase("admin"))
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //to connect the next page, check name of jsp.
			else
				request.getRequestDispatcher("member_login.jsp").include(request, response);
			
			
		}
		else if(action.equalsIgnoreCase("remove_from_waitlist"))
		{
			int bookid = Integer.parseInt(request.getParameter("book-id"));
			int userid = u.getMemId();
			boolean x = mydbConnect.removeFromWaitlist(bookid, userid);
			if(x) {
				request.setAttribute("remWaitlistS", "yes");
			}
			else
				request.setAttribute("remWaitlistF", "yes");
			if(u.getType().equalsIgnoreCase("admin"))
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //to connect the next page, check name of jsp.
			else
				request.getRequestDispatcher("member_login.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("search")) {
			String search_title=request.getParameter("search_title");
			String search_genre=request.getParameter("search_genre");
			String search_publisher=request.getParameter("search_publisher");
			String search_author=request.getParameter("search_author");
			String search_isbn=request.getParameter("search_isbn");
			Book toSearch=new Book();
			System.out.println(search_title);
			toSearch.setid(0);
			toSearch.setAuthor(search_author);
			toSearch.setAvailable(0);
			toSearch.setGenre(search_genre);
			toSearch.setISBN(search_isbn);
			toSearch.setPublisher(search_publisher);
			toSearch.setQuantity(0);
			toSearch.setTitle(search_title);
			List<Object[]> objectlist = new ArrayList<Object[]>();
			int user_id=u.getMemId();
			objectlist=mydbConnect.searchBooks(toSearch,user_id);
		    request.setAttribute("object_list",objectlist);
			request.getRequestDispatcher("browse_books.jsp").include(request, response);
			
		}
		
	}
		
}

