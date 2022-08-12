package com.zeer.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class ContactDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactDemoApplication.class, args);
	}


}
