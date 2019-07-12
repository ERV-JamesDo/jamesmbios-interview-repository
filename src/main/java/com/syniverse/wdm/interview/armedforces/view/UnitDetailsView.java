package com.syniverse.wdm.interview.armedforces.view;

import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.dto.UnitType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitDetailsView {

  private Long id;
  private Long combatPower;
  private UnitType type;

  public static UnitDetailsView fromUnit(final Unit unit) {
    return UnitDetailsView.builder().id(unit.getId()).combatPower(unit.getCombatPower()).type(unit.getType()).build();
  }
}
