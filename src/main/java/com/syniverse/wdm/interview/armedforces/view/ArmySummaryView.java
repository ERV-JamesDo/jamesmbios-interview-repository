package com.syniverse.wdm.interview.armedforces.view;

import java.util.List;

import com.syniverse.wdm.interview.armedforces.dto.ArmyType;
import com.syniverse.wdm.interview.armedforces.dto.UnitType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArmySummaryView {

  private Long totalArmy;
  private Long totalUnit;
  private List<ArmyType> armytypes;
  private List<UnitType> unitType;
  private Long combatPower;
  private ArmyDetailsView strongestArmy;
  private ArmyDetailsView weakestArmy;
}
