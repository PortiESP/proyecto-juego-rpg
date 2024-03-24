package src.characters;

import src.abilities.SpecialAbility;
import src.equipment.*;
import src.minions.*;
// Import statements
import src.modifiers.*;
import src.users.Player;

public abstract class Character {

    private String name;
    private int health;
    private int power;
    private Modifier[] modifiers;
    private Minion[] minions;
    private int minionsHealth;
    protected Equipment[] equipment;
    protected SpecialAbility special;

    // ============================================================================================[ Constructor ]>>>
    public Character() {
        this.loadInitialValues();
        this.loadModifiers();
    }

    // ============================================================================================[ Abstract Methods ]>>>

    // Load initial values for the character
    public abstract void loadInitialValues();

    // Load the character's special ability
    public abstract void loadSpecial();

    // Load the character's minions
    // TODO DANI: Loading the minions implies calculating the defense they provide and storing it in the minionsHealth attribute
    public abstract void loadMinions();

    // Modify the character's attributes --> To be implemented in subclasses
    public static void modifyAttributes() {}

    // ============================================================================================[ Public Methods ]>>>

    public int getHit(Character target) {
        int damage = target.getAttackPower();
        int defense = getDefensePower();
        if (defense > damage) {
            defense = damage;
        }

        int finalAttackValue = damage - defense;

        // Remove minions health
        if (minionsHealth > 0) {
            if (minionsHealth >= finalAttackValue) {
                minionsHealth -= finalAttackValue;
                finalAttackValue = 0;
            } else {
                finalAttackValue -= minionsHealth;
                minionsHealth = 0;
            }
        }

        int remainingHealth = health - finalAttackValue;

        if (remainingHealth < 0) {
            health = 0;
        } else {
            health = remainingHealth;
        }

        return (damage - defense);
    }

    // Check if the character is dead
    public boolean isDead() {
        return health == 0;
    }

    public void assignEquipment(Player player) {
        Equipment[] weapons = player.getWeapons();
        this.equipment[0] = weapons[0];
        this.equipment[1] = weapons[1];
        this.equipment[2] = player.getArmor();
    }

    // ============================================================================================[ Private methods ]>>>

    // Get a random number between 1 and 6
    private int rollDice() {
        return (int) (Math.random() * 6) + 1;
    }

    private int getAttackPower() {
        int success = 0;
        int attackPower = calcAttackPower();
        for (int i = 0; i < attackPower; i++) {
            int roll = rollDice();
            if (roll >= 5) {
                success++;
            }
        }

        return success;
    }

    private int getDefensePower() {
        int success = 0;
        int defensePower = calcDefensePower();
        for (int i = 0; i < defensePower; i++) {
            int roll = rollDice();
            if (roll >= 5) {
                success++;
            }
        }

        return success;
    }

    // Load the character's modifiers
    private void loadModifiers() {
        Modifier[] mods = { new Strength(), new Weakness() };
        this.modifiers = mods;
    }

    // Calculate the attack power provided by the equipment
    private int calcEquipmentAttack() {
        int cumPower = 0;
        for (Equipment e : this.equipment) {
            if (e == null) {
                continue;
            }
            cumPower += e.getAttack();
        }

        return cumPower;
    }

    private int calcEquipmentDefense() {
        int cumDefense = 0;
        for (Equipment e : this.equipment) {
            if (e == null) {
                continue;
            }
            cumDefense += e.getDefense();
        }

        return cumDefense;
    }

    private int calcModifiersAttack() {
        int sum = 0;
        for (Modifier m : this.modifiers) {
            if (m == null) {
                continue;
            }
            if (m instanceof Strength) {
                Strength s = (Strength) m;
                sum += s.getEffectiveness();
            } else if (m instanceof Weakness) {
                Weakness w = (Weakness) m;
                sum -= w.getSensitivity();
            }
        }
        return sum;
    }

    private int calcMinionsDefense() {
        return this.minionsHealth;
    }

    // ============================================================================================[ Protected Methods ]>>>
    protected int calcBaseAttackPower() {
        int cumAtt = 0;

        cumAtt += calcEquipmentAttack();
        cumAtt += calcModifiersAttack();
        cumAtt += this.special.getAttack();

        return cumAtt;
    }

    protected int calcBaseDefensePower() {
        int cumDef = 0;

        cumDef += calcEquipmentDefense();
        cumDef += calcMinionsDefense();
        cumDef += this.special.getDefense();

        return cumDef;
    }

    // Calculate total attack power of the character
    protected int calcAttackPower() {
        return calcBaseAttackPower();
    }

    // Calculate total defense power of the character
    protected int calcDefensePower() {
        return calcBaseDefensePower();
    }

    // ============================================================================================[ Getters & Setters ]>>>
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Modifier[] getModifiers() {
        return modifiers;
    }

    public void setModifiers(Modifier[] modifiers) {
        this.modifiers = modifiers;
    }

    public Minion[] getMinions() {
        return minions;
    }

    public void setMinions(Minion[] minions) {
        this.minions = minions;
    }

    public Equipment[] getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment[] equipment) {
        this.equipment = equipment;
    }
}
