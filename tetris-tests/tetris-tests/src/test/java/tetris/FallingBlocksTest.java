

package tetris;

import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tetris.TestUtils.thrownException;


@RunWith(NestedJUnit.class)
public class FallingBlocksTest {

    private final Board board = new Board(3, 3);


    public class A_new_board {

        @Test
        public void is_empty() {
            assertThat(board.toString(), is("" +
                    "...\n" +
                    "...\n" +
                    "...\n"));
        }

        @Test
        public void has_no_falling_blocks() {
            assertThat("falling", board.hasFalling(), is(false));
        }
    }

    public class When_a_block_is_dropped {

        @Before
        public void dropBlock() {
            board.drop(new Block('X'));
        }

        @Test
        public void the_block_is_falling() {
            assertThat("falling", board.hasFalling(), is(true));
        }

        @Test
        public void it_starts_from_the_top_middle() {
            assertThat(board.toString(), is("" +
                    ".X.\n" +
                    "...\n" +
                    "...\n"));
        }

        @Test
        public void it_moves_down_one_row_per_tick() {
            board.tick();
            assertThat(board.toString(), is("" +
                    "...\n" +
                    ".X.\n" +
                    "...\n"));
        }

        @Test
        public void at_most_one_block_may_be_falling_at_a_time() {
            Throwable t = thrownException(() ->
                            board.drop(new Block('Y'))
            );
            assertThat(t, is(instanceOf(IllegalStateException.class)));
            assertThat(t.getMessage(), containsString("already falling"));
            assertThat(board.toString(), is("" +
                    ".X.\n" +
                    "...\n" +
                    "...\n"));
        }
    }

    public class When_a_block_reaches_the_bottom {

        @Before
        public void fallToLastRow() {
            board.drop(new Block('X'));
            board.tick();
            board.tick();
        }

        @Test
        public void it_is_still_falling_on_the_last_row() {
            assertThat(board.toString(), is("" +
                    "...\n" +
                    "...\n" +
                    ".X.\n"));
            assertTrue( board.hasFalling());
        }

        @Test
        public void it_stops_when_it_hits_the_bottom() {
            board.tick();
            assertThat(board.toString(), is("" +
                    "...\n" +
                    "...\n" +
                    ".X.\n"));
            assertFalse( board.hasFalling());
        }
    }

    public class When_a_block_lands_on_another_block {

        @Before
        public void landOnAnother() {
            board.drop(new Block('X'));
            board.tick();
            board.tick();
            board.tick();
            assertThat(board.toString(), is("" +
                    "...\n" +
                    "...\n" +
                    ".X.\n"));
            assertThat("falling", board.hasFalling(), is(false));

            board.drop(new Block('Y'));
            board.tick();
        }

        @Test
        public void it_is_still_falling_right_above_the_other_block() {
            assertThat(board.toString(), is("" +
                    "...\n" +
                    ".Y.\n" +
                    ".X.\n"));
            assertTrue( board.hasFalling());
        }

        @Test
        public void it_stops_when_it_hits_the_other_block() {
            board.tick();
            assertThat(board.toString(), is("" +
                    "...\n" +
                    ".Y.\n" +
                    ".X.\n"));
            assertFalse( board.hasFalling());
        }
    }
}
