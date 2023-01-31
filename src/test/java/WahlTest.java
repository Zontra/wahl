import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created: 31.01.2023
 *
 * @author Patrick Schneeweis (patrick)
 */
public class WahlTest {
    static Kandidat[] k = new Kandidat[5];

    static {
        k[0] = new Kandidat("Dominik Hofmann");
        k[1] = new Kandidat("Kilian Prager");
        k[2] = new Kandidat("Niklas Hochstöger");
        k[3] = new Kandidat("Paul Pfiel");
        k[4] = new Kandidat("Raid Alarkhanov");
    }

    @Test
    void testifeingabeKorrektTest() {
       // assertThat(Wahl.testifeingabeKorrekt("Dominik Hofmann", 1)).isEqualTo(2);
    }
}
