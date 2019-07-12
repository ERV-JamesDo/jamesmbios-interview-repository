package com.syniverse.wdm.interview.armedforces.dto;

import static com.syniverse.wdm.interview.armedforces.dto.ArmyType.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnitType {
  AIRCRAFT_CARRIER(NAVY), CORVETTE(NAVY), FIGHTER_JET(AIR_FORCE), BOMBER(AIR_FORCE), PARATROOPER(INFANTRY);

  private final ArmyType armyType;
}
