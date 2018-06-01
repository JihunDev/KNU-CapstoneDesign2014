package com.example.amigo;


public class User {
	private int id;
	private String name;
	private String age;
	private String sex;
	private String height;
	private String weight;
	
	// To use for selecting Friend List
	public User(int id, String name, String age, String sex, String height, String weight){
		this.setId(id);
		this.setName(name);
		this.setAge(age);
		this.setSex(sex);
		this.setHeight(height);
		this.setWeight(weight);
	}


	// To use for inserting Friend List
	public User(String name, String age, String sex, String height, String weight){
		this.setName(name);
		this.setAge(age);
		this.setSex(sex);
		this.setHeight(height);
		this.setWeight(weight);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//////////////////////////////
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	///////////////////////////////
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	/////////////////////////////////
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	//////////////////////////////////
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	 

}