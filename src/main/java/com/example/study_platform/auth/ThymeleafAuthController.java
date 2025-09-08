package com.example.study_platform.auth;

import com.example.study_platform.auth.user.User;
import com.example.study_platform.auth.user.UserService;
import com.example.study_platform.auth.user.dto.request.UserCreateRequest;
import com.example.study_platform.auth.user.dto.request.UserLoginRequest;
import com.example.study_platform.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final AuthenticationManager authenticationManager;


    @GetMapping("/")
    public String indexPage() {
        return "auth";
    }

    // get pages
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserLoginRequest(null, null));
        return "login"; //resources/templates
    }
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserCreateRequest(null, null, null, null));
        return "register"; //resources/templates
    }


//    process pages
    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserCreateRequest request, Model model) {
        try {
            if (!validator.isEmailValid(request.email())) {
                throw new RuntimeException("Invalid email");
            }
            if (!validator.isValidPassword(request.password())) {
                throw new RuntimeException("\"Password must contain at least 8 characters, including digits, \" +\n" +
                        "                    \"uppercase and lowercase letters.\"");
            }
            userService.createUser(request);
            return "redirect:/web/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserLoginRequest request, Model model) {
        try {

            if (!validator.isEmailValid(request.email()) || !validator.isValidPassword(request.password())) {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            User user = userService.getUserByEmail(request.email());
            if (user == null) {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
            String role = user.getRole().getName();
            switch (role)  {
                case "ROLE_STUDENT":
                    return "redirect:/web/student/dashboard";
                case "ROLE_TEACHER":
                    return "redirect:/web/teacher/dashboard";
                case "ROLE_ADMIN":
                    return "redirect:/web/admin/dashboard";
            }
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}
