package com.saml2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Returns the name of the view (HTML file in src/main/resources/templates)
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Principal principal, Model model) { // Add Model as a parameter
        // Assumes that the Principal object is not null
        String username = principal.getName(); // Get the username from the Principal object
        model.addAttribute("username", username); // Add the username attribute to the model
        model.addAttribute("title", "test"); // Add the title attribute to the model
        return "loginSuccess"; // Returns the name of the view (HTML file in src/main/resources/templates)
    }
}
