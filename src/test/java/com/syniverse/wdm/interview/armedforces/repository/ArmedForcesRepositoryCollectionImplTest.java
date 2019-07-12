package com.syniverse.wdm.interview.armedforces.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.syniverse.wdm.interview.armedforces.dto.Army;
import com.syniverse.wdm.interview.armedforces.dto.ArmyType;
import com.syniverse.wdm.interview.armedforces.dto.Unit;
import com.syniverse.wdm.interview.armedforces.dto.UnitType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArmedForcesRepositoryCollectionImplTest {

  @Autowired
  private ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl;

  @InjectMocks
  private ArmedForcesRepositoryCollectionImpl armedForcesRepositoryCollectionImpl2;

  @Test
  public void testGetArmyById() {
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

    // Test normal
    try {
      Army army1 = armedForcesRepositoryCollectionImpl.getArmyById(1L);
      assertThat(army1.getId(), is(1L));

      Army army2 = armedForcesRepositoryCollectionImpl.getArmyById(4L);
      assertThat(army2.getId(), is(4L));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateArmy() {
    // Test normal
    Army army01 = Army.builder().id(5L).name("South navy").type(ArmyType.NAVY)
        .units(Arrays.asList(
            Unit.builder().id(1L).combatPower(20L).type(UnitType.CORVETTE).build(),
            Unit.builder().id(2L).combatPower(80L).type(UnitType.AIRCRAFT_CARRIER).build(),
            Unit.builder().id(3L).combatPower(30L).type(UnitType.CORVETTE).build()))
        .build();
    long newArmyId01 = this.armedForcesRepositoryCollectionImpl.createArmy(army01);
    assertThat(newArmyId01, is(5L));
  }

//  @Test
  public void testCreateArmy_Exception() {
    // Test Exception
    MockitoAnnotations.initMocks(this);
    Map<Long, Army> armiesMock = new ConcurrentHashMap<>();
    ReflectionTestUtils.setField(armedForcesRepositoryCollectionImpl2, "armies", armiesMock);
    doReturn(100).when(armiesMock).size();
    Army army02 = Army.builder().id(6L).name("West navy").type(ArmyType.NAVY)
        .units(Arrays.asList(
            Unit.builder().id(1L).combatPower(10L).type(UnitType.CORVETTE).build(),
            Unit.builder().id(2L).combatPower(20L).type(UnitType.AIRCRAFT_CARRIER).build(),
            Unit.builder().id(3L).combatPower(30L).type(UnitType.CORVETTE).build()))
        .build();
    try {
      this.armedForcesRepositoryCollectionImpl2.createArmy(army02);
      fail("Cannot add more unit. Must be throw ResponseStatusException");
    } catch (ResponseStatusException e) {
      assertThat(e.getReason(), is("Cannot add more armies. You already have way too many to manage, Sir!"));
    } catch (Exception ex) {
      fail("Must be throw ResponseStatusException");
    }

  }

}
