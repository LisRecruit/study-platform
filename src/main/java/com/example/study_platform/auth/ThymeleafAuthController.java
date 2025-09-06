package com.example.study_platform.auth;

import com.example.study_platform.auth.user.UserService;
import com.example.study_platform.auth.user.dto.request.UserCreateRequest;
import com.example.study_platform.auth.user.dto.request.UserLoginRequest;
import com.example.study_platform.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/auth")
public class ThymeleafAuthController {

    private final UserService userService;
    private final Validator validator;


    @GetMapping("/")
    public String indexPage() {
        return "auth";
    }

        // Страница логина
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserLoginRequest(null, null));
        return "login"; // login.html из resources/templates
    }

    // Страница регистрации
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserCreateRequest(null, null, null, null));
        return "register"; // register.html из resources/templates
    }

    // Обработка формы регистрации
    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserCreateRequest request, Model model) {
        try {
            userService.createUser(request);
            return "redirect:/auth/login?success"; // после регистрации перенаправляем на логин
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register"; // остаёмся на странице регистрации с сообщением об ошибке
        }
    }

    // Обработка формы логина через Thymeleaf (опционально, если хочешь без JS)
    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserLoginRequest request, Model model) {
        try {
            if (!validator.isEmailValid(request.email()) && !validator.isValidPassword(request.password())) {
                return "redirect:/dashboard"; // куда-то после успешного логина
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}
