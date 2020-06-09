package edu.iis.mto.testreactor.dishwasher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.iis.mto.testreactor.dishwasher.engine.Engine;
import edu.iis.mto.testreactor.dishwasher.pump.WaterPump;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    FillLevel fillLevel = FillLevel.FULL;
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

    @Test void name() {

        when(door.closed()).thenReturn(true);
        dishWasher.start(programConfiguration);
        verify(door).closed();
    }
}
