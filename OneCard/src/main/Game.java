package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
	public static ArrayList<Player> players = new ArrayList();	// 플레이어 저장하는 리스트
	public static ArrayList<Card> cards = new ArrayList();		// 카드 목록 저장하는 리스트
	public static ArrayList<Card> used_cards = new ArrayList();	// 사용된 카드 목록 저장하는 리스트
	static Card home_card;										// 마지막으로 사용된 카드 저장하는 변수
	ArrayList<Player> finish_players = new ArrayList();
	
	Scanner scanner = new Scanner(System.in);

	
//	게임 생성시 게임 설정 및 시작
	Game() {
		set();
		play();
	}

//	게임 기본설정을 위한 메서드
	private void set() {
		int player_number;

		// 게임을 플레이할 플레이어 수 입력
		player_number = inputPlayerNumber();

		// 플레이어 수 만큼 Player 객체 생성
		players.addAll(createPlayers(player_number));

		// 카드 생성
		cards.addAll(createCardSet());

//		카드 순서 섞음
		Collections.shuffle(cards);
		
//		플레이어들에게 7장씩 카드를 나눠줌
		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < 7; j++) {
				players.get(i).getPlayer_cards().add(cards.remove(0));
			}
		}
	}

	
//	게임 실행하는 메서드
	private void play() {
//		첫 카드를 한장 꺼내놓음
		used_cards.add(cards.remove(0));

//		시작할 순서를 랜덤 지정
		int turn = getRandNum();

//		사용자 턴 반복
		while (true) {
			home_card = used_cards.get(used_cards.size() - 1);

//			플레이어가 카드를 모두 사용하여 끝난 경우
			if (players.get(turn).is_finish == true) {
			    boolean isAlreadyInFinishPlayers = false;
			    for(int i =0; i < finish_players.size(); i++) {
			        if(finish_players.get(i) == players.get(turn)) {
			            isAlreadyInFinishPlayers = true;
			            break;
			        }
			    }
			    if(!isAlreadyInFinishPlayers) {
			        finish_players.add(players.get(turn));
			        
			        if(finish_players.size() == players.size()-1) {
			        	Finish();
			        }
			    }
			    turn++;
			} else {
				players.get(turn).startTurn();
			}

//			턴 값 초기화
			if (turn == players.size() - 1) {
				turn = turn - players.size() + 1;
			} else {
				turn++;
			}
			System.out.println("==============================================================================");
			
			try {
				Thread.sleep(1000); //1초 대기
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

//	게임을 플레이할 플레이어 수 입력받고 반환하는 메서드
	private int inputPlayerNumber() {
		while (true) {
			System.out.print("플레이어 수를 입력하세요. : ");
			int player_num = scanner.nextInt();

			if (player_num < 2 || player_num > 4) {
				System.out.println("플레이어 수는 2~4명이어야합니다.");
			} else {
				return player_num;
			}
		}
	}

//	게임할 플레이어 수 만큼 플레이어 객체 생성 및 플레이어 목록 반환 메서드
	private ArrayList<Player> createPlayers(int player_number) {
		ArrayList<Player> players = new ArrayList();

//		ComputerPlayer player0 = new ComputerPlayer("player0");
		HumanPlayer player0 = new HumanPlayer("mainPlayer");
		players.add(player0);
		if (player_number >= 2) {
			ComputerPlayer player1 = new ComputerPlayer("player1");
			players.add(player1);
		}
		if (player_number >= 3) {
			ComputerPlayer player2 = new ComputerPlayer("player2");
			players.add(player2);
		}
		if (player_number >= 4) {
			ComputerPlayer player3 = new ComputerPlayer("player3");
			players.add(player3);
		}

		return players;
	}

//	카드 생성 및 목록 반환 메서드
	private ArrayList<Card> createCardSet() {
		ArrayList<Card> card_list = new ArrayList<Card>();
		ArrayList<String> suit = new ArrayList<String>(Arrays.asList("S", "C", "D", "H"));
		ArrayList<String> rank = new ArrayList<String>(
				Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"));

		for (String s : suit) {
			for (String r : rank) {
				Card card = new Card(s, r);
				card_list.add(card);
			}
		}

		Card color_joker = new Card("Color", "Joker");
		Card black_jocker = new Card("Black", "Joker");

		card_list.add(black_jocker);
		card_list.add(color_joker);

		return card_list;
	}

//	첫번째 순서 랜덤 지정 후 반환 메서드
	private int getRandNum() {
		int randnum = (int) (Math.random() * players.size() - 1);

		System.out.println("첫번째 순서는 " + players.get(randnum).getName() + "입니다.");
		System.out.println("==============================================================================");
		return randnum;
	}
	
//	플레이어가 카드 뽑기를 선택한 경우 호출되는 메서드
	public static void drawCardFromPlayer(Player player) {
//		뽑을 수 있는 카드가 없는 경우 실행
		if (cards.size() == 0) {
//			사용된 카드 목록에 있는 카드 미사용 카드에 넣음
			cards.addAll(getUsed_cards());

//			사용된 카드 목록에서 마지막 한장을 제외하고 삭제
			for (int i = 0; i < used_cards.size() - 1; i++) {
				getUsed_cards().remove(0);
			}

//			미사용 카드 목록 섞은 뒤 플레이어에게 카드를 줌
			Collections.shuffle(cards);
			player.getPlayer_cards().add(cards.remove(0));
			
//			뽑을 수 있는 카드가 있는 경우 플레이어에게 카드를 줌
		} else {
			player.getPlayer_cards().add(cards.remove(0));
		}
	}

	public static ArrayList<Card> getUsed_cards() {
		return used_cards;
	}

	public static void setUsed_cards(ArrayList<Card> used_cards) {
		Game.used_cards = used_cards;
	}

	public static ArrayList<Card> getCards() {
		return cards;
	}

	public static void setCards(ArrayList<Card> cards) {
		Game.cards = cards;
	}	

	public static Card getHome_card() {
		return home_card;
	}

	public void setHome_card(Card home_card) {
		this.home_card = home_card;
	}
	
	void Finish() {
		for(int i =0; i < finish_players.size(); i++) {
			if(finish_players.get(i) == finish_players.get(0)) {
				finish_players.get(i).score += 4;
			}else if (finish_players.get(i) == finish_players.get(1)) {
				finish_players.get(i).score += 3;
			}
		}
		
		for(int j = 0; j < players.size(); j++) {
			System.out.println(players.get(j).score);
		}
		
		System.out.println("게임이 끝났습니다\n1.게임 종료, 2: 게임 재시작 중 선택하세요");
		Scanner scanner = new Scanner(System.in);
	    int input_idx = scanner.nextInt();
	    
	    if(input_idx == 1) {
	    	System.exit(0);
	    }else if(input_idx == 2) {
	    	System.out.println("게임을 재시작합니다.");
	    	play();
	    }
	}
}