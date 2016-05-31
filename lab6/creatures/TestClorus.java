package creatures;
import huglife.Action;
import huglife.Direction;
import huglife.Impassible;
import huglife.Occupant;
import huglife.Empty;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;


/**
 * Created by LouisCho on 5/31/16.
 */
public class TestClorus {

	@Test
	public void testBasics() {
		Clorus c = new Clorus();
		assertEquals("clorus", c.name());
		assertEquals(2, c.energy(), 0.01);
		assertEquals(new Color(34, 0, 231), c.color());
		c.move();
		assertEquals(1.97, c.energy(), 0.01);
		c.move();
		assertEquals(1.94, c.energy(), 0.01);
		c.stay();
		assertEquals(1.93, c.energy(), 0.01);
		c.stay();
		assertEquals(1.92, c.energy(), 0.01);
	}

	@Test
	public void testAttack() {
		// Starting energy for a Clorus is 2.0.
		Clorus c = new Clorus();
		// Starting energy for a Plip is 1.0.
		Plip p = new Plip();
		c.attack(p);

		assertEquals(3.0, c.energy(), 0.01);
	}

	@Test
	public void testReplicate() {
		Clorus c = new Clorus();
		Clorus o = c.replicate();

		assertNotSame(c, o);
		assertEquals(1.0, o.energy(), 0.01);
		assertEquals(1.0, c.energy(), 0.01);
	}

	@Test
	public void  testChoose() {
		Clorus c = new Clorus();
		HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
		surrounded.put(Direction.TOP, new Plip());
		surrounded.put(Direction.BOTTOM, new Impassible());
		surrounded.put(Direction.LEFT, new Impassible());
		surrounded.put(Direction.RIGHT, new Impassible());


		Action actual = c.chooseAction(surrounded);
		Action expected = new Action(Action.ActionType.STAY);
		// Stay even when a Plip is at the TOP.
		assertEquals(expected, actual);

		surrounded.remove(Direction.BOTTOM);
		surrounded.put(Direction.BOTTOM, new Empty());

		actual = c.chooseAction(surrounded);
		expected = new Action(Action.ActionType.ATTACK, Direction.TOP);
		// Should attack the Plip this time, since there is space at the BOTTOM.
		assertEquals(expected, actual);
	}

	public static void main(String[] Args) {
		System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
	}
}
