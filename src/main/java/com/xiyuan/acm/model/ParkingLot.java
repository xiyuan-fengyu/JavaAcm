package com.xiyuan.acm.model;

import com.xiyuan.acm.util.DataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public int size;

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
        size = 1;
    }
}

class Car extends Vehicle {
    public Car() {
        vehicleSize = VehicleSize.Compact;
        size = 1;
    }
}

class Bus extends Vehicle {
    public Bus() {
        vehicleSize = VehicleSize.Large;
        size = 5;
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

    public boolean parkVehicle(Vehicle vehicle, int start, int end) {
        int size = vehicle.size;
        if (end - start < size) {
            return false;
        }

        int availibleNum = 0;
        for (int i = 0; i < row; i++) {
            availibleNum = 0;
            for (int j = start; j < end; j++) {
                if (!states[i][j]) {
                    availibleNum++;
                    if (availibleNum == size) {
                        for (int k = j; k > j - size; k--) {
                            states[i][k] = true;
                        }
                        vehicle.park(level, i, j - size + 1, j);
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

    private boolean parkSmall(Vehicle vehicle, int start, int end) {
        for (int i = 0; i < row; i++) {
            for (int j = start; j < end; j++) {
                if (!states[i][j]) {
                    states[i][j] = true;
                    vehicle.park(level, i, j, j);
                    return true;
                }
            }
        }
        return false;
    }

    public void unParkVehicle(Vehicle vehicle) {
        for (int j = vehicle.startColumn; j <= vehicle.endColumn; j++) {
            states[vehicle.row][j] = false;
        }
        vehicle.unpark();
    }

}

public class ParkingLot {

    private Level[] levels;
    private int levelNum;
    private int row;
    private int column;
    private int column_1_4;
    private int column_3_4;

    // @param n number of leves
    // @param num_rows  each level has num_rows rows of spots
    // @param spots_per_row each row has spots_per_row spots
    public ParkingLot(int n, int num_rows, int spots_per_row) {
        levelNum = n;
        row = num_rows;
        column = spots_per_row;
        column_1_4 = column / 4;
        column_3_4 = column_1_4 * 3;
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

        if (vehicle.size < 5) {
            if (vehicle.vehicleSize == VehicleSize.Motorcycle) {
                for (Level level1 : levels) {
                    if (level1.parkVehicle(vehicle, 0, column_1_4)) {
                        return true;
                    }
                }
            }

            for (Level level : levels) {
                if (level.parkVehicle(vehicle, column_1_4, column_3_4)) {
                    return true;
                }
            }
        }
        for (Level level : levels) {
            if (level.parkVehicle(vehicle, column_3_4, column)) {
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
        List<String> inLines = DataUtil.getFileLines("data/parking-lot-33.in");
        Pattern firstLineP = Pattern.compile("level=([0-9]+), num_rows=([0-9]+), spots_per_row=([0-9]+)");
        Matcher firstLineM = firstLineP.matcher(inLines.get(0));
        if (!firstLineM.find()) {
            return;
        }

        ParkingLot parkingLot = new ParkingLot(Integer.parseInt(firstLineM.group(1)), Integer.parseInt(firstLineM.group(2)), Integer.parseInt(firstLineM.group(3)));
        List<Motorcycle> motos = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        List<Bus> buses = new ArrayList<>();

        List<String> outLines = DataUtil.getFileLines("data/parking-lot-33.out");
        int outIndex = 0;
        for (int i = 1, size = inLines.size(); i< size; i++) {
            String line = inLines.get(i);
            if (line.startsWith("parkVehicle")) {
                //停车
                String nameAndIndex = line.substring("parkVehicle(\"".length(), line.length() - 2);
                String[] split = nameAndIndex.split("_");
                String name = split[0];
                int index = Integer.parseInt(split[1]);
                Vehicle vehicle;
                if (name.equals("Motorcycle")) {
                    Motorcycle moto;
                    if (index - 1 >= motos.size()) {
                        moto = new Motorcycle();
                        motos.add(moto);
                    }
                    else {
                        moto = motos.get(index - 1);
                    }
                    vehicle = moto;
                }
                else if (name.equals("Car")) {
                    Car car;
                    if (index - 1 >= cars.size()) {
                        car = new Car();
                        cars.add(car);
                    }
                    else {
                        car = cars.get(index - 1);
                    }
                    vehicle = car;
                }
                else {
                    Bus bus;
                    if (index - 1 >= buses.size()) {
                        bus = new Bus();
                        buses.add(bus);
                    }
                    else {
                        bus = buses.get(index - 1);
                    }
                    vehicle = bus;
                }
                boolean parkResult = parkingLot.parkVehicle(vehicle);
                boolean expected = Boolean.parseBoolean(outLines.get(outIndex++));

                if (parkResult != expected) {
                    System.out.println("line: " + (i + 1) + "   " + line + "   expected: " + expected + "(" + outIndex + ")   real: " + parkResult);
                    printParkState(parkingLot);
                }
                else {
                    System.out.println("line: " + (i + 1) + "   " + line + "   expected: " + expected);
                    printParkState(parkingLot);
                }
            }
            else {
                //离开
                String nameAndIndex = line.substring("unParkVehicle(\"".length(), line.length() - 2);
                String[] split = nameAndIndex.split("_");
                String name = split[0];
                int index = Integer.parseInt(split[1]);
                Vehicle vehicle;
                if (name.equals("Motorcycle")) {
                    vehicle = motos.get(index - 1);
                }
                else if (name.equals("Car")) {
                    vehicle = cars.get(index - 1);
                }
                else {
                    vehicle = buses.get(index - 1);
                }
                parkingLot.unParkVehicle(vehicle);

                System.out.println("line: " + (i + 1) + "   " + line);
                printParkState(parkingLot);
            }
        }
    }

    public static void printParkState(ParkingLot parkingLot) {
        int levelNum = parkingLot.levelNum;
        int rowNum = parkingLot.row;
        int columnNum = parkingLot.column;
        int k_1_4 = parkingLot.column_1_4;
        int k_3_4 = parkingLot.column_3_4;
        for (int i = 0; i < levelNum; i++) {
            Level level = parkingLot.levels[i];
            System.out.println("Level " + i);
            for (int j = 0; j < rowNum; j++) {
                System.out.print("Row " + j + "    ");
                System.out.print('[');
                for (int k = 0; k < k_1_4; k++) {
                    System.out.print(level.states[j][k]?'口': '一');
                }
                System.out.print(']');

                System.out.print('[');
                for (int k = k_1_4; k < k_3_4; k++) {
                    System.out.print(level.states[j][k]?'口': '一');
                }
                System.out.print(']');

                System.out.print('[');
                for (int k = k_3_4; k < columnNum; k++) {
                    System.out.print(level.states[j][k]?'口': '一');
                }
                System.out.print(']');
                System.out.println();
            }
            System.out.println();
        }
    }

}