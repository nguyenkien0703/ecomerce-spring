package com.ecommerce.shopapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopappApplication {

	public static void main(String[] args) {



		Dotenv dotenv = Dotenv.load();
//
//		// Tải tất cả các biến môi trường vào System properties
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);
		System.setProperty("mail.mime.charset", "utf8");
//		System.setProperty("file.encoding", "UTF-8");
		SpringApplication.run(ShopappApplication.class, args);
	}

}
