.
############################################################################################################################################################
.
.
1. Overview and Rules

This is the classic game of Connect4. It is played in a 7x7 board. It is a two player game hence has two colours(although in the actual version of connect4, the colours used are red and yellow, I have used green and orange instead as they looked better and more subtle). The ultimate goal is to get four or more coins in your colour in the same row, column or diagonal (left or right). Green will always play first and then orange. That's pretty much all the rules.

2. User interactions

Once the program is made to run, the board gets displayed and there will be a moving coin that helps the player decide where to drop the coin andwhich player's chance it is at that instant. Below the board, it displays which player's chance it is in words. The moving coin can be made to move by either pressing the left and right arrows or by pressing the 'a' and 'd' buttons. Once the player decides which column to drop the coin into, they can simply press the ENTER button. In case if the player is having a hard time in deciding which column, they can press SHIFT which will give the best next move by looking at all possiblities for the next six moves and shows it by putting a grey coin in that place. Once the game is over, the winning move gets highlighted in red and the player can press ENTER or SPACE to start a new game. To end the game, the player can simply click on the cross button in the top-right corner.

3. The Coding Part

The coding for the game was done using JAVA (version 11.0), using IntelliJ IDEA Community Edition 2019.3.3 .

4. The Classes

The program starts from the Main class, which simply initiates the game loop in the GameLoop class. The GameLoop class extends from the Runnable class, in order to run and display the board continuously. This class listens to the keyboard (KeyManager class) and displays (GameDisplay class) continuosly. The GameDisplay class creates a frame for a canvas, and a canvas on which the board is going to be drawn. This canvas is being passed to the render function in the DisplayBoard class which displays or draws the board onto the canvas passed for the current state of the game. The GameState class indicates the state of the game. This class has the board of the game at a particular time and whose chance it is at a particular time. The KeyManager class takes actions based on what buttons are being pressed on the keyboard. The GameTree class, makes a tree for six layers and returns the best next move for the current player. For finding the best next move, the class uses the AI search algorithm, Minimax.
.
.
############################################################################################################################################################
.       