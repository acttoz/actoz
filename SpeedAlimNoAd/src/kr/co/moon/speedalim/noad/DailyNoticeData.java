package kr.co.moon.speedalim.noad;


public class DailyNoticeData {
	private String contents;
	boolean boo;


	public DailyNoticeData(String contents) {
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public boolean getBoolean() {
		return boo;
	}

	public void changeBoolean() {
		boo = !boo;
	}

}
