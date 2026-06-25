 
    import java.util.ArrayList;
import java.util.List;

// Abstract Class
abstract class Vehicle {
    private String name;
    private String id;

    public Vehicle(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public void displayDetails() {
        System.out.println("Vehicle ID: " + id + " | Model: " + name);
    }

    public abstract double calculateRentalCost(int duration);
}

// Interface for cross-cutting capability
interface Insurable {
    double calculateInsurance(double rentalCost);
}

// Car Class (Insurable)
class Car extends Vehicle implements Insurable {
    private static final double DAILY_RATE = 50.0;

    public Car(String name, String id) {
        super(name, id);
    }

    @Override
    public double calculateRentalCost(int days) {
        return days * DAILY_RATE;
    }

    @Override
    public double calculateInsurance(double rentalCost) {
        return rentalCost * 0.10; // 10% of rental cost
    }
}

// Bicycle Class (Not Insurable)
class Bicycle extends Vehicle {
    private static final double HOURLY_RATE = 5.0;

    public Bicycle(String name, String id) {
        super(name, id);
    }

    @Override
    public double calculateRentalCost(int hours) {
        return hours * HOURLY_RATE;
    }
}

public class MainQuestionTwo {
    public static void main(String[] args) {
        List<Vehicle> rentalFleet = new ArrayList<>();
        rentalFleet.add(new Car("Tesla Model 3", "C001"));
        rentalFleet.add(new Bicycle("Trek Mountain Bike", "B002"));
        rentalFleet.add(new Car("Toyota RAV4", "C003"));

        // Let's assume a uniform duration unit for simplification 
        // (Cars use days, Bicycles use hours as per the specification)
        int rentalDuration = 3; 

        for (Vehicle vehicle : rentalFleet) {
            vehicle.displayDetails();
            double cost = vehicle.calculateRentalCost(rentalDuration);
            
            String unit = (vehicle instanceof Car) ? " days" : " hours";
            System.out.printf("Rental Cost for %d%s: $%.2f%n", rentalDuration, unit, cost);

            // Using instanceof to safely cast and call interface method
            if (vehicle instanceof Insurable) {
                Insurable insurableVehicle = (Insurable) vehicle;
                double insurance = insurableVehicle.calculateInsurance(cost);
                System.out.printf("-> Insurance Fee (10%%): $%.2f%n", insurance);
            } else {
                System.out.println("-> Insurance: Not applicable for this vehicle type.");
            }
            System.out.println("---------------------------------------------");
        }
    }
}
