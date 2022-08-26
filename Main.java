/*
*The purpose of this program is to allow the user/users to play the game Family Feud and also demonstrates my adeptness at programming (coding) when it comes to the topic covered in Grade 11 Computer Science/why I have improved to first place in the class
*
*Author: Sean Pourgoutzidis
*Date Created: May 24, 2020
*Last Modified: June 15, 2020
*
*/

import java.io.*;//Import java.io
import java.util.*;//Import java.util

class Main {//Start of main class
  public static void main(String[] args)throws IOException {
    new Main ();//Main method calls the non-static main method
  }

  /*
  *The purpose of this method is to call all of the other important methods of the game
  */
  public Main () throws IOException {//Start of non-static main method
    boolean playAgain=false;

    do {//Start of do-while that iterates if the user still wants to play
      char choice='z';//char varaible choice (initialized to z) is used to hold user input to maneuvre around the menu
      String winner="Sean";//String winner is initialized and will hold the name of the family that "wins" family feud and moves to the fast money round
      int score=0;//Holds the score of the winner
      
      choice = menu(choice);//Passes the variable choice into the method menu and makes the value of choice equal to what its returned by the method

      nextPage();//Calls the method nextPage to clear the screen

      if (choice=='b'||choice=='B')//if the value of choice is b
      {
        winner=roundsComputer();//Makes the value of winner equal to what is returned by the method roundsPlayers
        nextPage();

        if (winner.equals("Computer"))
        {
          System.out.println("The computer will advance to Fast-Money, thanks for playing");//If the computer wins, there is no point for the user to watch it play fast money
          choice='c';//sets choice to c to ensure that the user that lost cannot add their non-existent fastmoney score to the Hall of Fame
        }
        
        else 
        {
          score=fastMoney(winner);
        }
    
        nextPage();
        playAgain=farewellPage(winner, score, choice);
      }

      else if (choice=='a'||choice=='A')//If the value of choice is a
      {
        winner=roundsPlayers();//Makes the value of winner equal to what is returned by the method roundsPlayers
        nextPage();
        score=fastMoney(winner);
        nextPage();
        playAgain=farewellPage(winner, score, choice);
      }

      else if (choice=='c'||choice=='C')//if the user chooses to quit the game
      { 
        playAgain=farewellPage(winner, score, choice);//Calls the farewellPage
      }
    
    } while (playAgain);//end of do-while Loop that controls the game will iterate if the player selects to play again in the farewell
  }

  /**
  *The purpose of this method is to call the gameplay methods associated with the player vs player gamemode. It also determines who the winner of the general rounds is. 
  *
  *@return Returns the winner of the general rounds
  */
  public String roundsPlayers ()throws IOException {//start of roundsPlayers method
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    char choice='n';//initialization of char variable choice that holds the user's choice on whether they'd like to see the instructions
    
    do {
      
      System.out.println("Would you like to see the instructions (y/n)");//prompts the user for input
      choice=br.readLine().charAt(0);

      if (choice!='y'&&choice!='Y'&&choice!='n'&&choice!='N') System.out.println("Invalid answer, try again");//if they enterred invalid input, let them know

    } while (choice!='y'&&choice!='Y'&&choice!='n'&&choice!='N');//loop iterates while the user has not enterred valid input

    if (choice=='Y'||choice=='y') instructions();//if they would like to see the instructions, call the instructions
    nextPage();

    String player1, player2;//Initializes the variables player1 and player2 to hold the names of the players

    System.out.println("Please enter the name of the first family:\n");//Prompts the user for input
    player1=br.readLine();//Initializes the value of player1 to user input

    System.out.println("\nPlease enter the name of the second family:\n");//Prompts the user for input
    player2=br.readLine();//Initializes the value of player2 to user input

    System.out.println("\n\n");//Prints several lines
    
    HashMap<String, Integer> scores = new HashMap<String, Integer>();//Declares the HashMap score that has the user's names as the keys and their scores as the elements
    scores.put(player1, 0);
    scores.put(player2, 0);//Initializes both players score to zero

    //Declare scanner and initialize it to read from the questions file
    File myFile = new File("Questions");
    Scanner questionsReader = new Scanner(myFile);//Initializes the scanner questionsReader to read from the Questions file in order to garner questions for the regular rounds
    Scanner scannerChecker = new Scanner (myFile);//Initializes the scanner questionsReader to read from the Questions file in order to garner questions for the regular rounds

    HashMap<String, Integer> roundAnswers = new HashMap<String, Integer>();//Initializes the HashMap roundAnswers to hold the answers for each questions and the scores associated with them
    ArrayList <String> question = new ArrayList<>();//Declares the ArrayList question that holds all of the data for each question

    String winner="Sean";//Initializes the string variable winner to the all-time greatest winner ever
    int i=0;//Initializes the int variable i which will be used to count the number of rounds
     
    do 
    {//Start of do while loop that iterates while either player has not reached 300 points   
      i++;//Increments the variable i
      
      do {
        question.clear();//Clears the ArrayList question

        question=roundsReader(questionsReader, scannerChecker);//Makes the value of question equal to what is returned by the method roundsReader after it is called

        if (question.get(0).equals("empty"))//if the scanner is at the end of the file
        {
          questionsReader.close();
          scannerChecker.close();//close both scanners

          myFile = new File("Questions"); 
          questionsReader = new Scanner(myFile);
          scannerChecker = new Scanner (myFile);//reinitialize the scanners to read from the top again
        }

      }while (question.get(0).equals("empty"));//Loop while the scanner is at the end of the file

      roundAnswers.clear();//Clears the HashMap roundAnswers

      for (int j=1; j<question.size(); j+=2)//Loops through each answer and its corresponding score in the ArrayList question
      {
        roundAnswers.put(question.get(j),Integer.parseInt(question.get(j+1)));//Adds every answer as a key and its score to the HashMap roundAnswers
      }

      nextPage();//Calls the nextPage method
      scores=answering(scores, question, roundAnswers, i, player1, player2);//Makes the value of HashMap scores equal to what is returned by the method answering
      System.out.println("\n"+player1+"'s score is "+ scores.get(player1)+" and "+player2+"'s score is "+ scores.get(player2));//Prints both players scores

    }while (scores.get(player1)<300&&scores.get(player2)<300);//end of Do while iterates while either player has not reached 300 points
      
    if (scores.get(player1)>scores.get(player2))//If player1 has the higher score
    {
      winner=player1;//Makes the value of winner equal to player1
    }
      
    else if (scores.get(player2)>scores.get(player1))//If player2 has the higher score
    {
      winner=player2;//Makes the value of winner equal to player2 
    }

    else if (scores.get(player1)==scores.get(player2))//If their scores are equal which is extremely unlikely
    {
      int coinFlip=coinFlip();

      System.out.println("The winner is to be decided by a coinFlip");//lets the user know what is happening

      if (coinFlip==0)winner=player1;
      else if (coinFlip==1) winner=player2;//The winner is decided by a coin flip
    }  
    
    questionsReader.close();
    scannerChecker.close();//Closes both scanners

    System.out.println("The winner that continues to the Fast-Money Round is "+ winner);//Lets the user know who the winner is
    
    return winner;//Returns the variable winner
  }//end of roundsPlayers method
  
  /**
  *The purpose of this method is to read the questions from the textfile that stores them =
  *
  *@param sc A scanner that is used to read the questions from the textfile
  *@param scChecker A scanner that is also used to read the questions from the textfile
  *
  *@return Returns the arrayList that holds all of the data for each question
  */
  public ArrayList<String> roundsReader (Scanner sc, Scanner scChecker)throws IOException {//start of roundsReader method
    
    ArrayList <String> question = new ArrayList<>();//Declares the ArrayList question

    ArrayList <String> empty = new ArrayList<>();//An ArrayList that denotes if the file has been read entirely
    empty.add("empty");//Adds the word empty to the empty ArrayList which will be used to check if the file is out of questions

    if (scChecker.nextLine().equals("end"))//if at the end of the file
    {
      return empty;//If the file is empty, return the empty arrayList
    }

    int headsOrTails=coinFlip();//Initializes the int variable headsOrTails to what is returned by the method coinFlip

    if (headsOrTails==0)//if headsOrTails is equal to zero
    {

      do {
        question.add(sc.nextLine());//Adds the nextLine in the questions document to the question ArrayList
      }while (scChecker.nextLine().equals("***")==false);//Loop iterates while scChecker is not equal to *** which separates each of the questions

      sc.nextLine();//Moves the sc scanner one line forward so its in line with scChecker
    }

    else if (headsOrTails==1)//If the value of headsOrTails is equal to 1
    {//Start of else if

      do {

        sc.nextLine();

      }while (scChecker.nextLine().equals("***")==false);//Skips one question in the document
      
      sc.nextLine();
      scChecker.nextLine();//Moves both scanners over one time

      do {
        
        question.add(sc.nextLine());//Adds each line to the questions ArrayList

      }while (scChecker.nextLine().equals("***")==false);//Loop iterates while the nextLine of scChecker is not equal to *** which divides the questions

      sc.nextLine();//Moves the scanner sc down one more line to bring it in line with scChecker

    }//end of else if

    return question;//returns the ArrayList question
  }//end of roundsReader method

