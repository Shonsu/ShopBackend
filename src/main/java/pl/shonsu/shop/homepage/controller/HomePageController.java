package pl.shonsu.shop.homepage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shonsu.shop.homepage.controller.dto.HomePageDto;
import pl.shonsu.shop.homepage.service.HomePageService;

@RestController
@RequiredArgsConstructor
public class HomePageController {
    private final HomePageService homePageService;

    @GetMapping("/homePage")
    @Cacheable(value = "homePage")
    public HomePageDto getHomePage(){
        return new HomePageDto(homePageService.getSaleProtucts());
    }
}
