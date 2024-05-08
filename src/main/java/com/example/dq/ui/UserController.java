package com.example.dq.ui;
import com.example.dq.ui.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/")
@Log4j2
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DBconnectionService dBconnectionService;
    @Autowired
    private ConnectionRepository connectionRepository;
    
    @GetMapping
    public ModelAndView loginPage(){
        ModelAndView modelAndView = new ModelAndView("loginPage");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }
      @GetMapping("/databaseSelection")
    public ModelAndView databaseSelection(){
        ModelAndView modelAndView = new ModelAndView("DataBaseSelection");
        modelAndView.addObject("connectionRequest", new ConnectionRequest());
        return modelAndView;
    }

    @PostMapping("/test-connection")
        public ModelAndView testConnection(@ModelAttribute ConnectionRequest connectionRequest,
                                           Model model) {
        try {
            boolean isConnected = dBconnectionService.testConnection(connectionRequest);
            if (isConnected) {
                return new ModelAndView("redirect:/profiling");
            } else {
                model.addAttribute("error", "Connection Is Wrong, check the fields");
                return new ModelAndView("DataBaseSelection");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ModelAndView("redirect:/");
        }
    }


    @PostMapping("/process")
    public ModelAndView processLogin(@ModelAttribute User user, Model model, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request){
        try {
            Optional<User> optionalUser = userRepository.findByEmailId(user.getEmailId());
            if (optionalUser.isEmpty()){
                model.addAttribute("error","User credentials is Wrong ");
                return new ModelAndView("loginPage");
            }
            else {
                User persistedUser = optionalUser.get();
                if (!user.getPassword().contains(persistedUser.getPassword())){
                    model.addAttribute("error","User Password is Wrong ");
                    return new ModelAndView("loginPage");
                }
                return new ModelAndView("redirect:/databaseSelection");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info(e.getMessage());
            return null;
        }
    }



    @GetMapping("/profiling")
    public ModelAndView profileForm() {
        ModelAndView modelAndView = new ModelAndView("profilingORcustom");
        return modelAndView;
    }

    @GetMapping("/profiling/schema")
    public ModelAndView profileSchema() {
        ModelAndView modelAndView = new ModelAndView("profiling_Schema");
        modelAndView.addObject("profileSchema",new ProfileSchema());
        return modelAndView;
    }


    @PostMapping("/addSchema")
    public ModelAndView profileSchema(@ModelAttribute ProfileSchema profileSchema){
        System.out.println("profileSchema.getSchemaName() = " + profileSchema.getSchemaName());
        return new ModelAndView();
    }
















}
