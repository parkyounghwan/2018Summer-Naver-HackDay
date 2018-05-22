package com.naver.park.feed.model;

public class Product {

	private String id;
	private String title;
	private String price_pc;
	private String link;
	private String image_link;
	private String category_name1;
	private String naver_category;
	private String condition;
	private String manufacture_define_number;
	private String maker;
	private String minimum_purchase_quantity;
	private String shipping;
	private String event_words;
	private String class_val;	//class -> class_val
	private String update_time;

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


	public String getNaver_category() {
		return naver_category;
	}


	public void setNaver_category(String naver_category) {
		this.naver_category = naver_category;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


	public String getManufacture_define_number() {
		return manufacture_define_number;
	}


	public void setManufacture_define_number(String manufacture_define_number) {
		this.manufacture_define_number = manufacture_define_number;
	}


	public String getMaker() {
		return maker;
	}


	public void setMaker(String maker) {
		this.maker = maker;
	}


	public String getMinimum_purchase_quantity() {
		return minimum_purchase_quantity;
	}


	public void setMinimum_purchase_quantity(String minimum_purchase_quantity) {
		this.minimum_purchase_quantity = minimum_purchase_quantity;
	}


	public String getShipping() {
		return shipping;
	}


	public void setShipping(String shipping) {
		this.shipping = shipping;
	}


	public String getEvent_words() {
		return event_words;
	}


	public void setEvent_words(String event_words) {
		this.event_words = event_words;
	}


	public String getClass_val() {
		return class_val;
	}


	public void setClass_val(String class_val) {
		this.class_val = class_val;
	}


	public String getUpdate_time() {
		return update_time;
	}


	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(update_time).append("\t");
		builder.append(id).append("\t");
		builder.append(shipping).append("\t");
		builder.append(price_pc).append("\t");
		builder.append(title).append("\t");
		builder.append(category_name1).append("\t");
		builder.append(maker).append("\t");
		builder.append(image_link).append("\t");
		builder.append(link).append("\t");
		builder.append(event_words).append("\t");
		builder.append(manufacture_define_number).append("\t");
		builder.append(condition).append("\t");
		builder.append(minimum_purchase_quantity).append("\t");

		return builder.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category_name1 == null) ? 0 : category_name1.hashCode());
		result = prime * result + ((class_val == null) ? 0 : class_val.hashCode());
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((event_words == null) ? 0 : event_words.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image_link == null) ? 0 : image_link.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((maker == null) ? 0 : maker.hashCode());
		result = prime * result + ((manufacture_define_number == null) ? 0 : manufacture_define_number.hashCode());
		result = prime * result + ((minimum_purchase_quantity == null) ? 0 : minimum_purchase_quantity.hashCode());
		result = prime * result + ((naver_category == null) ? 0 : naver_category.hashCode());
		result = prime * result + ((price_pc == null) ? 0 : price_pc.hashCode());
		result = prime * result + ((shipping == null) ? 0 : shipping.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((update_time == null) ? 0 : update_time.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {

		Product other = (Product) obj;

		boolean result = this.id.equals(other.getId()) &&
				this.title.equals(other.getTitle()) &&
				this.price_pc.equals(other.getPrice_pc()) &&
				this.link.equals(other.getLink()) &&
				this.image_link.equals(other.getImage_link()) &&
				this.category_name1.equals(other.getCategory_name1()) &&
				this.naver_category.equals(other.getNaver_category()) &&
				this.condition.equals(other.getCondition()) &&
				this.manufacture_define_number.equals(other.getManufacture_define_number()) &&
				this.maker.equals(other.getMaker()) &&
				this.minimum_purchase_quantity.equals(other.getMinimum_purchase_quantity()) &&
				this.shipping.equals(other.getShipping()) &&
				this.event_words.equals(other.getEvent_words()) &&
				this.class_val.equals(other.getClass_val()) &&
				this.update_time.equals(other.getUpdate_time());

		return result;
	}

}