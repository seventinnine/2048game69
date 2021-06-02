package spw4.game2048;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameTests {
  @Test
  @DisplayName("ctor: game object created returns game")
  void ctorBoardIsValid() {
    Game sut = new Game();

    assertNotNull(sut);
  }

  @Test
  @DisplayName("ctor: game is initialized with correct dimensions returns game")
  void ctorBoardIsValidDimension() {
    Game sut = new Game();
    sut.placeRandomTiles = false;
    sut.initialize();

    String expected = ".\t.\t.\t.\n" +
                      ".\t.\t.\t.\n" +
                      ".\t.\t.\t.\n" +
                      ".\t.\t.\t.\n";

    String result = sut.toString();

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("ctor: game is initialized with 2 random tiles")
  void ctorBoardIsValidWithRandomTiles(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(11);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.95);
    sut.random = random;
    sut.initialize();
    String expected = ".\t.\t.\t.\n" +
                      "2\t.\t.\t.\n" +
                      ".\t.\t.\t4\n" +
                      ".\t.\t.\t.\n";

    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: player moves tiles up")
  void moveWhenMovingTilesDirectionUp(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(11);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.95);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = "2\t.\t.\t4\n" +
                      ".\t.\t.\t.\n" +
                      ".\t.\t.\t.\n" +
                      ".\t.\t.\t.\n";

    sut.move(Direction.up);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: player moves tiles down")
  void moveWhenMovingTilesDirectionDown(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(11);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.95);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t.\t.\t.\n" +
                      ".\t.\t.\t.\n" +
                      ".\t.\t.\t.\n" +
                      "2\t.\t.\t4\n";

    sut.move(Direction.down);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: player moves tiles left")
  void moveWhenMovingTilesDirectionLeft(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(11);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.95);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t.\t.\t.\n" +
                      "2\t.\t.\t.\n" +
                      "4\t.\t.\t.\n" +
                      ".\t.\t.\t.\n";

    sut.move(Direction.left);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: player moves tiles right")
  void moveWhenMovingTilesDirectionRight(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(11);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.95);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t.\t.\t.\n" +
                      ".\t.\t.\t2\n" +
                      ".\t.\t.\t4\n" +
                      ".\t.\t.\t.\n";

    sut.move(Direction.right);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: player moves tiles into each other, not same, dont merge")
  void moveWhenMovingTilesCollisionDoesntMerge(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(7);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.95);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t.\t.\t.\n" +
                      ".\t.\t2\t4\n" +
                      ".\t.\t.\t.\n" +
                      ".\t.\t.\t.\n";

    sut.move(Direction.right);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: player moves tile right into each other, same, will merge")
  void moveWhenMovingRightTilesCollisionSameValueMerges(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(7);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.7);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t.\t.\t.\n" +
            ".\t.\t.\t4\n" +
            ".\t.\t.\t.\n" +
            ".\t.\t.\t.\n";

    sut.move(Direction.right);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: player moves tile left into each other, same, will merge")
  void moveWhenMovingLeftTilesCollisionSameValueMerges(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(7);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.7);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t.\t.\t.\n" +
            "4\t.\t.\t.\n" +
            ".\t.\t.\t.\n" +
            ".\t.\t.\t.\n";

    sut.move(Direction.left);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: player moves tile up into each other, same, will merge")
  void moveWhenMovingUpTilesCollisionSameValueMerges(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(5).thenReturn(9);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.7);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t4\t.\t.\n" +
            ".\t.\t.\t.\n" +
            ".\t.\t.\t.\n" +
            ".\t.\t.\t.\n";

    sut.move(Direction.up);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }
  @Test
  @DisplayName("move: player moves tile down into each other, same, will merge")
  void moveWhenMovingDownTilesCollisionSameValueMerges(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(5).thenReturn(9);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.7);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t.\t.\t.\n" +
            ".\t.\t.\t.\n" +
            ".\t.\t.\t.\n" +
            ".\t4\t.\t.\n";

    sut.move(Direction.down);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: player moves tiles which have been merged but could merge but won't merge")
  void moveWhenAlreadyMergedTilesCollideWithMergeableTileNotMerging(@Mock Random random) {
    Game sut = new Game();
    sut.startTileCount = 4;
    when(random.nextInt(anyInt())).thenReturn(1).thenReturn(5).thenReturn(9).thenReturn(13);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.7).thenReturn(0.3).thenReturn(0.1);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t.\t.\t.\n" +
            ".\t.\t.\t.\n" +
            ".\t4\t.\t.\n" +
            ".\t4\t.\t.\n";

    sut.move(Direction.down);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("score: player merges two tiles and score correctly updates")
  void getScoreAfterTwoAndTwoMergeReturnsFour(@Mock Random random) {
    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(1).thenReturn(5);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.7);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    int expectedScore = 4;

    sut.move(Direction.down);
    int result = sut.getScore();

    assertEquals(expectedScore, result);
  }

  @Test
  @DisplayName("move: player merges two tiles and score correctly updates")
  void getScoreAfterFoursTwosMergeInTwoStepsReturnsEight(@Mock Random random) {
    Game sut = new Game();
    sut.startTileCount = 4;
    when(random.nextInt(anyInt())).thenReturn(1).thenReturn(5).thenReturn(9).thenReturn(13);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.7).thenReturn(0.3).thenReturn(0.1);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    int expectedScore = 16;

    sut.move(Direction.down);
    sut.move(Direction.up);
    int result = sut.getScore();

    assertEquals(expectedScore, result);
  }

  @Test
  @DisplayName("move: player moves tile right with two possible merging tiles, the two the most to the right will merge")
  void moveWhenMovingMultipleSameValuesMergesTilesFurtherInDirection(@Mock Random random) {
    Game sut = new Game();
    sut.startTileCount = 4;
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(5).thenReturn(6).thenReturn(7);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.92);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    String expected = ".\t.\t.\t.\n" +
            ".\t2\t4\t8\n" +
            ".\t.\t.\t.\n" +
            ".\t.\t.\t.\n";

    sut.move(Direction.right);
    String result = sut.toString();
    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: new Tile spawns after every move")
  void moveNewTileSpawnsOnEmptyTileAfterMove(@Mock Random random) {

    Game sut = new Game();
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(5).thenReturn(1).thenReturn(2);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.92).thenReturn(0.7).thenReturn(0.95);
    sut.random = random;
    sut.initialize();
    String expected = ".\t.\t4\t.\n" +
                      ".\t.\t.\t.\n" +
                      ".\t.\t.\t.\n" +
                      ".\t2\t2\t4\n";
    sut.move(Direction.right);
    sut.move(Direction.down);

    String result = sut.toString();

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("move: if a merge results in a wincondition, the game is won")
  void moveWhenMoveResultsInWinGameIsWon(@Mock Random random) {
    Game sut = new Game();
    sut.startTileCount = 3;
    when(random.nextInt(anyInt())).thenReturn(4).thenReturn(5).thenReturn(6);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.5).thenReturn(0.98);
    sut.random = random;
    sut.initialize();
    sut.placeRandomTiles = false;
    sut.winCondition = 8;

    sut.move(Direction.right);
    sut.move(Direction.right);

    System.out.println(sut.toString());

    assertTrue(sut.isWon());
  }

  @Test
  @DisplayName("move: if no move can be done, Game is over")
  void moveWhenGameIsLost(@Mock Random random) {
    Game sut = new Game();
    sut.startTileCount = 16;
    when(random.nextInt(anyInt())).thenReturn(0).thenReturn(1).thenReturn(2).thenReturn(3)
                                  .thenReturn(4).thenReturn(5).thenReturn(6).thenReturn(7)
                                  .thenReturn(8).thenReturn(9).thenReturn(10).thenReturn(11)
                                  .thenReturn(12).thenReturn(13).thenReturn(14).thenReturn(15);
    when(random.nextDouble()).thenReturn(0.69).thenReturn(0.97).thenReturn(0.69).thenReturn(0.97)
                             .thenReturn(0.97).thenReturn(0.69).thenReturn(0.97).thenReturn(0.69)
                             .thenReturn(0.69).thenReturn(0.97).thenReturn(0.69).thenReturn(0.97)
                             .thenReturn(0.97).thenReturn(0.69).thenReturn(0.97).thenReturn(0.69);
    sut.random = random;
    sut.initialize();

    assertTrue(sut.isOver());
  }
}
