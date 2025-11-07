# Tetris Refactor Plan

## Separation of concerns
- [x] Add ControllerManager (All scene change goes through here)
- [x] Add SceneManager (Helps with scene changing)
- [ ] Add KeyHandlerManager (Changes key input for different game states: pause, resume, gameOver)
-  [ ] Add TimelineManager (Play, pause, stop timeline)
- [ ] Add OverlayManager (Show and hide overlays)
- [ ] Add Renderer (Rendering done outside GameController)

## Core game improvements
- [x] Add Home and Theme page (fxml, and it's controller)
- [ ] Add scoreboard (Points & rows cleared)
- [ ] Add next piece preview (3 pieces)

## Enhancements
- [ ] Add ghost piece (where piece will land, translucent color)
- [ ] Add hard drop (space bar)
- [ ] Add level progression (increasing speed)
- [ ] Change background and block style to match (for better visibility)
- [ ] Add Visual + Sound effects (clear row/s, level up)
-  [ ] Add background music / Mute option

## Optional / Extra
- [ ] High score table (CSV file, sorting)
- [ ] Change themes 
- [ ] Pause / resume
- [ ] Short tutorial / instructions