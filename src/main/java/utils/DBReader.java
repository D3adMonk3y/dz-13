package utils;

import model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBReader {

    private final static String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final static String USER_NAME = "postgres";
    private final static String USER_PASSWORD = "postgres";

    private final static String QUERY_SELECT_ALL = "select * from cars";
    private final static String QUERY_SELECT_ONE = "select * from cars where id=?";
    private final static String QUERY_INSERT = "insert into cars (manufacturer, model, year, color, price) " +
            "values(?, ?, ?, ?, ?)";
    private final static String QUERY_UPDATE = "update cars set manufacturer=?, model=?, year=?, color=?, price=?" +
            " where id=?";
    private final static String QUERY_DELETE = "delete from cars where id=?";

    private final static String EXCEPTION_MESSAGE = String.format("Please check connection string" +
            ". URL [%s], name [%s], pass [%s]", URL, USER_NAME, USER_PASSWORD);

    public static List<Car> getCarsFromDB(){
        List<Car> cars = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)){

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL);

            while(resultSet.next()){
                Car car = new Car(resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getString(5), resultSet.getDouble(6));
                car.setId(resultSet.getInt(1));
                cars.add(car);
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(EXCEPTION_MESSAGE);

        }

        return cars;
    }

    public static Car getCar(int id){
        Car car = null;

        try(Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)){

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_ONE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

           if(resultSet.next()){
                car = new Car(resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getString(5), resultSet.getDouble(6));
                car.setId(resultSet.getInt(1));
           }

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }

        return car;
    }

    public static void insert(Car car) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
            addCarValueToStatement(preparedStatement, car);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }


    public static void update(Car car, int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            addCarValueToStatement(preparedStatement, car);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }


    public static void delete(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }


    private static void addCarValueToStatement(PreparedStatement preparedStatement, Car car) throws SQLException {
        preparedStatement.setString(1, car.getManufacturer());
        preparedStatement.setString(2, car.getModel());
        preparedStatement.setInt(3, car.getYear());
        preparedStatement.setString(4, car.getColor());
        preparedStatement.setDouble(5, car.getPrice());
    }

    public static void prepareForTest() {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            Statement statement = connection.createStatement();
            statement.addBatch("DROP TABLE IF EXISTS cars;");
            statement.addBatch("CREATE TABLE cars (\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    manufacturer VARCHAR(50) NOT NULL,\n" +
                    "    model VARCHAR(50) NOT NULL,\n" +
                    "    year INTEGER NOT NULL,\n" +
                    "    color VARCHAR(20),\n" +
                    "    price DECIMAL(10, 2),\n" +
                    "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                    ");");
            statement.addBatch("INSERT INTO cars (manufacturer, model, year, color, price)\n" +
                    "VALUES\n" +
                    "    ('Toyota', 'Camry', 2021, 'Silver', 25000.00),\n" +
                    "    ('Honda', 'Civic', 2022, 'Red', 22000.00),\n" +
                    "    ('Ford', 'Mustang', 2020, 'Black', 35000.00),\n" +
                    "    ('Chevrolet', 'Cruze', 2019, 'Blue', 18000.00),\n" +
                    "    ('BMW', 'X5', 2021, 'White', 50000.00),\n" +
                    "    ('Mercedes-Benz', 'C-Class', 2022, 'Gray', 45000.00),\n" +
                    "    ('Audi', 'A4', 2020, 'Silver', 40000.00);");

           statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }


    public static int getCarsCount() {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM cars AS count");

            if(resultSet.next()){
                return resultSet.getInt("count");
            }
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }
}
