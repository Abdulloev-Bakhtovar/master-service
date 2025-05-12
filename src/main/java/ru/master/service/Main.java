package ru.master.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Main {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().directory("./.env").load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(Main.class, args);
    }

}
