import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private class Point implements Comparable<Point> {
		private int row;
		private int col;
		private Point parent;
		private int fear;
		public Point(int row, int col, Point parent, int fear) {
			this.row = row;
			this.col = col;
			this.parent = parent;
			this.fear = fear;
		}

		@Override
		public int compareTo(Point o) {
			if (this.fear != o.fear) {
				return this.fear - o.fear;
			} else {
				if (this.row != o.row) {
					return this.row - o.row;
				} else {
					return this.col - o.col;
				}
			}
		}
	}

	private Maze maze;
	private TreeSet<Point> priorityQueue;
	private HashMap<Room, Point> map;

	public MazeSolver() {
		// TODO: Initialize variables.
		this.priorityQueue = new TreeSet<>();
		this.map = new HashMap<>();
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.map = new HashMap<>();
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		this.map = new HashMap<>();
		Point source = new Point(startRow, startCol, null, 0);

		// set all Rooms to Max fear
		// before we begin our search
		// O(RC)
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				Room room = maze.getRoom(i, j);
				map.put(room, new Point(i, j, null, Integer.MAX_VALUE));
			}
		}

		this.map.put(this.maze.getRoom(startRow, startCol), source); // O(1)
		this.priorityQueue.add(source);
		return dijkstra(startRow, startCol, endRow, endCol, source);
	}

	public Integer dijkstra(int startRow, int startCol, int endRow, int endCol, Point source) {
//		Point leastFearPoint = null;
		int leastFear = Integer.MAX_VALUE;
		while (this.priorityQueue.size() > 0) { // V times / RC times
			Point min = this.priorityQueue.first(); // O(log RC)
			int row = min.row;
			int col = min.col;
			if (row == endRow && col == endCol) {
				// YES! we found it!
				if (min.fear < leastFear) {
//					leastFearPoint = min;
					leastFear = min.fear;
				}
			}
			this.priorityQueue.remove(min); // O(log RC)
			Room room = this.maze.getRoom(min.row, min.col);
			for (int direction = 0; direction < 4; ++direction) { // 4 times
				Integer value = WALL_FUNCTIONS.get(direction).apply(room);
				if (value != TRUE_WALL) {
					Point neighbourPoint;
					if (value == EMPTY_SPACE) {
						neighbourPoint = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], min, min.fear + 1);
					} else {
						// value = some spirit wall
						neighbourPoint = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], min, min.fear + value);
					}
					Room neighbourRoom = this.maze.getRoom(neighbourPoint.row, neighbourPoint.col);
					if (neighbourPoint.fear < this.map.get(neighbourRoom).fear) {
						this.priorityQueue.remove(this.map.get(neighbourRoom)); // O(log RC)
						this.priorityQueue.add(neighbourPoint); // O(log RC)
						this.map.put(neighbourRoom, neighbourPoint); // O(1)
					}
				}
			}
		}

		return leastFear == Integer.MAX_VALUE ? null : leastFear;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		this.map = new HashMap<>();
		Point source = new Point(startRow, startCol, null, 0);

		// set all Rooms to Max fear
		// before we begin our search
		// O(RC)
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				Room room = maze.getRoom(i, j);
				map.put(room, new Point(i, j, null, Integer.MAX_VALUE));
			}
		}

		this.map.put(this.maze.getRoom(startRow, startCol), source); // O(1)
		this.priorityQueue.add(source);
		return dijkstraBonus(startRow, startCol, endRow, endCol, source);
	}

	public Integer dijkstraBonus(int startRow, int startCol, int endRow, int endCol, Point source) {
//		Point leastFearPoint = null;
		int leastFear = Integer.MAX_VALUE;
		while (this.priorityQueue.size() > 0) { // V times / RC times
			Point min = this.priorityQueue.first(); // O(log RC)
			int row = min.row;
			int col = min.col;
			if (row == endRow && col == endCol) {
				// YES! we found it!
				if (min.fear < leastFear) {
//					leastFearPoint = min;
					leastFear = min.fear;
				}
			}
			this.priorityQueue.remove(min); // O(log RC)
			Room room = this.maze.getRoom(min.row, min.col);
			for (int direction = 0; direction < 4; ++direction) { // 4 times
				Integer value = WALL_FUNCTIONS.get(direction).apply(room);
				if (value != TRUE_WALL) {
					Point neighbourPoint;
					if (value == EMPTY_SPACE) {
						neighbourPoint = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], min, min.fear + 1);
					} else {
						if (min.fear < value) {
							// value = some spirit wall
							neighbourPoint = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], min, value);
						} else {
							neighbourPoint = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], min, min.fear);
						}
					}
					Room neighbourRoom = this.maze.getRoom(neighbourPoint.row, neighbourPoint.col);
					if (neighbourPoint.fear < this.map.get(neighbourRoom).fear) {
						this.priorityQueue.remove(this.map.get(neighbourRoom)); // O(log RC)
						this.priorityQueue.add(neighbourPoint); // O(log RC)
						this.map.put(neighbourRoom, neighbourPoint); // O(1)
					}
				}
			}
		}

		return leastFear == Integer.MAX_VALUE ? null : leastFear;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		// TODO: Find minimum fear level given new rules.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		this.map = new HashMap<>();
		Point source = new Point(startRow, startCol, null, 0);

		// set all Rooms to Max fear
		// before we begin our search
		// O(RC)
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				Room room = maze.getRoom(i, j);
				map.put(room, new Point(i, j, null, Integer.MAX_VALUE));
			}
		}

		this.map.put(this.maze.getRoom(startRow, startCol), source); // O(1)
		this.priorityQueue.add(source);
		return dijkstraBonus(startRow, startCol, endRow, endCol, source, sRow, sCol);
	}

	public Integer dijkstraBonus(int startRow, int startCol, int endRow, int endCol, Point source, int sRow, int sCol) {
//		Point leastFearPoint = null;
		int leastFear = Integer.MAX_VALUE;
		while (!this.priorityQueue.isEmpty()) { // V times / RC times
			Point min = this.priorityQueue.first(); // O(log RC)
			int row = min.row;
			int col = min.col;
			if (row == endRow && col == endCol) {
				// YES! we found it!
				if (min.fear < leastFear) {
//					leastFearPoint = min;
					leastFear = min.fear;
				}
			}
			this.priorityQueue.remove(min); // O(log RC)
			Room room = this.maze.getRoom(min.row, min.col);
			for (int direction = 0; direction < 4; ++direction) { // 4 times
				Integer value = WALL_FUNCTIONS.get(direction).apply(room);
				int neighbourRow = row + DELTAS[direction][0];
				int neighbourCol = col + DELTAS[direction][1];
				Point neighbourPoint;

				if (value != TRUE_WALL) {
					if (neighbourRow == sRow && neighbourCol == sCol) {
						neighbourPoint = new Point(neighbourRow, neighbourCol, min, -1);
					} else {
						if (value == EMPTY_SPACE) {
							neighbourPoint = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], min, min.fear + 1);
						} else {
							if (min.fear < value) {
								// value = some spirit wall
								neighbourPoint = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], min, value);
							} else {
								neighbourPoint = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], min, min.fear);
							}
						}
					}
					Room neighbourRoom = this.maze.getRoom(neighbourPoint.row, neighbourPoint.col);
					if (neighbourPoint.fear < this.map.get(neighbourRoom).fear) {
						this.priorityQueue.remove(this.map.get(neighbourRoom)); // O(log RC)
						this.priorityQueue.add(neighbourPoint); // O(log RC)
						this.map.put(neighbourRoom, neighbourPoint); // O(1)
					}
				}
			}
		}

		return leastFear == Integer.MAX_VALUE ? null : leastFear;
	}

	public static void main(String[] args) {
		try {
//			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			Maze maze = Maze.readMaze("haunted-maze-simple.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);
			System.out.println(solver.pathSearch(0, 0, 0, 5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
