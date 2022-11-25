package ru.job4j.todo.filter;

import org.springframework.ui.Model;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;

public final class SessionChecker {

    private SessionChecker() {

    }

    public static void checkSession(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Guest");
        }
        model.addAttribute("user", user);
    }

}