  /**
  *The purpose of this method is to allow the users to guess the answers during the round and to manage all of the general functions of the general rounds
  *
  *@param scores A HashMap that stores the user's as the keys and an their scores as the elements
  *@param question An ArrayList that holds all of the data regarding the question
  *@param roundAnswers A HashMap that holds all the answers as the Keys and their corresponding scores as their integers
  *@param roundNumber An int variable that holds which round number it is
  *@param player1 The String variable that holds the name of the first user
  *@param player2 The String variable that holds the name of the second user
  *
  *@return Returns the HashMap that holds the user's scores
  */
  public HashMap <String, Integer> answering (HashMap<String, Integer> scores, ArrayList<String> question, HashMap<String, Integer> roundAnswers, int roundNumber, String player1, String player2 )throws IOException {//start of answering method
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    System.out.println("\n\nRound "+ (roundNumber)+": \n");//Prints the round number
    System.out.println(question.get(0));//Prints the first element of the ArrayList question which is the question itself to the viewer
    System.out.println("There are "+roundAnswers.size()+" answers on the board");//Prints how many answers there are to the viewer

    int meFirst=0;//Initializes variable meFirst which determines which player goes first when it comes to answering
    ArrayList <String> correctAnswers = new ArrayList<>();//Declares the ArrayList correctAnswers which will hold all the correct guesses from the user

    meFirst=faceoff(correctAnswers, roundAnswers, player1, player2);//Makes the value of meFirst equal to what is returned by the method faceoff

    int roundScore=0;//Initializes the int variable roundScore that will hold the current score for each round
    
    if (correctAnswers.size()==2)//if both users get the faceoff question right, add the values of those answers to the roundScore
    {
      roundScore+=roundAnswers.get(correctAnswers.get(0));
      roundScore+=roundAnswers.get(correctAnswers.get(1));
    }

    else if (correctAnswers.size()==1)//if only one user got the faceoff question right, add its value to the roundScore
    {
      roundScore+=roundAnswers.get(correctAnswers.get(0));
    }
  
    if (meFirst==0)//if meFirst is equal to 0, make player1 go first in the round
    {//start of if statement that makes player 1 go first
      int wrongAnswer=0;//Initializes the int variable wrongAnswer which holds the number of incorrect guesses from the user who is going first
      String answer;//Declares the string answer that will hold user input

      do {//Start of do while that iterates while the user hasn't gotten three wrong
        System.out.println("\n______________________________________________________________________________________________________________________________");
        System.out.println("                       "+question.get(0));
        System.out.println("                                                                                                                      Round: "+roundNumber);
        System.out.println("                                                                                                          Incorrect Guesses: "+wrongAnswer);
        System.out.println("                                                                                                               Answers Left: "+(roundAnswers.size()-correctAnswers.size()));//Prints the current status of the game such as the round number, incorrect guesses, answers left and both user's scores
        System.out.println("                                                                                                               "+player1+"'s score: "+scores.get(player1));
        System.out.println("                                                                                                               "+player2+"'s score: "+scores.get(player2));
        System.out.println("There are "+(roundAnswers.size()-correctAnswers.size())+" left on the board\n");//Lets the user know how many answers are still left on the board
        System.out.println("Please enter your answer:\n");//Prompts the user for input
        answer=br.readLine().toLowerCase();//Makes answer equal to user input (converted to lowercase)

        if (roundAnswers.containsKey(answer)==false||correctAnswers.contains(answer))//if the answer is incorrect or has already been said
        {//start of if statement that checks to see if they got it right
          wrongAnswer++;//Increments wrongAnswer
          if (wrongAnswer==1)//if they have one wrong answer
          {
            System.out.println("\n .----------------. ");
            System.out.println("| .--------------. |");
            System.out.println("| |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | |");//Print one big x
            System.out.println("| |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | |");
            System.out.println("| |              | |");
            System.out.println("| '--------------' |");
            System.out.println(" '----------------' \n");
          }

          else if (wrongAnswer==2)//two wrong answers
          {
            System.out.println("\n .----------------.  .----------------. ");
            System.out.println("| .--------------. || .--------------. |");
            System.out.println("| |  ____  ____  | || |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | || | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | || |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | || |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | |");//two big x's are printed
            System.out.println("| | |____||____| | || | |____||____| | |");
            System.out.println("| |              | || |              | |");
            System.out.println("| '--------------' || '--------------' |");
            System.out.println(" '----------------'  '----------------' \n");
          }

          else if (wrongAnswer==3)//if they enter three wrong answers
          {
            System.out.println("\n .----------------.  .----------------.  .----------------. ");
            System.out.println("| .--------------. || .--------------. || .--------------. |");
            System.out.println("| |  ____  ____  | || |  ____  ____  | || |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | || | |_  _||_  _| | || | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | || |   \\ \\  / /   | || |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | || |    > `' <    | || |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | |");//Print three big x's
            System.out.println("| | |____||____| | || | |____||____| | || | |____||____| | |");
            System.out.println("| |              | || |              | || |              | |");
            System.out.println("| '--------------' || '--------------' || '--------------' |");
            System.out.println(" '----------------'  '----------------'  '----------------' \n");
          }//Prints a number of x's corresponding to the number of incorrect guesses
          
          if (roundAnswers.containsKey(answer)==false&&correctAnswers.contains(answer)==false)//Lets the user know if the answer is wrong
          System.out.println("Unfortunately your answer of \""+answer+"\" is not on the board");

          else if (roundAnswers.containsKey(answer)==true&&correctAnswers.contains(answer))//Lets the user know that the answer they inputted has already been said
          System.out.println("The answer you entered was already on the board");
        }//end of if statement that checks to see if they got it right

        else if (roundAnswers.containsKey(answer)&&correctAnswers.contains(answer)==false)//If the user's answer is correct and has not already been said
        { 
          System.out.println("\nNice one! Your answer of \""+answer+"\" was on the board");
          System.out.println("That answer was worth "+roundAnswers.get(answer));//Lets the user know that they got it right and adds its value to the roundScore
          correctAnswers.add(answer);//Adds the correct answer to the ArrayList correctAnswers
          roundScore+=roundAnswers.get(answer);          
        }  

        if (correctAnswers.size()==roundAnswers.size())//If the number of correct answers is equal to the size of the HashMap that holds all of the answers
        {
          System.out.println("\n______________________________________________________________________________________________________________________________");
          System.out.println("\n\nYou have cleared the board, good job "+player1+"!");//Lets the player know that they've cleared the board
          System.out.println(roundScore+" has been added to your point total");
          scores.put(player1, scores.get(player1)+roundScore);//Adds the roundScore to the user's score
          break;//breaks out of loop
        }

      } while (wrongAnswer!=3);//End of do while that iterates while the user hasn't gotten three wrong

      if (wrongAnswer==3)//If the other use got three wrong
      {//Start of if statement that checks to see if they got three wrong
        System.out.println("\n______________________________________________________________________________________________________________________________");
        System.out.println("\n\n"+player2+", you now have the opportunity to steal!");//Prompts the other user telling them that they can steal and to send their answer
        System.out.println("There are "+(roundAnswers.size()-correctAnswers.size())+" left on the board\n");
        System.out.println("Enter your answer:");
        answer=br.readLine();

        if (roundAnswers.containsKey(answer)&&correctAnswers.contains(answer)==false)//if they get it right with a unique answer
        {
          System.out.println("\nYou have successfully stolen this round!");//Lets them know that they've stolen the round
          roundScore+=roundAnswers.get(answer);
          System.out.println(roundScore+" will be added to your point total");
          scores.put(player2, scores.get(player2)+roundScore);//Adds the roundScore to the user's score
        }

        else//if they get it wrong
        {
          System.out.println(" .----------------. ");
          System.out.println("| .--------------. |");
          System.out.println("| |  ____  ____  | |");
          System.out.println("| | |_  _||_  _| | |");
          System.out.println("| |   \\ \\  / /   | |");
          System.out.println("| |    > `' <    | |");
          System.out.println("| |  _/ /'`\\ \\_  | |");
          System.out.println("| | |____||____| | |");
          System.out.println("| |              | |");
          System.out.println("| '--------------' |");
          System.out.println(" '----------------' \n");
          System.out.println("Unfortunately, your answer was either not on the board or it was already said");//Lets the user know that they've got it wrong
          System.out.println(roundScore+" will be added to your opponents score");
          scores.put(player1, scores.get(player1)+roundScore);//Adds the roundScore to the other player's score
        }
      }//end of if statement that checks to see if they got three wrong
    }//end of if statement that makes player 1 go first 

    else if (meFirst==1)//if meFirst is equal to one then player2 gains control of the question
    {//start of else if statement that makes player2 answer the question
      int wrongAnswer=0;//Initializes the int variable wrongAnswer which holds the number of incorrect guesses from the user who is going first
      String answer;//Declares the String answer which holds user input

      do {//Start of do while
        System.out.println("\n______________________________________________________________________________________________________________________________");
        System.out.println("                       "+question.get(0));
        System.out.println("                                                                                                                      Round: "+roundNumber);
        System.out.println("                                                                                                          Incorrect Guesses: "+wrongAnswer);
        System.out.println("                                                                                                               Answers Left: "+(roundAnswers.size()-correctAnswers.size()));//Prints the current status of the game such as the round number, incorrect guesses, answers left and both user's scores
        System.out.println("                                                                                                               "+player1+"'s score: "+scores.get(player1));
        System.out.println("                                                                                                               "+player2+"'s score: "+scores.get(player2));
        System.out.println("There are "+(roundAnswers.size()-correctAnswers.size())+" left on the board\n");//Lets the user know how many answers are left on the board
        System.out.println("Please enter your answer:\n");//Prompts the user for input
        answer=br.readLine().toLowerCase();//makes the variable answer equal to user input (converted to lowerCase)

        if (roundAnswers.containsKey(answer)==false||correctAnswers.contains(answer))//if the user enterred a wrong answer or a non-unique answer
        {//start of if statement that checks to see if the user's answer is wrong or not unique
          wrongAnswer++;//Increments the variable wrongAnswer
          if (wrongAnswer==1)
          {
            System.out.println("\n .----------------. ");
            System.out.println("| .--------------. |");
            System.out.println("| |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | |");//if they get one wrong print a big x
            System.out.println("| |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | |");
            System.out.println("| |              | |");
            System.out.println("| '--------------' |");
            System.out.println(" '----------------' \n");
          }

          else if (wrongAnswer==2)
          {
            System.out.println("\n .----------------.  .----------------. ");
            System.out.println("| .--------------. || .--------------. |");
            System.out.println("| |  ____  ____  | || |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | || | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | || |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | || |    > `' <    | |");//if they get two wrong, print two big x's
            System.out.println("| |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | || | |____||____| | |");
            System.out.println("| |              | || |              | |");
            System.out.println("| '--------------' || '--------------' |");
            System.out.println(" '----------------'  '----------------' \n");
          }

          else if (wrongAnswer==3)
          {
            System.out.println("\n .----------------.  .----------------.  .----------------. ");
            System.out.println("| .--------------. || .--------------. || .--------------. |");
            System.out.println("| |  ____  ____  | || |  ____  ____  | || |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | || | |_  _||_  _| | || | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | || |   \\ \\  / /   | || |   \\ \\  / /   | |");//if they get three wrong, print three big x's
            System.out.println("| |    > `' <    | || |    > `' <    | || |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | || | |____||____| | || | |____||____| | |");
            System.out.println("| |              | || |              | || |              | |");
            System.out.println("| '--------------' || '--------------' || '--------------' |");
            System.out.println(" '----------------'  '----------------'  '----------------' \n");
          }//Prints a number of x's equivalent to wrongAnswer
          
          if (roundAnswers.containsKey(answer)==false&&correctAnswers.contains(answer)==false)
          System.out.println("Unfortunately your answer of \""+answer+"\" is not on the board");//If they got the answer wrong, print that to the console

          else if (roundAnswers.containsKey(answer)==true&&correctAnswers.contains(answer))
          System.out.println("The answer you entered was already on the board");//If the user enterred an answer that has already been answered, print this to the console
        }//end of if statement that checks to see if the user's answer is wrong or not unique

        else if (roundAnswers.containsKey(answer)&&correctAnswers.contains(answer)==false)//If the user enterred a correct and unique answer
        {
          System.out.println("\nNice one! Your answer of \""+answer+"\" was on the board");//Let them know that they got it right
          System.out.println("That answer was worth "+roundAnswers.get(answer));
          correctAnswers.add(answer);//Adds the answer to the correctAnswers ArrayList
          roundScore+=roundAnswers.get(answer);//Adds the answer's point value to roundScore
        }
        
        if (correctAnswers.size()==roundAnswers.size())//If the number of correctAnswers is equal to the number of answers
        {
          System.out.println("\n______________________________________________________________________________________________________________________________");
          System.out.println("\nYou have cleared the board, good job "+player2+"!");//Let the user know that they've cleared the board
          System.out.println(roundScore+" has been added to your point total");//Add roundScore to player2's score
          scores.put(player2, scores.get(player2)+roundScore);
          break;//breaks out of the loop
        }

      } while (wrongAnswer!=3);//end of do while that iterates while the user hasn't gotten three wrong

      if (wrongAnswer==3)//if the above user got three wrong
      {//start of if statement that checks to see if the user answering got three wrong
        System.out.println("\n______________________________________________________________________________________________________________________________");
        System.out.println("\n\n"+player1+", you now have the opportunity to steal!");//Prompt the other user to enter an answer
        System.out.println("There are "+(roundAnswers.size()-correctAnswers.size())+" left on the board\n");//Let the user know how many answers are left
        System.out.println("Enter your answer:");
        answer=br.readLine();//Makes the value of answer equal to user input

        if (roundAnswers.containsKey(answer)&&correctAnswers.contains(answer)==false)//If they get the steal attempt right and its a unique answer
        {
          System.out.println("\nYou have successfully stolen this round!");//Let them know that they've stolen
          roundScore+=roundAnswers.get(answer);//Adds the answers score to roundScore
          System.out.println(roundScore+" will be added to your point total");
          scores.put(player1, scores.get(player1)+roundScore);//Adds the roundScore to the user's score
        }

        else//If they get it wrong or their answer is not unique
        {
          System.out.println("\n .----------------. ");
          System.out.println("| .--------------. |");
          System.out.println("| |  ____  ____  | |");
          System.out.println("| | |_  _||_  _| | |");
          System.out.println("| |   \\ \\  / /   | |");
          System.out.println("| |    > `' <    | |");
          System.out.println("| |  _/ /'`\\ \\_  | |");
          System.out.println("| | |____||____| | |");
          System.out.println("| |              | |");
          System.out.println("| '--------------' |");
          System.out.println(" '----------------' \n");
          System.out.println("Unfortunately, your answer either was not on the board or has already been said");
          System.out.println(roundScore+" will be added to your opponents score");//Let them know that they've gotten it wrong
          scores.put(player2, scores.get(player2)+roundScore);//Add the roundScore to the opponents score
        }
      }//end of if statement that checks to see if the user answering got three wrong
    }//end of else if statement that makes player2 answer the question
      
    return scores;//returns the scores HashMap
  }//end of answering method

