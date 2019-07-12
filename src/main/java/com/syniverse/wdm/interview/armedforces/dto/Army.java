package com.syniverse.wdm.interview.armedforces.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Army {

  private Long id;
  private String name;
  private ArmyType type;
  private List<Unit> units;
}
