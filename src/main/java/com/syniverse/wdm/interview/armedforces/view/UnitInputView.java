package com.syniverse.wdm.interview.armedforces.view;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.dto.UnitType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitInputView {
  @NotNull
  @Min(1)
  @Max(100)
  private Long combatPower;
  private UnitType type;

  public Unit toUnit() {
    return Unit.builder().combatPower(this.combatPower).type(this.type).build();
  }
}
