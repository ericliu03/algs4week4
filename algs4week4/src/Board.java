

public class Board {
	
	private int N;

	private int[][] tiles;
	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
    	this.N = blocks.length;
    	this.tiles = new int[this.N][this.N];
    	for (int i = 0; i < this.N; i++) {
    		this.tiles[i] = blocks[i].clone();
    	}
    }          
    
    
    // board dimension N                                       
    public int dimension() {
    	return this.N;
    }              
    
    // number of blocks out of place
    public int hamming() {             
    	int num = 0;
    	for (int i = 0; i < this.N; i++) {
    		for (int j = 0; j < this.N; j++) {
    			int correctNum = i * this.N + j + 1;
    			if (this.tiles[i][j] == 0) continue;
    			if (this.tiles[i][j] != correctNum) num ++;
    		}
    	}
    	return num;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
    	int move = 0;
    	for (int i = 0; i < this.N; i++) {
    		for (int j = 0; j < this.N; j++) {
    			
    			if(this.tiles[i][j] == 0) continue;
    			int correctPos = this.tiles[i][j] - 1;
    			int x = correctPos/this.N;
    			int y = correctPos%this.N;
    			move += Math.abs(x - i) + Math.abs(y - j);
    		}
    	}
    	return move;
    }            
    // is this board the goal board?
    public boolean isGoal() {
    	for (int i = 0; i < this.N; i++) {
    		for (int j = 0; j < this.N; j++) {
    			int correctNum = i * this.N + j + 1;
    			if (correctNum == N*N) correctNum = 0;
    			if (this.tiles[i][j] != correctNum) return false;
    		}
    	}
    	return true;
    }             
    
    private int[][] exchange(int[] pos1, int[] pos2, int[][] oTiles) {
    	int[][] tiles = this.cloneTiles(oTiles);
    	int temp = tiles[pos1[0]][pos1[1]];
    	tiles[pos1[0]][pos1[1]] = tiles[pos2[0]][pos2[1]];
    	tiles[pos2[0]][pos2[1]] = temp;
    	return tiles;
    }
    private int[][] cloneTiles(int[][] origin) {
    	int length = origin.length;
    	int[][] newTiles = new int[length][length];
    	for (int i = 0; i < length; i ++) {
    		newTiles[i] = origin[i].clone();
    	}
    	return newTiles;
    }
    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
    	//twinTiles = this.cloneTiles(this.tiles);
    	int[] pos1 = new int[2];
    	int[] pos2 = new int[2];
    	boolean go = true;
    	for (int i = 0; i < this.N && go; i++) {
    		for (int j = 0; j < this.N - 1; j++) {
    			if(this.tiles[i][j] != 0 && this.tiles[i][j + 1] != 0) {
    				pos1[0] = i;
    				pos1[1] = j;
    		    	pos2[0] = pos1[0];
    		    	pos2[1] = pos1[1] + 1;
    		    	go = false;
    		    	break;
    			}
    		}
    	}
    	int[][] twinTiles = exchange(pos1, pos2, this.tiles);
    	Board twin = new Board(twinTiles);
    	return twin;
    }            
    
    // does this board equal x?
    public boolean equals(Object x) {
    	if (x == this) return true;
        if (x == null) return false;
        if (x.getClass() != this.getClass()) return false;
        Board that = (Board) x;
        if(this.N != that.N) return false;
        for (int i = 0; i < this.N; i++) {
    		for (int j = 0; j < this.N; j++) {
    			if(this.tiles[i][j] != that.tiles[i][j]) return false;
    		}
        }
        return true;
    }  

    // all neighboring boards
    public Iterable<Board> neighbors() {
    	int[] pos = new int[2];
    	boolean go = true;
        for (int i = 0; i < this.N && go; i++) {
    		for (int j = 0; j < this.N; j++) {
    			if (this.tiles[i][j] == 0) {
    				pos[0] = i;
    				pos[1] = j;
    				go = false;
    				break;
    			}
    		}
        }
        
        Stack<Board> boards = new Stack<Board>();
        if(pos[0] != 0) {
        	int[] newPos = {pos[0] - 1, pos[1]};
        	Board temp = new Board(this.exchange(pos, newPos, this.tiles));
        	boards.push(temp);
        }
        if(pos[0] != this.N - 1) {
        	int[] newPos = {pos[0] + 1, pos[1]};
        	Board temp = new Board(this.exchange(pos, newPos, this.tiles));
        	boards.push(temp);
        }
        if(pos[1] != 0) {
        	int[] newPos = {pos[0], pos[1] - 1};
        	Board temp = new Board(this.exchange(pos, newPos, this.tiles));
        	boards.push(temp);
        }
        if(pos[1] != this.N - 1) {
        	int[] newPos = {pos[0], pos[1] + 1};
        	Board temp = new Board(this.exchange(pos, newPos, this.tiles));
        	boards.push(temp);
        }
        return boards;
    }  
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
 // unit tests (not graded)
    public static void main(String[] args) {
//    	int[][] tiles = {{5, 8, 7},{1, 4, 6},{3, 0, 2}};
//    	Board board = new Board(tiles);
//    	Board twin = board.twin();
//    	System.out.println(board.manhattan());
//    	System.out.println(board.toString());

    	
    }
}