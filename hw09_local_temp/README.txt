=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 74295356
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections (Good to Go)
  I used ArrayList to manage obstacles in the GolfCourse class.

  The collection dynamically stores varying numbers of obstacles, allowing for flexibility in the game's level design.
  Iteration techniques are used to navigate through and interact with obstacles during game updates.
  ArrayList offers efficient access to elements, which is crucial for real-time updates in the game loop.

  2. File I/O (Good to Go)
  The state of my game is stored using the GameState class, it includes two helper methods toCustomString
  to turn GameState to string and fromCustomString to turn string separated by "," and ";" to GameState.

  The game state is written to a file in a specific format that encapsulates all necessary information to
  recreate the game state.

  I also use a GameSaver class to write GameState in string into txt
  and read txt to convert back to GameState in string.

  IOExceptions such as FileNotFoundException and potential formatting errors are caught and handled.
  This robust error handling ensures that the game does not crash due to file I/O issues.

  3. Inheritance and Subtyping (Good to Go)
  Different types of obstacles (e.g., Tree, Water, Sand) extend a common Obstacle class or implement an ObstacleInterface.

  Demonstrates polymorphism, where different obstacle types share common methods but have distinct behaviors
  (e.g., different collision responses).
  The use of inheritance allows for code reuse and a cleaner organization of the code, which aligns with
  object-oriented programming principles.
  Dynamic dispatch is utilized to handle different obstacle behaviors during collisions,
  which demonstrates polymorphism.

  4. JUnit Tests (changed from 2D array since 2D array is kinda useless in my game)
  I wrote JUnit tests for the GolfCourse class and GameState Class to test game mechanics like collisions, ball movements,
  and course updates.

  The tests are designed to be unit-testable, focusing on the core game model without relying on GUI components.
  Assertions are used to validate game state changes, ensuring the game logic works as expected.
  Tests cover various scenarios and edge cases, such as collisions with different obstacles, stopping
  of ball, and keeping ball position within bounds.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  hans1.pixelgolf Package
  BigGame Class
  Serves as the main class to run the game.
  Manages game initialization, including creating instances of GameManager, GameController, and GameWindow.
  Contains the major game loop, which updates game state and renders the game.
  Manages camera positioning based on the ball's position.

  GameController Class
  Central controller for managing game actions and interactions.
  Handles player actions like shooting, adjusting power, and angle.
  Responsible for updating the game state through the GolfCourse and GameManager.
  Manages saving and loading game states using GameSaver.

  Player Class
  Represents the player in the game.
  Holds the player's score, power, and angle for shots.
  Provides methods to modify these attributes.


  hans2.pixelgolf.ui Package
  CircularSlider Class
  Custom UI component for selecting angles in a circular motion.
  Tracks and updates the angle based on user interaction.

  ControlPanel Class
  UI component that holds game controls like shoot button, power slider, and angle selector.
  Interacts with GameController to trigger game actions.

  CoursePanel Class
  The main panel where the golf course and game elements are rendered.
  Draws the golf course, obstacles, ball, hole, and club.
  Manages camera movement based on the game state.

  GameWindow Class
  The main game window that hosts different UI panels.
  Integrates the CoursePanel, ScorePanel, and ControlPanel.
  Manages updating the timer and score display.

  ScorePanel Class
  A small panel that displays the current score.
  Updates dynamically as the game progresses.

  hans3.pixelgolf.utils Package
  GameManager Class
  Manages the high-level game logic and progression.
  Controls the transition between different holes and checks for game completion.
  Interacts with GolfCourse to initialize and update the course state.

  GameSaver Class
  Handles saving and loading the game state to and from a file.
  Utilizes GameState's custom string format for persistence.

  GameState Class
  Represents a snapshot of the game's current state.
  Holds information like current hole number, ball position, and obstacle data.
  Provides methods to convert game state to and from a custom string format for saving/loading.


  hans4.pixelgolf.physics Package
  Ball Class
  Represents the golf ball in the game.
  Manages its position, velocity, and interactions with gravity and obstacles.
  Provides methods to move the ball, handle collisions, and reset its position.

  PhysicsEngine Class
  Handles the physical interactions in the game, like ball movement and collisions.
  Works with Ball and Obstacle instances to apply game physics.

  Vector2D Class
  A utility class representing a two-dimensional vector.
  Used in physics calculations, especially for collision handling.
  hans5.pixelgolf.course Package

  Club Class
  Represents the golf club used by the player.
  Manages the power and angle of shots.

  GolfCourse Class
  The main class representing the golf course.
  Manages obstacles, the hole, the ball, and the club.
  Responsible for updating the game state and checking hole completion.

  Hole Class
  Represents the golf hole on the course.
  Holds its position and radius.

  Obstacle Class (and its subclasses Tree, Sand, Water)
  Represents obstacles on the golf course.
  Different obstacle types like Tree, Sand, and Water have specific behaviors and visual representations.
  Handles interactions with the ball, like collisions.

  ObstacleFactory Class
  A factory class for creating Obstacle instances for JUnit Tests.
  Useful for creating obstacles dynamically based on type.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  Implementing File I/O for obstacles.

  Since I made Obstacle an abstract class, I cannot directly instantiate obstacle objects. So
  when saving game state, I have to keep track of type and then pattern match.
  The obstacleData handling was quite tedious.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  Separation of functionality:
  Each class has a specific role, such as managing the game loop (BigGame),
  handling user interactions (GameController), and representing the game world (GolfCourse).

  Encapsulation:
  Currently encapsulated to some extent and rely a lot on getters and setters.

  Refactoring:
  I would have centralized components (e.g., player) primarily in gameManager and golfCourse.
  Right now there are too many getter and setter methods and I often confuse myself.

  I would also let GameManager handle high-level game progression (e.g., moving to the next hole),
  and let GolfCourse manages the immediate state of the current hole and obstacles


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
  1.stackoverflow.com/questions/35258853/how-can-i-paint-a-custom-circular-jcomponent-without-an-invisible-box-around-i
  2.math.stackexchange.com/questions/13261/how-to-get-a-reflection-vector