  /**
  * The purpose of this method is to determine which user wins the faceoff and gains control of the question
  *
  *@param roundAnswers A HashMap that stores the answers to the question and the answers corresponding scores as elements
  *@param player1 A string variable that stores the name of player1
  *@param player2 A string variable that stores the name of player2
  *
  *@return Returns a int variable that denotes which user gains control of the question
  */
  public int faceoff (ArrayList<String> correctAnswers, HashMap<String, Integer> roundAnswers, String player1, String player2 ) throws IOException {//start of faceoff method
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    String answer1="Sean", answer2="Anthony";//Declares and initializes the variables answer1 and answer2 that holds user input
    int meFirst=coinFlip();//Makes the value of int variable meFirst that determines which user goes first
    boolean answer1Correct=true, answer2Correct=true;//Initializes the boolean variables answer1Correct and answer2Correct that determine whether or not each use got their answer right

      do {//Start of do while
        answer1Correct=true;
        answer2Correct=true;//Resets the values of answer1Correct and answer2Correct

        if (meFirst==0)//if the value of meFirst is equal to 0, player1 goes first
        {//start of meFirst that makes player1 go first
          System.out.println("______________________________________________________________________________________________________________________________");
          System.out.println("\n\n"+player1+ ", it's your turn to guess:\n");//Lets player1 know that its their turn to guess
          answer1=br.readLine().toLowerCase();//Makes the value of answer1 equal to user input

          if (roundAnswers.containsKey(answer1)&&correctAnswers.contains(answer1)==false)//If they get the answer right and it hasn't been guessesd
          {
            System.out.println(answer1+" is on the board with a score of "+roundAnswers.get(answer1));//Lets the user know that they've got it right
            correctAnswers.add(answer1);//Add the answer to the ArrayList correctAnswers
          }

          else if (roundAnswers.containsKey(answer1)==false||correctAnswers.contains(answer1))//If they got it wrong or the answer enterred was already said
          {
            System.out.println("\n .----------------. ");
            System.out.println("| .--------------. |");
            System.out.println("| |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | |");
            System.out.println("| |              | |");
            System.out.println("| '--------------' |");
            System.out.println(" '----------------' \n");
            System.out.println(answer1+ " is unfortunately not on the board or has already been said");//Let them know that they've got it wrong
            answer1Correct=false;//Makes the value of answer1Corret to false
          }

          System.out.println("______________________________________________________________________________________________________________________________");
          System.out.println("\n\n"+player2+", now it's your turn to guess:\n");//Lets the other user know that it's their turn to guess
          answer2=br.readLine().toLowerCase();//Makes the value of answer2 to user input

          if (roundAnswers.containsKey(answer2)&&correctAnswers.contains(answer2)==false)//if the answer is correct and unique
          {
            System.out.println(answer2+" is on the board with a score of "+roundAnswers.get(answer2));//Let them know that they got it right
            correctAnswers.add(answer2);//Add the answer to the arraylist correctAnswers
          }

          else if (roundAnswers.containsKey(answer2)==false||correctAnswers.contains(answer2))//if the answer is wrong or is not unique
          {
            System.out.println("\n .----------------. ");
            System.out.println("| .--------------. |");
            System.out.println("| |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | |");
            System.out.println("| |              | |");
            System.out.println("| '--------------' |");
            System.out.println(" '----------------' \n");
            System.out.println(answer2+ " is unfortunately not on the board or has already been said");//Let them know that they've got it wrong
            answer2Correct=false;//Make the value of answer2Correct to false
          }
        }//end of meFirst that makes player1 go first

        else if (meFirst==1)//If the value of meFirst is 1 the second user goes first
        {//start of else if that maes player2 answer first
          System.out.println("______________________________________________________________________________________________________________________________");
          System.out.println("\n\n"+player2+ ", it's your turn to guess:\n");//tells the user it is time for them to guess
          answer2=br.readLine().toLowerCase();//converts the answer to lowercase to make it easier to compare

          if (roundAnswers.containsKey(answer2)&&correctAnswers.contains(answer2)==false)//if they get the answer right and it is unique
          {
            System.out.println(answer2+" is on the board with a score of "+roundAnswers.get(answer2));//Tell them that the answer is correct
            correctAnswers.add(answer2);//Add the answer to the corrextAnswers ArrayList
          }

          else if (roundAnswers.containsKey(answer2)==false||correctAnswers.contains(answer2))
          {
            System.out.println("\n .----------------. ");
            System.out.println("| .--------------. |");
            System.out.println("| |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | |");//if the answer is repeated or incorrect tell them it is wrong
            System.out.println("| |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | |");
            System.out.println("| |              | |");
            System.out.println("| '--------------' |");
            System.out.println(" '----------------' \n");
            System.out.println(answer2+ " is unfortunately not on the board or has already been said");
            answer2Correct=false;//Make the variable answer2Correct as false
          }

          System.out.println("______________________________________________________________________________________________________________________________");
          System.out.println("\n\n"+player1+", now it's your turn to guess:\n");//lets the other user know it is their time to guess
          answer1=br.readLine().toLowerCase();//converts the answer to lowercase for ease of handling

          if (roundAnswers.containsKey(answer1)&&correctAnswers.contains(answer1)==false)//if they got the answer right and its unique
          {
            System.out.println(answer1+" is on the board with a score of "+roundAnswers.get(answer1));
            correctAnswers.add(answer1);//add the correct answer to the correctAnswers ArrayList and let them know that they got it right
          }

          else if (roundAnswers.containsKey(answer1)==false||correctAnswers.contains(answer1))//if they get it wrong or the answer is not unique
          {
            System.out.println("\n .----------------. ");
            System.out.println("| .--------------. |");
            System.out.println("| |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | |");//let them know they got it wrong
            System.out.println("| |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | |");
            System.out.println("| |              | |");
            System.out.println("| '--------------' |");
            System.out.println(" '----------------' \n");
            System.out.println(answer1+ " is unfortunately not on the board or has already been said");
            answer1Correct=false;//Make the value of answer1Correct false
          }
        }//end of else if that makes player2 answer first
      
      }while (answer1Correct==false&&answer2Correct==false);//End of do-while that iterates while both user's have gotten the question wrong

      if (answer1Correct&&answer2Correct==false) 
      {
        meFirst=0;//If player1 got it right and player2 didn't, player1 gains control of the question
        System.out.println("\n\n______________________________________________________________________________________________________________________________");
        System.out.println(player1+", you again control of the question\n");//Lets the user know if they are going first
      }
      else if (answer1Correct==false&&answer2Correct) 
      {
        meFirst=1;//If player2 got it right and player1 didn't, player2 gains control of the question
        System.out.println("\n\n______________________________________________________________________________________________________________________________");
        System.out.println(player2+", you again control of the question\n");//Lets the user know if they are going first
      }
      else if (answer1Correct&&answer2Correct)//If both user's got their answer right
      {
        if (roundAnswers.get(answer1)>roundAnswers.get(answer2))
        {
          meFirst=0;//If player1's answer was higher on the board, they gain control of the question
          System.out.println("\n\n______________________________________________________________________________________________________________________________");
          System.out.println(player1+", you again control of the question\n");//Lets the user know if they are going first
        }

        else if (roundAnswers.get(answer2)>roundAnswers.get(answer1))
        {  
          meFirst=1;//If player2's answer was higher on the board, they gain control of the question
          System.out.println("\n\n______________________________________________________________________________________________________________________________");
          System.out.println(player2+", you again control of the question\n");//Lets the user know if they are going first
        }
      }
    
    nextPage();
    return meFirst;//return the value of meFirst
  }//end of faceoff method

  
  /**
  *The purpose of this method is to call the gameplay methods associated with the player vs computer gamemode. It also determines who the winner of the general rounds is.
  *
  *@return Returns the winner of the general rounds between the player and the computer
  */
  public String roundsComputer ()throws IOException {//start of roundsComputer method
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    char choice='n';//char n is used to store the user's input
    
    do {
      
      System.out.println("Would you like to see the instructions (y/n)");//Prompts for input
      choice=br.readLine().charAt(0);

      if (choice!='y'&&choice!='Y'&&choice!='n'&&choice!='N') System.out.println("Invalid answer, try again");//lets the user know if they enter invalid input
    
    } while (choice!='y'&&choice!='Y'&&choice!='n'&&choice!='N');//do while iterates while the user enters invalid input

    if (choice=='Y'||choice=='y') instructions();//if they chose to see the instructions, call the instructions method
    nextPage();

    String player1, player2="Computer";//Initializes the variables player1 and player2 to hold the names of the players

    System.out.println("Please enter the name of the family:\n");//Prompts the user for input
    player1=br.readLine();//Initializes the value of player1 to user input

    System.out.println("\n\n");//Prints several lines
    
    HashMap<String, Integer> scores = new HashMap<String, Integer>();//Declares the HashMap score that has the user's names as the keys and their scores as the elements
    scores.put(player1, 0);
    scores.put(player2, 0);//Initializes both players score to zero

    //Declare scanner and initialize it to read from the questions file
    File myFile = new File("Questions");
    Scanner questionsReader = new Scanner(myFile);//Initializes the scanner questionsReader to read from the Questions file in order to garner questions for the regular rounds
    Scanner scannerChecker = new Scanner (myFile);//Initializes the scanner questionsReader to read from the Questions file in order to garner questions for the regular rounds

    HashMap<String, Integer> roundAnswers = new HashMap<String, Integer>();//Initializes the HashMap roundAnswers to hold the answers for each questions and the scores associated with them
    ArrayList <String> question = new ArrayList<>();//Declares the ArrayList question that holds all of the data for each question

    String winner="Sean";//Initializes the string variable winner to the all-time greatest winner ever
    int i=0;//Initializes the int variable i which will be used to count the number of rounds
     
    do 
    {//Start of do while loop that iterates while either player has not reached 300 points   
      i++;//Increments the variable i
      
      do {//start of do-while that loops if the questions file has been read entirely
        question.clear();//Clears the ArrayList question

        question=roundsReader(questionsReader, scannerChecker);//Makes the value of question equal to what is returned by the method roundsReader after it is called

        if (question.get(0).equals("empty"))//if the scanner is at the end of the file
        {
          questionsReader.close();
          scannerChecker.close();

          myFile = new File("Questions"); 
          questionsReader = new Scanner(myFile);
          scannerChecker = new Scanner (myFile);//reinitialize the scanners to read from the top again
        }

      }while (question.get(0).equals("empty"));//end of do while that loops while the scanner is at the end of the file

      roundAnswers.clear();//Clears the HashMap roundAnswers

      for (int j=1; j<question.size(); j+=2)//Loops through each answer and its corresponding score in the ArrayList question
      {
        roundAnswers.put(question.get(j),Integer.parseInt(question.get(j+1)));//Adds every answer as a key and its score to the HashMap roundAnswers
      }

      nextPage();//Calls the nextPage method
      scores=answeringComputer(scores, question, roundAnswers, i, player1, player2);//Makes the value of HashMap scores equal to what is returned by the method answering
      System.out.println("\n"+player1+"'s score is "+ scores.get(player1)+" and "+player2+"'s score is "+ scores.get(player2));//Prints both players scores

    }while (scores.get(player1)<300&&scores.get(player2)<300);//end of Do while iterates while either player has not reached 300 points
      
    if (scores.get(player1)>scores.get(player2))//If player1 has the higher score
    {
      winner=player1;//Makes the value of winner equal to player1
    }
      
    else if (scores.get(player2)>scores.get(player1))//If the computer has the higher score
    {
      winner=player2;//Makes the value of winner equal to the computer 
    }

    else if (scores.get(player1)==scores.get(player2))//If their scores are equal which is extremely unlikely
    {
      int coinFlip=coinFlip();

      System.out.println("The winner is to be decided by a coinFlip");

      if (coinFlip==0)winner=player1;
      else if (coinFlip==1) winner=player2;//The winner is decided by a coin flip
    }  
    
    questionsReader.close();
    scannerChecker.close();//Closes both scanners

    System.out.println("The winner that continues to the Fast-Money Round is "+ winner);//Lets the user know who the winner is
    
    return winner;//Returns the variable winner
  }//end of roundsComputer method

