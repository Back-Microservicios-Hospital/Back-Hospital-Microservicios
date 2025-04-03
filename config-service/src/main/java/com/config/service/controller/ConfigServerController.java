package com.config.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiconfig")
public class ConfigServerController {

	@GetMapping("/alta")
	public String mensaje() {
		return "Servidor de configuración levantado!!";
	}
}
