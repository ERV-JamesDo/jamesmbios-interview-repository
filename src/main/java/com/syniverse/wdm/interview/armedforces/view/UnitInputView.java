package com.syniverse.wdm.interview.armedforces.view;

import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.dto.UnitType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitInputView {

  private Long combatPower;
  private UnitType type;

  public Unit toUnit() {
    return Unit.builder().combatPower(this.combatPower).type(this.type).build();
  }
}
