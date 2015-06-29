package com.github.prometheus.votar.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/** Represents a candidate related to a {@link Vote}.
 */
public class Candidate {

  public enum CandidateType {
    COMMUNE("COM"),
    DEPUTY("DIP"),
    GOVERNOR("JEF");

    private String id;

    private CandidateType(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }

    public static CandidateType of(String id) {
      Validate.notEmpty(id, "The id cannot be null.");

      for (CandidateType type : values()) {
        if (type.getId().equals(id)) {
          return type;
        }
      }
      throw new RuntimeException("Type not found: " + id);
    }
  }

  private CandidateType type;
  private String id;
  private String list;
  private String name;

  public Candidate(CandidateType type, final String code,
      final String name) {
    Validate.notNull(type, "The type cannot be null.");
    Validate.notEmpty(code, "The code cannot be null or empty.");
    Validate.notEmpty(name, "The name cannot be null or empty.");
    Validate.isTrue(code.indexOf(".") > 0, "Invalid code format: " + code);

    this.type = type;
    this.id = StringUtils.substringAfter(code, ".");
    this.list = StringUtils.substringBefore(code, ".");
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getList() {
    return list;
  }

  public String getName() {
    return name;
  }

  public CandidateType getType() {
    return type;
  }
}
