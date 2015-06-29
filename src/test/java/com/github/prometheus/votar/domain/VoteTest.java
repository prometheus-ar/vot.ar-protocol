package com.github.prometheus.votar.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.prometheus.votar.domain.Candidate.CandidateType;
import com.github.prometheus.votar.domain.Location.LocationType;

public class VoteTest {

  @Test
  public void voteToString() {
    Location district = new Location(LocationType.DISTRICT, "CABA.1", null,
        "Comuna 1", "pin");
    List<Candidate> candidates = Arrays.asList(
        new Candidate(CandidateType.COMMUNE, "16.5730", "John Doe"),
        new Candidate(CandidateType.DEPUTY, "17.5492", "John Jackson"),
        new Candidate(CandidateType.GOVERNOR, "876.5294", "Jack Johnson"));

    Vote vote = new Vote(district, candidates);
    assertThat(vote.toString(), is("06CABA.1COM5730DIP5492JEF5294"));
  }
}
