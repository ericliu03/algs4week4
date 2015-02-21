

public class Solver {
	// find a solution to the initial board (using the A* algorithm)
	private boolean solvable;
	private int moves;
	private Node finalNode;

	
	private class Node implements Comparable<Node>{
		private int movedNum;
		private Node preNode;
		private Board board;
		private int forCmp;
		public Node(Board board, int movedNum) {
			this.board = board;
			this.movedNum = movedNum;
			this.forCmp = this.board.manhattan() + this.movedNum;
		}

		public Iterable<Node> neighbors() {
			Stack<Node> result = new Stack<Node>();
			Iterable<Board> neighbors = this.board.neighbors();
			for (Board neighbor: neighbors) {
				Node temp = new Node(neighbor, this.movedNum + 1);
				temp.preNode = this;
				result.push(temp);
			}
			return result;
		}
		
		public int compareTo(Node that) {
			int result = this.forCmp - that.forCmp;
			if (result == 0) {
				result = this.board.hamming() - that.board.hamming();
				result += this.movedNum - that.movedNum;
			}
			return result;
		}
		
		public boolean equals(Object x) {
			if (x == this) return true;
	        if (x == null) return false;
	        if (x.getClass() != this.getClass()) return false;
			Node that = (Node) x;
			return this.board.equals(that.board);
		}
		
	}
    public Solver(Board initial)  {
    	Node initNode = new Node(initial, 0);
    	Node twinNode = new Node(initNode.board.twin(), 0);
    	MinPQ<Node> boardQueue = new MinPQ<Node>();
    	MinPQ<Node> twinQueue = new MinPQ<Node>();

    	Node initThis = initNode;
    	Node twinThis = twinNode;
    	while (!(initThis.board.isGoal() || twinThis.board.isGoal())){
    		for (Node temp: initThis.neighbors()) {
    			if (!temp.equals(initThis.preNode)) boardQueue.insert(temp);
    		}
    		for (Node temp: twinThis.neighbors()) {
    			if (!temp.equals(twinThis.preNode)) twinQueue.insert(temp);
    		}
    		initThis = boardQueue.delMin();
    		twinThis = twinQueue.delMin();
    	}
    	if (initThis.board.isGoal()) {
    		this.solvable = true;
    		this.moves = initThis.movedNum;
    		this.finalNode = initThis;
    	}
    	else {
    		this.solvable = false;
    		this.moves = -1;
    	}
    }         
    // is the initial board solvable?
    public boolean isSolvable()   {
    	return this.solvable;
    }        
    // Minimum number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	return this.moves;
    }        
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()  {
    	if (!this.isSolvable()) return null;
    	Stack<Board> solvePath = new Stack<Board>();
    	Node thisNode = this.finalNode; 
    	while( thisNode != null) {
    		solvePath.push(thisNode.board);
    		thisNode = thisNode.preNode;
    	}
    	return solvePath;
    }    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}