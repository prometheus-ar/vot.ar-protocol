package com.github.prometheus.votar.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.github.prometheus.votar.protocol.Protocol;

/** Parses votes previously read by the {@link Protocol}.
 */
public class VoteParser {

  private ElectionRepository electionRepository;

  public VoteParser(ElectionRepository electionRepository) {
    Validate.notNull(electionRepository, "The repository cannot be null.");
    this.electionRepository = electionRepository;
  }

  /** Parses vote data as it is read from the RFID tag.
   *
   * @param voteStr Vote data to parse. Cannot be null or empty.
   * @return A valid vote, never null.
   */
  public Vote parse(String voteStr) {
    Validate.notEmpty(voteStr, "The vote data cannot be null or empty.");
    int districtLength = Integer.parseInt(voteStr.substring(0, 2));
    String districtCode = voteStr.substring(2, districtLength + 2);
    String candidateItems = voteStr.substring(districtLength + 2);
    List<Candidate> candidates = new ArrayList<Candidate>();

    for (int i = 0; i < candidateItems.length(); i += 7) {
      String candidateStr = candidateItems.substring(i, i + 7);
      Candidate candidate = electionRepository
          .findCandidate(districtCode, candidateStr.substring(3));
      Validate.notNull(candidate, "Candidate not found: " + candidateStr);
      candidates.add(candidate);
    }

    return new Vote(electionRepository.findLocation(districtCode), candidates);
  }
}
