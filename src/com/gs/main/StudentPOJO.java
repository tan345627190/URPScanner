package com.gs.main;

public class StudentPOJO {
	private String id;
	private String school;
	private String name;
	private String clazz;
	private String price;
	private String major;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	@Override
	public String toString() {
		return "StudentPOJO [" + (id != null ? "id=" + id + ", " : "")
				+ (school != null ? "school=" + school + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (clazz != null ? "clazz=" + clazz + ", " : "")
				+ (price != null ? "price=" + price + ", " : "")
				+ (major != null ? "major=" + major : "") + "]";
	}
}
