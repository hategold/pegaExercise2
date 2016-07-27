package yt.item2;

import java.io.IOException;
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
 * Servlet implementation class ShoesDBController
 */
@WebServlet("/ShoesDBController")
public class ShoesDBController extends HttpServlet {

	private Connection conn;

	private static final long serialVersionUID = 1L;

	public static final String LIST_BRANDS = "/listBrands.jsp";

	public static final String INSERT_OR_EDIT = "/modifyBrand.jsp";

	public static final String DB_CONFIG = "/shoesDB.properties";

	public ShoesDBController() {
		setConnByProperties(DB_CONFIG);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		RequestDispatcher view = request.getRequestDispatcher(excuteAction(action, request));
		view.forward(request, response);
	}

	private String excuteAction(String action, HttpServletRequest request) {
		String forward = "";
		Brand brand = new Brand();
		try {
			if (action.equalsIgnoreCase("delete")) {

				forward = LIST_BRANDS;
				deleteBrand(request.getParameter("BrandID"));
				request.setAttribute("brandList", readFullBrands());
				return forward;

			}
			if (action.equalsIgnoreCase("edit")) {

				forward = INSERT_OR_EDIT;
				brand.setBrandID(Integer.parseInt(request.getParameter("BrandID")));
				selectBrandByID(brand);
				request.setAttribute("brand", brand);
				return forward;

			}

			if (action.equalsIgnoreCase("insert")) {

				forward = INSERT_OR_EDIT;
				return forward;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
		}

		forward = LIST_BRANDS;
		request.setAttribute("brandList", readFullBrands());
		return forward;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int brandID;

		try {
			brandID = Integer.valueOf(request.getParameter("brandID"));
		} catch (NumberFormatException e) {
			brandID = -1;
		}

		Brand brand = new Brand(brandID, request.getParameter("brandName"), request.getParameter("website"), request.getParameter("country"));

		try {
			if (brand.getBrandID() < 0) {
				insertBrand(brand);
				response.sendRedirect("/webExercise2/ShoesDBController?action=listbrands"); //end post
				return;
			}

			updateBrand(brand);
			response.sendRedirect("/webExercise2/ShoesDBController?action=listbrands");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void selectBrandByID(Brand brand) throws SQLException {

		String query = "SELECT * FROM brands WHERE BrandID= ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, brand.getBrandID());

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			brand.setBrandName(resultSet.getString("BrandName"));
			brand.setWebsite(resultSet.getString("Website"));
			brand.setCountry(resultSet.getString("Country"));
		}
		resultSet.close();
		preparedStatement.close();

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

	private void updateBrand(Brand brand) throws SQLException {
		String query = "UPDATE brands SET BrandName=?, Website=?, Country=? WHERE BrandID=?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);

		preparedStatement.setString(1, brand.getBrandName());
		preparedStatement.setString(2, brand.getWebsite());
		preparedStatement.setString(3, brand.getCountry());
		preparedStatement.setInt(4, brand.getBrandID());
		preparedStatement.executeUpdate();
		preparedStatement.close();

	}

	private void insertBrand(Brand brand) throws SQLException {
		String query = "INSERT brands (BrandName, Website, Country) VALUES (?,?,?)";
		PreparedStatement preparedStatement;

		preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, brand.getBrandName());
		preparedStatement.setString(2, brand.getWebsite());
		preparedStatement.setString(3, brand.getCountry());
		preparedStatement.executeUpdate();
		preparedStatement.close();

	}

	public List<Brand> readFullBrands() {
		List<Brand> brandList = new ArrayList<Brand>();

		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM brands");
			while (resultSet.next()) {
				Brand brand = new Brand();
				brand.setBrandID(Integer.valueOf(resultSet.getString("brandID")));
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
