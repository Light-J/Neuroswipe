package com.nsa.cubric.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping(value = "/ratings")
public class Ratings {
	private static final Logger LOG = LoggerFactory.getLogger(Ratings.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showTutorialOverview(WebRequest webRequest, Model model) {
		LOG.debug("Handling GET request to /ratings/");
		return "ratings";
	}

	@RequestMapping(value = "/after25", method = RequestMethod.GET)
	public String after25(WebRequest webRequest, Model model){
		LOG.debug("Handling GET request to /ratings/after25/");
		return "ratings_after_25";
	}

	@RequestMapping(value = "/after50", method = RequestMethod.GET)
	public String after50(WebRequest webRequest, Model model){
		LOG.debug("Handling GET request to /ratings/after25/");
		return "ratings_after_50";
	}
}