  /**
  *The purpose of this method is to allow the user and computer to guess the answers during the round and to manage all of the general functions of the general rounds
  *
  *@param scores A HashMap that stores the user's as the keys and an their scores as the elements
  *@param question An ArrayList that holds all of the data regarding the question
  *@param roundAnswers A HashMap that holds all the answers as the Keys and their corresponding scores as their integers
  *@param roundNumber An int variable that holds which round number it is
  *@param player1 The String variable that holds the name of the first user
  *@param player2 The String variable is used to name the computer
  *
  *@return Returns the HashMap that holds the player's scores
  */
  public HashMap <String, Integer> answeringComputer (HashMap<String, Integer> scores, ArrayList<String> question, HashMap<String, Integer> roundAnswers, int roundNumber, String player1, String player2) throws IOException {//Start of answeringComputer method
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    System.out.println("\n\nRound "+ (roundNumber)+": \n");//Prints the round number
    System.out.println(question.get(0));//Prints the first element of the ArrayList question which is the question itself to the viewer
    System.out.println("There are "+roundAnswers.size()+" answers on the board");//Prints how many answers there are to the viewer

    int meFirst=0;//Initializes variable meFirst which determines which player goes first when it comes to answering
    ArrayList <String> correctAnswers = new ArrayList<>();//Declares the ArrayList correctAnswers which will hold all the correct guesses from the user

    meFirst=faceoffComputer(correctAnswers, roundAnswers, player1, player2);//Makes the value of meFirst equal to what is returned by the method faceoffComputer

    int roundScore=0;//Initializes the int variable roundScore that will hold the current score for each round
    
    if (correctAnswers.size()==2)//if both the player and the computer get the faceoff question right, add the values of those answers to the roundScore
    {
      roundScore+=roundAnswers.get(correctAnswers.get(0));
      roundScore+=roundAnswers.get(correctAnswers.get(1));
    }

    else if (correctAnswers.size()==1)//if only one player got the faceoff question right, add its value to the roundScore
    {
      roundScore+=roundAnswers.get(correctAnswers.get(0));
    }
  
    if (meFirst==0)//if meFirst is equal to 0, make player1 goes first in the round
    {//start of if that makes the user answer
      int wrongAnswer=0;//Initializes the int variable wrongAnswer which holds the number of incorrect guesses from the user who is going first
      String answer;//Declares the string answer that will hold user input

      do {//Start of do while that loops while the user hasn't gotten three incorrect answers
        System.out.println("\n______________________________________________________________________________________________________________________________");
        System.out.println("                       "+question.get(0));
        System.out.println("                                                                                                                      Round: "+roundNumber);
        System.out.println("                                                                                                          Incorrect Guesses: "+wrongAnswer);
        System.out.println("                                                                                                               Answers Left: "+(roundAnswers.size()-correctAnswers.size()));
        System.out.println("                                                                                                               "+player1+"'s score: "+scores.get(player1));
        System.out.println("                                                                                                           "+player2+"'s score: "+scores.get(player2));
        System.out.println("There are "+(roundAnswers.size()-correctAnswers.size())+" left on the board\n");//Lets the user know how many answers are still left on the board
        System.out.println("Please enter your answer:\n");//Prompts the user for input
        answer=br.readLine().toLowerCase();//Makes answer equal to user input (converted to lowercase)

        if (roundAnswers.containsKey(answer)==false||correctAnswers.contains(answer))//if the answer is incorrect or has already been said
        {//start of if statment that checks to see if the user got the answer wrong or it was already answered
          wrongAnswer++;//Increments wrongAnswer
          if (wrongAnswer==1)//if they get one wrong
          {
            System.out.println("\n .----------------. ");
            System.out.println("| .--------------. |");
            System.out.println("| |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | |");//print one big red x
            System.out.println("| |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | |");
            System.out.println("| |              | |");
            System.out.println("| '--------------' |");
            System.out.println(" '----------------' \n");
          }

          else if (wrongAnswer==2)//if they get 2 wrong
          {
            System.out.println("\n .----------------.  .----------------. ");
            System.out.println("| .--------------. || .--------------. |");
            System.out.println("| |  ____  ____  | || |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | || | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | || |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | || |    > `' <    | |");//print two large x's
            System.out.println("| |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | || | |____||____| | |");
            System.out.println("| |              | || |              | |");
            System.out.println("| '--------------' || '--------------' |");
            System.out.println(" '----------------'  '----------------' \n");
          }

          else if (wrongAnswer==3)//if they get 3 wrong
          {
            System.out.println("\n .----------------.  .----------------.  .----------------. ");
            System.out.println("| .--------------. || .--------------. || .--------------. |");
            System.out.println("| |  ____  ____  | || |  ____  ____  | || |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | || | |_  _||_  _| | || | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | || |   \\ \\  / /   | || |   \\ \\  / /   | |");//print 3 large x's
            System.out.println("| |    > `' <    | || |    > `' <    | || |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | || | |____||____| | || | |____||____| | |");
            System.out.println("| |              | || |              | || |              | |");
            System.out.println("| '--------------' || '--------------' || '--------------' |");
            System.out.println(" '----------------'  '----------------'  '----------------' \n");
          }//Prints a number of x's corresponding to the number of incorrect guesses
          
          if (roundAnswers.containsKey(answer)==false&&correctAnswers.contains(answer)==false)//Lets the user know if the answer is wrong
          System.out.println("Unfortunately your answer of \""+answer+"\" is not on the board");

          else if (roundAnswers.containsKey(answer)==true&&correctAnswers.contains(answer))//Lets the user know that the answer they inputted has already been said
          System.out.println("The answer you entered was already on the board");
        }//end of if statment that checks to see if the user got the answer wrong or it was already answered

        else if (roundAnswers.containsKey(answer)&&correctAnswers.contains(answer)==false)//If the user's answer is correct and has not already been said
        { 
          System.out.println("\nNice one! Your answer of \""+answer+"\" was on the board");
          System.out.println("That answer was worth "+roundAnswers.get(answer));//Lets the user know that they got it right and adds its value to the roundScore
          correctAnswers.add(answer);//Adds the correct answer to the ArrayList correctAnswers
          roundScore+=roundAnswers.get(answer);          
        }  

        if (correctAnswers.size()==roundAnswers.size())//If the number of correct answers is equal to the size of the HashMap that holds all of the answers
        {
          System.out.println("\n______________________________________________________________________________________________________________________________");
          System.out.println("\n\nYou have cleared the board, good job "+player1+"!");//Lets the player know that they've cleared the board
          System.out.println(roundScore+" has been added to your point total");
          scores.put(player1, scores.get(player1)+roundScore);//Adds the roundScore to the user's score
          break;//breaks out of loop
        }

      } while (wrongAnswer!=3);//End of do while that iterates while the user hasn't gotten three wrong

      if (wrongAnswer==3)//If the user got three wrong
      {//start of if statement that runs if the user has gotten three answers wrong
        System.out.println("\n______________________________________________________________________________________________________________________________");
        System.out.println("\n\n The Computer now has an opportunity to steal");//Lets the user know what's happening
        System.out.println("There are "+(roundAnswers.size()-correctAnswers.size())+" left on the board\n");//Prints how many answers are on the board
        answer=computerAnswers(roundAnswers);//Makes the value of answer equal to the value returned by computerAnswers
        System.out.println("The computer's answer is: " + answer);//Lets the user know what the computer guessed
        System.out.println("Press Enter to Continue");//Prompts the user to press enter to continue - just to break up pace
        br.readLine();
        

        if (roundAnswers.containsKey(answer)&&correctAnswers.contains(answer)==false)//if they get it right with a unique answer
        {
          System.out.println("The computer has successfully stolen this round!");//Lets them know that they've stolen the round
          roundScore+=roundAnswers.get(answer);
          System.out.println(roundScore+" will be added to the computer's point total");//Let them know how many points are added to the computer's score
          scores.put(player2, scores.get(player2)+roundScore);//Adds the roundScore to the computer's score
        }

        else//if they get it wrong
        {
          System.out.println("\n .----------------. ");
          System.out.println("| .--------------. |");
          System.out.println("| |  ____  ____  | |");
          System.out.println("| | |_  _||_  _| | |");
          System.out.println("| |   \\ \\  / /   | |");
          System.out.println("| |    > `' <    | |");//print a large x
          System.out.println("| |  _/ /'`\\ \\_  | |");
          System.out.println("| | |____||____| | |");
          System.out.println("| |              | |");
          System.out.println("| '--------------' |");
          System.out.println(" '----------------' \n");
          System.out.println("The computer's answer was either not on the board or it was already said");//Lets the user know that the computer got it wrong
          System.out.println(roundScore+" will be added to your score");
          scores.put(player1, scores.get(player1)+roundScore);//Adds the roundScore to the other player's score
        }

      }//start of if statement that runs if the user has gotten three answers wrong
    }//end of if that makes the user answer

    else if (meFirst==1)//if meFirst is equal to one then player2 (the computer) gains control of the question
    {//start of else if that causes the computer to answer
      int wrongAnswer=0;//Initializes int variable wrongAnswer to zero that holds the number of incorrect guesses
      String answer;//Declares the String answer which holds user input

      do {//Start of do while
        System.out.println("\n______________________________________________________________________________________________________________________________");
        System.out.println("                       "+question.get(0));
        System.out.println("                                                                                                                      Round: "+roundNumber);
        System.out.println("                                                                                                          Incorrect Guesses: "+wrongAnswer);
        System.out.println("                                                                                                               Answers Left: "+(roundAnswers.size()-correctAnswers.size()));//Prints the current status of the game such as the round number, incorrect guesses, answers left and both user's scores
        System.out.println("                                                                                                               "+player1+"'s score: "+scores.get(player1));
        System.out.println("                                                                                                           "+player2+"'s score: "+scores.get(player2));
        System.out.println("There are "+(roundAnswers.size()-correctAnswers.size())+" left on the board\n");//Lets the user know how many answers are left on the board
        answer=computerAnswers(roundAnswers);//Makes the computer's answer equal to the value returned by computerAnswers
        System.out.println("The computer's answer is: " + answer);//Lets the user know what the computer's answer is
        System.out.println("Press Enter to Continue");//Prompts the user to press enter to continue - just to break up pace
        br.readLine();

        if (roundAnswers.containsKey(answer)==false||correctAnswers.contains(answer))//if the computer enterred a wrong answer or a non-unique answer
        {
          wrongAnswer++;//Increments the variable wrongAnswer
          if (wrongAnswer==1)//if they get one wrong
          {
            System.out.println("\n .----------------. ");
            System.out.println("| .--------------. |");
            System.out.println("| |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | |");//print a large x
            System.out.println("| | |____||____| | |");
            System.out.println("| |              | |");
            System.out.println("| '--------------' |");
            System.out.println(" '----------------' \n");
          }

          else if (wrongAnswer==2)//if they get two wrong
          {
            System.out.println("\n .----------------.  .----------------. ");
            System.out.println("| .--------------. || .--------------. |");
            System.out.println("| |  ____  ____  | || |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | || | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | || |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | || |    > `' <    | |");//print two large x's
            System.out.println("| |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | || | |____||____| | |");
            System.out.println("| |              | || |              | |");
            System.out.println("| '--------------' || '--------------' |");
            System.out.println(" '----------------'  '----------------' \n");
          }

          else if (wrongAnswer==3)//if they get two wrong
          {
            System.out.println("\n .----------------.  .----------------.  .----------------. ");
            System.out.println("| .--------------. || .--------------. || .--------------. |");
            System.out.println("| |  ____  ____  | || |  ____  ____  | || |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | || | |_  _||_  _| | || | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | || |   \\ \\  / /   | || |   \\ \\  / /   | |");//print three large x's
            System.out.println("| |    > `' <    | || |    > `' <    | || |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | || |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | || | |____||____| | || | |____||____| | |");
            System.out.println("| |              | || |              | || |              | |");
            System.out.println("| '--------------' || '--------------' || '--------------' |");
            System.out.println(" '----------------'  '----------------'  '----------------' \n");
          }//Prints a number of x's equivalent to wrongAnswer
          
          if (roundAnswers.containsKey(answer)==false&&correctAnswers.contains(answer)==false)
          System.out.println("The computer's answer of \""+answer+"\" is not on the board");//If they got the answer wrong, print that to the console

          else if (roundAnswers.containsKey(answer)==true&&correctAnswers.contains(answer))
          System.out.println("The answer the computer entered was already on the board");//If the computer enterred an answer that has already been answered, print this to the console
        }

        else if (roundAnswers.containsKey(answer)&&correctAnswers.contains(answer)==false)//If the computer enterred a correct and unique answer
        {
          System.out.println("\nThe computer's answer of \""+answer+"\" was on the board");//Let them know that they got it right
          System.out.println("That answer was worth "+roundAnswers.get(answer));//Lets the user know what the value of the answer was
          correctAnswers.add(answer);//Adds the answer to the correctAnswers ArrayList
          roundScore+=roundAnswers.get(answer);//Adds the answer's point value to roundScore
        }
        
        if (correctAnswers.size()==roundAnswers.size())//If the number of correctAnswers is equal to the number of answers
        {
          System.out.println("\n______________________________________________________________________________________________________________________________");
          System.out.println("\nYou have cleared the board, good job "+player2+"!");//Let the user know that the computer has cleared the board
          System.out.println(roundScore+" has been added to your point total");//Add roundScore to the computer's score
          scores.put(player2, scores.get(player2)+roundScore);
          break;//breaks out of the loop
        }

      } while (wrongAnswer!=3);//end of do while that iterates while the computer hasn't gotten three wrong

      if (wrongAnswer==3)//if the computer got three wrong
      {//start of if statement that runs if the computer gets three answers wrong
        System.out.println("\n______________________________________________________________________________________________________________________________");
        System.out.println("\n\n"+player1+", you now have the opportunity to steal!");
        System.out.println("There are "+(roundAnswers.size()-correctAnswers.size())+" left on the board\n");//Let the user know how many answers are left
        System.out.println("Enter your answer:");

        answer=br.readLine();//Makes the value of answer equal to user input

        if (roundAnswers.containsKey(answer)&&correctAnswers.contains(answer)==false)//If they get the steal attempt right and its a unique answer
        {
          System.out.println("You have successfully stolen this round!");//Let them know that they've stolen
          roundScore+=roundAnswers.get(answer);//Adds the answers score to roundScore
          System.out.println(roundScore+" will be added to your point total");
          scores.put(player1, scores.get(player1)+roundScore);//Adds the roundScore to the user's score
        }

        else//If they get it wrong or their answer is not unique
        {
          System.out.println("\n .----------------. ");
          System.out.println("| .--------------. |");
          System.out.println("| |  ____  ____  | |");
          System.out.println("| | |_  _||_  _| | |");
          System.out.println("| |   \\ \\  / /   | |");
          System.out.println("| |    > `' <    | |");
          System.out.println("| |  _/ /'`\\ \\_  | |");
          System.out.println("| | |____||____| | |");
          System.out.println("| |              | |");
          System.out.println("| '--------------' |");
          System.out.println(" '----------------' \n");
          System.out.println("Unfortunately, your answer either was not on the board or has already been said");
          System.out.println(roundScore+" will be added to the computer's score");//Let them know that they've gotten it wrong
          scores.put(player2, scores.get(player2)+roundScore);//Add the roundScore to the computer's score
        }
      }//end of if statement that runs if the computer gets three answers wrong
    }//end of else if that causes the computer to answer
      
    return scores;//returns the scores HashMap
  }//end of answeringComputer method
  

