package yt.item2;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ShoesBrands {

	private Connection conn;

	private int brandID;
	
	private String brandName;

	private String website;

	private String country;
	
	public ShoesBrands(){};
	
	public ShoesBrands(String brandName, String website, String country) {
		setBrandName(brandName);
		setWebsite(website);
		setCountry(country);
	}

	public ShoesBrands(String brandName, String website, String country, String filepath) {
		setBrandName(brandName);
		setWebsite(website);
		setCountry(country);
		setConnByProperties(filepath);
	}

	public void addBrand() {
	}

	public void deleteBrand(int deleteID) {
		try {
            String query = "delete from brands where BrandID=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, deleteID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public void updateBrand() {
	}

	static public ArrayList<ShoesBrands> listAllBrand() {
		ArrayList<ShoesBrands> brandList = new ArrayList<ShoesBrands>();
		return brandList;
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

			this.conn = DriverManager.getConnection(host, user, password);

		} catch (SQLException e) {
			System.out.println("SQLException :" + e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException :" + e.toString());
		} catch (IOException e) {
			System.out.println("IOException :" + e.toString());
		}
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getBrandID() {
		return brandID;
	}

	public void setBrandID(int brandID) {
		this.brandID = brandID;
	}

}
