/**
   @author Jomikael Ruiz Period 6
   BlackJack Project
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * BlackJackTester simulates a game of blackjack, including betting
 */
public class BlackJackTester_6Ruiz {
    /**
       main method that implements all classes into a main, testing file
       @param args
     */
    public static void main(String[] args) {
        BlackJackGame blackjack = new BlackJackGame();
        blackjack.run();
    }
}

/**
  Game class that contains the midGame and endGame static methods, which
  display the player and dealer's cards at the beginning and at the end
 */
class BlackJackGame {
    // instance variables for BlackJackGame
    private Deck playDeck;
    private Dealer cardDealer;
    private Player user;
    private int dealerWin;
    private int playerWin;
    private Scanner input2;
    private int playerBetAmount;

    /**
      BlackJackGame constructor that initializes all instance variables for the class
     */
    public BlackJackGame() {
        playDeck = new Deck();
        cardDealer = new Dealer();
        user = new Player();
        dealerWin = 0;
        playerWin = 0;
        input2 = new Scanner(System.in);
    }

    /**
       displayCards method that prints out the toString methods of dealer and player
       @param player
       @param dealer
     */
    public void displayCards(Player player, Dealer dealer) {
        System.out.println("Dealer's Hand: \nHidden, " + dealer.toString());
        System.out.println();
        System.out.println("\n" + player.getName() + "'s cards: " + player.toString());
        System.out.println();
    }

    /**
       endGame method that checks who won the game of blackjack and thus, prints out
       a statement saying the winner. method also checks for bets, adding or subtracting
       based on rules provided in the Rubric
       @param player
       @param dealer
     */
    public void endGame(Player player, Dealer dealer) {
        System.out.println("\n" + player.getName() + "'s cards: " + player.toString());
        System.out.println();
        if (dealer.dealerWin()) {
            if (player.getHandValue() == 21) {
                System.out.println("A push has occurred; Players keep their bets");
            } else {
                System.out.println("The Dealer Has Won!");
                dealerWin++;
                user.removeCredits(playerBetAmount);
                System.out.println("You lost: " + playerBetAmount + " credits");
            }
        } else if (player.getHandValue() == 21) {
            System.out.println(user.getName() + " Has Won!");
            int winningAmount = (playerBetAmount * 15) / 10;
            System.out.println("You have won: " + winningAmount);
            user.addCredits(winningAmount);
            playerWin++;
        } else if (player.getHandValue() > dealer.getHandValue()
                && !player.busted()) {
            System.out.println(user.getName() + " Has Won!");
            playerWin++;
            user.addCredits(2 * playerBetAmount);
            System.out.println("You won: " + playerBetAmount + " credits");
        } else if (dealer.getHandValue() > player.getHandValue()
                && dealer.busted()) {
            System.out.println("The Dealer Has Won!");
            dealerWin++;
            user.removeCredits(playerBetAmount);
            System.out.println("You lost: " + playerBetAmount + " credits");
        } else if (player.busted()
                && !dealer.busted()) {
            System.out.println("The Dealer Has Won!");
            dealerWin++;
            user.removeCredits(playerBetAmount);
            System.out.println("You lost: " + playerBetAmount + " credits");
        } else if(dealer.busted() && !player.busted()) {
            System.out.println(user.getName() + " Has Won!");
            playerWin++;
            user.addCredits(2 * playerBetAmount);
            System.out.println("You won: " + playerBetAmount + " credits");
        } else if(player.getHandValue()==dealer.getHandValue()
                && !player.busted()
                && !dealer.busted()) {
            System.out.println("A push has occurred, it is a draw.");
        } else if(player.busted() && dealer.busted()) {
            if(player.getHandValue()>dealer.getHandValue()) {
                user.addCredits(2*playerBetAmount);
                System.out.println("You won: " + playerBetAmount + " credits");
                playerWin++;
            } else if(player.getHandValue()<dealer.getHandValue()) {
                user.removeCredits(playerBetAmount);
                System.out.println("You lost: " + playerBetAmount + " credits");
                dealerWin++;
            }
        }
        player.clear();
        dealer.clear();
        playDeck = new Deck();
        System.out.println("Dealer: " + dealerWin + "\n"
                + player.getName() + ": " + playerWin);
        System.out.println();
    }

