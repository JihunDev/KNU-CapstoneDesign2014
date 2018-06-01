package com.example.amigo;

public class InfoClass {
	public int _id;
	public String name;
    public String age;
    public String sex;
	public String height;
    public String weight;
   
	public InfoClass(){}
	
	public InfoClass(int _id , String name , String age , String sex, String height, String weight){
		this._id = _id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.height = height;
		this.weight = weight;
	}
}
