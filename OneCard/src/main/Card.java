package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//	카드 클래스
class Card{
	String suit;			// 카드의 문양	
	String rank;			// 카드의 숫자
	String name;			// 카드의 전체 이름
	static ArrayList<String> card_list = new ArrayList<String>();
	
	Card(String suit, String rank) {
		this.suit = suit;
		this.rank = rank;
		this.name = suit+rank;
	}
}