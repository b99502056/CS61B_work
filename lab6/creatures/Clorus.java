package creatures;

import huglife.*;

import java.util.Map;
import java.util.List;
import java.awt.Color;

/**
 * Created by LouisCho on 5/31/16.
 */
public class Clorus extends Creature {

	private int r = 34;
	private int g = 0;
	private int b = 231;

	public Clorus(double e) {
		super("clorus");
		energy = e;
	}

	public Clorus() {
		this(2.0);
	}

	public Color color() {return new Color(r, g, b);}

	public void move() {
		energy -= 0.03;
	}

	public void attack(Creature c) {
		energy += c.energy();
	}

	public Clorus replicate() {
		energy /= 2.0;
		return new Clorus(energy);
	}

	public void stay() {
		energy -= 0.01;
	}

	public Action chooseAction(Map<Direction, Occupant> neighbors) {
		List<Direction> empties = getNeighborsOfType(neighbors, "empty");
		List<Direction> plips = getNeighborsOfType(neighbors, "plip");

		if (empties.size() == 0) {
			return new Action(Action.ActionType.STAY);
		} else if (plips.size() > 0) {
			Direction moveDir = HugLifeUtils.randomEntry(plips);
			return new Action(Action.ActionType.ATTACK, moveDir);
		} else if (energy >= 1.0) {
			Direction moveDir = HugLifeUtils.randomEntry(empties);
			return new Action(Action.ActionType.REPLICATE, moveDir);
		} else {
			Direction moveDir = HugLifeUtils.randomEntry(empties);
			return new Action(Action.ActionType.MOVE, moveDir);
		}
	}
}
