package com.test.bonusCard;

import com.test.bonusCard.model.BonusCard;
import com.test.bonusCard.model.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BonusCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BonusCardApplication.class, args);
	}

	//Transaction transaction = new Transaction(1000L, 63L);
}
