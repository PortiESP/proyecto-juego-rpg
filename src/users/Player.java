package src.users;

import src.characters.CharacterSelection;

public class Player extends User {

    public Player(String id, Challenge pendingChallenge, boolean pendingNotification,boolean banned, int gold, long lastLostFight,CharacterSelection currentCharacter, Armor armor, Weapon[2]weapons){
        this.id = id;
        this.pendingChallenge = pendingChallenge;
        this.pendingNotification = pendingNotification;
        this.banned = banned;
        this.gold = gold;
        this.lastLostFight = lastLostFight;
        this.currentCharacter = currentCharacter;
        this.armor = armor;
        this.weapons = weapons;
    } 
    public String getId(){
        return this.id;
    }   
    public Challenge getPendingChallenge(){
        this.pendingChallenge()
    }
    public boolean getPendingNotification(){
        return this.pendingNotification;
    }
    public int getGold(){
        return this.gold;
    }
    public boolean getBanned(){
        return this.banned;
    }
    public long getLastLostFight(){
        return this.lastLostFight;
    }
    public CharacterSelection getCurrentCharacter(){
        return this.currentCharacter;
    }
    public Armor getArmor(){
        return this.armor;
    }
    public Weapon getWeapons(){
        return this.weapons[2];
    }

    public void setId(String id){
        this.id= id;
    }

}
