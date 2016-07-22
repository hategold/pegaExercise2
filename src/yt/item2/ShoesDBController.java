package yt.item2;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShoesDBController
 */
@WebServlet("/ShoesDBController")
public class ShoesDBController extends HttpServlet {

	private Connection conn;

	private static final long serialVersionUID = 1L;

	public static final String LIST_BRANDS = "/listBrands.jsp";

	public static final String INSERT_OR_EDIT = "/modifyBrand.jsp";

	public ShoesDBController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("delete")) {
		} else if (action.equalsIgnoreCase("edit")) {
		} else if (action.equalsIgnoreCase("insert")) {
		} else {
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String brandID = request.getParameter("brandID");

		String brandName = request.getParameter("brandName");

		String website = request.getParameter("website");

		String country = request.getParameter("country");
		try {
			if (brandID.equals("") || brandID == null) {
				String query = "INSERT brands (BrandName, Website, Country) VALUES (?,?,?)";
				PreparedStatement preparedStatement;

				preparedStatement = conn.prepareStatement(query);
				preparedStatement.setString(1, brandName);
				preparedStatement.setString(2, website);
				preparedStatement.setString(3, country);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				
			} else {
				String query = "UPDATE brands SET BrandName=?, Website=?, Country=? WHERE BrandID=?";
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				preparedStatement.setString(1, brandName);
				preparedStatement.setString(2, website);
				preparedStatement.setString(3, country);
				preparedStatement.setString(4, brandID);
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		doGet(request, response);
	}

	public void setConnByProperties(String filepath) {

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(filepath));

			String driver = properties.getProperty("driver", "com.mysql.jdbc.Driver");
			String host = properties.getProperty("host");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");

			Class.forName(driver);

			conn = DriverManager.getConnection(host, user, password);

		} catch (SQLException e) {
			System.out.println("SQLException :" + e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException :" + e.toString());
		} catch (IOException e) {
			System.out.println("IOException :" + e.toString());
		}
	}

}
