/*
 * Copyright (c) 2022 Bondo Pangaji
 * This software is released under the MIT License.
 * https://opensource.org/licenses/MIT
 */


import app.CoffeeMachine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine(511, 511, 511, 511, 511);

        while (coffeeMachine.isOn()) {
            try {
                coffeeMachine.execute(scanner.next());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
