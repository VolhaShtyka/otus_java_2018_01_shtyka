package ru.otus.shtyka.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.entity.User;

public class SomeActions {

    @Autowired
    private FrontendService frontendService;

    public SomeActions() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public void checkConnectDB() {
        User sidorov = new User("Sidorov", 22);
        frontendService.save(sidorov);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frontendService.load(User.class, sidorov.getId());

        frontendService.load(User.class, sidorov.getId());
        frontendService.save(new User("Ivanov", 98));
        frontendService.load(User.class, sidorov.getId());
        frontendService.load(User.class, sidorov.getId());
        frontendService.load(User.class, 5);
    }
}
