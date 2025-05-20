import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
public class MazeSolver implements IMazeSolver {
	private class Point {
		private int row;
		private int col;
		private Point parent;
		public Point(int row, int col, Point parent) {
			this.row = row;
			this.col = col;
			this.parent = parent;
		}
	}
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 1, 0 }, // South
			{ 0, 1 }, // East
			{ 0, -1 } // West
	};
	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	private int endRow, endCol;
	private HashMap<Integer, Integer> map;
	public MazeSolver() {
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
		return bfs(startRow, startCol, endRow, endCol);
	}
	public Integer bfs(int startRow, int startCol, int endRow, int endCol) {
		Queue<Point> queue = new LinkedList<>();
		Integer shortestDistance = Integer.MAX_VALUE;
		Integer[][] distances = new Integer[maze.getRows()][maze.getColumns()];
		distances[startRow][startCol] = 0;
		Point finalPoint = null;
		queue.add(new Point(startRow, startCol, null));
		visited[startRow][startCol] = true;
		while (!queue.isEmpty()) {
			Point point = queue.poll();
			// for each of the 4 directions
			int row = point.row;
			int col = point.col;
			visited[row][col] = true;
			if (row == endRow && col == endCol) {
				// YES! we found it!
				this.solved = true;
				shortestDistance = distances[endRow][endCol];
				finalPoint = point;
			}
			for (int direction = 0; direction < 4; ++direction) {
				if (canGo(row, col, direction)) { // can we go in that direction?
					// yes we can :)
					Point neighbour = new Point(row + DELTAS[direction][0], col + DELTAS[direction][1], point);
					int neighbourRow = neighbour.row;
					int neighbourCol = neighbour.col;
					if (!visited[neighbourRow][neighbourCol]) {
						visited[neighbourRow][neighbourCol] = true;
						distances[neighbourRow][neighbourCol] = distances[row][col] + 1;
						int newDistance = distances[neighbourRow][neighbourCol];
						if (this.map.containsKey(newDistance)) {
							this.map.put(newDistance, map.get(newDistance) + 1);
						} else {
							this.map.put(newDistance, 1);
						}
						queue.add(neighbour);
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
		}
		return shortestDistance == Integer.MAX_VALUE ? null : shortestDistance;
	}
	private boolean canGo(int row, int col, int dir) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;
		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}
		return false;
	}
	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (!this.map.containsKey(k)) {
			return 0;
		}
		return this.map.get(k);
	}
	public static void main(String[] args) {
		// Do remember to remove any references to ImprovedMazePrinter before submitting
		// your code!
		try {
			Maze maze = Maze.readMaze("maze-empty.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);
			System.out.println(solver.pathSearch(0, 0, 3, 3));
			MazePrinter.printMaze(maze);
			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}