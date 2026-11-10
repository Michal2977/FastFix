package com.fastfix.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TurnStileResponse {

    private boolean success;
    private  String hostName;
    private String challenge_ts;

    @JsonProperty("error_codes")
    private String[]error_codes;
}


