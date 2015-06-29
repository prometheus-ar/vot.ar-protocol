package com.github.prometheus.votar.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;

public class VoteParserTest {

  private ElectionRepository repository;
  private VoteParser parser;

  @Before
  public void setUp() {
    repository = new ElectionRepository(FilenameUtils
        .normalize("src/test/resources/data/"));
    parser = new VoteParser(repository);
  }

  @Test
  public void parse() {
    Vote vote = parser.parse("06CABA.1COM5533DIP5297JEF5292");
    assertThat(vote, is(notNullValue()));
    assertThat(vote.getDistrict().getCode(), is("CABA.1"));
    assertThat(vote.getCandidates().size(), is(3));
    assertThat(vote.getCandidates().get(0).getId(), is("5533"));
    assertThat(vote.getCandidates().get(1).getId(), is("5297"));
    assertThat(vote.getCandidates().get(2).getId(), is("5292"));
  }
}
