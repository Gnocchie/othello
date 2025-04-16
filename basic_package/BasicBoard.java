package de.lmu.bio.ifi.basicpackage;

public class BasicBoard {
	
	private static String rules 	= "Basic type: No rules for basic type";
	private static String boardtype = "basic";

	protected int[][] board;
	protected String boardname;

	public String getBoardtype() {
		return BasicBoard.boardtype;
	}

	public void setBoardtype(String boardtype) {
		BasicBoard.boardtype = boardtype;
	}

	public void setRules(String r) {
		BasicBoard.rules = r;
	}

	public String getRules() {
		return BasicBoard.rules;
	}

	public void setBoardname(String boardname) {
		this.boardname = boardname;
	}

	public String getBoardname() {
		if (this.boardname == null) {
			return "no name";
		} else {
			return this.boardname;
		}
	}
	
	public int[][] getBoard() {
		return this.board;
	}

	public String toString() {
		String out;
		out  = "The board type is : " 	+ this.boardtype + "\n";
		out += "The rules are     : "	+ this.rules + "\n";
		out += "The boardname is   :" 	+ this.getBoardname() + "\n";
		return out;
	}
}
