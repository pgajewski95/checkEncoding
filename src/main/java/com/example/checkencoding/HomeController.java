package com.example.checkencoding;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(@RequestParam(required = false) String sciezka,
                        @RequestParam(required = false) String rozszerzenie,

                        ModelMap modelMap) throws IOException {

        if (sciezka != null && rozszerzenie != null) {
            CheckEncoding checkEncoding = new CheckEncoding();
            modelMap.put("change", " " + checkEncoding.getEncode(sciezka, rozszerzenie));
            modelMap.put("sciezka", sciezka);
            modelMap.put("rozszerzenie", rozszerzenie);

        }


        return "index";
    }
}
