package com.github.prometheus.votar.domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.json.JSONArray;
import org.json.JSONObject;

import com.github.prometheus.votar.domain.Candidate.CandidateType;
import com.github.prometheus.votar.domain.Location.LocationType;

/** Factory to read VOT.AR data files in JSON format.
 */
public class ElectionRepository {

  private static final String FILE_LOCATIONS = "Ubicaciones_1.json";
  private static final String FILE_CANDIDATES = "Candidatos.json";

  private File dataDir;

  public ElectionRepository(String dataDir) {
    this.dataDir = new File(dataDir);
    Validate.isTrue(this.dataDir.exists(), "The directory doesn't exist.");
  }

  public List<Location> getCities() {
    return getLocations(LocationType.CITY, null);
  }

  public List<Location> getDistricts(String cityCode) {
    return getLocations(LocationType.DISTRICT, cityCode);
  }

  public List<Location> getBranches(String districtCode) {
    return getLocations(LocationType.BRANCH, districtCode);
  }

  public List<Location> getTables(String branchCode) {
    return getLocations(LocationType.TABLE, branchCode);
  }

  public Location findLocation(String id) {
    JSONArray jsonLocations = readList(FILE_LOCATIONS);

    for (int i = 0; i < jsonLocations.length(); i++) {
      JSONObject jsonLocation = jsonLocations.getJSONObject(i);
      String code = jsonLocation.getString("codigo");
      if (code.equals(id)) {
        return new Location(LocationType.of(jsonLocation.optString("clase")),
            code, jsonLocation.optString("numero"),
            jsonLocation.getString("descripcion"),
            jsonLocation.getString("pin"));
      }
    }

    return null;
  }

  /** Returns the list of candidates in the specified district.
   * @param districtCode District of the required candidates. Cannot be null or
   *    empty.
   * @return A list of candidates, never null.
   */
  public List<Candidate> getCandidates(String districtCode) {
    return getCandidates(districtCode, null);
  }

  /** Returns the list of candidates in the specified district.
   * @param districtCode District of the required candidates. Cannot be null or
   *    empty.
   * @param type Required candidate type. Can be null.
   * @return A list of candidates, never null.
   */
  public List<Candidate> getCandidates(String districtCode,
      CandidateType type) {
    Validate.notEmpty(districtCode, "The district cannot be null or empty.");

    JSONArray jsonCandidates = readList(FilenameUtils
        .normalize(districtCode + "/" + FILE_CANDIDATES));
    List<Candidate> candidates = new ArrayList<Candidate>();

    for (int i = 0; i < jsonCandidates.length(); i++) {
      JSONObject jsonCandidate = jsonCandidates.getJSONObject(i);
      String category = jsonCandidate.getString("cod_categoria");

      boolean isSpecial = jsonCandidate.getString("cod_lista").equals("BLC");
      boolean valid = type == null
          || (type != null && type.getId().equals(category));

      if (!isSpecial && valid) {
        candidates.add(new Candidate(CandidateType.of(category),
            jsonCandidate.getString("codigo"),
            jsonCandidate.getString("nombre")));
      }
    }

    return candidates;
  }

  /** Finds a candidate in the specified district.
   * @param districtCode District code. Cannot be null or empty.
   * @param candidateId Id of the required candidate. Cannot be null or empty.
   * @return A candidate, or null if it doesn't exist.
   */
  public Candidate findCandidate(String districtCode,
      String candidateId) {
    Validate.notEmpty(districtCode, "The district code cannot be null.");
    Validate.notEmpty(candidateId, "The candidate id cannot be null or empty.");

    for (Candidate candidate : getCandidates(districtCode)) {
      if (candidate.getId().equals(candidateId)) {
        return candidate;
      }
    }

    return null;
  }

  private List<Location> getLocations(LocationType type, String parentId) {
    JSONArray jsonLocations = readList(FILE_LOCATIONS);
    List<Location> locations = new ArrayList<Location>();

    for (int i = 0; i < jsonLocations.length(); i++) {
      JSONObject jsonLocation = jsonLocations.getJSONObject(i);
      String code = jsonLocation.getString("codigo");
      String id = StringUtils.substringAfterLast(code, ".");

      if (parentId != null) {
        id = parentId + "." + id; 
      }

      boolean matches = id.isEmpty() || code.equals(id);

      if (matches && type.getId().equals(jsonLocation.getString("clase"))) {
        locations.add(new Location(type, code, jsonLocation.optString("numero"),
            jsonLocation.getString("descripcion"),
            jsonLocation.getString("pin")));
      }
    }

    return locations;
  }

  private JSONArray readList(String fileName) {
    File file = new File(dataDir, fileName);
    try {
      return new JSONArray(FileUtils.readFileToString(file));
    } catch (IOException cause) {
      throw new RuntimeException("Cannot read file " + file, cause);
    }
  }
}
