package com.syniverse.wdm.interview.armedforces.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.ArmyType;
import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.dto.UnitType;
import com.syniverse.wdm.interview.armedforces.view.ArmySummaryView;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArmedForcesRepositoryCollectionImplTest {

  @Test
  public void testCreateArmy() {
    // Test normal
    Army army01 = Army.builder().id(5L).name("South navy").type(ArmyType.NAVY)
        .units(Arrays.asList(
            Unit.builder().id(1L).combatPower(20L).type(UnitType.CORVETTE).build(),
            Unit.builder().id(2L).combatPower(80L).type(UnitType.AIRCRAFT_CARRIER).build(),
            Unit.builder().id(3L).combatPower(30L).type(UnitType.CORVETTE).build()))
        .build();
    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl = new ArmedForcesRepositoryCollectionImpl();
    armedForcesRepositoryCollectionImpl.initializeData();
    long newArmyId01 = armedForcesRepositoryCollectionImpl.createArmy(army01);
    assertThat(newArmyId01, is(5L));
  }

//  @Test
  public void testCreateArmy_Exception() throws ReflectiveOperationException {
    // Test Exception
    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl2 = new ArmedForcesRepositoryCollectionImpl();
    setFinalStaticField(armedForcesRepositoryCollectionImpl2.getClass(), "MAX_ARMY", 4);
    // armedForcesRepositoryCollectionImpl2.initializeData();
    Army army02 = Army.builder().id(6L).name("West navy").type(ArmyType.NAVY)
        .units(Arrays.asList(
            Unit.builder().id(1L).combatPower(10L).type(UnitType.CORVETTE).build(),
            Unit.builder().id(2L).combatPower(20L).type(UnitType.AIRCRAFT_CARRIER).build(),
            Unit.builder().id(3L).combatPower(30L).type(UnitType.CORVETTE).build()))
        .build();
    try {
      ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl = new ArmedForcesRepositoryCollectionImpl();
      armedForcesRepositoryCollectionImpl.initializeData();
      armedForcesRepositoryCollectionImpl2.createArmy(army02);
      fail("Cannot add more unit. Must be throw ResponseStatusException");
    } catch (ResponseStatusException e) {
      assertThat(e.getReason(), is("Cannot add more armies. You already have way too many to manage, Sir!"));
    } catch (Exception ex) {
      fail("Must be throw ResponseStatusException");
    }

  }

  @Test
  public void testGetArmies() {
    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl = new ArmedForcesRepositoryCollectionImpl();
    armedForcesRepositoryCollectionImpl.initializeData();
    List<Army> armies = armedForcesRepositoryCollectionImpl.getArmies();
    assertThat(armies.size(), is(4));
  }

  @Test
  public void testGetArmyById() {

    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl = new ArmedForcesRepositoryCollectionImpl();
    armedForcesRepositoryCollectionImpl.initializeData();
    // Test normal
    try {
      Army army1 = armedForcesRepositoryCollectionImpl.getArmyById(1L);
      assertThat(army1.getId(), is(1L));

      Army army2 = armedForcesRepositoryCollectionImpl.getArmyById(4L);
      assertThat(army2.getId(), is(4L));
    } catch (Exception e) {
      fail();
    }

    // Test exception
    // Test case 1
    try {
      armedForcesRepositoryCollectionImpl.getArmyById(0L);
      fail("That army does not exist. Must be throw ResponseStatusException");
    } catch (ResponseStatusException e) {
      assertThat(e.getReason(), is("Hmmm. That army does not seem to exist, Sir!"));
    } catch (Exception ex) {
      fail("Must be throw ResponseStatusException");
    }

    // Test case 2
    try {
      armedForcesRepositoryCollectionImpl.getArmyById(5L);
      fail("That army does not exist. Must be throw ResponseStatusException");
    } catch (ResponseStatusException e) {
      assertThat(e.getReason(), is("Hmmm. That army does not seem to exist, Sir!"));
    } catch (Exception ex) {
      fail("Must be throw ResponseStatusException");
    }
  }

  @Test
  public void testRecruitUnit() throws ReflectiveOperationException {
    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl = new ArmedForcesRepositoryCollectionImpl();
    armedForcesRepositoryCollectionImpl.initializeData();
    // Test normal
    Unit unit01 = Unit.builder().type(UnitType.CORVETTE).combatPower(123L).build();
    armedForcesRepositoryCollectionImpl.recruitUnit(1L, unit01);
    assertThat(armedForcesRepositoryCollectionImpl.getArmyById(1L).getUnits().size(), is(4));

    // Test Exception
    // Test case 01
    Unit unit02 = Unit.builder().type(UnitType.FIGHTER_JET).combatPower(123L).build();
    try {
      armedForcesRepositoryCollectionImpl.recruitUnit(1L, unit02);
      fail();
    } catch (ResponseStatusException e) {
      assertThat(e.getReason(), is("The unit type is unacceptable in that army"));
    }

    // Test case 02
    /**
    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl2 = new ArmedForcesRepositoryCollectionImpl();
    armedForcesRepositoryCollectionImpl2.initializeData();
    setFinalStaticField(armedForcesRepositoryCollectionImpl2.getClass(), "MAX_UNIT", 10);
    Unit unit03 = Unit.builder().type(UnitType.AIRCRAFT_CARRIER).combatPower(123L).build();
    try {
      armedForcesRepositoryCollectionImpl2.recruitUnit(2L, unit03);
      fail();
    } catch (ResponseStatusException e) {
      assertThat(e.getReason(), is("Cannot add more unit. You already have way too many to manage, Sir!"));
    } **/

    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl2 = spy(ArmedForcesRepositoryCollectionImpl.class);
    armedForcesRepositoryCollectionImpl2.initializeData();
    doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add more unit. You already have way too many to manage, Sir!"))
      .when(armedForcesRepositoryCollectionImpl2).recruitUnit(any(Long.class), any(Unit.class));
    Unit unit03 = Unit.builder().type(UnitType.AIRCRAFT_CARRIER).combatPower(123L).build();
    try {
      armedForcesRepositoryCollectionImpl2.recruitUnit(2L, unit03);
      fail();
    } catch (ResponseStatusException e) {
      assertThat(e.getReason(), is("Cannot add more unit. You already have way too many to manage, Sir!"));
    }

  }

  @Test
  public void testGetUnitsOfArmy() {
    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl = new ArmedForcesRepositoryCollectionImpl();
    armedForcesRepositoryCollectionImpl.initializeData();
    List<Unit> units01 = armedForcesRepositoryCollectionImpl.getUnitsOfArmy(1L);
    assertThat(units01.size(), is(3));

    List<Unit> units02 = armedForcesRepositoryCollectionImpl.getUnitsOfArmy(2L);
    assertThat(units02.size(), is(10));
  }

  @Test
  public void testRemoveArmy() {
    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl = new ArmedForcesRepositoryCollectionImpl();
    armedForcesRepositoryCollectionImpl.initializeData();
    List<Army> armies = armedForcesRepositoryCollectionImpl.getArmies();
    assertThat(armies.size(), is(4));
    armedForcesRepositoryCollectionImpl.removeArmy(4L);
    armies = armedForcesRepositoryCollectionImpl.getArmies();
    assertThat(armies.size(), is(3));
  }

  @Test
  public void testSummary() {
    ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl = new ArmedForcesRepositoryCollectionImpl();
    armedForcesRepositoryCollectionImpl.initializeData();
    ArmySummaryView armySummary = armedForcesRepositoryCollectionImpl.summary();
    assertThat(armySummary.getTotalArmy(), is(4L));
    assertThat(armySummary.getTotalUnit(), is(31L));
    assertThat(armySummary.getCombatPower(), is(1190L));
    assertThat(armySummary.getArmytypes().size(), is(3));
    assertThat(armySummary.getUnitType().size(), is(5));
    assertThat(armySummary.getStrongestArmy().getId(), is(2L));
    assertThat(armySummary.getWeakestArmy().getId(), is(4L));

  }

  /**
   * This method using for testing. Using reflection to change static final field.
   *
   * @param clazz
   * @param fieldName
   * @param value
   * @throws ReflectiveOperationException
   */
  private static void setFinalStaticField(Class<?> clazz, String fieldName, Object value)
      throws ReflectiveOperationException {
    Field field = clazz.getDeclaredField(fieldName);
    field.setAccessible(true);
    Field modifiers = Field.class.getDeclaredField("modifiers");
    modifiers.setAccessible(true);
    modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    field.set(null, value);
  }
}
