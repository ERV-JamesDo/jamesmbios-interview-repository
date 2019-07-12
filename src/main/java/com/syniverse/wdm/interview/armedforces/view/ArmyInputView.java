package com.syniverse.wdm.interview.armedforces.view;

import java.util.ArrayList;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.ArmyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArmyInputView {

  private String name;
  private ArmyType type;

  public Army toArmy() {
    return Army.builder().name(this.name).type(this.type).units(new ArrayList<>()).build();
  }
}
