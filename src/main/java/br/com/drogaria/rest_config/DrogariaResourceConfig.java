package br.com.drogaria.rest_config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

//http:localhost:8080/Drogaria/[nomeRest]
@ApplicationPath("rest")
public class DrogariaResourceConfig extends ResourceConfig {
	public DrogariaResourceConfig() {
		packages("br.com.drogaria.service");
	}
}
