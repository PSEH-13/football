package com.sapient.football.controller;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "coach_name", "coach_country", "coach_age" })
public class Coach {

	@JsonProperty("coach_name")
	private String coachName;
	@JsonProperty("coach_country")
	private String coachCountry;
	@JsonProperty("coach_age")
	private String coachAge;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("coach_name")
	public String getCoachName() {
		return coachName;
	}

	@JsonProperty("coach_name")
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	@JsonProperty("coach_country")
	public String getCoachCountry() {
		return coachCountry;
	}

	@JsonProperty("coach_country")
	public void setCoachCountry(String coachCountry) {
		this.coachCountry = coachCountry;
	}

	@JsonProperty("coach_age")
	public String getCoachAge() {
		return coachAge;
	}

	@JsonProperty("coach_age")
	public void setCoachAge(String coachAge) {
		this.coachAge = coachAge;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}
