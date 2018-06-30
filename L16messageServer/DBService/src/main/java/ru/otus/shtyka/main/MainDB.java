package ru.otus.shtyka.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.shtyka.service.DBService;

public class MainDB {

    private static final Logger logger = LoggerFactory.getLogger(MainDB.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans-db.xml");
        DBService dbService = (DBService) context.getBean("dbService");
        logger.info(dbService.getCacheEngine().getCacheInfo());
    }
}
