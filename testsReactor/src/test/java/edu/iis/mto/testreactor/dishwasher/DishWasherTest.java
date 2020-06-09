package edu.iis.mto.testreactor.dishwasher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.iis.mto.testreactor.dishwasher.engine.Engine;
import edu.iis.mto.testreactor.dishwasher.engine.EngineException;
import edu.iis.mto.testreactor.dishwasher.pump.PumpException;
import edu.iis.mto.testreactor.dishwasher.pump.WaterPump;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DishWasherTest {

    @Test
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

    @Mock
    DirtFilter dirtFilter;

    @Mock
    Door door;

    @Mock
    Engine engine;

    @Mock
    WaterPump waterPump;

    DishWasher dishWasher;
    ProgramConfiguration programConfiguration;
    FillLevel fillLevel = FillLevel.HALF;
    WashingProgram washingProgram = WashingProgram.INTENSIVE;
    boolean tablets = true;

    @BeforeEach
    public void setUp(){
        dishWasher = new DishWasher(waterPump,engine,dirtFilter,door);
        programConfiguration = ProgramConfiguration.builder()
                                                   .withProgram(washingProgram)
                                                   .withTabletsUsed(tablets)
                                                   .withFillLevel(fillLevel).build();
    }

    @Test void dishWasherShouldCallProperlyMethods() throws PumpException, EngineException {
        when(door.closed()).thenReturn(true); //drzwi zamkniete
        when(dirtFilter.capacity()).thenReturn(51.0d);
        dishWasher.start(programConfiguration);

        InOrder order = Mockito.inOrder(dirtFilter,engine,door,waterPump);
        order.verify(door).closed();
        order.verify(dirtFilter).capacity();
        order.verify(waterPump).pour(fillLevel);
        order.verify(engine).runProgram(washingProgram);
        order.verify(waterPump).drain();
        order.verify(door).unlock();
    }

    @Test void dishWasherShouldResultWithSuccess() throws PumpException, EngineException {
        when(door.closed()).thenReturn(true); //drzwi zamkniete
        when(dirtFilter.capacity()).thenReturn(51.0d);
        RunResult runResult = dishWasher.start(programConfiguration);
        RunResult expected = RunResult.builder().withStatus(Status.SUCCESS).withRunMinutes(120).build();
        assertEquals(expected,runResult);
    }

    @Test void dishWasherShouldFinishedWithDoorOpenError() throws PumpException, EngineException {
        when(door.closed()).thenReturn(false);
        RunResult runResult = dishWasher.start(programConfiguration);
        RunResult expected = RunResult.builder().withStatus(Status.DOOR_OPEN).withRunMinutes(0).build();
        assertTrue(runResult==expected);
    }
}
