package com.github.prometheus.votar.domain;

import org.apache.commons.lang3.Validate;

/** Represents a location, which could be any of the {@link LocationType}s.
 */
public class Location {

  /** Location types */
  public enum LocationType {
    /** Represents a electoral district. */
    CITY("Distrito"),

    /** Represents a district within a city. */
    DISTRICT("Comuna"),

    /** Represents a single branch within a district. */
    BRANCH("Establecimiento"),

    /** Represents a voting table within a branch. */
    TABLE("Mesa");

    private String id;

    private LocationType(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }

    public static LocationType of(String id) {
      for (LocationType location : values()) {
        if (location.getId().equals(id)) {
          return location;
        }
      }
      throw new RuntimeException("Location not found: " + id);
    }
  }

  private LocationType type;
  private String code;
  private String number;
  private String name;
  private String pin;

  public Location(LocationType type, String code, String number, String name,
      String pin) {
    Validate.notNull(type, "The type cannot be null.");
    Validate.notEmpty(code, "The code cannot be null.");
    Validate.notEmpty(name, "The name cannot be null.");
    this.type = type;
    this.code = code;
    this.number = number;
    this.name = name;
    this.pin = pin;
  }

  public LocationType getType() {
    return type;
  }

  public String getCode() {
    return code;
  }

  public String getNumber() {
    return number;
  }

  public String getName() {
    return name;
  }

  public String getPin() {
    return pin;
  }
}
