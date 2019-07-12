package com.syniverse.wdm.interview.armedforces.view;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.ArmyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArmyDetailsView {

  private Long id;
  private String name;
  private ArmyType type;

  public static ArmyDetailsView fromArmy(final Army army) {
    return ArmyDetailsView.builder().id(army.getId()).name(army.getName()).type(army.getType()).build();
  }
}