    /**
      run method that follows through a game of blackjack.
      Starts by printing out a banner to welcome the user to play a friendly
      game of blackjack. Runs through the logic and rules of blackjack, utilizing
      the displayCards methods and endGame methods of the BlackJackGame class
     */
    public void run() {
        System.out.println(" \\ \\      / /__| | ___ ___  _ __ ___   ___  | " +
                "|_ ___   | __ )| | __ _  ___| | __  | " +
                "| __ _  ___| | _| |\n" + "  \\ \\ /\\ / / _ \\ |/ __/ _ \\| " +
                "'_ ` _ \\ / _ \\ | __/ _ \\  |  _ \\| " +
                "|/ _` |/ __| |/ /  | |/ _` |/ __| |/ / |\n" + "   \\ V  " +
                "V /  __/ | (_| (_) | | | | | |  __/ | || (_) | " +
                "| |_) | | (_| | (__|   < |_| | (_| | (__|   <|_|\n" + "   " +
                " \\_/\\_/ \\___|_|\\___\\___/|_| |_| " +
                "|_|\\___|  \\__\\___/  |____/|_|\\__,_|\\___|_|\\_\\___/ " +
                "\\__,_|\\___|_|\\_(_)\n" + "" +
                "                                                          " +
                "           " +
                "                                  \n");
        System.out.println("What is your name?");
        user.setName();
        System.out.println();
        // loop that runs the blackjack game
        do {
            playerBetAmount = user.bet();
            // deals out initial 2 cards to dealer and player
            cardDealer.addCard(playDeck.deal());
            cardDealer.addCard(playDeck.deal());
            user.addCard(playDeck.deal());
            user.addCard(playDeck.deal());

            // displays the cards of each player
            displayCards(user, cardDealer);

            // based on if keepDealing(), dealer gains more cards
            while (cardDealer.keepDealing()) {
                cardDealer.addCard(playDeck.deal());
            }


            // asks user if they would like to hit
            while (user.getUserChoice()) {
                // adds a card to austin's hand and prints out the toString
                user.addCard(playDeck.deal());
                System.out.println("\n" + user.getName() + "'s cards: " + user.toString());
                // checkpoint to see if player has reached 21
                if (user.getHandValue() >= 21) {
                    break;
                }
            }

            //end game display
            endGame(user, cardDealer);
            System.out.println("\nWould you like to play again? Please type y/n");
        } while(input2.nextLine().equals("y"));
        System.out.println("Thank You for playing BlackJack! " +
                "Hopefully this did not crash :)");
    }
}

/**
  Player class that represents a player, asking for user input when needed
  contains several methods that ask for user input, which is needed to function
  in the game, Blackjack
 */
class Player {
    // instance variables
    private Hand playerCollection;
    private Scanner input;
    private String name;
    private int credits;

    // constructor method for the Player class, gives players 300 credits to start
    public Player() {
        playerCollection = new Hand();
        input = new Scanner(System.in);
        credits = 300;
    }

    /**
      getUserChoice method that asks if player still wants to hit using user input
      @return true or false
     */
    public boolean getUserChoice() {
        System.out.print("Would you like to hit? Type y/n: ");
        if(input.nextLine().equals("y")) {
            return true;
        } else {return false;}
    }

    /**
      bet method that takes User Input to ask for a bet amount for Blackjack.
      Checks to see if the player is rich enough to play the game
      @return betAmount
     */
    public int bet() {
        System.out.println("You have: " + credits + " credits");
        System.out.println("How much do you want to bet?");
        int betAmount = input.nextInt();
        input.nextLine();
        while(betAmount>credits) {
            System.out.println("I'm sorry, you do not have enough credits. Try again");
            betAmount = input.nextInt();
            input.nextLine();
        }
        return betAmount;
    }

