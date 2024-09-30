package org.fnkcode.springsecc4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class DemoController {

    @GetMapping(value = "/demo")
    public String demo() { return "demo";}
}
