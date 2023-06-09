import listeners.DBTestListeners;
import model.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.DBReader;
import static org.testng.Assert.*;
import java.util.List;

@Listeners(DBTestListeners.class)
public class DBTest {
    private static Car testCar;
    private static final Logger log = LogManager.getLogger(DBTest.class);

    @BeforeClass
    public void setUp() {
        log.info("start prepare for test");
        DBReader.prepareForTest();
        testCar = new Car("Peugeot", "5008", 2014, "Dark-Blue", 15000.00);
        log.info("finish preparing for test\n");
    }

    @Test
    public void testInsertCar() {
        log.info("start of insert car test");
        DBReader.insert(testCar);
        List<Car> cars = DBReader.getCarsFromDB();

        assertTrue(cars.contains(testCar));
        log.info("finish of insert car test\n");

    }

    @Test
    public void testUpdateCar() {
        log.info("start update test");
        List<Car> cars = DBReader.getCarsFromDB();
        Car randomCar = cars.get((int) (Math.random() * cars.size() -1));
        randomCar.setPrice(27000.00);
        DBReader.update(randomCar, randomCar.getId());
        Car updatedCar = DBReader.getCarsFromDB().stream()
                .filter(car -> car.getId() == randomCar.getId())
                .findFirst()
                .orElse(null);

        assertNotNull(updatedCar);
        assertEquals(updatedCar.getPrice(), 27000.00);
        log.info("finish update test\n");
    }

    @Test
    public void testDeleteCar() {
        log.info("start of deletion test");
        List<Car> cars = DBReader.getCarsFromDB();
        int randomIndex = (int) (Math.random() * cars.size() - 1);
        Car carToRemove = DBReader.getCar(randomIndex);
        DBReader.delete(randomIndex);
        List<Car> updatedCars = DBReader.getCarsFromDB();

        assertFalse(updatedCars.contains(carToRemove));
        log.info("finish deletion test\n");
    }

    @Test()
    public void testCount(){
        log.info("start count test");
        int count = DBReader.getCarsCount();
        List<Car> carsList = DBReader.getCarsFromDB();

        assertEquals(count + 1, carsList.size());
        log.info("finish count test\n");
    }

    @AfterClass
    public void tearDown(){
        log.info("start after test clean up");
        List<Car> cars = DBReader.getCarsFromDB();
        cars.forEach(car -> DBReader.delete(car.getId()));
        log.info("finish after test clean up\n");
    }

}