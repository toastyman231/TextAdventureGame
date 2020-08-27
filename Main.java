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
				player = new Character(name, 5, 14, 14, 30, 30);
				break;
			case 2:
				player = new Character(name, 4, 15, 15, 30, 30);
				break;
			case 3:
				player = new Character(name, 10, 16, 16, 5, 5);
				break;
			default:
				player = new Character(name, 5, 14, 14, 30, 30);
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

		Character teacher = new Character(teacherName, player.strength+1, player.defense-1, player.minDefense-1, 40, 40);

		if(Battle(player, teacher)){
			System.out.print(teacherName + ": Very impressive, " + name + ". You will do well out there.");
		} else {
			System.out.println(teacherName + ": Disappointing. You may need to practice more.");
		}
  }

  public static boolean Battle(Character player, Character enemy){
    if(player.health <= 0) { return false; } 
    else if(enemy.health <= 0) { player.health = player.maxHealth; return true; }
    int playerAttack = 0;
    int enemyAttack = 0;

    //Determine player action
    System.out.println("It is your turn! Your current health is " + player.health + ". Please type 'a' to attack, or 'd' to defend, without the quotes.");
    String choice = scan.nextLine();

    //Attack or defend based on player choice
    if(choice.equalsIgnoreCase("a")){
      //Roll to decide if the player hits. An attack hits if its equal to or greater than the enemies defense
      playerAttack = rand.ints(1, 21).findFirst().getAsInt() + player.strength;
			if(playerAttack >= enemy.defense){
				//Roll for damage, strength also applies to this
				int dam = enemy.Damage(rand.ints(1, 11).findFirst().getAsInt() + player.strength);
				System.out.println("Your attack hit! You did " + dam + " damage!");
			} else {
				System.out.println("The enemy " + enemy.name + " blocked your attack!");
			}
			enemy.defense = enemy.minDefense;
    } else {
      //Defense will be raised by a random amount next turn only.
      player.defense += rand.ints(1, 5).findFirst().getAsInt();
      System.out.println("Your defense will go up next turn!");
    }

    //Enemy will defend if at or below 30 percent health, otherwise they attack
    if((double)enemy.health / enemy.maxHealth <= 0.3){
      enemy.defense += rand.ints(1, 5).findFirst().getAsInt();
			System.out.println("The enemy " + enemy.name + " seems to be blocking.");
    } else {
      enemyAttack = rand.ints(1, 21).findFirst().getAsInt() + enemy.strength;
			if(enemyAttack >= player.defense){
				int dam = player.Damage(rand.ints(1, 11).findFirst().getAsInt() + player.strength);
				System.out.println("The enemy " + enemy.name + "'s attack hit! They did " + dam + " damage!");
			} else {
				System.out.println("You blocked the enemy " + enemy.name + "'s' attack!");
			}
			player.defense = player.minDefense;
    }

    return Battle(player, enemy);
  }
}

class Character {
  String name;
  int strength;
  int defense;
	int minDefense;
  int maxHealth;
  int health;

  public Character(String charName, int str, int def, int mDef, int mHealth, int hp){
    this.name = charName;
    this.strength = str;
    this.defense = def;
		this.minDefense = mDef;
    this.maxHealth = mHealth;
    this.health = hp;
  }

  public int Damage(int damage){
    if(damage >= 0){
      health = clamp(health - damage, 0, maxHealth);
    }
		return damage;
  }

  public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}