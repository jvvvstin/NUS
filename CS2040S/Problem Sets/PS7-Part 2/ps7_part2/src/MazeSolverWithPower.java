import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private class Point {
		private int row;
		private int col;
		private Point parent;
		private int superpowers;
		private int distance;
		public Point(int row, int col, Point parent, int distance, int superpowers) {
			this.row = row;
			this.col = col;
			this.parent = parent;
			this.distance = distance;
			this.superpowers = superpowers;
		}
	}

	private class CanGo {
		private boolean canGo;
		private int superpowers;
		private boolean brokeWall;

		public CanGo(boolean canGo, int superpowers, boolean brokeWall) {
			this.superpowers = superpowers;
			this.canGo = canGo;
			this.brokeWall = brokeWall;
		}

		public CanGo(boolean canGo) {
			this.canGo = canGo;
		}
	}

	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	private boolean[][][] visitedWithSP;
	private int endRow, endCol;
	private HashMap<Integer, Integer> map;

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
		this.solved = false;
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
		this.solved = false;
		this.map = new HashMap<>();
		this.map.put(0, 1);
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}
		this.map = new HashMap<>();
		this.map.put(0, 1);
		this.endRow = endRow;
		this.endCol = endCol;
		return bfs(startRow, startCol, endRow, endCol, 0);
	}

	public Integer bfs(int startRow, int startCol, int endRow, int endCol, int superpowers) {
		Queue<Point> queue = new LinkedList<>();
		Integer shortestDistance = Integer.MAX_VALUE;
		this.visitedWithSP = new boolean[maze.getRows()][maze.getColumns()][superpowers + 1];
		Point finalPoint = null;
		queue.add(new Point(startRow, startCol, null, 0, superpowers));
		visited[startRow][startCol] = true;
		this.visitedWithSP[startRow][startCol][superpowers] = true;
		while (!queue.isEmpty()) {
			Point point = queue.poll();
			// for each of the 4 directions
			int row = point.row;
			int col = point.col;
			visited[row][col] = true;
			if (row == endRow && col == endCol) {
				// YES! we found it!
				this.solved = true;
				if (finalPoint == null) {
					shortestDistance = point.distance;
					finalPoint = point;
				}
			}
			for (int direction = 0; direction < 4; ++direction) {
				CanGo canGo = canGo(row, col, direction, point.superpowers);
				if (canGo.canGo) { // can we go in that direction?
					// yes we can :)
					Point neighbour = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], point, point.distance + 1, canGo.superpowers);
					int neighbourRow = neighbour.row;
					int neighbourCol = neighbour.col;
					if (!visitedWithSP[neighbourRow][neighbourCol][canGo.superpowers]) {
						this.visitedWithSP[neighbourRow][neighbourCol][canGo.superpowers] = true;
						if (!visited[neighbourRow][neighbourCol]) {
							visited[neighbourRow][neighbourCol] = true;
							int newDistance = neighbour.distance;
							if (this.map.containsKey(newDistance)) {
								this.map.put(newDistance, map.get(newDistance) + 1);
							} else {
								this.map.put(newDistance, 1);
							}
						}

						queue.add(neighbour);
					}
				}
			}
		}
		if (shortestDistance != Integer.MAX_VALUE) {
			maze.getRoom(startRow, startCol).onPath = true;
			maze.getRoom(endRow, endCol).onPath = true;
			Point parent = finalPoint.parent;
			while (parent != null) {
				maze.getRoom(parent.row, parent.col).onPath = true;
				parent = parent.parent;
			}
		}
		return shortestDistance == Integer.MAX_VALUE ? null : shortestDistance;
	}

	private CanGo canGo(int row, int col, int dir, int superpowers) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return new CanGo(false);
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return new CanGo(false);
		switch (dir) {
			case NORTH:
				// if no north wall,
				if (!maze.getRoom(row, col).hasNorthWall()) {
					return new CanGo(true, superpowers, false);
				} else {
					// there is a north wall
					// if still have superpowers,
					if (superpowers > 0) {
						return new CanGo(true, superpowers - 1, true);
					}
				}
				// else cannot go
				return new CanGo(false);
			case SOUTH:
				// if no south wall,
				if (!maze.getRoom(row, col).hasSouthWall()) {
					return new CanGo(true, superpowers, false);
				} else {
					// there is a south wall
					// if still have superpowers,
					if (superpowers > 0) {
						return new CanGo(true, superpowers - 1, true);
					}
				}
				// else cannot go
				return new CanGo(false);
			case EAST:
				// if no east wall,
				if (!maze.getRoom(row, col).hasEastWall()) {
					return new CanGo(true, superpowers, false);
				} else {
					// there is a east wall
					// if still have superpowers,
					if (superpowers > 0) {
						return new CanGo(true, superpowers - 1, true);
					}
				}
				// else cannot go
				return new CanGo(false);
			case WEST:
				// if no west wall,
				if (!maze.getRoom(row, col).hasWestWall()) {
					return new CanGo(true, superpowers, false);
				} else {
					// there is a west wall
					// if still have superpowers,
					if (superpowers > 0) {
						return new CanGo(true, superpowers - 1, true);
					}
				}
				// else cannot go
				return new CanGo(false);
		}
		return new CanGo(false);
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (!this.map.containsKey(k)) {
			return 0;
		}
		return this.map.get(k);
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}
		this.map = new HashMap<>();
		this.map.put(0, 1);
		this.endRow = endRow;
		this.endCol = endCol;
		if (superpowers <= 0) {
			return bfs(startRow, startCol, endRow, endCol, 0);
		}
		return bfs(startRow, startCol, endRow, endCol, superpowers);
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(4, 4, 4, 0, 0));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}