package org.jvmgames.java.numberguesser;


enum GameDifficulty {
  EASY(10, 5), MEDIUM(20, 4), HARD(50, 3);

  private final int bound;
  private final int maxGuesses;

  GameDifficulty(int bound, int maxGuesses) {
    this.bound = bound;
    this.maxGuesses = maxGuesses;
  }

  public int getBound() { return bound; }
  public int getMaxGuesses() { return maxGuesses; }
}
