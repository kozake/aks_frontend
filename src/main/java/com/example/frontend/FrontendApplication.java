package com.example.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}

	public static class Service {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Controller()
	@RequestMapping("/home")
	public static class HomeController {

		private RestTemplate restTemplate ;

		@Value("${app.backend.url}")
		private String appBackendUrl;

		public HomeController(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}

		@GetMapping()
		public String init(Model model) {
			System.out.println("frontend start.");
			System.out.println("  appBackendUrl=[" + appBackendUrl + "]");

			Service service = this.restTemplate.getForObject(appBackendUrl + "/service", Service.class);
			model.addAttribute("name", service.getName());
			System.out.println("frontend end.");
			return "home";
		}
	}

}
