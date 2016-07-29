package yt.item2;

public class Brand {

	private int brandId;

	private String brandName;

	private String website;

	private String country;

	public Brand() {
	}

	public Brand(int brandId, String brandName, String website, String country) {
		this.brandId = brandId;
		this.brandName = brandName;
		this.website = website;
		this.country = country;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int id) {
		this.brandId = id;
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
}
