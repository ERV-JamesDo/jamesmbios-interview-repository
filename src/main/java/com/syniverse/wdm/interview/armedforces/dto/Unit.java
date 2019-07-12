package com.syniverse.wdm.interview.armedforces.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Unit {

  private Long id;
  private UnitType type;
  private Long combatPower;
}
