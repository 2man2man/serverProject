package com.thumann.server.helper.json;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.thumann.server.helper.date.DateUtil;
import com.thumann.server.helper.string.StringUtil;

public class JsonUtil {

	public static String getString(ObjectNode node, String property)
	{
		if (node == null) {
			return null;
		}
		else if (StringUtil.isEmpty(property)) {
			return null;
		}
		else if (!node.hasNonNull(property)) {
			return null;
		}
		JsonNode jsonNode = node.get(property);
		if (!(jsonNode instanceof TextNode)) {
			return null;
		}
		TextNode textNode = (TextNode) jsonNode;
		return textNode.asText();
	}

	public static Date getDate(ObjectNode node, String property)
	{
		if (node == null) {
			return null;
		}
		else if (StringUtil.isEmpty(property)) {
			return null;
		}
		else if (!node.hasNonNull(property)) {
			return null;
		}
		JsonNode jsonNode = node.get(property);
		if (!(jsonNode instanceof TextNode)) {
			return null;
		}
		TextNode textNode = (TextNode) jsonNode;
		String dateString = textNode.asText();
		if (StringUtil.isEmpty(dateString)) {
			return null;
		}
		return DateUtil.getDate(dateString);
	}

}
