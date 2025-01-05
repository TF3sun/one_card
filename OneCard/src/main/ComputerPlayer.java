package main;

import java.util.ArrayList;
import java.util.Scanner;

//	컴퓨터 플레이어는 플레이어로부터 상속
public class ComputerPlayer extends Player {
	public ComputerPlayer(String name) {
		super(name);
	}

//	컴퓨터 플레이어의 턴 시작시 호출
	public void startTurn() {
		System.out.println(this.getName() + "의 턴입니다.");
		
		
		
		selectCard();
	}

//	카드 목록에 있는 카드 중 랜덤하게 카드를 선택
	private void selectCard() {
		int rand_idx = (int) (Math.random() * getPlayer_cards().size() - 1);

		if (rand_idx == 0) {
			System.out.println("카드를 뽑았습니다.");
			drawCard();
		} else {
			useCard(getPlayer_cards().get(rand_idx));
		}

	}

}