    /**
      setName method that initializes the name instance variable to a user
      chosen name and prints this name out
     */
    public void setName() {
        String inputName = input.nextLine();
        this.name = inputName;
        System.out.println();
    }

    /**
     * getName method that returns the private variable name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
      addCredits method that adds credits to variable credit
      @param amount
     */
    public void addCredits(int amount) {
        credits+=amount;
    }

    public void removeCredits(int amount) {
        credits-=amount;
    }

    /**
      addCard method adds a card to the playerCollection arrayList
      @param dealtCard
     */
    public void addCard(Card dealtCard) {
        playerCollection.add(dealtCard);
    }

    // toString method that returns the toString of playerCollection
    public String toString() {
        return playerCollection.toString();
    }

    // getHandValue method that returns the getHandValue method of playerCollection
    public int getHandValue() {
        return playerCollection.getHandValue();
    }

    // clear method that clears the Player's hand and calls the Hand class's clear method
    public void clear() {
        playerCollection.getCards().clear();
    }

    // getHandSize method that returns the size of the Hand ArrayList
    public int getHandSize() {
        return playerCollection.getCards().size();
    }

    /**
     * getCard method that returns a card at a specific index in the ArrayList
     * @param index
     * @return Card at a specific index
     */
    public Card getCard(int index) {
        return playerCollection.getCards().get(index);
    }

    /**
     * has11Ace method that calls the Hand method has11Ace
     * @return true or false
     */
    public boolean has11Ace() {
        return playerCollection.has11Ace();
    }

    /**
     * busted method that checks if the player has busted or not,
     * based on getHandValue method from Hand class
     * @return true or false
     */
    public boolean busted() {
        return playerCollection.getHandValue()>21;
    }

}

/**
 Dealer class that acts as a real life dealer in the game Blackjack
 This class contains several methods that represent the rules of Blackjack,
 such as the keepDealing method, that returns a boolean, deciding whether
 the dealer needs to continue drawing cards
 */
class Dealer extends Player {

    // constructor for the dealer class that initializes dealerHand
    public Dealer() {
        super();
    }

    /**
     keepDealing method that determines whether the dealer still needs cards
     @return true or false
     */
    public boolean keepDealing() {
        return super.getHandValue() <= 16
                || super.getHandValue() <= 17
                && super.has11Ace();
    }

    /**
     toString method that return the dealer's hand but keeps the last card hidden
     @return returnVal
     */
    public String toString() {
        String returnVal = "\n";
        for(int i=1; i<super.getHandSize(); i++) {
            returnVal+=super.getCard(i).toString() + "\n";
        }
        return returnVal;
    }

    /**
     dealerWin method that determines whether the dealer has won based on the
     dealerHand's arraylist's value
     @return true or false
     */
    public boolean dealerWin() {
        int dealerValue = super.getHandValue();
        System.out.println("Dealer's Hand: ");
        for(int i=0; i<super.getHandSize(); i++) {
            System.out.print(super.getCard(i) + " ");
            if(i==(super.getHandSize()-1)) {
                System.out.println("\nTotal Value: " + super.getHandValue());
            }
        }
        System.out.println();
        return dealerValue == 21;
    }
}

/**
  Hand class that represents the cards a player has
  Possesses methods that determine the value of the cards in hand, adds cards to the hand
  and checks whether or not there is an ace in the hand
 */
class Hand {
    // instance variables of Hand class
    private ArrayList<Card> cards;
    private int totalValue;

    /**
      constructor for the Hand class that initializes the cardHand variable
     */
    public Hand() {
        cards = new ArrayList<Card>();
    }

    /**
      add method that adds a card to cardHand arrayList
      @param dealtCard
     */
    public void add(Card dealtCard) {
        cards.add(dealtCard);
    }

