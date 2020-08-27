import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

class Main {
  public static Scanner scan = new Scanner(System.in);
  public static Random rand = new Random();

  public static void main(String[] args) {
		Character player;
		String name = "";
		String classChosen = "";
		int classChoice = 1;
		boolean loopBool = true;

		while(loopBool){
			System.out.println("Please enter your name: ");
			name = scan.nextLine();
			System.out.println("So your name is " + name + ", is that correct? Please type 'y' if you are okay with this name, or 'n' if you'd like to enter another one.");
			String cont = scan.nextLine();
			if(cont.equalsIgnoreCase("y")) {loopBool = false; }
		}
		loopBool = true;
		
		while(loopBool){
			System.out.println("Please enter the number that corresponds to the class you'd like to play as:\n1.Bruiser\n2.Thief\n3.Glass Cannon");
			classChoice = scan.nextInt();
			scan.nextLine(); //I know we have to store these in a variable, this is just so it doesnt read a newline character as an input
			switch(classChoice){
				case 1:
					classChosen = "Bruiser";
					break;
				case 2:
					classChosen = "Thief";
					break;
				case 3:
					classChosen = "Glass Cannon";
					break;
				default:
					classChosen = "Bruiser";
					break;
			}
			System.out.println("So you'd like to play as a " + classChosen + ", is that right? Please type 'y' if you are okay with this class, or 'n' if you'd like to enter another one.");
			String cont = scan.nextLine();
			if(cont.equalsIgnoreCase("y")) {loopBool = false; }
		}
		loopBool = true;

		switch(classChoice){
			case 1:
				player = new Character(name, 4, 2, 30, 30);
				break;
			case 2:
				player = new Character(name, 2, 4, 30, 30);
				break;
			case 3:
				player = new Character(name, 8, 4, 5, 5);
				break;
			default:
				player = new Character(name, 4, 2, 30, 30);
				break;
		}

		System.out.println("An old man sits before you. He has been silent for quite a while. Suddenly, he speaks.");
    System.out.println("???: So, how many years have you been alive?");
		int age = scan.nextInt();
		scan.nextLine(); //Another scan to get rid of the newline character
		System.out.println("???: " + age + " years, huh? And you've gone that long without learning how to battle? I'll have to remedy that.");
		System.out.println("???: Seeing as I've been around a lot longer than " + age + " years, there's a lot I can teach you. But for now, we'll just cover the basics.");
		System.out.println("???: So, you wish to become a " + classChosen + "? What weapon do you prefer to use?");
		String weapon = scan.nextLine();
		System.out.println("???: You really want a " + weapon + " as your weapon, huh? Well if that's what you're set on, I guess I'll have to find something to counter that.");
		while(loopBool){
			System.out.println("???: Would you like to learn about attacking or blocking first?");
			System.out.println("Please enter 'attacking' or 'blocking' to make your choice.");
			String tutorialChoice = scan.nextLine();
			if(tutorialChoice.equalsIgnoreCase("attacking")){
				System.out.println("???: You'd like to learn about " + tutorialChoice + ", huh? I can do that.");
				System.out.println("???: Attacking will do more damage the higher your strength is. Your enemy may attempt to block, which will lower the amount of damage you do.");
				System.out.println("???: You may also choose to block, which will give you a chance to absorb some damage the next time your enemy attacks. If you manage to block their entire attack, you may even regain some health points! But beware, for the same goes for your enemies...");
			} else {
				System.out.println("???: Well then. First we'll cover blocking. When you block you have a chance to absorb some damage from the next enemy attack. If you manage to block the enemies entire attack, you may regain some health points. The same goes for your enemies, of course.");
				System.out.println("???: And of course, there's attacking. You may attack your enemy on your turn, which will deal damage based on your strength. If the enemy blocked last turn, they may absorb some or all of your attack, as we discussed.");
			}
			System.out.println("???: I believe that covers everything. Do you need me to go over it again?");
			System.out.println("Please enter 'y' to hear the tutorial again, or 'n' to continue.");
			String cont = scan.nextLine();
			if(cont.equalsIgnoreCase("n")) { loopBool = false; }
		}

		System.out.println("???: Say, I never told you my name did I? Well, I unfortunately forgot it long ago. You may give me a nickname if you wish.");
		System.out.println("Please enter a name for the old man.");
		String teacherName = scan.nextLine();
		System.out.println(teacherName + ": So, you wish to call me " + teacherName + ", do you? I suppose I can accept that. Very well.");
		System.out.println(teacherName + ": To test your new knowledge, I propose a duel. Actually, I insist. En garde!");

		Character teacher = new Character(teacherName, player.strength+1, player.defense-1, 40, 40);

		if(Battle(player, teacher, player.health/3, 0)){
			System.out.print(teacherName + ": Impressive. You will do well out there.");
		} else {
			System.out.println(teacherName + ": Disappointing. You may need to practice more.");
		}
  }

  public static boolean Battle(Character player, Character enemy, int pDef, int eDef){
    if(player.health <= 0) { return false; } 
    else if(enemy.health <= 0) { return true; }
    int playerAttack = 0;
    int enemyAttack = 0;
    int playerDefense = pDef;
    int enemyDefense = eDef;

    //Determine player action
    System.out.println("It is your turn! Your current health is " + player.health + ". Please type 'a' to attack, or 'd' to defend, without the quotes.");
    String choice = scan.nextLine();

    //Attack or defend based on player choice
    if(choice.equalsIgnoreCase("a")){
      //Deal any damage not absorbed by current enemy block amount, then reset their block
      playerAttack = rand.ints(1, 21).findFirst().getAsInt() + player.strength;
      enemy.Damage(playerAttack - enemyDefense);
      System.out.println("You attacked for " + playerAttack + ", the " + enemy.name + " defended for " + enemyDefense + ", and you dealt " + (playerAttack - enemyDefense) + " damage.");
      enemyDefense = 0;
    } else {
      //Determine defense for next turn
      playerDefense = 0;
      playerDefense = rand.ints(1, 21).findFirst().getAsInt() + player.defense;
      System.out.println("Next turn you will defend against " + playerDefense + " damage.");
    }

    //Enemy will defend if at or below 30 percent health, otherwise they attack
    if((double)enemy.health / enemy.maxHealth <= 0.3){
      enemyDefense = 0;
      enemyDefense = rand.ints(1, 21).findFirst().getAsInt() + enemy.defense;
    } else {
      enemyAttack = rand.ints(1, 21).findFirst().getAsInt() + enemy.strength;
      player.Damage(enemyAttack - playerDefense);
      System.out.println(enemy.name + " attacked for " + enemyAttack + ", you defended for " + playerDefense + ", and they dealt " + (enemyAttack - playerDefense) + " damage to you.");
      playerDefense = 0;
    }

    return Battle(player, enemy, playerDefense, enemyDefense);
  }
}

class Character {
  String name;
  int strength;
  int defense;
  int maxHealth;
  int health;

  public Character(String charName, int str, int def, int mHealth, int hp){
    this.name = charName;
    this.strength = str;
    this.defense = def;
    this.maxHealth = mHealth;
    this.health = hp;
  }

  public void Damage(int damage){
    if(damage >= 0){
      health = clamp(health - damage, 0, maxHealth);
    } else {
      health += clamp(damage, 0, maxHealth/3);
    }
  }

  public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}



// TODO: Delete the following once its no longer needed for reference
/*Character c = new Character("Gamer", 4, 2, 20, 20);
Character e = new Character("Toad", 5, 3, 40, 40);

if(Battle(c, e, c.health/3, 0)){
  System.out.println("You won!");
} else {
  System.out.println("You lost...");
}*/