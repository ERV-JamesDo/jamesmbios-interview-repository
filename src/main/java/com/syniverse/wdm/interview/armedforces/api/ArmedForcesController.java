// $Id: CompanyResource.java 6699 2018-04-18 14:58:06Z g801797 $
// $URL: https://am1p-gen-ias-vcs001.syniverse.com/svn-am/obf/obf-rest/branches/obf_dev_Victor/src/main/java/com/syniverse/obf/company/ui/CompanyResource.java $
package com.syniverse.wdm.interview.armedforces.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.ArmyType;
import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.repository.ArmedForcesRepository;
import com.syniverse.wdm.interview.armedforces.view.ArmyDetailsView;
import com.syniverse.wdm.interview.armedforces.view.ArmyInputView;
import com.syniverse.wdm.interview.armedforces.view.ArmySummaryView;
import com.syniverse.wdm.interview.armedforces.view.UnitDetailsView;
import com.syniverse.wdm.interview.armedforces.view.UnitInputView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/armed-forces/v1/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ArmedForcesController {

  private final ArmedForcesRepository armedForcesRepository;

  @ApiOperation(value = "Create an army", notes = "Returns the ID of the newly created army")
  @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Long.class) })
  @PostMapping("/armies")
  public Long createArmy(@RequestBody final ArmyInputView army) {
    return this.armedForcesRepository.createArmy(Optional.ofNullable(army).map(ArmyInputView::toArmy).orElse(null));
  }

  @ApiOperation(value = "List the summary of all the armies", notes = "Returns a list of all armies")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class, responseContainer = "List") })
  @GetMapping("/armies")
  public List<ArmyDetailsView> getArmies() {
    return this.armedForcesRepository.getArmies().stream().map(ArmyDetailsView::fromArmy).collect(Collectors.toList());
  }

  @ApiOperation(value = "Recruit a unit to the army", notes = "Returns the ID of the newly recruited unit")
  @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Long.class) })
  @PostMapping("/army/{armyId:[\\d]+}/unit")
  public Long recruitUnit(@PathVariable(name = "armyId") final Long armyId,
      @Valid @RequestBody final UnitInputView unit, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Combat power must between 1 and 100");
    }
    return this.armedForcesRepository.recruitUnit(armyId,
        Optional.ofNullable(unit).map(UnitInputView::toUnit).orElse(null));
  }

  @ApiOperation(value = "List armies of a given type", notes = "Returns a list of all armies of a given type")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class, responseContainer = "List") })
  @GetMapping("/armies/armyType/{armyType}")
  public List<ArmyDetailsView> getArmiesByType(
      @PathVariable @ApiParam(value = "army type", required = true, type = "string") ArmyType armyType) {
    return this.armedForcesRepository.getArmies().stream()
        .filter(army -> army.getType().equals(armyType))
        .map(ArmyDetailsView::fromArmy)
        .collect(Collectors.toList());
  }

  @ApiOperation(value = "Fetch the army's details", notes = "Returns detailed information of the army")
  @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class) })
  @GetMapping("/army/{armyId:[\\d]+}")
  public ArmyDetailsView getArmyDetail(@PathVariable(name = "armyId") final Long armyId) {
    return ArmyDetailsView.fromArmy(this.armedForcesRepository.getArmyById(armyId));
  }

  @ApiOperation(value = "Fetch all units of the army. Filter by given param", notes = "Returns a list of all or filtered units of the army."
      + "If sorted = true then return list Units sorted by combat power descending")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class, responseContainer = "List") })
  @GetMapping("/army/{armyId:[\\d]+}/units")
  public List<UnitDetailsView> getUnitsOfArmy(
      @PathVariable(name = "armyId") final Long armyId,
      @RequestParam(value = "sorted", defaultValue = "false") boolean sorted,
      @RequestParam(value = "powerLimit", defaultValue = "0") long powerLimit) {
    Army army = this.armedForcesRepository.getArmyById(armyId);
    Stream<Unit> unitStream = army.getUnits().stream();
    if (powerLimit > 0) {
      unitStream = unitStream.filter(unit -> unit.getCombatPower() >= powerLimit);
    }
    if (sorted) {
      unitStream = unitStream.sorted(Comparator.comparingLong(Unit::getCombatPower).reversed());
    }
    return unitStream.map(UnitDetailsView::fromUnit).collect(Collectors.toList());
  }

  @ApiOperation(value = "Fetch the unit details", notes = "Fetch detail of an unit by given army id and unit id")
  @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = UnitDetailsView.class) })
  @GetMapping("/army/{armyId:[\\d]+}/unit/{unitId:[\\d]+}")
  public UnitDetailsView getUnitDetail(@PathVariable(name = "armyId") final Long armyId,
      @PathVariable(name = "unitId") final Long unitId) {
    Army army = this.armedForcesRepository.getArmyById(armyId);
    return Optional
        .of(army.getUnits().stream().filter(u -> u.getId().equals(unitId)).map(UnitDetailsView::fromUnit).findFirst())
        .get()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "That unit does not seem to exist in this army, Sir!"));
  }

  @ApiOperation(value = "The given unit killed/destroyed (removed from the army)", notes = "Remove unit by given army id and unit id. "
      + "If unit id is 0, use unit that have max combat power")
  @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = UnitDetailsView.class) })
  @DeleteMapping("/army/{armyId:[\\d]+}/unit/{unitId:[\\d]+}")
  public UnitDetailsView removeUnit(@PathVariable(name = "armyId") final Long armyId,
      @PathVariable(name = "unitId") final Long unitId) {
    Army army = this.armedForcesRepository.getArmyById(armyId);
    Optional<Unit> toBeRemovedUnit = unitId > 0
        ? army.getUnits().stream().filter(u -> u.getId().equals(unitId)).findFirst()
        : army.getUnits().stream().max(Comparator.comparingLong(Unit::getCombatPower));
    if (!toBeRemovedUnit.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That unit does not seem to exist in this army, Sir!");
    }
    List<Unit> units = new ArrayList<>(army.getUnits());
    units.remove(toBeRemovedUnit.get());
    // When the last unit is removed from the army – the army gets removed from the armed forces
    if (units.isEmpty()) {
      this.armedForcesRepository.removeArmy(armyId);
    } else {
      army.setUnits(units);
    }
    return UnitDetailsView.fromUnit(toBeRemovedUnit.get());
  }

  @ApiOperation(value = "Fetch armed forces executive summary", notes = "Get summary of the armed forces")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Success", response = ArmySummaryView.class, responseContainer = "List") })
  @GetMapping("/armies/summary")
  public ArmySummaryView summary() {
    return this.armedForcesRepository.summary();
  }

}
