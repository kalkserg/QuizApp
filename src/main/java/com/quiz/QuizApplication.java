package com.quiz;

import ch.qos.logback.classic.Level;
import com.quiz.model.Entity;
import com.quiz.service.CsvToBeanService;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
public class QuizApplication {

    public static void main(String[] args) throws IOException {

        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.toLevel("error"));

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        Scanner myScanner = new Scanner(System.in);

        System.out.print("Enter Firstname: ");
        String firstName = myScanner.nextLine();
        System.out.print("Enter Lastname: ");
        String lastName = myScanner.nextLine();

        CsvToBeanService csvToBeanService = (CsvToBeanService) context.getBean("csvToBeanService");
        List<Entity> entitylist = csvToBeanService.getList();

        System.out.println(entitylist.size() + " questions for " + firstName.toUpperCase() + " " + lastName.toUpperCase() + ": ");
        int count = 0;
        int rightAnswer = 0;
        for (Entity e : entitylist) {
            System.out.println("Question " + (++count) + ": " + e.getQuestion());
            System.out.print("Answer " + count + ": ");
            String answer = myScanner.nextLine();
            if (answer.equals(e.getAnswer())) { System.out.println("   Right!"); ++rightAnswer;}
            else System.out.println("   Wrong!");
        }
        System.out.println("Correct answers " + rightAnswer + " (" + String.format("%.2f",rightAnswer*100./count) + "%)");
    }
}
