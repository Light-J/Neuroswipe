package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.UserResponse;
import com.nsa.cubric.application.services.UserResponseServiceStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserResponseResult {

	private UserResponseServiceStatic responsesService;

	@Autowired
	public UserResponseResult(UserResponseServiceStatic aRepo) {
		responsesService = aRepo;
	}

	private static final Logger LOG = LoggerFactory.getLogger(UserResponseResult.class);

	@RequestMapping(value = "/response", method = RequestMethod.GET)
	public String getAllUserResponse(WebRequest webRequest, Model model) {
		LOG.debug("Handling GET request to /response/");

		Map<Integer, Map<String, Integer>> userResponseMap = new HashMap<>();
		List<UserResponse> responsesList = responsesService.getAll();
		convertResultToMap(userResponseMap, responsesList);
		model.addAttribute("userResponseMap", userResponseMap);
		return "all_user_response_result";
	}

	@RequestMapping(value = "/response/{userProfileId}", method = RequestMethod.GET)
	public String getSingleUserResponse(@PathVariable("userProfileId") String userProfileId, WebRequest webRequest,
			Model model) {
		LOG.debug("Handling GET request to /response/");

		Map<Integer, Map<String, Integer>> userResponseMap = new HashMap<>();
		List<UserResponse> responsesList = responsesService.getUserResponses(userProfileId);
		convertResultToMap(userResponseMap, responsesList);
		model.addAttribute("userResponseMap", userResponseMap);
		return "single_user_response_result";
	}

	/**
	 * Method used to convert DB result to Map
	 * 
	 * @param userResponseMap
	 * @param responsesList
	 */
	private void convertResultToMap(Map<Integer, Map<String, Integer>> userResponseMap,
			List<UserResponse> responsesList) {
		responsesList.forEach(userResponse -> {
			Map<String, Integer> userResponseCountMap = new HashMap<>();
			if (userResponseMap.containsKey(userResponse.getScanId())) {
				userResponseCountMap = userResponseMap.get(userResponse.getScanId());
				if (userResponse.getResponse()) {
					if (null != userResponseCountMap.get("yes")) {
						userResponseCountMap.put("yes", userResponseCountMap.get("yes") + 1);
					}
					else {
						userResponseCountMap.put("yes", 1);
					}
				}
				else {
					if (null != userResponseCountMap.get("no")) {
						userResponseCountMap.put("no", userResponseCountMap.get("no") + 1);
					}
					else {
						userResponseCountMap.put("no", 1);
					}
				}
			}
			else {
				if (userResponse.getResponse()) {
					userResponseCountMap.put("yes", 1);
				}
				else {
					userResponseCountMap.put("no", 1);
				}
			}
			userResponseMap.put(userResponse.getScanId(), userResponseCountMap);
		});
	}
}