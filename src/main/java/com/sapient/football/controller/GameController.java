package com.sapient.football.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GameController {

	@Autowired
	RestTemplate restTemplate;

	// @GetMapping(path = "/countries", produces = "application/json")
	public Country getCountry(String countryName) {
		Country country = null;
		ResponseEntity<List<Country>> responseEntity = restTemplate.exchange(
				"https://apiv2.apifootball.com/?action=get_countries&"
						+ "APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Country>>() {
				});
		Set<Country> countrySet = new HashSet<Country>();
		countrySet.addAll(responseEntity.getBody());
		Country c = new Country();
		c.setCountry_name(countryName);
		if (!countrySet.contains(c)) {
			return null;
		} else {
			country = countrySet.stream().filter(p -> p.getCountry_name().equals(countryName)).findAny().get();
		}
		return country;
	}

	public League getLeague(String countryId, String leagueName) {
		League league = null;
		ResponseEntity<List<League>> responseEntity = restTemplate.exchange(
				"https://apiv2.apifootball.com/?action=get_leagues&country_id=" + countryId + "&"
						+ "APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<League>>() {
				});
		Set<League> leagueSet = new HashSet<League>();
		leagueSet.addAll(responseEntity.getBody());
		League c = new League();
		c.setLeague_name(leagueName);
		if (!leagueSet.contains(c)) {
			return null;
		} else {
			league = leagueSet.stream().filter(p -> p.getLeague_name().equals(leagueName)).findAny().get();
		}
		return league;
	}

	@GetMapping(path = "/team_stand", produces = "application/json")
	public ResponseObject getTeamStand(@RequestParam(required = false) String countryName,
			@RequestParam(required = false) String leagueName, @RequestParam(required = false) String teamName) {
		Country country = null;
		League league = null;
		Standing standing = null;
		// Team team = null;
		if (countryName != null) {
			country = getCountry(countryName);
		}
		if (countryName != null && country == null) {
			return new ResponseObject();
		}
		if (leagueName != null && country != null) {
			league = getLeague(country.getCountry_id(), leagueName);
		}
		if (country == null && league == null) {
			return new ResponseObject();
		}
		ResponseEntity<List<Standing>> responseEntity = restTemplate.exchange(
				"https://apiv2.apifootball.com/?action=get_standings&league_id=" + league.getLeague_id() + "&"
						+ "APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Standing>>() {
				});
		Set<Standing> standingSet = new HashSet<Standing>();
		standingSet.addAll(responseEntity.getBody());
		Standing c = new Standing();
		c.setCountryName(countryName);
		c.setLeagueName(leagueName);
		c.setTeamName(teamName);
		// standing = stadingSet.stream().filter(p ->
		// {p.getCountryName().equals(countryName);}).findAny().get();
		for (Standing s : standingSet) {
			System.out.println(s.getCountryName() + "-" + s.getLeagueName() + "-" + s.getTeamName());
			if (s.getCountryName().equals(countryName) && s.getLeagueName().equals(leagueName)
					&& s.getTeamName().equals(teamName)) {
				standing = s;
				break;
			}
		}

		return prepareResponseObject(standing, country);

	}

	private ResponseObject prepareResponseObject(Standing standing, Country country) {
		ResponseObject responseObject = null;
		if (standing != null) {
			responseObject = new ResponseObject();
			responseObject.setCountry_id(country.getCountry_id());
			responseObject.setCountry_name(standing.getCountryName());
			responseObject.setLeague_id(standing.getLeagueId());
			responseObject.setLeague_name(standing.getLeagueName());
			responseObject.setTeam_id(standing.getTeamId());
			responseObject.setTeam_name(standing.getTeamName());
			responseObject.setOverAllLeaguePostion(standing.getOverallLeaguePosition());
		}
		return responseObject;

	}

	@GetMapping(path = "/standing", produces = "application/json")
	public List<Standing> getStanding(@RequestParam(required = false) String countryName,
			@RequestParam(required = false) String leagueName, @RequestParam(required = false) String teamName) {
		Country country = null;
		League league = null;
		Standing standing = null;
		// Team team = null;
		if (countryName != null) {
			country = getCountry(countryName);
		}
		if (countryName != null && country == null) {
			return new ArrayList<Standing>();
		}
		ResponseEntity<List<Standing>> responseEntity = restTemplate.exchange(
				"https://apiv2.apifootball.com/?action=get_standings&league_id=" + country.getCountry_id() + "&"
						+ "APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Standing>>() {
				});
		Set<Standing> standingSet = new HashSet<Standing>();
		standingSet.addAll(responseEntity.getBody());
		Standing c = new Standing();
		c.setCountryName(countryName);
		c.setLeagueName(leagueName);
		c.setTeamName(teamName);
		if (!standingSet.contains(c)) {
			return null;
		} else {
			// standing = stadingSet.stream().filter(p ->
			// {p.getCountryName().equals(countryName);}).findAny().get();
			for (Standing s : standingSet) {
				if (s.getCountryName().equals(countryName) && s.getLeagueName().equals(leagueName)
						&& s.getTeamName().equals(teamName)) {
					standing = s;
					break;
				}
			}
		}
		return responseEntity.getBody();

	}

}