  /**
  * The purpose of this method is to determine if the user or the computer wins the faceoff and gains control of the question
  *
  *@param roundAnswers A HashMap that stores the answers to the question and the answers corresponding scores as elements
  *@param player1 A string variable that stores the name of player1
  *@param player2 A string variable that stores the computer's name
  *
  *@return Returns a int variable that denotes which player gains control of the question
  */
  public int faceoffComputer (ArrayList<String> correctAnswers, HashMap<String, Integer> roundAnswers, String player1, String player2) throws IOException {//start of faceOffComputer method
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    String answer1="Sean", answer2="Anthony";//Declares and initializes the variables answer1 and answer2 that holds user input
    int meFirst=coinFlip();//Makes the value of int variable meFirst that determines which user goes first
    boolean answer1Correct=true, answer2Correct=true;//Initializes the boolean variables answer1Correct and answer2Correct that determine whether or not each use got their answer right

    do {//Start of do while that iterates while both user's have gotten the question wrong
      answer1Correct=true;
      answer2Correct=true;//Resets the values of answer1Correct and answer2Correct

      if (meFirst==0)//if the value of meFirst is equal to 0, player1 goes first
      {//start of if statement that makes the user guess first
        System.out.println("______________________________________________________________________________________________________________________________");
        System.out.println("\n\n"+player1+ ", it's your turn to guess:\n");//Lets player1 know that its their turn to guess
        answer1=br.readLine().toLowerCase();//Makes the value of answer1 equal to user input

        if (roundAnswers.containsKey(answer1)&&correctAnswers.contains(answer1)==false)//If they get the answer right and it hasn't been guessesd
        {
          System.out.println(answer1+" is on the board with a score of "+roundAnswers.get(answer1));//Lets the user know that they've got it right
          correctAnswers.add(answer1);//Add the answer to the ArrayList correctAnswers
        }

        else if (roundAnswers.containsKey(answer1)==false||correctAnswers.contains(answer1))//If they got it wrong or the answer enterred was already said
        {
          System.out.println("\n .----------------. ");
          System.out.println("| .--------------. |");
          System.out.println("| |  ____  ____  | |");
          System.out.println("| | |_  _||_  _| | |");
          System.out.println("| |   \\ \\  / /   | |");
          System.out.println("| |    > `' <    | |");
          System.out.println("| |  _/ /'`\\ \\_  | |");
          System.out.println("| | |____||____| | |");
          System.out.println("| |              | |");
          System.out.println("| '--------------' |");
          System.out.println(" '----------------' \n");
          System.out.println(answer1+ " is unfortunately not on the board or has already been said");//Let them know that they've got it wrong
          answer1Correct=false;//Makes the value of answer1Corret to false
        }

        System.out.println("______________________________________________________________________________________________________________________________");
        System.out.println("\n\nIts the Computer's score to guess\n");//Lets the user know that its the computer's turn to guess
        answer2=computerAnswers(roundAnswers);//Makes the value of answer2 to the value returned by computerAnswers
        System.out.println("The computer's answer is: " + answer2);//Prints the computer's answer
        System.out.println("Press Enter to Continue");//Prompts the user to press enter to continue (to break up pace)
        br.readLine();

        if (roundAnswers.containsKey(answer2)&&correctAnswers.contains(answer2)==false)//if the answer is correct and unique
        {
          System.out.println(answer2+" is on the board with a score of "+roundAnswers.get(answer2));//Let the user know that the computer got it right
          correctAnswers.add(answer2);//Add the answer to the arraylist correctAnswers
        }

        else if (roundAnswers.containsKey(answer2)==false||correctAnswers.contains(answer2))//if the answer is wrong or is not unique
        {
          System.out.println("\n .----------------. ");
          System.out.println("| .--------------. |");
          System.out.println("| |  ____  ____  | |");
          System.out.println("| | |_  _||_  _| | |");
          System.out.println("| |   \\ \\  / /   | |");
          System.out.println("| |    > `' <    | |");
          System.out.println("| |  _/ /'`\\ \\_  | |");
          System.out.println("| | |____||____| | |");
          System.out.println("| |              | |");
          System.out.println("| '--------------' |");
          System.out.println(" '----------------' \n");
          System.out.println(answer2+ " is unfortunately not on the board or has already been said");//Let them know that the computer got it wrong
          answer2Correct=false;//Make the value of answer2Correct to false
        }
      }//end of if statement that makes the user guess first

      else if (meFirst==1)//If the value of meFirst is 1 the computer goes first
      {//start of else if that makes the computer guess first
        System.out.println("______________________________________________________________________________________________________________________________");
        System.out.println("\n\nIts the Computer's turn to guess\n");//lets the user know the computer is going
        answer2=computerAnswers(roundAnswers);//Makes the computer's answer equal to what is returned by the method computerAnswers
        System.out.println("The computer's answer is: " + answer2);//Lets the user know what the computer guessed
        System.out.println("Press Enter to Continue");//Pauses the program to break up pace
        br.readLine();

        if (roundAnswers.containsKey(answer2)&&correctAnswers.contains(answer2)==false)//if the computer gets the answer correct
        {
          System.out.println(answer2+" is on the board with a score of "+roundAnswers.get(answer2));//let the user know that the computer got it right
          correctAnswers.add(answer2);//add the correct answer to the ArrayList
        }

        else if (roundAnswers.containsKey(answer2)==false||correctAnswers.contains(answer2))//if the computer gets it wrong or enters a answer that was already said
        {
          System.out.println("\n .----------------. ");
          System.out.println("| .--------------. |");
          System.out.println("| |  ____  ____  | |");
          System.out.println("| | |_  _||_  _| | |");
          System.out.println("| |   \\ \\  / /   | |");
          System.out.println("| |    > `' <    | |");//let them know that they got it wrong
          System.out.println("| |  _/ /'`\\ \\_  | |");
          System.out.println("| | |____||____| | |");
          System.out.println("| |              | |");
          System.out.println("| '--------------' |");
          System.out.println(" '----------------' \n");
          System.out.println(answer2+ " is unfortunately not on the board or has already been said");
          answer2Correct=false;//denotes their answer as false
        }

        System.out.println("______________________________________________________________________________________________________________________________");
        System.out.println("\n\n"+player1+", now it's your turn to guess:\n");//Prompts the user for input
        answer1=br.readLine().toLowerCase();//Makes the value of answer1 equal to user input

        if (roundAnswers.containsKey(answer1)&&correctAnswers.contains(answer1)==false)//if the user gets it right
        {
          System.out.println(answer1+" is on the board with a score of "+roundAnswers.get(answer1));//let them know they got it right
          correctAnswers.add(answer1);//add the answer to the arrayList containing the correct answers
        }

        else if (roundAnswers.containsKey(answer1)==false||correctAnswers.contains(answer1))//if they got it wrong or enterred in an answer that was already said
        {
          System.out.println("\n .----------------. ");
          System.out.println("| .--------------. |");
          System.out.println("| |  ____  ____  | |");
          System.out.println("| | |_  _||_  _| | |");
          System.out.println("| |   \\ \\  / /   | |");
          System.out.println("| |    > `' <    | |");
          System.out.println("| |  _/ /'`\\ \\_  | |");
          System.out.println("| | |____||____| | |");//lets them know they got it wrong
          System.out.println("| |              | |");
          System.out.println("| '--------------' |");
          System.out.println(" '----------------' \n");
          System.out.println(answer1+ " is unfortunately not on the board or has already been said");
          answer1Correct=false;//denotes their answer as false
        }
      }//end of else if that makes the computer guess first
      
    }while (answer1Correct==false&&answer2Correct==false);//End of do-while that iterates while both user's have gotten the question wrong

    if (answer1Correct&&answer2Correct==false) 
    {
      meFirst=0;//If the user got it right and the computer didn't, player1 gains control of the question
      System.out.println("\n\n______________________________________________________________________________________________________________________________");
      System.out.println(player1+", you again control of the question\n");//Lets the user know if they are going first
    }
      
    else if (answer1Correct==false&&answer2Correct) 
    {
      meFirst=1;//If the computer got it right and the user didn't, the computer gains control of the question
      System.out.println("\n\n______________________________________________________________________________________________________________________________");
      System.out.println("The computer gains control of the question\n");//Lets the user know that the computer is in control of the question
    }
      
    else if (answer1Correct&&answer2Correct)//If both the user and the computer got their answer right
    {
      if (roundAnswers.get(answer1)>roundAnswers.get(answer2))
      {
        meFirst=0;//If the user's answer was higher on the board, they gain control of the question
        System.out.println("\n\n______________________________________________________________________________________________________________________________");
        System.out.println(player1+", you again control of the question\n");//Lets the user know if they are going first
      }

      else if (roundAnswers.get(answer2)>roundAnswers.get(answer1))
      {  
        meFirst=1;//If the computer's answer was higher on the board, they gain control of the question
        System.out.println("\n\n______________________________________________________________________________________________________________________________");
        System.out.println("The computer gains control of the question\n");//Lets the user know that the computer is in control of the question
      }
    }
    
    nextPage();
    return meFirst;//return the value of meFirst 
  }//end of faceOffComputer method

