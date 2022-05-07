package sayollo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayollo.test.dto.RequestDto;
import sayollo.test.dto.StatsDto;
import sayollo.test.service.IAppService;

@RestController
public class AppController {
    IAppService appService;

    @Autowired
    public AppController(IAppService appService) {
        this.appService = appService;
    }

    @PostMapping(value = "/ad", produces = { "application/xml;charset=UTF-8" })
    String getAd(@RequestBody RequestDto requestDto){
       return appService.getAd(requestDto);
    }

    @PostMapping("/impression")
    void impression(@RequestBody  RequestDto requestDto){
        appService.impression(requestDto);
    }

    @GetMapping("/stats/{filterType}/{filter}")
    StatsDto getStats(@PathVariable String filterType, @PathVariable String filter){
        return appService.getStats(filterType, filter);
    }
}
