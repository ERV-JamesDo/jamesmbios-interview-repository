// $Id: CompanyResource.java 6699 2018-04-18 14:58:06Z g801797 $
// $URL: https://am1p-gen-ias-vcs001.syniverse.com/svn-am/obf/obf-rest/branches/obf_dev_Victor/src/main/java/com/syniverse/obf/company/ui/CompanyResource.java $
package com.syniverse.wdm.interview.armedforces.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.syniverse.wdm.interview.armedforces.repository.ArmedForcesRepository;
import com.syniverse.wdm.interview.armedforces.view.ArmyDetailsView;
import com.syniverse.wdm.interview.armedforces.view.ArmyInputView;
import com.syniverse.wdm.interview.armedforces.view.UnitDetailsView;
import com.syniverse.wdm.interview.armedforces.view.UnitInputView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/armed-forces/v1/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ArmedForcesController {

  private final ArmedForcesRepository armedForcesRepository;

  @ApiOperation(value = "Create an army", notes = "Returns the ID of the newly created army")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Long.class)})
  @PostMapping("/armies")
  public Long createArmy(@RequestBody final ArmyInputView army) {
    return this.armedForcesRepository.createArmy(Optional.ofNullable(army).map(ArmyInputView::toArmy).orElse(null));
  }

  @ApiOperation(value = "List the summary of all the armies", notes = "Returns a list of all armies")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArmyDetailsView.class, responseContainer = "List")})
  @GetMapping("/armies")
  public List<ArmyDetailsView> getArmies() {
    return this.armedForcesRepository.getArmies().stream().map(ArmyDetailsView::fromArmy).collect(Collectors.toList());
  }

  @ApiOperation(value = "Recruit a unit to the army", notes = "Returns the ID of the newly recruited unit")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Long.class)})
  @PostMapping("/armies/{armyId:[\\d]+}/units")
  public Long recruitUnit(@PathVariable(name = "armyId") final Long armyId, @RequestBody final UnitInputView unit) {
    return this.armedForcesRepository.recruitUnit(armyId, Optional.ofNullable(unit).map(UnitInputView::toUnit).orElse(null));
  }

  @ApiOperation(value = "Fetch all units of the army", notes = "Returns a list of all units of the army")
  @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UnitDetailsView.class, responseContainer = "List")})
  @GetMapping("/armies/{armyId:[\\d]+}/units")
  public List<UnitDetailsView> getUnitsOfArmy(@PathVariable(name = "armyId") final Long armyId) {
    return this.armedForcesRepository.getUnitsOfArmy(armyId).stream().map(UnitDetailsView::fromUnit).collect(Collectors.toList());
  }
}
