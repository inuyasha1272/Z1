package com.hjwordsgame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WordBean implements Parcelable{
	private String word;
	private String phonetic;
	private String chinese;
	private String sentence;
	private String chineseSentence;
	// 音频地址（实际项目中应该是一个存放在sdcard里的mp3文件路径，这里为了方便直接把mp3文件放到raw文件夹中使用了）
	private int sentenceVoicePath;
	private int wordVoicePath;
	private boolean isSelected;

	public int getSentenceVoicePath() {
		return sentenceVoicePath;
	}

	public void setSentenceVoicePath(int sentenceVoicePath) {
		this.sentenceVoicePath = sentenceVoicePath;
	}

	public int getWordVoicePath() {
		return wordVoicePath;
	}

	public void setWordVoicePath(int wordVoicePath) {
		this.wordVoicePath = wordVoicePath;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPhonetic() {
		return phonetic;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getChineseSentence() {
		return chineseSentence;
	}

	public void setChineseSentence(String chineseSentence) {
		this.chineseSentence = chineseSentence;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("word     		= ").append(word).append("\n");
		sb.append("phonetic	 		= ").append(phonetic).append("\n");
		sb.append("chinese  		= ").append(chinese).append("\n");
		sb.append("sentence 		= ").append(sentence).append("\n");
		sb.append("chineseSentence 	= ").append(chineseSentence).append("\n");
		sb.append("sentenceVoicePath= ").append(sentenceVoicePath).append("\n");
		sb.append("wordVoicePath	= ").append(wordVoicePath).append("\n");
		sb.append("isSelected 		= ").append(isSelected).append("\n");

		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(word);
		dest.writeString(phonetic);
		dest.writeString(chinese);
		dest.writeString(sentence);
		dest.writeString(chineseSentence);
		dest.writeInt(wordVoicePath);
		dest.writeInt(sentenceVoicePath);
	}

	public static Parcelable.Creator<WordBean> CREATOR = 
												new Parcelable.Creator<WordBean>() {

		@Override
		public WordBean createFromParcel(Parcel in) {
			WordBean data = new WordBean();
			data.setWord(in.readString());
			data.setPhonetic(in.readString());
			data.setChinese(in.readString());
			data.setSentence(in.readString());
			data.setChineseSentence(in.readString());
			data.setWordVoicePath(in.readInt());
			data.setSentenceVoicePath(in.readInt());
			return data;
		}

		@Override
		public WordBean[] newArray(int size) {
			return new WordBean[size];
		}
	};
}
