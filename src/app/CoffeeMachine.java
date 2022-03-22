/*
 * Copyright (c) 2022 Bondo Pangaji
 * This software is released under the MIT License.
 * https://opensource.org/licenses/MIT
 */

package app;

import app.enums.Beverage;
import app.enums.State;

import static app.enums.Beverage.*;

public class CoffeeMachine {
    private State state;
    private int water;
    private int milk;
    private int coffee;
    private int cups;
    private int money;

    public CoffeeMachine(int water, int milk, int coffee, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.cups = cups;
        this.money = money;

        setMainState();
    }

    public boolean isOn() {
        return state != State.OFF;
    }

    public void execute(String input) {
        switch (state) {
            case MAIN -> setState(input);
            case BUYING -> {
                handleBuying(input);
                setMainState();
            }
            case FILLING_WATER -> {
                water += Integer.parseInt(input);
                System.out.print("Write how many ml of milk do you want to add:\n> ");
                state = State.FILLING_MILK;
            }
            case FILLING_MILK -> {
                milk += Integer.parseInt(input);
                System.out.print("Write how many grams of coffee beans do you want to add:\n> ");
                state = State.FILLING_COFFEE;
            }
            case FILLING_COFFEE -> {
                coffee += Integer.parseInt(input);
                System.out.print("Write how many disposable cups of coffee do you want to add:\n> ");
                state = State.FILLING_CUPS;
            }
            case FILLING_CUPS -> {
                cups += Integer.parseInt(input);
                setMainState();
            }
            default -> {
            }
        }
    }

    public void setState(String input) {
        switch (input.toLowerCase()) {
            case "buy" -> {
                System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:\n> ");
                state = State.BUYING;
            }
            case "fill" -> {
                System.out.print("Write how many ml of water do you want to add:\n> ");
                state = State.FILLING_WATER;
            }
            case "take" -> {
                giveMoney();
                setMainState();
            }
            case "remaining" -> {
                printState();
                setMainState();
            }
            case "exit" -> state = State.OFF;
            default -> {
                System.out.println("Unexpected action.");
                setMainState();
            }
        }
    }

    private void printState() {
        System.out.printf("The coffee machine has:\n%d of water\n%d of milk\n", water, milk);
        System.out.printf("%d of coffee beans\n%d of disposable cups\n%d of money\n", coffee, cups, money);
    }

    private void setMainState() {
        state = State.MAIN;
        System.out.print("\nWrite action (buy, fill, take, remaining, exit):\n> ");
    }

    private void handleBuying(String input) {
        Beverage recipe;

        switch (input.toLowerCase()) {
            case "back" -> {
                state = State.MAIN;
                return;
            }
            case "1" -> recipe = ESPRESSO;
            case "2" -> recipe = LATTE;
            case "3" -> recipe = CAPPUCCINO;
            default -> {
                System.out.println("Unexpected option.");
                return;
            }
        }

        makeCoffee(recipe);
        acceptPayment(recipe.getPrice());
    }

    private void makeCoffee(Beverage recipe) {
        if (water < recipe.getWater()) {
            System.out.println("Sorry, not enough water!");
            return;
        }

        if (milk < recipe.getMilk()) {
            System.out.println("Sorry, not enough milk!");
            return;
        }

        if (coffee < recipe.getCoffee()) {
            System.out.println("Sorry, not enough coffee bean!");
            return;
        }

        if (cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return;
        }

        water -= recipe.getWater();
        milk -= recipe.getMilk();
        coffee -= recipe.getCoffee();
        cups--;

        System.out.println("I have enough resources, making you a coffee!");
    }

    private void acceptPayment(int price) {
        money += price;
    }

    private void giveMoney() {
        System.out.printf("I gave you $%d\n", money);
        money = 0;
    }
}
