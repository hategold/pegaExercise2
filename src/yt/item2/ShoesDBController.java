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

	public static enum ActionEnum {
		INSERT("insert"), EDIT("edit"), DELETE("delete"), LIST("list");

		private String value;

		private ActionEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	};

	public ShoesDBController() {
		setConnByProperties(DB_CONFIG);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		RequestDispatcher view = request.getRequestDispatcher(excuteAction(action, request));
		view.forward(request, response);
	}

	private String excuteAction(String action, HttpServletRequest request) {

		try {
			if (ActionEnum.DELETE.getValue().equals(action.toLowerCase())) {

				deleteBrand(request.getParameter("BrandID"));
				request.setAttribute("brandList", readFullBrands());
				return LIST_BRANDS;

			}
			if (ActionEnum.EDIT.getValue().equals(action.toLowerCase())) {

				Brand brand = new Brand();
				brand.setBrandID(Integer.parseInt(request.getParameter("BrandID")));
				selectBrandByID(brand);
				request.setAttribute("brand", brand);
				return INSERT_OR_EDIT;

			}

			if (ActionEnum.INSERT.getValue().equals(action.toLowerCase())) {

				return INSERT_OR_EDIT;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
		}

		request.setAttribute("brandList", readFullBrands());
		return LIST_BRANDS;

	}

	private boolean isCreate(String id) {

		try {
			Integer.valueOf(id);
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean isNewCreate = isCreate(request.getParameter("brandID"));

		Brand brand = new Brand(isNewCreate ? -1 : Integer.valueOf(request.getParameter("brandID")), request.getParameter("brandName"),
				request.getParameter("website"), request.getParameter("country"));
		//修改constructor 很多參數 嗽物件封裝起來
		try {
			if (isNewCreate) {
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
		preparedStatement.close();//id not found 

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
		String sqlStatement = "UPDATE brands SET BrandName=?, Website=?, Country=? WHERE BrandID=?";
		PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);

		preparedStatement.setString(1, brand.getBrandName());
		preparedStatement.setString(2, brand.getWebsite());
		preparedStatement.setString(3, brand.getCountry());
		preparedStatement.setInt(4, brand.getBrandID());
		preparedStatement.executeUpdate();
		preparedStatement.close();

	}

	private void insertBrand(Brand brand) throws SQLException {
		String query = "INSERT brands (BrandName, Website, Country) VALUES (?,?,?)";
		
		PreparedStatement preparedStatement = conn.prepareStatement(query);
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