  /**
  *The purpose of this method is to provide an answer for the computer that may or may not be right
  *
  *@param roundAnswers A HashMap that stores the answers and their corresponding scores to the current question
  *
  *@return Returns the string that holds the computer's answer
  */
  public String computerAnswers (HashMap <String, Integer> roundAnswers) throws IOException {//start of computerAnswers method
    
    int tosser=coinFlip();//tosser has a 50% chance of holding either a 0 or a 1
    int num=0;//int num is an integer variable meant to hold a random element number for either of the data structures initialized below
    String answer="blahblahblah";//String answer is what will be returned with the computer's right or wrong answer

    ArrayList <String> answers = new ArrayList <>();//An arraylist that holds all of the correct answers to the question
    String [] wrongAnswers = {"burger king","speedy gonzales","naruto uzimaki","j.i.d","repl.it","bazinga","cool ranch doritos","ICarly","hypnosis","chinchilla","pinball machine","wa'er boto","ti fighter","sean","pranav","bieber fever","xXJai-MeisterXx","charizard","Beep boop","blah"};//Holds several random answers that are very wrong :)

    if (tosser==0)//if tosser is equal to zero the computer is going to pick a correct answer
    {
      for (String s : roundAnswers.keySet())//Loops through each key in roundAnswer
      {
        answers.add(s);//Adds each key to the arrayList that holds all of the correct answers
      }

      num=(int)(Math.random()*(answers.size()-1));//num is initialized to a random element address within the arrayList's capacity

      answer=answers.get(num);//Makes the computer's answer equal to the random, correct, answer
    }

    else if (tosser==1)//if tosser is equal to 1, the computer picks a random wrong answer
    {
      num=(int)(Math.random()*(wrongAnswers.length-1));//num is initialized to a random element address within the array's capacity
      
      answer=wrongAnswers[num];//Makes the value of answer equal to the element at num, a wrong answer
    }

    return answer;//returns answer
  }//end of computerAnswers method

  
  /**
  *Allows the user to rotate between two "family members" to answer the same five questions to see if they can get at least 200 points to win family feud
  *
  *@param winner Holds the name of the player that won the general rounds of family feud
  *
  *@return Returns the user's fastMoney score
  */
  public int fastMoney (String winner)throws IOException {//start of fastMoney method
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    System.out.println("Congradulations "+ winner + " on making it to the fast-money round.\nNow you and another member of your family will rotate between answering the same 5 questions to see if you can get 200 points");//Lets the winner that won the general rounds know what is happening

    String [][] fmQuestions = new String [5][];//Declares a 2D array that will hold all of the data for each question in each of its "rows"

    fmQuestions=fastMoneyReader();//Initializes the value of fmQuestion to the values returned by the method fastMoneyReader

    int score=0;//Initializes the int variable score to zero. It will be used to measure how many points the user's have collected

    ArrayList <String> correctAnswers = new ArrayList <>();//Declares the arraylist correctAnswers that will hold any correctAnswers achieved by the user to ensure there are no repeats

    for (int i=0; i<2; i++)//For loop iterates twice, once for each family member
    {//start of for loop that iterates twice, once for each family member
      if (i==0) System.out.println("\nFirst Family Member, it is now your turn to play Fast Money\n");//If its the first family member's turn, let them know they are going
      else if (i==1)
      {
        nextPage();
        System.out.println("Second Family Member, it is now your turn to play Fast Money\n");//If its the second user's turn, let them know
      }
      
      for(int x=0; x<5; x++)//Loop iterates five times for the five questions
      {//start of for loop that iterates five times, once for each question in fastmoney
        HashMap <String, Integer> answers = new HashMap <String, Integer>();//HashMap answers holds all of the answers to the question at hand

        for (int y=1; y<(fmQuestions[x].length-1);y+=2)
        {
          answers.put(fmQuestions[x][y], Integer.parseInt(fmQuestions[x][y+1]));//Adds all of the answers and their corresponding score to the HashMap
        }

        boolean repeat=false;//repeat holds whether or not the user has enterred an answer that has already been answered

        do {//Start of do-while that iterates if the user enters a duplicate answer
          
          repeat=false;//resets the variable repeat

          System.out.println("______________________________________________________________________________________________________________________________");
          System.out.println("                                                                                                                      Round: "+i);//lets the user know the current progress of the game
          System.out.println("                                                                                                                      Score: "+score);
          System.out.println("\n"+fmQuestions[x][0]);//Prints the question (the first element of the current row in the 2D array)
          System.out.println("Enter your answer: \n");//Prompts the user to enter their answer
          String answer=br.readLine();

          if (answers.containsKey(answer)&&correctAnswers.contains(answer)==false)//if they got it correct and their answer was not enterred already
          {
            System.out.println("\nYour answer was of "+answer+" was correct! It added "+answers.get(answer)+" points to your total\n\n");
            correctAnswers.add(answer);//Add the answer to the list of correct answers
            score+=answers.get(answer);//add the score of that question to the user's score
          }

          else if (answers.containsKey(answer)==false)//if they get it wrong
          {
            System.out.println("\n .----------------. ");
            System.out.println("| .--------------. |");
            System.out.println("| |  ____  ____  | |");
            System.out.println("| | |_  _||_  _| | |");
            System.out.println("| |   \\ \\  / /   | |");
            System.out.println("| |    > `' <    | |");
            System.out.println("| |  _/ /'`\\ \\_  | |");
            System.out.println("| | |____||____| | |");
            System.out.println("| |              | |");
            System.out.println("| '--------------' |");
            System.out.println(" '----------------' \n");
            System.out.println("\nYou got this answer wrong, no points will be awarded\n\n");//let them know
          }

          else if (correctAnswers.contains(answer))//if the answer has already been enterred 
          {
            System.out.println("That answer was already guessed by your family member, try again!\n\n");
            repeat=true;//the loop will iterate for them to answer again
          }

        } while(repeat);//end of do-while that iterates if the user enters a duplicate answer

      }//end of for loop that iterates five times, once for each question in fastmoney

      if (i==0) System.out.println("Your family's score following the first round is "+ score);//Lets the user's know their score following the rounds
      else if (i==1) System.out.println("Your family's score following the second round is "+score);

    }//end of for loop that iterates twice, once for each family member

    if (score>=200)//if they get 200 or over 200 at the end of the five questions, let them know they've won
    {
      System.out.println("\nCongratulations! You have scored "+score+" points.\nYOUR FAMILY HAS WON FAMILY FEUD");//if they win, congratulate them
    }

    else if (score<200)//if they get it wrong, let them know that they got it wrong
    {
      System.out.println("\nYou have scored "+score+" points, which is unfortunately less than 200 points\nYour family returns home as a collection of losers");
    }

    return score;//returns the user's fastmoney score
  }//end of fastmoney method
  

