# Tetris Refactoring Project

## GitHub
[Tetris Refactoring Project Repository](https://github.com/zhun06/CW2025.git)

---

## Compilation Instructions
1. Clone the repository:
    ```bash
    git clone https://github.com/zhun06/CW2025.git
   ```
2. Navigate to the project folder:
    ```bash
    cd CW2025
   ```
3. Build and run the project using Maven:
    ```bash
    mvn clean javafx:run
   ```

## Basic key controls
- Move left: Left arrow, A
- Move right: Right arrow, D
- Move down: Down arrow, S
- Rotate: Up arrow, W
- Hard drop: Space, tab, enter

## Implemented and working properly
### In game features
1. In game scoreboard (stats): level, score, rows, time and high score.
2. Added next 3 bricks preview. (so players know what to expect)
3. New hard drop move: drops brick to the lowest unoccupied position.
4. Ghost piece: shows where brick will land when hard dropped. Translucent color of the current brick.
5. New game modes added: 
    - Zen mode: endless marathon style game, where speed increases as you level up.
    - Forty mode: clear 40 lines as fast as possible.
    - Blitz mode: clear as many rows as possible in 1 minute.
    - Hardcore mode: survive as long as possible as the board rises up. (garbage spawn)
6. Added wall kicks to rotation, now possible to rotate near the wall.
7. Added pause option.
### HighScore
1. Added csv file to store high score for different game modes. (local persistence)
2. Added leaderboard page to view the high score table for different game modes.
3. Defined different high score metrics for different game modes.
### Themes
1. Added theme page where user can select different themes.
2. Each theme has its respective css file and color pallet.
3. Each theme implements the interface ThemeColor which is used to render the board, bricks and visual effects differently.
### Special effects
1. Added in-game background music. (Pauses on pause and stops on game over)
2. Added sound effects for different events: on start, on line clear, on level up, etc.
3. Added visual effects for different events: on start, on line clear, on level up, etc.

## Implemented but not working properly
All implemented features are working correctly.

## Features not implemented
1. Short tutorial / instructions: not implemented because of time constraints and I do not think it is worth the effort.

## New java classes
### com.comp2042.controllers
- `HomeController`: controls the landing page for game mode selection, also links to theme and leader board page.
- `ThemeController`: controls theme switching.
- `LeaderBoardController`: controls the leader board table page.
### com.comp2042.managers
- `ControllerManager`: static utility class, manages all scene switch with methods like callHomeController, callGameController, etc.
- `SceneManager`: static utility class to help with switching scenes and themes.
- `GameManager`: singleton class, which manages all game states. (start, pause. resume, etc.) 
                 Delegates task to `OverlayManager`, `GameKeyHandlerManager` and `TimelineManager`
- `OverlayManager`: hides and shows overlays based on current game state: in game, on pause and on game over.
- `GameKeyHandlerManager`: attaches and detaches key handlers based on current game state: in game, on pause and on game over.
- `TimelineManager`: play/pause/stop timeline and animation timer based on current game state: in game, on pause and on game over.
### com.comp2042.engine
- `TetrisEngine`: sets up game timeline and animation loop. 
                  Delegates task to renderers and setters which updates every frame based on the game current game state.
### com.comp.renderers
- `BoardRenderer`: responsible for rendering board and brick and ghost brick.
- `PreviewRenderer`: responsible for rendering preview bricks. (next 3 bricks)
### com.comp.setters
- `StatsSetter`: updates in game labels: level, score, rows cleared etc. (fully dynamic based on game mode)
- `ResultSetter`: updates on game over labels to show game result. (fully dynamic based on game mode)
- `LeaderBoardSetter`: helper class for `LeaderBoardController`, sets up leader board table view. 
### com.comp2042.highScore
- `CsvLoader`: loads data from the csv file.
- `ScoreRecord`: represents a leaderboard entry for a specific game mode.
### com.comp2042.logic.time
- `Stopwatch`: counts up, starting from zero.
- `Timer`: counts down from a set duration.
### com.comp2042.logic.data
- `TimeData`: manages both Stopwatch and Timer. Provides pause/resume and query methods for elapsed/remaining time.
- `SfxData`: stores active special effects events during gameplay, allowing the game to track and manage sound/visual effects.
- `GameResult`: store the game result on game over.
### com.comp2042.logic.games
- `GameMode`: interface class which has different implementations of onGameStart, onLineClear, onTick and onGameOver.
- `ZenMode`: implements `GameMode`.
- `FortyMode`: implements `GameMode`.
- `BlitzMode`: implements `GameMode`.
- `HardcoreMode`: implements `GameMode`.
### com.comp2042.sfx
- `SfxManager`: delegates task to `VfxRenderer` and `SfxPlayer`.
- `VfxRenderer`: renders visual effects on events like: on line clear, on game over, etc.
- `SfxPlayer`: plays sound effects on events like: on line clear, on game over, etc.
- `SoundLoader`: static utility class which cache sound effects and plays them. Play/pause/stop background music.
### com.comp2042.colors
- `ThemeColor`: interface, provides a color pallet to `BoardRenderer`, `PreviewRenderer` and `VfxRenderer` based on current theme.
- `CandyColor': implements `ThemeColor`
- `MinionColor': implements `ThemeColor`
- `NatureColor': implements `ThemeColor`
- `NeonColor': implements `ThemeColor`
- `OceanColor': implements `ThemeColor`
- `SunsetColor': implements `ThemeColor`
### com.comp2042.util
- `GameChoice`: enum for game modes: zen, forty, etc.
- `GameState`: enum for game states: start, pause, resume, game over, etc.
- `SfxEvent`: enum for special effects events: line clear, level up, etc.
- `Theme`: enum for themes: candy, nature, neon, etc.

## Modified java classes
1. Rename `GameController` to `TetrisGame`
   ### Reason for rename:
        The original class name, GameController, conflicted with the meaning of “controller” in JavaFX where controllers 
        typically handle UI logic for FXML views. 
        The class was not a UI controller; instead, it acted as the core game logic engine, maintaining board state, 
        handling gameplay rules, applying scoring, and responding to input events.
        Renaming it to TetrisGame better reflects its role as the central gameplay model and removes naming ambiguity with actual FXML controllers.
   ### Modifications made to TetrisGame (previously GameController)
        - Separated UI logic from gameplay logic:
          Removed all direct UI interactions and responsibilities (removed reference to GuiController).
          Reason: this makes the class a pure game model and enforces proper separation of concerns.
        - Introduced game mode abstraction (GameMode interface + mode classes): 
          Refactored monolithic logic into overridable hooks (onGameStart, onLineClear, onTick, etc.) that each mode implements.
          Reason: this makes the game extensible, avoids duplicated logic, and clearly follows the strategy pattern.
        - Added new systems (SfxData, TimeData and GameResult):
          Reason: These data wrappers were integrated to allow clean transfer of information through encapsulation of getters and setters.
        - Improved merge and row-clearing logic:
          Consolidated merge behaviour into onMerge(), centralising all game loop consequences. 
          (scoring, SFX, row clear handling, new brick creation, game-over state, etc.)
          Reason: reduces scattered behaviours and makes the game loop easier to read, test, and maintain.
2. Rename `GuiController` to `GameController`
    ### Reason for rename:
        The original name, GuiController, was too general and did not match the naming conventions used across the project
        (e.g., HomeController, ThemeController).
        This class specifically controls the in-game view, so renaming it to GameController better describes its purpose
        and keeps controller names consistent.
    ### Modifications made to GameController (previously GuiController)
        - Extracted key input handling, timeline management, and board rendering into dedicated classes:
          Reason: improves separation of concerns by removing non-UI responsibilities from the controller.
        - Introduced getters for relevant FXML nodes.
          Reason: enables other components (renderers, managers, etc.) to interact with UI elements through controlled access, 
          improving encapsulation.
        - Removed manual layouts (magic numbers):
          Reason: Avoids hard coding and makes it easier for maintanability and extensibility using VBox and Hbox allignment features.
3. `SimpleBoard`
    ### Modifications made to SimpleBoard:
        - Fixed incorrect board initialisation. (width/height swapped)
        - Fixed brick spawn position from center of board to top.
        - New method, hardDropBrick(): instantly drops brick to lowest unobstructed y position.
        - New method, tryApplyRotation(): uses wall kick attempts to try rotate when obstructed.
        - New method, getGhostY: computes the y coordinate of ghost piece.
        - New methods, setBrickOffset() and setBoardMatrix(): allows TetrisGame to update brick and board.
        - Removed return values of moveBrickLeft(), moveBrickDown(), etc. because return values not used.
        - SimpleBoard now fully encapsulates board state and brick state,
          exposing getters/setters (getBoardMatrix, setBoardMatrix, getNextBricks, setBrickOffset, etc.) for the game logic to manipulate safely.
4. `RandomBrickGenerator`
    ### Modifications made to RandomBrickGenerator:
        - Changed getNextBrick() to getNextBricks to return the list of upcoming bricks.
        - Fixed preview size so size of nextBricks is always 3.
5. `BrickRotator`
    ### Modifications made to BrickRotator:
        - Added kick offsets: to apply offsets for rotation.
6. `Brick`
    ### Modifications made to Brick:
        - New method, getColor(), returns integer representing the color of the current brick.
7. `ViewData`
    ### Modifications made to ViewData:
        - New method getCoordinates: return a list of current brick coordinates for rendering.
        - New method getGhostCoordinates: return a list of ghost coordinates for rendering.
8. `Score`
   ### Modifications made to ViewData:
        - New method, onDownEvent(): adds score for soft drop.
        - New method, onHardDropEvent(): adds score for hard drop.
        - New method, onLineClear(ClearRow clearRow): handles line clear scoring and row tracking.
        - New getters and setters: setLevel(), setRowsRemaining, rowsClearedProperty, levelProperty, etc.
          Reason: allows setters to get information through encapsulation.
9. Deleted `NotificationPanel`: redundant because VfxRenderer already handles popups and along with other visual effects.
10. Deleted `GameOverPanel`: redundant because OverlayManager shows and hide game over overlay along with pause overlay.
11. Deleted `DownData`: no longer used.

## Unexpected problems
1. Brick Rendering and Layout Shifts:
    - Problem: Initially, bricks were rendered using a separate GridPane (BrickPanel) and manually shifted using offsets. 
               Near the walls, the entire GridPane would go out of bounds, causing unwanted layout shifts.
               Trying to remove BrickPanel and rendering brick directly on the board caused the whole board to move with the brick.
    - Solution: Introduced getCoordinates() in ViewData and removed the BrickPanel.
                Bricks are now rendered directly into the BoardPanel based on coordinates, eliminating layout shifts.
2. Hardcore Mode Garbage Row Conflicts:
    - Problem: In Hardcore mode, garbage rows were added to the board mid-game. 
              This caused bricks to merge into the board prematurely, conflicting with the ongoing game timeline.
    - Solution: Implemented a gravity lock to temporarily prevent bricks from falling during garbage row spawns. 
                Bricks are also pushed upward as rows are added to maintain correct positioning.
3. Duplicate Key Handlers:
    - Problem: Multiple key handlers could become active simultaneously, leading to conflicting input responses and double input.
    - Solution: Key handlers are now attached and detached based on the current game state (in-game, paused, or game over), 
                ensuring only one active set of handlers at a time.
4. Stage Resizing in Fullscreen Mode
    - Problem: Switching scenes while in fullscreen caused the stage to resize unexpectedly.
    - Solution: Each FXML is loaded only once, and its root node is stored and reused. 
                Only the root is swapped during scene changes, preserving the stage size and fullscreen state.
                This also prevents memory leaks and improves performance by avoiding repeated FXML loading and object creation.
5. Messy Data Flow Between Game and Engine
    - Problem: Passing data from TetrisGame to renderers and setters was inconsistent, causing lost or incomplete information.
    - Solution: Introduced data wrappers such as TimeData, GameResult, and SfxData, with encapsulated getters and setters, 
                ensuring consistent and clean data flow throughout the game.
                This was inspired by the existing use of Score, ViewData, ClearRow and NextShapeInfo which I thought was clean and cool.

## Summary
### Major features & achievements
- New game modes (Zen, Forty, Blitz, Hardcore)
- Hard drop & ghost piece
- Wall kicks & pause system
- High score system with CSV persistence and leaderboard
- Themes with fully modular color support
- Sound & visual effects integrated with gameplay
### Refactoring Highlights
- Managers modularized (GameManager, TimelineManager, etc.) for clean separation of concerns.
- Full encapsulation of game/board/bricks. (SfxData, TimeData, etc.)
- TetrisEngine delegates task to Renderers and Setters, clean separation of concerns.
- GameMode and ThemeColor interface fully extensible and reusable.
### Background and previous experience
- No prior java experience before taking this course.
- Built a SnakeGame using ChatGPT to learn Java/JavaFX (branch: week-1)
- Extended the program to support multiple games, to add Tetris.
- Built Tetris myself from scratch, to figure out the game logic
  (collision, merging, board representation, etc.) before starting this coursework. (branch: week-2)
- Managers and modular design patterns were imported and improved from the earlier project. (SceneManager, TimelineManager, etc.)
- Upgraded previously static GameManager to a singleton for better state management.
- This project took approximately 100 hours.
- [Previous game project](https://github.com/zhun06/2DGAMES.git)