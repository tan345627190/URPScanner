package com.gs.main;

public class ClassPOJO {
	private String name;
	private String score;
	private float credit;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public float getCredit() {
		return credit;
	}
	public void setCredit(float credit) {
		this.credit = credit;
	}
	@Override
	public String toString() {
		return "ClassPOJO [" + (name != null ? "name=" + name + ", " : "")
				+ "score=" + score + ", credit=" + credit + "]";
	}
}