  /**
  *The purpose of this method is to read the questions for fast-money and add 5 somewhat random questions and their data to a 2D array
  *
  *@return Returns a 2D array that holds all of the questions and their associated data
  */
  public String [][] fastMoneyReader () throws IOException {//start of fastMoney reader

    File inputFile = new File ("FastMoneyQuestions");
    Scanner fmReader = new Scanner(inputFile);
    Scanner fmReaderChecker = new Scanner (inputFile);//Initializes two scanners to read from the text file that holds all of the Fast money questions

    String [][] fmQuestions = new String [5] [];//2D array that will holds all of the questions and their data, each row is for a different question
    ArrayList <String> fmQuestionsHolder = new ArrayList <>();//arrayList holds each question

    int tosser=0;//initialization of int tosser which holds the value of the coinflip

    for (int i=0; i<5; i++)
    {//start of for loop that iterates five times, once for each question in fastMoney
      fmReaderChecker.nextLine();//Moves one of the scanner ahead of the other

      fmQuestionsHolder.clear();//clears the arrayList that holds the question

      tosser=coinFlip();//Makes tosser equal to either 0 or 1

      if (tosser==0)//if tosser is equal to zero, skip the next question in the file and go to the one after that
      {//start of if that skips the next question and goes to the one following
        do 
        {

          fmReader.nextLine();

        }while (fmReaderChecker.nextLine().equals("***")==false);

        fmReader.nextLine();
        fmReaderChecker.nextLine();//progresses to the next question

        do 
        {

          fmQuestionsHolder.add(fmReader.nextLine());//Adds each piece of information in the question to the arraylist

        }while (fmReaderChecker.nextLine().equals("***")==false);//Loop iterates while the question is still being read

        fmReader.nextLine();//Progresses the reader so its ready for next time

      }//end of if that skips the next question and goes to the one following

      else if (tosser==1)//if tosser is one, read the next question
      {//start of else if that reads the next question in the textfile (if tosser is equal to 1)

        do 
        {

          fmQuestionsHolder.add(fmReader.nextLine());//Adds each piece of information in the question to the arraylist

        }while (fmReaderChecker.nextLine().equals("***")==false);//Loop iterates while the question is still being read

        fmReader.nextLine();//Progresses the reader so its ready for next time

      }//end of else if that reads the next question in the textfile (if tosser is equal to 1)

      String question [] = new String [fmQuestionsHolder.size()];//Array that holds all the data to do with the current question

      for (int d=0; d<fmQuestionsHolder.size();d++)
      {
        question[d] = fmQuestionsHolder.get(d);//Adds all of the data from the question to array
      }

      fmQuestions[i]=question;//Adds all the data from the current question to its corresponding row in the 2D array

    }//start of for loop that iterates five times, once for each question in fastMoney

    fmReader.close();
    fmReaderChecker.close();//Closes the scanners
    
    return fmQuestions;//returns the 2D array
  }//end of fastMoneyReader method
  
  /**
  *The purpose of this method is two "flip a coin", providing one of two values each with a 50% chance of being picked
  *
  *@return Returns the value of num which has a 50% chance of being 0 and another 50% chance of being 1, acting as a coinFlip
  */
  public int coinFlip ()throws IOException {

    int num=(int)(Math.random()*2);
    return num;//Returns the value of num which has a 50% chance of being 0 and another 50% chance of being 1, acting as a coinFlip
  } 


  /**
  *The purpose of this method is to act as a menu that allows the user to maneuvre through the program
  *
  *@param choice - Holds the user's input that they use to choose which part of the game they'd like to see
  *
  *@return Returns the variable char which will then be used to determine which methods are called and in which order
  */
  public char menu (char choice)throws IOException{//start of menu method
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    System.out.println("\n\n           /$$      /$$           /$$                                                     /$$                    ");
    System.out.println("          | $$  /$ | $$          | $$                                                    | $$                    ");
    System.out.println("          | $$ /$$$| $$  /$$$$$$ | $$  /$$$$$$$  /$$$$$$  /$$$$$$/$$$$   /$$$$$$        /$$$$$$    /$$$$$$       ");
    System.out.println("          | $$/$$ $$ $$ /$$__  $$| $$ /$$_____/ /$$__  $$| $$_  $$_  $$ /$$__  $$      |_  $$_/   /$$__  $$      ");
    System.out.println("          | $$$$_  $$$$| $$$$$$$$| $$| $$      | $$  \\ $$| $$ \\ $$ \\ $$| $$$$$$$$        | $$    | $$  \\ $$      ");
    System.out.println("          | $$$/ \\  $$$| $$_____/| $$| $$      | $$  | $$| $$ | $$ | $$| $$_____/        | $$ /$$| $$  | $$      ");
    System.out.println("          | $$/   \\  $$|  $$$$$$$| $$|  $$$$$$$|  $$$$$$/| $$ | $$ | $$|  $$$$$$$        |  $$$$/|  $$$$$$/      ");
    System.out.println("          |__/     \\__/ \\_______/|__/ \\_______/ \\______/ |__/ |__/ |__/ \\_______/         \\___/   \\______/       ");
    System.out.println("");
    System.out.println("");
    System.out.println("");
    System.out.println("           /$$$$$$$$                     /$$ /$$                 /$$$$$$$$                        /$$            ");
    System.out.println("          | $$_____/                    |__/| $$                | $$_____/                       | $$            ");
    System.out.println("          | $$    /$$$$$$  /$$$$$$/$$$$  /$$| $$ /$$   /$$      | $$     /$$$$$$  /$$   /$$  /$$$$$$$            ");
    System.out.println("          | $$$$$|____  $$| $$_  $$_  $$| $$| $$| $$  | $$      | $$$$$ /$$__  $$| $$  | $$ /$$__  $$            ");
    System.out.println("          | $$__/ /$$$$$$$| $$ \\ $$ \\ $$| $$| $$| $$  | $$      | $$__/| $$$$$$$$| $$  | $$| $$  | $$            ");
    System.out.println("          | $$   /$$__  $$| $$ | $$ | $$| $$| $$| $$  | $$      | $$   | $$_____/| $$  | $$| $$  | $$            ");
    System.out.println("          | $$  |  $$$$$$$| $$ | $$ | $$| $$| $$|  $$$$$$$      | $$   |  $$$$$$$|  $$$$$$/|  $$$$$$$            ");
    System.out.println("          |__/   \\_______/|__/ |__/ |__/|__/|__/ \\____  $$      |__/    \\_______/ \\______/  \\_______/            ");
    System.out.println("                                                 /$$  | $$                                                       ");//Family Feud Figlet
    System.out.println("                                                |  $$$$$$/                                                       ");
    System.out.println("                                                 \\______/");
    System.out.println("");
    System.out.println("");
    System.out.println("                                            IT'S TIME TO PLAY FAMILY FEUD");
    System.out.println("");
    System.out.println("                                        Enter A to Play Classic Family Feud");
    System.out.println("");
    System.out.println("                                          Enter B to Play vs the Computer");//Menu prompting user input
    System.out.println("");
    System.out.println("                                             Enter C to Quit the Game");
    System.out.println("");
    
    boolean badAnswer=true;//Initializes badAnswer that determines if the user enters invalid input

    do {//Start of do-while
      
      System.out.print("Enter Option:");//Prompts user input
      choice=br.readLine().charAt(0);//Makes the value of choice equal to user input

      if (choice=='A'||choice=='a'||choice=='B'||choice=='b'||choice=='C'||choice=='c') badAnswer=false;//If they enter valid input, make badAnswer equal to false

      else  
      System.out.println("Invalid Answer, Please Try Again");//If invalid input is enterred, let the user know 

    } while (badAnswer);//End of do-while that iterates while badAnswer is true (input is invalid)

    return choice;//returns the variable choice
  }//end of menu method

