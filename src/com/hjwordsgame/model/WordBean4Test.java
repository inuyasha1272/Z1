package com.hjwordsgame.model;


public class WordBean4Test {
	private String word;
	private boolean isRight;
	private String chinese;

	public WordBean4Test(String word, boolean isRight, String chinese) {
		super();
		this.word = word;
		this.isRight = isRight;
		this.chinese = chinese;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("word     		= ").append(word).append("\n");
		sb.append("isRight	 		= ").append(isRight).append("\n");
		sb.append("chinese  		= ").append(chinese).append("\n");
		return sb.toString();

	}

}
