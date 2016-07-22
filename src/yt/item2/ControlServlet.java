package yt.item2;

//TODO ? import java.io.FileInputStream;
import java.io.IOException;
//TODO ? import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControlServlet
 */
@WebServlet("/ControlServlet")
public class ControlServlet extends HttpServlet {

	private Connection conn;

	private static final long serialVersionUID = 1L;

	public static final String LIST_BRANDS = "/listBrands.jsp";

	public static final String INSERT_OR_EDIT = "/modifyBrand.jsp";

	public static final String DB_CONFIG = "/shoesDB.properties";

	public ControlServlet() {
		setConnByProperties(DB_CONFIG);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		String action = request.getParameter("action");

		try {
			if (action.equalsIgnoreCase("delete")) {
				forward = LIST_BRANDS;
				String brandID = request.getParameter("BrandID");
				deleteBrand(brandID);
				request.setAttribute("brandList", getFullBrands());
			} else if (action.equalsIgnoreCase("edit")) {

				//TODO no else if and encapsulate the code

				String brandName = null, website = null, country = null;
				forward = INSERT_OR_EDIT;

				int brandID = Integer.parseInt(request.getParameter("BrandID"));
				String query = "SELECT * FROM brands WHERE BrandID= ?";
				PreparedStatement preparedStatement;
				preparedStatement = conn.prepareStatement(query);
				preparedStatement.setInt(1, brandID);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					brandName = resultSet.getString("BrandName");
					website = resultSet.getString("Website");
					country = resultSet.getString("Country");
				}
				resultSet.close();
				preparedStatement.close();
				request.setAttribute("BrandID", brandID);
				request.setAttribute("BrandName", brandName);
				request.setAttribute("Website", website);
				request.setAttribute("Country", country);
			} else if (action.equalsIgnoreCase("insert")) {

				//TODO no else if and encapsulate the code

				forward = INSERT_OR_EDIT;
			} else {
				forward = LIST_BRANDS;
				request.setAttribute("brandList", getFullBrands());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	private void deleteBrand(String brandID) {
		try {
			String query = "DELETE FROM brands WHERE brandId=?";

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, brandID);
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

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

		response.sendRedirect("/test_exercise2/ControlServlet?action=listbrands");

	}

	public List<Brand> getFullBrands() {
		List<Brand> brandList = new ArrayList<Brand>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM brands");
			while (resultSet.next()) {
				Brand brand = new Brand();
				brand.setBrandID(resultSet.getString("brandID"));
				brand.setBrandName(resultSet.getString("brandName"));
				brand.setCountry(resultSet.getString("country"));
				brand.setWebsite(resultSet.getString("website"));

				brandList.add(brand);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return brandList;
	}

	public void setConnByProperties(String filepath) {

		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream(filepath));

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
