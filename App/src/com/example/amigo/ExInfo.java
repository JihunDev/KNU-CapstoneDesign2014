package com.example.amigo;

public class ExInfo {

		private int id;
		private String name;
		private String spin;
		private String speed;
		private String height;
		private String technique;
		private String score;
		
		// To use for selecting Friend List
		public ExInfo(int id, String name, String spin, String speed, String height,String technique, String score){
			this.setId(id);
			this.setName(name);
			this.setSpin(spin);
			this.setSpeed(speed);
			this.setHeight(height);
			this.setTechnique(technique);
			this.setScore(score);

		}


		// To use for inserting Friend List
		public ExInfo(String name, String spin, String speed, String height,String technique, String score){
			this.setName(name);
			this.setSpin(spin);
			this.setSpeed(speed);
			this.setHeight(height);
			this.setTechnique(technique);
			this.setScore(score);

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
		public String getSpin() {
			return spin;
		}

		public void setSpin(String spin) {
			this.spin = spin;
		}

		///////////////////////////////
		public String getSpeed() {
			return speed;
		}

		public void setSpeed(String speed) {
			this.speed = speed;
		}
		/////////////////////////////////
		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}
		/////////////////////////////////////
		public String getTechnique() {
			return technique;
		}

		public void setTechnique(String technique) {
			this.technique = technique;
		}
		/////////////////////////////////////
		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}
		

	
}
