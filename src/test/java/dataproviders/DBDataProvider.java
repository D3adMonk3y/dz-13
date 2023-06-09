package dataproviders;

import org.testng.annotations.DataProvider;
import utils.DBReader;


public class DBDataProvider {

    @DataProvider(name = "cars")
    public static Object[][] cars() {
        return DBReader.getCarsFromDB().stream().map(car -> new Object[]{car.getManufacturer(), car.getModel(),
                car.getYear(), car.getColor(), car.getPrice()}).toArray(Object[][]::new);
    }
}
