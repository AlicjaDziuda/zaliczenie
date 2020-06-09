package edu.iis.mto.testreactor.dishwasher;

import static org.hamcrest.MatcherAssert.assertThat;

import edu.iis.mto.testreactor.dishwasher.engine.Engine;
import edu.iis.mto.testreactor.dishwasher.pump.WaterPump;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

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

    @BeforeEach
    public void setUp(){
        dishWasher = new DishWasher(waterPump,engine,dirtFilter,door);
    }

}
