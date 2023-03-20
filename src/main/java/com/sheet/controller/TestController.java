package com.sheet.controller;

import com.sheet.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final SheetService sheetService;

    @GetMapping("/test/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/sheet/read")
    public String readSheet() {
        sheetService.readSheet();
        return "Read Sheet";
    }

    @GetMapping("/sheet/write")
    public String writeSheet() {
        sheetService.writeSheet();
        return "Write Sheet";
    }
}
