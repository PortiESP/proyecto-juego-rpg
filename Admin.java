package src.users;

public class Admin extends User {
    //pasar una lista de todos los usuarios
    public manageBans(User users){

    }
    public manageChallenges(Challenge challenges){

    }
    private void banPlayer(Player player){  //banear a un jugador
        player.setBanned(true);
    }
    private void unbanPlayer(Player player){    //desbanear a un jugador
        player.setBanned(false);    
    }
    private void approveChallenge(Challenge challenge){
        //confirmar un reto
    }
    private void declineChallenge(Challenge challenge){
        //eliminar un reto
    }
    private void modifyChallenge(Challenge challenge){
        //modificar un reto
    }
    private Character createCharacter(){
        //crear un personaje
    }
    private void deleteCharacter(Character character){
        //eliminar un personaje
    }
    private void modifyCharacter(Character character){
        //modificar un personaje
    }
}
