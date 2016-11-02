package com.xiyuan.acm.model;

/**
 * http://www.lintcode.com/zh-cn/problem/parking-lot/
 * Created by xiyuan_fengyu on 2016/11/2.
 */
// enum type for Vehicle
enum VehicleSize {
    Motorcycle,
    Compact,
    Large,
}

abstract class Vehicle {
    public int level = -1;
    public int row = -1;
    public int startColumn = -1;
    public int endColumn = -1;
    public VehicleSize vehicleSize;

    public void park(int level, int row, int startColumn, int endColumn) {
        this.level = level;
        this.row = row;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
    }

    public void unpark() {
        level = -1;
        row = -1;
        startColumn = -1;
        endColumn = -1;
    }
}

class Motorcycle extends Vehicle {
    public Motorcycle() {
        vehicleSize = VehicleSize.Motorcycle;
    }
}

class Car extends Vehicle {
    public Car() {
        vehicleSize = VehicleSize.Compact;
    }
}

class Bus extends Vehicle {
    public Bus() {
        vehicleSize = VehicleSize.Large;
    }
}

/* Represents a level in a parking garage */
class Level {
    public int level;
    public int row;
    public int column;
    public boolean[][] states;

    public Level(int level, int row, int column) {
        this.level = level;
        this.row = row;
        this.column = column;
        states = new boolean[row][column];
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (vehicle.vehicleSize == VehicleSize.Motorcycle) {
            return parkMotorcycle(vehicle);
        }
        else if (vehicle.vehicleSize == VehicleSize.Compact) {
            return parkCompact(vehicle);
        }
        else {
            return parkLarge(vehicle);
        }
    }

    private boolean parkMotorcycle(Vehicle vehicle) {
        for (int i = 0; i < row; i++) {
            for (int j = 0, len = column / 4; j < len; j++) {
                if (!states[i][j]) {
                    states[i][j] = true;
                    vehicle.park(level, i, j, j);
                    return true;
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = column / 4, len = column / 4 * 3; j < len; j++) {
                if (!states[i][j]) {
                    states[i][j] = true;
                    vehicle.park(level, i, j, j);
                    return true;
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = column / 4 * 3, len = column; j < len; j++) {
                if (!states[i][j]) {
                    states[i][j] = true;
                    vehicle.park(level, i, j, j);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean parkCompact(Vehicle vehicle) {
        for (int i = 0; i < row; i++) {
            for (int j = column / 4, len = column / 4 * 3; j < len; j++) {
                if (!states[i][j]) {
                    states[i][j] = true;
                    vehicle.park(level, i, j, j);
                    return true;
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = column / 4 * 3, len = column; j < len; j++) {
                if (!states[i][j]) {
                    states[i][j] = true;
                    vehicle.park(level, i, j, j);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean parkLarge(Vehicle vehicle) {
        if (column - column / 4 * 3 < 5) {
            return false;
        }

        int availibleNum = 0;
        for (int i = 0; i < row; i++) {
            availibleNum = 0;
            for (int j = column / 4 * 3, len = column; j < len; j++) {
                if (!states[i][j]) {
                    availibleNum++;
                    if (availibleNum == 5) {
                        for (int k = j; k > j - 5; k--) {
                            states[i][k] = true;
                        }
                        vehicle.park(level, i, j - 5 + 1, j);
                        return true;
                    }
                }
                else {
                    availibleNum = 0;
                }
            }
        }
        return false;
    }

    public void unParkVehicle(Vehicle vehicle) {
        for (int j = vehicle.startColumn; j <= vehicle.endColumn; j++) {
            states[vehicle.row][j] = false;
            vehicle.unpark();
        }
    }

}

public class ParkingLot {

    private Level[] levels;

    // @param n number of leves
    // @param num_rows  each level has num_rows rows of spots
    // @param spots_per_row each row has spots_per_row spots
    public ParkingLot(int n, int num_rows, int spots_per_row) {
        levels = new Level[n];
        for (int i = 0; i < n; i++) {
            levels[i] = new Level(i, num_rows, spots_per_row);
        }
    }

    // Park the vehicle in a spot (or multiple spots)
    // Return false if failed
    public boolean parkVehicle(Vehicle vehicle) {
        if (vehicle.level > -1) {
            return true;
        }

        for (int i = 0, len = levels.length; i < len; i++) {
            if (levels[i].parkVehicle(vehicle)) {
                return true;
            }
        }
        return false;
    }

    // unPark the vehicle
    public void unParkVehicle(Vehicle vehicle) {
        if (vehicle.level > -1) {
            levels[vehicle.level].unParkVehicle(vehicle);
        }
    }

    public static void test() {
        ParkingLot parkingLot = new ParkingLot(1, 1, 11);
        Motorcycle motorcycle1 = new Motorcycle();
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        Car car4 = new Car();
        Car car5 = new Car();
        Bus bus1 = new Bus();

        System.out.println(parkingLot.parkVehicle(motorcycle1));
        System.out.println(parkingLot.parkVehicle(car1));
        System.out.println(parkingLot.parkVehicle(car2));
        System.out.println(parkingLot.parkVehicle(car3));
        System.out.println(parkingLot.parkVehicle(car4));
        System.out.println(parkingLot.parkVehicle(car5));
        System.out.println(parkingLot.parkVehicle(bus1));
        parkingLot.unParkVehicle(car5);
        System.out.println(parkingLot.parkVehicle(bus1));
    }

}