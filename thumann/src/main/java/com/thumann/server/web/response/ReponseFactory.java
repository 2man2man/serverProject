package com.thumann.server.web.response;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.JsonNode;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ReponseFactory {

	public JsonNode createResponse(CreateJsonInterface object)
	{
		return object.createJson();
	}

}
