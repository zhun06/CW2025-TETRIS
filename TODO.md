# Tetris Refactor Plan

## Separation of concerns
- [x] Add ControllerManager (All scene change goes through here)
- [x] Add SceneManager (Helps with scene changing)
- [x] Add KeyHandlerManager (Changes key input for different game states: pause, resume, gameOver)
-  [x] Add TimelineManager (Play, pause, stop timeline)
- [x] Add OverlayManager (Show and hide overlays)
- [x] Add Renderers (Rendering done outside GameController)
- [x] Add setters(update labels)

## Core game improvements
- [x] Add Home and Theme page (fxml, and it's controller)
- [x] Add scoreboard (Points & rows cleared)
- [x] Add next piece preview (3 pieces)

## Enhancements
- [x] Add ghost piece (where piece will land, translucent color)
- [x] Add hard drop (space bar)
- [x] Add level progression (increasing speed)
- [x] Add different game modes
- [x] Change background and block style to match (for better visibility)
- [x] Add Visual + Sound effects (clear row/s, level up, etc.)
-  [x] Add background music 

## Optional / Extra
- [x] High score table (CSV file)
- [x] Change themes 
- [x] Pause / resume
- [ ] Short tutorial / instructions