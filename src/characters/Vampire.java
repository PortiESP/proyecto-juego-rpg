package src.characters;

import src.Game;
//Import Statements
import src.abilities.*;
import src.equipment.Equipment;
import src.minions.Minion;
import src.modifiers.Modifier;
import utils.MenuBuilder;

public class Vampire extends Character {

    private int age;
    private int blood;
    private Discipline discipline;

    // CONSTANTS
    public static int MAX_BLOOD = 10;
    public static int MIN_BLOOD = 1;
    public static int MAX_HEALTH = 5;
    public static int MAX_POWER = 5;
    public static int MIN_POWER = 1;
    public static int INIT_MINIONS = 3;

    // ============================================================================================[ Constructor ]>>>
    public Vampire() {
        super();
        this.loadSpecial();
        this.loadMinions();
    }

    // ============================================================================================[ Public Methods ]>>>

    @Override
    public int calcAttackPower() {
        if (blood >= 5) {
            return calcBaseAttackPower() + 2;
        } else {
            return calcBaseAttackPower();
        }
    }

    @Override
    public void loadMinions() {
        int health = 0;
        Minion[] minions = new Minion[INIT_MINIONS];

        for (int i = 0; i < INIT_MINIONS; i++) {
            int index = i % 2;
            if (index == 0) {
                minions[i] = Game.ghoulsAvailable.get(i % Game.ghoulsAvailable.size());
            }else{
                minions[i] = Game.devilsAvailable.get(i % Game.devilsAvailable.size());
            }

            health += minions[i].getHealth();
        }
        this.setMinionsHealth(health);
    }

    @Override
    public void loadSpecial() {
        this.special = new Discipline();
    }

    @Override
    public void loadInitialValues() {
        this.setHealth(MAX_HEALTH);
        this.setPower(MAX_POWER);
        this.setModifiers(new Modifier[2]);
        this.setMinions(new Minion[INIT_MINIONS]);
        this.setEquipment(new Equipment[3]);
        this.setAge(0);
        this.setBlood(MAX_BLOOD);
    }

    // Method to modify the attributes of the vampire
    public static void modifyAttributes() {
        while (true) {
            String[] options = { "Alter Max Health", "Alter Max Power", "Alter Max Blood", "Alter Initial Minions", "Exit" };
            int opt = MenuBuilder.menu("Modify Hunter", options);

            if (opt < options.length) {
                alterAttr(opt);
            } else {
                break;
            }
        }
    }

    // Method to alter health
    public static void alterAttr(int opt) {
        String[] attributes = { "Max Health", "Max Power", "Max Blood", "Initial Minions"};
        String msg = "Enter the new value for the " + attributes[opt] + "(Positive Value)";
        int value = MenuBuilder.readInt(msg, 0, 1000); 

        switch (opt) {
            case 1 -> MAX_HEALTH = value;
            case 2 -> MAX_POWER = value;
            case 3 -> MAX_BLOOD = value;
            case 4 -> INIT_MINIONS = value;
        }
    }

    // ============================================================================================[ Getters & Setters ]>>>

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
