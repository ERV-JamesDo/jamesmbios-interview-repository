package com.syniverse.wdm.interview.armedforces.repository;

import java.util.List;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.view.ArmySummaryView;

import org.springframework.stereotype.Repository;

@Repository
public interface ArmedForcesRepository {

  public Long createArmy(Army army);

  public List<Army> getArmies();

  public Army getArmyById(Long armyId);

  public Long recruitUnit(Long armyId, Unit unit);

  public List<Unit> getUnitsOfArmy(Long armyId);

  public void removeArmy(Long armyId);

  public ArmySummaryView summary();
}
