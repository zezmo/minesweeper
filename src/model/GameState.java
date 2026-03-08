package model;

public enum GameState {
    READY,
    PLAYING,
    WON,
    LOST,
    END // if player cancels the win/lose menu, make the board not react
}
