package com.poem.main.util;

public class Pending {

	public final static boolean Matches(String item) {
		return item.matches("^<\\w+>");
	}

	public final static boolean isTheSame(String item) {
		return item.startsWith("$");
	}

	public static void main(String[] args) {
		System.out.println("result reference == " + Matches("<2TOP>"));
		System.out.println("result keyword   == " + isTheSame("$BREAKLINE"));
	}
}