  /**
  *The purpose of this method is to display the instructions of the game
  */
  public void instructions () {//start of instructions method
    System.out.println("");
    System.out.println(" /$$$$$$                       /$$                                     /$$     /$$                              ");
    System.out.println("|_  $$_/                      | $$                                    | $$    |__/                               ");
    System.out.println("  | $$   /$$$$$$$   /$$$$$$$ /$$$$$$    /$$$$$$  /$$   /$$  /$$$$$$$ /$$$$$$   /$$  /$$$$$$  /$$$$$$$   /$$$$$$$");
    System.out.println("  | $$  | $$__  $$ /$$_____/|_  $$_/   /$$__  $$| $$  | $$ /$$_____/|_  $$_/  | $$ /$$__  $$| $$__  $$ /$$_____/");//Instructions Figlet
    System.out.println("  | $$  | $$  \\ $$|  $$$$$$   | $$    | $$  \\__/| $$  | $$| $$        | $$    | $$| $$  \\ $$| $$  \\ $$|  $$$$$$ ");
    System.out.println("  | $$  | $$  | $$ \\____  $$  | $$ /$$| $$      | $$  | $$| $$        | $$ /$$| $$| $$  | $$| $$  | $$ \\____  $$");
    System.out.println(" /$$$$$$| $$  | $$ /$$$$$$$/  |  $$$$/| $$      |  $$$$$$/|  $$$$$$$  |  $$$$/| $$|  $$$$$$/| $$  | $$ /$$$$$$$/");
    System.out.println("|______/|__/  |__/|_______/    \\___/  |__/       \\______/  \\_______/   \\___/  |__/ \\______/ |__/  |__/|_______/ ");
    System.out.println("");
    System.out.println("");
    System.out.println("            1: Following every question, there will be faceoff in which the family with the");
    System.out.println("               highest scoring answer will gain control of the question");
    System.out.println("");
    System.out.println("            2: The family who goes first in the playoff is decided by a coinflip");
    System.out.println("");
    System.out.println("            3: There rounds will continue until one player gets 300 points, questions");
    System.out.println("               are scored based on the responses of 100 correspondents");
    System.out.println("");
    System.out.println("            4: A Family can only answer incorrectly three times each questions before");
    System.out.println("               the questions changes possession giving the other family a chance to ");
    System.out.println("              \"Steal\" all the points gained by other family if they get a correct answer");//Instructions
    System.out.println("");
    System.out.println("            5: Points will only be added to a family's total following the round");
    System.out.println("");
    System.out.println("            6: The first family to reach 300 points in the general rounds will advance to the fast money round");
    System.out.println("");
    System.out.println("            7: The Fast Money will have two members of a family try to reach 200 points to win the game ");
    System.out.println("");
    System.out.println("            8: Fast Money will start with the first family member where they will have to answer 5 questions");
    System.out.println("               to the best of their ability, to which the other member will then have to answer the same");
    System.out.println("               questions with unique responses");
    System.out.println("");
    System.out.println("            9: Please enter all answers as simply as possible, similar answers are not accepted");
    System.out.println("");
    System.out.println("           10: You are required to clap and say \"Good Answer\" even if it wasn't a good answer\n");//Pretty sure this is mandatory if you've watched the show
  
  }//end of instructions method

  /**
  *The purpose of this method is to display the farewell page for the program
  *
  *@param winner Holds the value of the player that played fastmoney
  *@param score Holds the user's score in fastmoney
  *@param previousChoice Holds which gamemode the user chose to play
  *
  *@return Returns a boolean variable that denotes whether or not the user would like to playAgain
  */
  public boolean farewellPage (String winner, int score, char previousChoice)throws IOException {//start of farewellPage method
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    System.out.println(" /$$$$$$$$ /$$                           /$$                        /$$$$$$                          /$$$$$$$  /$$                     /$$                    ");
    System.out.println("|__  $$__/| $$                          | $$                       /$$__  $$                        | $$__  $$| $$                    |__/                    ");
    System.out.println("   | $$   | $$$$$$$   /$$$$$$  /$$$$$$$ | $$   /$$  /$$$$$$$      | $$  \\__//$$$$$$   /$$$$$$       | $$  \\ $$| $$  /$$$$$$  /$$   /$$ /$$ /$$$$$$$   /$$$$$$ ");
    System.out.println("   | $$   | $$__  $$ |____  $$| $$__  $$| $$  /$$/ /$$_____/      | $$$$   /$$__  $$ /$$__  $$      | $$$$$$$/| $$ |____  $$| $$  | $$| $$| $$__  $$ /$$__  $$");
    System.out.println("   | $$   | $$  \\ $$  /$$$$$$$| $$  \\ $$| $$$$$$/ |  $$$$$$       | $$_/  | $$  \\ $$| $$  \\__/      | $$____/ | $$  /$$$$$$$| $$  | $$| $$| $$  \\ $$| $$  \\ $$");
    System.out.println("   | $$   | $$  | $$ /$$__  $$| $$  | $$| $$_  $$  \\____  $$      | $$    | $$  | $$| $$            | $$      | $$ /$$__  $$| $$  | $$| $$| $$  | $$| $$  | $$");
    System.out.println("   | $$   | $$  | $$|  $$$$$$$| $$  | $$| $$ \\  $$ /$$$$$$$/      | $$    |  $$$$$$/| $$            | $$      | $$|  $$$$$$$|  $$$$$$$| $$| $$  | $$|  $$$$$$$");
    System.out.println("   |__/   |__/  |__/ \\_______/|__/  |__/|__/  \\__/|_______/       |__/     \\______/ |__/            |__/      |__/ \\_______/ \\____  $$|__/|__/  |__/ \\____  $$");
    System.out.println("                                                                                                                             /$$  | $$               /$$  \\ $$");
    System.out.println("                                                                                                                            |  $$$$$$/              |  $$$$$$/");
    System.out.println("                                                                                                                             \\______/                \\______/");
    System.out.println("");
    System.out.println("                          /$$$$$$$$                     /$$ /$$                 /$$$$$$$$                        /$$            ");
    System.out.println("                         | $$_____/                    |__/| $$                | $$_____/                       | $$            ");
    System.out.println("                         | $$    /$$$$$$  /$$$$$$/$$$$  /$$| $$ /$$   /$$      | $$     /$$$$$$  /$$   /$$  /$$$$$$$            ");//Farewell page Figlet
    System.out.println("                         | $$$$$|____  $$| $$_  $$_  $$| $$| $$| $$  | $$      | $$$$$ /$$__  $$| $$  | $$ /$$__  $$            ");
    System.out.println("                         | $$__/ /$$$$$$$| $$ \\ $$ \\ $$| $$| $$| $$  | $$      | $$__/| $$$$$$$$| $$  | $$| $$  | $$            ");
    System.out.println("                         | $$   /$$__  $$| $$ | $$ | $$| $$| $$| $$  | $$      | $$   | $$_____/| $$  | $$| $$  | $$            ");
    System.out.println("                         | $$  |  $$$$$$$| $$ | $$ | $$| $$| $$|  $$$$$$$      | $$   |  $$$$$$$|  $$$$$$/|  $$$$$$$            ");
    System.out.println("                         |__/   \\_______/|__/ |__/ |__/|__/|__/ \\____  $$      |__/    \\_______/ \\______/  \\_______/            ");
    System.out.println("                                                      /$$  | $$                                                       ");
    System.out.println("                                                     |  $$$$$$/                                                       ");
    System.out.println("                                                      \\______/\n");
    System.out.println("                                                    Thanks for Playing Family Feud\n");
    System.out.println("                  Come back next week to see which family gets to split the $20.00 Burger King Coupon 5 ways!");
    
    int choice='a';//choice holds user input
    
    if (previousChoice!='c'&&previousChoice!='C')//if they didn't quit the game and they made it to fastmoney (when playing against the computer), allow them to record their score
    {  
      do {
        
        System.out.println("\nWould you like to add your score to the hall of fame (y/n):");//Asks the user if they would like to add their score to the text file
        choice=br.readLine().charAt(0);

        if (choice!='y'&&choice!='Y'&&choice!='n'&&choice!='N') System.out.println("Invalid answer, try again");

      } while (choice!='y'&&choice!='Y'&&choice!='n'&&choice!='N');//loop iterates while the user enters invalid input

      if (choice=='y'||choice=='Y')
      {
        highscore(winner, score);//if they would like to enter their score, it calls the highscore method
      }
    }
    
    do {
      
      System.out.println("_____________________________________________________________________________________________________________________________________________________________");
      System.out.println("\nWould you like to play again (y/n)");//Asks the user if they'd like to play again
      choice=br.readLine().charAt(0);

      if (choice!='y'&&choice!='Y'&&choice!='n'&&choice!='N') System.out.println("Invalid answer, try again");

    } while (choice!='y'&&choice!='Y'&&choice!='n'&&choice!='N');

    boolean playAgain=true;//playAgain holds whether or not the user would like to play again

    if (choice=='y'||choice=='Y')//if they do make the value of playAgain true
    {
      playAgain=true;
    }

    else if (choice=='n'||choice=='N')//if they don't make the value of playAgain to false
    {
      playAgain=false;
      System.out.println("\n|Thanks for playing! Come back soon! - Steve Harvey|");//its true, I got his signed permission to use his likeness in my game
    }

    return playAgain;//return playAgain
  }//end of farewellPage method

  /**
  *The purpose of this method is to enter the user's score into a text file holding previous scores and to update which user has the highscore
  *
  *@param player Holds the string value containing the player's name who wishes to enter their score
  *@param score Holds the int value of the user's fastmoney score
  *
  */
  public void highscore (String player, int score) throws IOException {//start of highscore method

    File myFile = new File("HallOfFame");
    Scanner highscoreReader = new Scanner(myFile);//Initializes a scanner meant to read all of the scores in the file
    Scanner highscoreCopy = new Scanner (myFile);//Initializes a scanner that copies the text file so that it can be re-printed

    ArrayList <String> copyOfFile = new ArrayList <>();//ArrayList copyOfFile holds every line within the previous text file

    int lineCounter=0;//int line counter holds the number of lines in the text file

    do 
    {
      
      copyOfFile.add(highscoreCopy.nextLine());
      lineCounter++;//line counter increments following every line

    }while (highscoreCopy.hasNextLine());//do while adds every line in the text file to the arrayList copyOfFile

    for (int i=0; i<lineCounter-1;i++)
    {
      highscoreReader.nextLine();//loops the highscoreReader to the last line in the file
    }

    int highscore=0;//int highscore holds the current highscore in the file

    highscoreReader.next();
    highscoreReader.next();
    highscoreReader.next();
    highscoreReader.next();
    highscore=Integer.parseInt(highscoreReader.next());//highscore is made equal to the current highscore in the file
    
    PrintWriter outputFile = new PrintWriter("HallOfFame");//opens a printwriter to clear the previous text file and make a new one

    for (int i=0; i<(copyOfFile.size())-2; i++)
    {
      outputFile.println(copyOfFile.get(i));//prints all of the previous lines excluding the highscore section
    }

    outputFile.println("______________________________________________________________________________________________________________________________");
    outputFile.println("Player: "+player);
    outputFile.println("Score: "+score);//adds the player's name and score to the bottom of the textfile
    
    if (score>highscore)//if the player's score is greater than the highest score recorded previously add it to the highscore section
    {
      outputFile.println("****************************************************************************************************************************");
      outputFile.println("The current Highscore of "+score+" is held by "+player);
    }

    else//if the score they wish to record is not the highscore
    {
      outputFile.println(copyOfFile.get(copyOfFile.size()-2));
      outputFile.println(copyOfFile.get(copyOfFile.size()-1));//print the previous highscore section
    }

    outputFile.close();//closes the filewriter to save what has been written
    highscoreReader.close();
    highscoreCopy.close();//Closes both scanners
    System.out.println("\nYour score has been added to the Hall of Fame!");//tells the user that their score has been recorded
  
  }//end of highscore method

  /**
  *The purpose of this method is to clear the page for the user after they prompt it to do so
  */
  public void nextPage ()throws IOException {
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Initializing bufferedReader to read from console input

    System.out.print("Press Enter to Continue: ");//Prompts the user to enter something
    br.readLine();//Program is paused
    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");//Prints several lines to clear the screen

  }


}//End of main class


//Figlets from http://patorjk.com/software/taag/#p=display&f=Blocks&t=X
//Questions from https://hobbylark.com/party-games/list-of-family-feud-questions



