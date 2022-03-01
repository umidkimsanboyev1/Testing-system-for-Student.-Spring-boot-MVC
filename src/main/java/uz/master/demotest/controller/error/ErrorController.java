package uz.master.demotest.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String renderErrorPageGet(Model model, HttpServletRequest httpRequest) {
        int code = getErrorCode(httpRequest);
        model.addAttribute("code", code);
        switch (code) {
            case 400:
                return "error/400";
            case 403:
                return "error/403";
            case 404:
                return "error/404";
            case 401:
                return "error/401";
            case 500:
                return "error/500";
        }
        return "error/error";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}
