import java.util.ArrayList;

/**
 * This is the primary file that runs the game
 * Contains all the commands the player can use as well as
 * the initialisation of the rooms and objects 
 */

public class Game {
  private Parser parser;
  private Room currentRoom;
  private Player MC;
  private CommandWords combine;

  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    createRooms();
    parser = new Parser();
    MC = new Player();
    combine = new CommandWords();
  }

  /**
   * Create all the rooms and link their exits together.
   */
  private void createRooms() {
    Room tartarus, bedroom, asphodel, erebus, chaos, hydra, chiron, elysium, styx, greece;

    ArrayList<Item> blade = new ArrayList<Item>(); // bedroom
    blade.add(new Item("Stygian blade", 10, "A used blade that will be sufficient. For now."));
    ArrayList<Item> key = new ArrayList<Item>(); // erebus
    key.add(new Item("Chthonic key", 5, "Keys unlock doors. So there's probably a door that needs unlocking..."));
    ArrayList<Item> note = new ArrayList<Item>(); // chaos
    note.add(new Item("Chaotic note", 5,
        "Decipher it and you may unlock a new skill. Hint: You will need help deciphering it."));
    ArrayList<Item> weapons = new ArrayList<Item>(); // elysium
    weapons.add(new Item("Chiron's bow", 40, "A heavy bow, but powerful. And it's not yours."));
    weapons.add(new Item("Guan Yu", 10, "One wonders what would happen if you COMBINEd this weapon with another..."));

    // create the rooms
    tartarus = new Room("Tartarus", "are in your home",
        "You examine your surroundings for anything that might aid you in your journey, but you see nothing. Maybe your room holds a trusty weapon...");
    bedroom = new Room("Tartarus", blade,
        "are in a dull looking room, with a picture of you and your dad. Take a look around, you might find something to help you escape",
        "You see a gleaming blade, tarnished through use. The Stygian Blade.");
    asphodel = new Room("Asphodel",
        "are in a scalding hot cavern, with lava surrouding you. One mistep and you might be back at Tartarus",
        "Nothing but lava...");
    erebus = new Room("Erebus", key,
        "hear a clock ticking and monster pour in. You hack and slash and kill them all... Without taking a single hit.",
        "You see a shiny object on the ground. Upon closer inspection, it's a key! This could help you escape.");
    chaos = new Room("Chaos", note,
        "are in a mysterious, floating colosseum-like structure. A chthonic being, neither man or woman, stands before you",
        "You see a scroll on an altar in front of you. It appears unstable.");
    hydra = new Room("Asphodel",
        "are back in the burning environment, you see some multi-headed snakes, but you ignore them.",
        "Nothing much in here.");
    chiron = new Room("Chiron's shop", "are in an eerie shop with a centaur running it.", "The centaur speaks to you.");
    elysium = new Room("Elysium", weapons, "are in a beautiful garden, full of chrysanthemums",
        "You see two weapons appear. One a bow: Heavy but powerful. Second one a staff: Lightweight and sharp.");
    styx = new Room("Temple of Styx",
        "turn the key and doors open. Your father, Hades, stands before you.\nHe asks you to stop. He tells you that you don't know what you're doing.\nYou simply do not care.\nYou both duel, father and son, and finally you gain the upper hand and your father falls to the ground.",
        "You are extremely close to escape.");
    greece = new Room("Greece", "in the outside world, where the air smells fresh and somewhat fragrant",
        "You see a woman in front of a cottage. Your mother. Persephone.");

    // initialise room exits
    tartarus.setExit("west", bedroom);
    tartarus.setExit("north", asphodel);

    bedroom.setExit("east", tartarus);

    asphodel.setExit("south", tartarus);
    asphodel.setExit("west", erebus);
    asphodel.setExit("east", chaos);

    erebus.setExit("east", asphodel);

    chaos.setExit("west", asphodel);
    chaos.setExit("north", hydra);

    hydra.setExit("west", elysium);
    hydra.setExit("north", chiron);

    chiron.setExit("south", hydra);

    elysium.setExit("east", hydra);
    elysium.setExit("north", styx);

    styx.setExit("south", elysium);
    styx.setExit("west", greece);

    currentRoom = tartarus; // start game in tartarus
  }

  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    printWelcome();

    // Enter the main command loop. Here we repeatedly read commands and
    // execute them until the game is over.

    boolean finished = false;
    while (!finished) {
      Command command = parser.getCommand();
      finished = processCommand(command);

      if (currentRoom.getName().equals("Greece")) {
        if (MC.hasItem("Staff of Hades")) {
          System.out.println("You have escaped!");
          System.out.println("You find your mother and live happily with the people of Greece.\nYOU WIN!");
        } else {
          System.out.println(
              "As you step into the outside world, you suddenly feel a blade through your abdomen. Your weapons were not strong enough to finish Hades. You die a painful death and go back to the underworld...\nYOU LOSE!");
        }

        finished = true;
      }
    }
    System.out.println("Thank you for playing. I hope you play the Hades video game.");
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome() {
    System.out.println();
    System.out.println("Welcome to the underworld");
    System.out.println("Your name is Zagreus, the son of Hades.");
    System.out.println("Turns out, you hate this place, so let's get out.");
    System.out.println("Take a look around and get ready to run.");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    System.out.println(currentRoom.getLongDescription());
  }

  /**
   * Given a command, process (that is: execute) the command.
   * 
   * @param command The command to be processed.
   * @return true If the command ends the game, false otherwise.
   */
  private boolean processCommand(Command command) {
    boolean wantToQuit = false;

    if (command.isUnknown()) {
      System.out.println("I don't know what you mean...");
      return false;
    }

    String commandWord = command.getCommandWord();
    if (commandWord.equals("help")) {
      printHelp();
    } else if (commandWord.equals("go")) {
      goRoom(command);
    } else if (commandWord.equals("quit")) {
      wantToQuit = quit(command);
    } else if (commandWord.equals("look")) {
      look(command);
    } else if (commandWord.equals("grab")) {
      grab(command);
    } else if (commandWord.equals("info")) {
      info(command);
    } else if (commandWord.equals("drop")) {
      drop(command);
    } else if (commandWord.equals("LOCKED")) { //Later becomes combine
      locked(command);
    } else if (commandWord.equals("combine")) {
      combine(command);
    }
    // else command not recognised.
    return wantToQuit;
  }

  // implementations of user commands:

  /**
   * Print out some help information.
   * Here we print some stupid, cryptic message and a list of the
   * command words.
   */
  private void printHelp() {
    System.out.println("Your objective is to escape the underworld. Make it to Greece. See your mother.\nYou wander");
    System.out.println("around in " + currentRoom.getName() + ".");
    System.out.println();
    System.out.println("Your command words are:");
    parser.showCommands();
  }

  /**
   * Try to in to one direction. If there is an exit, enter the new
   * room, otherwise print an error message.
   */
  private void goRoom(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to go...
      System.out.println("Go where?");
      return;
    }

    String direction = command.getSecondWord();

    // Try to leave current room.
    Room nextRoom = currentRoom.getExit(direction);

    if (nextRoom == null) {
      System.out.println("There is no door!");
    } else if (nextRoom.getName().equals("Temple of Styx") && !(MC.hasItem("Chthonic key"))) {
      System.out.println("You need some kind of key to continue...");
    } else {
      currentRoom = nextRoom;
      System.out.println(currentRoom.getLongDescription());
    }
  }

  /**
   * Checks for items in room
   */
  private void look(Command command) {
    if (currentRoom.getName().equals("Chiron's shop")) {
      if (MC.hasItem("Chiron's bow") && MC.hasItem("Chaotic note")) {
        System.out.println(
            "Chiron is pleased that you have brought him his bow. He will decipher the note in return for the bow...");
        MC.removeInv("Chiron's");
        System.out.println("New skill: Combine");
        combine.setCommand(7, "combine");
        System.out.println(
            "Chiron explains to you that you will need this skill to combine two weapons, one old and the other the new, to create and even greater weapon. Strong enough to defeat the final enemy.");
        System.out.println("He then asks you to leave and never come back.\n His way of saying good luck.");

      } else if (MC.hasItem("Chaotic note")) {
        System.out.println(
            "Chiron sees that you have a cryptic note. He offers to decipher it for you, but you must find a weapon of his first. Come back once you have found it.");
      } else {
        System.out.println("Perhaps Chiron could help you if you had a note of some kind...");
      }
    } else {
      System.out.println(currentRoom.getLookDesc());
      currentRoom.printItems();
    }

  }

  /**
   * @return The name of the room
   *         (the one that was defined in the constructor)
   */
  private void grab(Command command) {
    String i = command.getSecondWord();
    if (i == null || !combine.isCommand(i)) {
      System.out.println("Grab what?");
    } else {
      Item temp = currentRoom.removeItem(command.getSecondWord());
      MC.addInv(temp);
      if (!(MC.hasItem(temp.getName()))) {
        currentRoom.addItem(temp);
      }

    }
  }

  private void info(Command command) {
    System.out.println("Inventory:");
    MC.getInv();
    System.out.println(currentRoom.getExitString());
  }

  private void drop(Command command) {
    if (command.getSecondWord() == null) {
      System.out.println("Drop what?");
    } else {
      currentRoom.addItem(MC.removeInv(command.getSecondWord()));

    }
  }

  private void locked(Command command) {
    System.out.println("You will need to unlock this skill by learning it. A note might be able to help you.");
  }

  private void combine(Command command) {
    if (MC.hasItem("Stygian blade") && MC.hasItem("Guan Yu")) {
      MC.removeInv("Stygian");
      MC.removeInv("Guan");
      MC.addInv(new Item("Staff of Hades", 10, "An extremely powerful weapon, capable of destroying the Gods."));
      System.out.println(
          "You have crafted a rather large staff that emanates power. You can use this to defeat the final foe.");
    } else if (MC.hasItem("Stygian blade") || MC.hasItem("Guan Yu")) {
      System.out.println("You're missing one weapon!");
    } else {
      System.out.println("You're missing both weapons!");
    }
  }

  /**
   * "Quit" was entered. Check the rest of the command to see
   * whether we really quit the game.
   * 
   * @return true, if this command quits the game, false otherwise.
   */
  private boolean quit(Command command) {
    if (command.hasSecondWord()) {
      System.out.println("Quit what?");
      return false;
    } else {
      return true; // signal that we want to quit
    }
  }
}
