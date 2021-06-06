
package tetris;

import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(NestedJUnit.class)
public class FallingPiecesTest {

    private static final RotatablePiece PIECE = new RotatablePiece("" +
            ".T.\n" +
            "TTT\n" +
            "...\n");

    private final Board board = new Board(6, 8);


    public class When_a_piece_is_dropped {

        @Before
        public void dropPiece() {
            board.drop(PIECE);
        }

        @Test
        public void it_starts_from_top_middle() {
            assertThat(board.toString(), is("" +
                    "....T...\n" +
                    "...TTT..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n"));
        }
    }

    public class When_a_piece_reaches_the_bottom {

        @Before
        public void fallToLastRow() {
            board.drop(PIECE);
            board.tick();
            board.tick();
            board.tick();
            board.tick();
        }

        @Test
        public void it_is_still_falling_on_the_last_row() {
            assertThat(board.toString(), is("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n"));
            assertThat("falling", board.hasFalling(), is(true));
        }

        @Test
        public void it_stops_when_it_hits_the_bottom() {
            board.tick();
            assertThat(board.toString(), is("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n"));
            assertThat( board.hasFalling(), is(false));
        }
    }

    public class When_a_piece_lands_on_another_piece {

        @Before
        public void landOnAnother() {
            board.drop(PIECE);
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            assertThat(board.toString(), is("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n"));
            assertThat(falling, board.hasFalling(), is(false));

            board.drop(PIECE);
            board.tick();
            board.tick();
        }

        @Test
        public void it_is_still_falling_right_above_the_other_piece() {
            assertThat(board.toString(), is("" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n" +
                    "....T...\n" +
                    "...TTT..\n"));
            assertThat("falling", board.hasFalling(), is(true));
        }

        @Test
        public void it_stops_when_it_hits_the_other_piece() {
            board.tick();
            assertThat(board.toString(), is("" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n" +
                    "....T...\n" +
                    "...TTT..\n"));
            assertThat("falling", board.hasFalling(), is(false));
        }
    }
}
