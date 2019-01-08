=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: 20837572
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Novel Linked Data Structure - I created a Room datatype to store information about the rooms of 
  the dungeon. These rooms are linked together via pointers - each room has 4 fields containing rooms
  to the north, south, east, and west of them. This allowed me to update the current room an object
  occupies upon movement into another roomby calling the next on its direction of movement. This also allowed
  me to draw the rooms differently depending on the condition of there being an adjacent room or not. I 
  decided not to use a tree to hold this data type, but rather an array with inherent indexing related to
  the cardinal direction of the 'next' adjacent rooms. The dungon data type stores the rooms in an
  array, and contains methods to manipulate the rooms. Its draw method calls the room draw method for each
  room. 

  2. 2D arrays - I utilized 2D arrays in my Dungeon class as a data structure to represent the
  dungeon as an array of rooms. Movement on the board was purely relative to the x and y coordinates
  that the object occupied. This allowed me to write many methods such as add, remove, check, getRoom,
  draw, etc. that can iterate over the array and perform actions on the state of the dungeon or its 
  rooms. I found this allowed me to understand and interact better with the game state, and was the best
  way to store an object which has inherent cardinality and direction associated with it.

  3. Collections - I utilized 2 types of collections to model the state of my game. The first
  is a HashSet, which I implemented by overriding equals and hashcode in both the Key and Monster
  game objects. I stored these values in HashSets because there is no inherent ordering to them and
  the number of objects in each set is variable depending on the game state. These sets are iterated
  over to check for collisions with the character, and their size is dependent on the game state - how
  many keys have been collected and the size of the dungeon, which dictates the number of monsters. 
  Additionally, non - null and non - star ting rooms are stored as an ArrayList. This allows me to use
  the math library's random method to randomly select a room for the gameobjects such as the monsters,
  chest, and keys. I can manipulate whether to add or remove items from this list depending on if I 
  want the door to be in its own room, keys to be in their own rooms, and monsters to be in any room
  except one with the door. These features expand upon what was commented upon in my gradescope
  proposal - I used a field of the game object to store its room, and instead used collections to
  model the state of rooms and objects and iterate over them under different conditions.
  
  4. JUnit Testing - I utilized JUnit testing to test the behavior of my classes, particularly
  the Room and Dungon classes I wrote, and some of their methods. I wrote some helper methods to
  manipulate the state of the program to add and remove rooms from the dungeon, as well as check
  that the pointers were being instantiated correctly for rooms in different positions in the dungeon.
  Encapsulation?


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  Room class represents the 'Room' object type having a size and position on the game canvas, as well
  as pointers to adjacent rooms. Fields of Room determine how and where it is drawn on the screen 
  (ex. does it have adjacent rooms). Dungeon is the main data structure of the game, which stores
  the random layout of rooms associated with the dungeon and methods to change or access its state.
  The other 4 classes are extensions of gameobject and store information related to the type of 
  object, such as the exit (escape door), player (cowboy knight), key and monster(skeleton). I added additional
  methods to the gameobj class (advance) and added fields within gameobjects to maintain their position
  relative to the gameboard by storing what room they are in (what are their limits)and what dungeon they 
  are part of.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  I had a lot of trouble incorporating randomness in my room layout design and ensuring that the 
  dungeon still 'percolated' from the middle - that all rooms were reachable from the center room.
  I ended up having to write a check function within Dungeon to establish that the starting room had to have
  4 adjacent rooms, and used conditinal statements to establish that a room cannot have no next rooms or
  only one next room that also only has one next room. Lastly, I determined that if there is a corner room
  there must also be a corner room on that edge closest to the middle. All of these cases establish that the
  dungeon will always be beatable for a 5x5 dungeon. Ideally I would incorporate a recursive algorithm to check 
  the pointers for each room and its connection with the center but I could not.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I think my game has a good separation of functionality between the gameobj types I made and changed, as well
  as the room and dungeon classes I wrote. State is encapsulated by using 'get' and 'set' methods while keeping
  fields of the objects private. Calling the getSouth etc. methods on a room return a reference type and can therefore break
  encapsulation, however this allows the dungeon characteristics to be updated in place following events in the gameplay that change its state.
  The methods for adding or removing from a dungeon are private - once it is created it is permentant. 
  Given the chance I would refactor some of the code to make it more modular - for
  example right now the size of the rooms is a static variable that is set at 130 pixels - perfect for a 5x5 dungeon with
  size 650x650 but this does not work if the dungeon is rectangular or a different size. I would also like to refactor the 
  check method because right now it has a lot of conditinoal statements and does not use recursion, making it a little clunky.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  I relied on documentation for the ArrayList and HashSet collections, as well as the 
  javadocs for JOptionPane to show my instruction button message.
  
  Here are the links to the images I used for the game objects:
  
  Key - https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwiH2KTlpoL
  YAhWIOBQKHZkXAB0QjRwIBw&url=http%3A%2F%2Fgossipgirl.wikia.com%2Fwiki%2FFile%3AHouse-Key-Drawing-Tutorial-
  final-step-215x382.png&psig=AOvVaw2Ek_mowH7y6BpR0V80a-Zm&ust=1513093521237151
  
  Knight - https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwiWrcv9
  poLYAhXHXBQKHRgOCkcQjRwIBw&url=http%3A%2F%2Fhelmet-heroes.wikia.com%2Fwiki%2FFile%3ACowboy_Knight_Helmet.png
  &psig=AOvVaw1nZPXeWMN_SxSZ0sPRalhg&ust=1513093581945988
  
  Skull - https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwin3aiVp4
  LYAhWHWxQKHY3LCGgQjRwIBw&url=http%3A%2F%2Fwww.iconarchive.com%2Fshow%2Fios7-icons-by-icons8%2FHealthcare-Skull
  -icon.html&psig=AOvVaw0XqMkznI5tnJYiPJR6gYDz&ust=1513093629282194
  
  Door - https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwiJhfamp4LY
  AhXGNxQKHY5VCH8QjRwIBw&url=https%3A%2F%2Fpixabay.com%2Fen%2Fdoor-closed-chain-wooden-entrance-576282%2F&psig=
  AOvVaw2fA6utuTP_x5cE3pELBpYO&ust=1513093665911867
  

