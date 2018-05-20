package com.shoppingmallparsing.batch.model.lumid;

public class LumidItem {
	private String id;
	private String title;
	private String price_pc;
	private String link;
	private String image_link;
	private String category_name1;
	private String category_name2;
	private String category_name3;
	private String category_name4;
	private String model_number;
	private String brand;
	private String maker;
	private String origin;
	private String point;
	private String shipping;

	@Override
	public boolean equals(Object obj) {

		LumidItem shopItem = (LumidItem) obj;

		boolean result = this.id.equals(shopItem.getId()) &&
				this.title.equals(shopItem.getTitle()) &&
				this.price_pc.equals(shopItem.getPrice_pc()) &&
				this.link.equals(shopItem.getLink()) &&
				this.image_link.equals(shopItem.getImage_link()) &&
				this.category_name1.equals(shopItem.getCategory_name1()) &&
				this.category_name2.equals(shopItem.getCategory_name2()) &&
				this.category_name3.equals(shopItem.getCategory_name3()) &&
				this.category_name4.equals(shopItem.getCategory_name4()) &&
				this.model_number.equals(shopItem.getModel_number()) &&
				this.brand.equals(shopItem.getBrand()) &&
				this.maker.equals(shopItem.getMaker()) &&
				this.origin.equals(shopItem.getOrigin()) &&
				this.point.equals(shopItem.getPoint()) &&
				this.shipping.equals(shopItem.getShipping());

		return result;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice_pc() {
		return price_pc;
	}
	public void setPrice_pc(String price_pc) {
		this.price_pc = price_pc;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImage_link() {
		return image_link;
	}
	public void setImage_link(String image_link) {
		this.image_link = image_link;
	}
	public String getCategory_name1() {
		return category_name1;
	}
	public void setCategory_name1(String category_name1) {
		this.category_name1 = category_name1;
	}
	public String getCategory_name2() {
		return category_name2;
	}
	public void setCategory_name2(String category_name2) {
		this.category_name2 = category_name2;
	}
	public String getCategory_name3() {
		return category_name3;
	}
	public void setCategory_name3(String category_name3) {
		this.category_name3 = category_name3;
	}
	public String getCategory_name4() {
		return category_name4;
	}
	public void setCategory_name4(String category_name4) {
		this.category_name4 = category_name4;
	}
	public String getModel_number() {
		return model_number;
	}
	public void setModel_number(String model_number) {
		this.model_number = model_number;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
}
