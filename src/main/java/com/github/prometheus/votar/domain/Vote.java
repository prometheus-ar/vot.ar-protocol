package com.github.prometheus.votar.domain;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/** Represents a vote data within the VOT.AR protocol. */
public class Vote {

  /** District this vote belongs to, it is never null. */
  private Location district;

  /** List of candidates related to this vote, it is never null or empty. */
  private List<Candidate> candidates;

  /** Creates a new vote in a district for the specified candidates.
   * @param district District this vote belongs to. Cannot be null.
   * @param candidates List of candidates to vote. Cannot be null or empty.
   */
  public Vote(Location district, List<Candidate> candidates) {
    Validate.notNull(district, "The district cannot be null.");
    Validate.notEmpty(candidates, "The candidates cannot be null or empty.");
    this.district = district;
    this.candidates = candidates;
  }

  public Location getDistrict() {
    return district;
  }

  public List<Candidate> getCandidates() {
    return candidates;
  }

  /** Generates the vote representation as String.
   * @return A vote String representation as it is required by the protocol,
   *    never or empty null.
   */
  @Override
  public String toString() {
    String size = StringUtils.leftPad(String
        .valueOf(district.getCode().length()), 2, "0");
    String vote = size + district.getCode();

    for (Candidate candidate : candidates) {
      vote += candidate.getType().getId()
          + StringUtils.leftPad(candidate.getId(), 4, "0");
    }

    return vote;
  }
}