    /**
      initializes the totalValue variable and adds together all the values of
      the cardHand arraylist. If the totalValue is greater than 21 and the hand has
      an ace, it will run through and change the ace's value to a 1.
      @return totalValue
     */
    public int getHandValue() {
        totalValue = 0;
        for(int i=0; i<cards.size();i++) {
            totalValue += cards.get(i).getValue();
        }
        if(totalValue>21) {
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i).getValue() == 11) {
                    cards.get(i).changeValue(1);
                    totalValue-=10;
                }
                if(totalValue<21) {
                    break;
                }
            }
        }
        return totalValue;
    }

    // has11Ace method that checks if there is an ace in the arrayList
    public boolean has11Ace() {
        for(int i=0; i<cards.size();i++) {
            if(cards.get(i).getValue()==11) {
                return true;
            }
        }
        return false;
    }

    /**
      toString method that returns the value of the cardHand
      @return response + getHandValue
     */
    public String toString() {
        String response = "Your Hand has:\n";
        for(int i=0; i<cards.size(); i++) {
            response += cards.get(i).toString() + "\n";
        }
        return response + getHandValue();
    }

    // getCards method that returns the cards arraylist
    public ArrayList<Card> getCards() {
        return cards;
    }
}

/**
 Deck class that initializes a full arrayList of Cards
 representing a real life deck of cards
 */
class Deck {
    // instance variable for the Deck class
    private ArrayList<Card> cardDeck;

    /**
     constructor for the deck class, that initializes an arraylist of cards
     representing a real deck of cards
     */
    public Deck() {
        cardDeck = new ArrayList<Card>();
        for(int i = 1; i<14; i++) {
            cardDeck.add(new Card(i,"Hearts", i));
        }
        for(int i = 1; i<14; i++) {
            cardDeck.add(new Card(i,"Clubs", i));
        }
        for(int i = 1; i<14; i++) {
            cardDeck.add(new Card(i,"Diamonds", i));
        }
        for(int i = 1; i<14; i++) {
            cardDeck.add(new Card(i,"Spades", i));
        }
        shuffle();
    }

    /**
     deal method that takes a card out of an arraylist and returns the removed card
     @return dealtCard
     */
    public Card deal() {
        Card dealtCard = cardDeck.get(cardDeck.size()-1);
        cardDeck.remove(cardDeck.size()-1);
        return dealtCard;
    }

    /**
     toString method that returns the arrayList
     @return cardDeck
     */
    public String toString() {
        return cardDeck.toString();
    }

    /**
     shuffle method that mixes up the organization of cardDeck
     */
    public void shuffle() {
        for(int i =51; i>=1; i--) {
            int randNum = (int)(Math.random()*(i+1));
            cardDeck.set(i,cardDeck.set(randNum,cardDeck.get(i)));
        }
    }
}

/**
   Card class that represents a real life card
   Used in order to play Blackjack
 */
class Card {
    // instance variables for Card Class
    private String suit;
    private int rank;
    private int value;

    /**
       Constructor method for class Card that updates the instance variables
       @param cardRank
       @param suit
       @param value
     */
    public Card(int cardRank, String suit, int value) {
        this.suit = suit;
        this.rank = cardRank;
        this.value = value;
        if(this.value == 11
            || this.value == 12
            || this.value == 13) {
                this.value=10;
        }
        if(this.value==1) {
            this.value=11;
        }
    }

    /**
       toString method that returns the suit and value of the card
       @return rank of suit
     */
    public String toString() {
        if(rank==11) {
            return "Jack of " + suit + "(point value = " + value + ")";
        } else if(rank==12) {
            return "Queen of " + suit + "(point value = " + value + ")";
        } else if(rank == 13) {
            return "King of " + suit + "(point value = " + value + ")";
        } else if(rank == 1) {
            return "Ace of " + suit + "(point value = " + value + ")";
        } else {
            return rank + " of " + suit + "(point value = " + value + ")";
        }
    }

    /**
       getValue method to get variable value
       @return value
     */
    public int getValue() {return value;}

    /**
       changeValue method that takes an int and
       changes the value of a card to the parameter
       @param value
     */
    public void changeValue(int value) {
        this.value = value;
    }
}
