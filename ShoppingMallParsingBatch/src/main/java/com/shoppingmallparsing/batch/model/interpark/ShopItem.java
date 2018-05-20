package com.shoppingmallparsing.batch.model.interpark;

public class ShopItem {

	//쇼핑몰 상품 정보
	private String id;				//id
	private String title;
	private String pricePc;				//price_pc
	private String priceMobile;			//price_mobile
	private String normalPrice;			//normal_price
	private String link;
	private String mobileLink;			//mobile_link
	private String imageLink;			//image_link
	private String categoryName1;		//category_name1
	private String categoryName2;		//category_name2
	private String categoryName3;		//category_name3
	private String categoryName4;		//category_name4
	private String naverCategory;		//naver_category
	private String naverProductId;		//naver_product_id
	private String condition;
	private String importFlag;			//import_flag
	private String productFlag;			//product_flag
	private String adult;
	private String modelNumber;			//model_number
	private String brand;
	private String maker;
	private String origin;
	private String cardEvent;			//card_event
	private String eventWords;			//event_words
	private String coupon;
	private String interestFreeEvent;	//interest_free_event
	private String point;
	private String installationCosts;	//installation_costs
	private String searchTag;			//search_tag
	private String reviewCount;			//review_count
	private String shipping;
	private String deliveryGrade;		//delivery_grade
	private String deliveryDetail;		//delivery_detail
	private String sellerId;			//seller_id;

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
	public String getPricePc() {
		return pricePc;
	}
	public void setPricePc(String pricePc) {
		this.pricePc = pricePc;
	}
	public String getPriceMobile() {
		return priceMobile;
	}
	public void setPriceMobile(String priceMobile) {
		this.priceMobile = priceMobile;
	}
	public String getNormalPrice() {
		return normalPrice;
	}
	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getMobileLink() {
		return mobileLink;
	}
	public void setMobileLink(String mobileLink) {
		this.mobileLink = mobileLink;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getCategoryName1() {
		return categoryName1;
	}
	public void setCategoryName1(String categoryName1) {
		this.categoryName1 = categoryName1;
	}
	public String getCategoryName2() {
		return categoryName2;
	}
	public void setCategoryName2(String categoryName2) {
		this.categoryName2 = categoryName2;
	}
	public String getCategoryName3() {
		return categoryName3;
	}
	public void setCategoryName3(String categoryName3) {
		this.categoryName3 = categoryName3;
	}
	public String getCategoryName4() {
		return categoryName4;
	}
	public void setCategoryName4(String categoryName4) {
		this.categoryName4 = categoryName4;
	}
	public String getNaverCategory() {
		return naverCategory;
	}
	public void setNaverCategory(String naverCategory) {
		this.naverCategory = naverCategory;
	}
	public String getNaverProductId() {
		return naverProductId;
	}
	public void setNaverProductId(String naverProductId) {
		this.naverProductId = naverProductId;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getImportFlag() {
		return importFlag;
	}
	public void setImportFlag(String importFlag) {
		this.importFlag = importFlag;
	}
	public String getProductFlag() {
		return productFlag;
	}
	public void setProductFlag(String productFlag) {
		this.productFlag = productFlag;
	}
	public String getAdult() {
		return adult;
	}
	public void setAdult(String adult) {
		this.adult = adult;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
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
	public String getCardEvent() {
		return cardEvent;
	}
	public void setCardEvent(String cardEvent) {
		this.cardEvent = cardEvent;
	}
	public String getEventWords() {
		return eventWords;
	}
	public void setEventWords(String eventWords) {
		this.eventWords = eventWords;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	public String getInterestFreeEvent() {
		return interestFreeEvent;
	}
	public void setInterestFreeEvent(String interestFreeEvent) {
		this.interestFreeEvent = interestFreeEvent;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getInstallationCosts() {
		return installationCosts;
	}
	public void setInstallationCosts(String installationCosts) {
		this.installationCosts = installationCosts;
	}
	public String getSearchTag() {
		return searchTag;
	}
	public void setSearchTag(String searchTag) {
		this.searchTag = searchTag;
	}
	public String getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(String reviewCount) {
		this.reviewCount = reviewCount;
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	public String getDeliveryGrade() {
		return deliveryGrade;
	}
	public void setDeliveryGrade(String deliveryGrade) {
		this.deliveryGrade = deliveryGrade;
	}
	public String getDeliveryDetail() {
		return deliveryDetail;
	}
	public void setDeliveryDetail(String deliveryDetail) {
		this.deliveryDetail = deliveryDetail;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	@Override
	public boolean equals(Object obj) {

		ShopItem shopItem = (ShopItem) obj;

		boolean result = this.title.equals(shopItem.getTitle())
				&& this.pricePc.equals(shopItem.getPricePc())
				&& this.priceMobile.equals(shopItem.getPriceMobile())
				&& this.normalPrice.equals(shopItem.getNormalPrice())
				&& this.link.equals(shopItem.getLink())
				&& this.mobileLink.equals(shopItem.getMobileLink())
				&& this.imageLink.equals(shopItem.getImageLink())
				&& this.categoryName1.equals(shopItem.getCategoryName1())
				&& this.categoryName2.equals(shopItem.getCategoryName2())
				&& this.categoryName3.equals(shopItem.getCategoryName3())
				&& this.categoryName4.equals(shopItem.getCategoryName4())
				&& this.naverCategory.equals(shopItem.getNaverCategory())
				&& this.naverProductId.equals(shopItem.getNaverProductId())
				&& this.condition.equals(shopItem.getCondition())
				&& this.importFlag.equals(shopItem.getImportFlag())
				&& this.productFlag.equals(shopItem.getProductFlag())
				&& this.adult.equals(shopItem.getAdult())
				&& this.modelNumber.equals(shopItem.getModelNumber())
				&& this.brand.equals(shopItem.getBrand())
				&& this.maker.equals(shopItem.getMaker())
				&& this.origin.equals(shopItem.getOrigin())
				&& this.cardEvent.equals(shopItem.getCardEvent())
				&& this.eventWords.equals(shopItem.getEventWords())
				&& this.coupon.equals(shopItem.getCoupon())
				&& this.interestFreeEvent.equals(shopItem.getInterestFreeEvent())
				&& this.point.equals(shopItem.getPoint())
				&& this.installationCosts.equals(shopItem.getInstallationCosts())
				&& this.searchTag.equals(shopItem.getSearchTag())
				&& this.reviewCount.equals(shopItem.getReviewCount())
				&& this.shipping.equals(shopItem.getShipping())
				&& this.deliveryGrade.equals(shopItem.getDeliveryGrade())
				&& this.deliveryDetail.equals(shopItem.getDeliveryDetail())
				&& this.sellerId.equals(shopItem.getSellerId());

		return result;
	}

	@Override
	public String toString() {
		return "ShopItem [id=" + id + ", title=" + title + ", pricePc=" + pricePc + ", priceMobile="
				+ priceMobile + ", normalPrice=" + normalPrice + ", link=" + link + ", mobileLink=" + mobileLink
				+ ", imageLink=" + imageLink + ", categoryName1=" + categoryName1 + ", categoryName2=" + categoryName2
				+ ", categoryName3=" + categoryName3 + ", categoryName4=" + categoryName4 + ", naverCategory="
				+ naverCategory + ", naverProductId=" + naverProductId + ", condition=" + condition + ", importFlag="
				+ importFlag + ", productFlag=" + productFlag + ", adult=" + adult + ", modelNumber=" + modelNumber
				+ ", brand=" + brand + ", maker=" + maker + ", origin=" + origin + ", cardEvent=" + cardEvent
				+ ", eventWords=" + eventWords + ", coupon=" + coupon + ", interestFreeEvent=" + interestFreeEvent
				+ ", point=" + point + ", installationCosts=" + installationCosts + ", searchTag=" + searchTag
				+ ", reviewCount=" + reviewCount + ", shipping=" + shipping + ", deliveryGrade=" + deliveryGrade
				+ ", deliveryDetail=" + deliveryDetail + ", sellerId=" + sellerId + "]";
	}
}
