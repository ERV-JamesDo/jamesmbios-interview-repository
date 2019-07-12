package com.syniverse.wdm.interview.armedforces.dto;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Army {

  private Long id;
  private String name;
  private ArmyType type;
  private List<Unit> units;

  public Long getMaxUnitId() {
    try {
      Unit unit = this.units
          .stream()
          .max(Comparator.comparing(Unit::getId))
          .orElseThrow(NoSuchElementException::new);
      return unit.getId();
    } catch (NoSuchElementException e) {
      return 0L;
    }
  }
}
