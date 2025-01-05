package main;

import java.util.ArrayList;
import java.util.Scanner;

//	사람 플레이어는 플레이어로부터 상속
class HumanPlayer extends Player {
	public HumanPlayer(String name) {
		super(name);
	}

//	사람 플레이어의 턴 시작시 호출되는 메서드
	public void startTurn() {
		System.out.println("홈 카드 : " + Game.home_card.name);
		System.out.println(this.getName() + "의 턴입니다. 카드를 선택해주세요.");
		showCards();
		selectCard();
	}

//	사용자가 가지고 있는 카드 목록 출력
	private void showCards() {
		System.out.print("0:카드 뽑기, ");
		
		ArrayList<Card> cards = getPlayer_cards();
		for (int i = 0; i < cards.size() ; i++) {
			System.out.print((i + 1) + ":" + cards.get(i).name + ", ");
		}
		System.out.println();
	}

//	사용자의 목록에서 카드 뽑기 혹은 카드 사용중 선택
	private void selectCard() {
	    System.out.print("숫자 입력 >> ");
	    Scanner scanner = new Scanner(System.in);
	    int input_idx = scanner.nextInt();
	    
//	    카드 뽑기를 선택한 경우
	    if(input_idx == 0) {
	    	System.out.println("카드를 뽑았습니다.");
	    	drawCard();
//	    카드 사용을 선택한 경우
	    }else {
	    	useCard(getPlayer_cards().get(input_idx-1));
	    }
	}

}