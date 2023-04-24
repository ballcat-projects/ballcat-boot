package com.your.packages.admin;

import org.ballcat.springsecurity.oauth2.server.authorization.annotation.EnableOauth2AuthorizationServer;
import org.ballcat.springsecurity.oauth2.server.resource.annotation.EnableOauth2ResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Hccake
 */
@EnableOauth2AuthorizationServer
@EnableOauth2ResourceServer
@MapperScan({ "com.your.packages.**.mapper" })
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
