package com.github.prometheus.votar.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;

import com.github.prometheus.votar.domain.Candidate;
import com.github.prometheus.votar.domain.ElectionRepository;
import com.github.prometheus.votar.domain.Location;
import com.github.prometheus.votar.domain.Candidate.CandidateType;

/** Tests the {@link ElectionRepository} class. */
public class ElectionRepositoryTest {

  private ElectionRepository repository;

  @Before
  public void setUp() {
    repository = new ElectionRepository(FilenameUtils
        .normalize("src/test/resources/data/"));
  }

  @Test
  public void getCities() {
    List<Location> locations = repository.getCities();
    assertThat(locations.size(), is(1));
    assertThat(locations.get(0).getCode(), is("CABA"));
  }

  @Test
  public void getDistricts() {
    List<Location> locations = repository.getDistricts("CABA");
    assertThat(locations.get(0).getCode(), is("CABA.1"));
  }

  @Test
  public void getBranches() {
    List<Location> locations = repository.getBranches("CABA.1");
    assertThat(locations.get(0).getCode(), is("CABA.1.1"));
  }

  @Test
  public void getTables() {
    List<Location> locations = repository.getTables("CABA.1.1");
    assertThat(locations.get(0).getCode(), is("CABA.1.1.518"));
  }

  @Test
  public void getCandidates() {
    List<Candidate> candidates = repository.getCandidates("CABA.1");
    assertThat(candidates.get(0).getId(), is("5533"));
  }

  @Test
  public void getCandidates_withType() {
    List<Candidate> candidates = repository.getCandidates("CABA.1",
        CandidateType.DEPUTY);
    assertThat(candidates.get(0).getId(), is("5297"));
  }
}
