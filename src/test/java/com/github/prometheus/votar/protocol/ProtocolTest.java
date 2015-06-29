package com.github.prometheus.votar.protocol;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.prometheus.votar.domain.Candidate;
import com.github.prometheus.votar.domain.Location;
import com.github.prometheus.votar.domain.Vote;
import com.github.prometheus.votar.domain.Candidate.CandidateType;
import com.github.prometheus.votar.domain.Location.LocationType;
import com.github.prometheus.votar.protocol.Protocol;
import com.github.prometheus.votar.protocol.Protocol.DataType;

public class ProtocolTest {

  @Test
  public void write() throws Exception {
    Location district = new Location(LocationType.DISTRICT, "CABA.1", null,
        "Comuna 1", "pin");
    List<Candidate> candidates = Arrays.asList(
        new Candidate(CandidateType.COMMUNE, "16.5533", "Clori Yelicic"),
        new Candidate(CandidateType.DEPUTY, "16.5297", "Roy Cortina"),
        new Candidate(CandidateType.GOVERNOR, "81.5292",
            "Luis Fernando Zamora"));
    Vote vote = new Vote(district, candidates);

    ByteArrayOutputStream output = new ByteArrayOutputStream(
        Protocol.BLOCKS * Protocol.BLOCK_SIZE);
    Protocol.write(output, DataType.VOTE, vote.toString());

    ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
    assertThat(Protocol.read(input), is("06CABA.1COM5533DIP5297JEF5292"));
  }

  @Test
  public void read() throws Exception {
    InputStream input = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("original-vote.bin");
    String voteStr = Protocol.read(input);
    assertThat(voteStr, is("06CABA.1COM5730DIP5492JEF5294"));
  }
}
