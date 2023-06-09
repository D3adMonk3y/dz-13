package model;

public class Car {
    private int id;
    private String manufacturer;
    private String model;
    private int year;
    private String color;
    private double price;

    // Constructors
    public Car() {
    }

    public Car(String manufacturer, String model, int year, String color, double price) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.color = color;
        this.price = price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // toString() method
    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (year != car.year) return false;
        if (Double.compare(car.price, price) != 0) return false;
        if (manufacturer != null ? !manufacturer.equals(car.manufacturer) : car.manufacturer != null) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        return color != null ? color.equals(car.color) : car.color == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = manufacturer != null ? manufacturer.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
