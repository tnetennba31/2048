import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoard implements ActionListener, KeyListener {
	JFrame board;
	ArrayList<JPanel> blocks = new ArrayList<>(16);
	int[] values = new int[16];
	ArrayList<Integer> combinedBlockPositions = new ArrayList<>();
	boolean somethingMoved = false;
	ArrayList<JButton> arrowButtons = new ArrayList<>(4);
	int currentScore = 0, bestScore = 0;
	JLabel score, best;
	JLabel title;
	JButton begin;
	
	Color blockColor = new Color(250, 243, 221);  // color for the blank blocks
	Color lineColor = new Color(24, 113, 115);  // color for between the blocks
	Color backgroundColor = new Color(180, 130, 145);  // color for the side panel
	
	JFrame colorSchemeWindow;
	JButton chooseColor;
	String[] colorSchemes = new String[]{"vintage", "neon dion", "ice cream shop", "tree hugger", "galaxy", "autumn leaves",
					"the future", "fig newton", "miserlou", "sparky"};
	JList<String> schemeList = new JList<>(colorSchemes);
	Color c2;
	Color c4;
	Color c8;
	Color c16;
	Color c32;
	Color c64;
	Color c128;
	Color c256;
	Color c512;
	Color c1024;
	Color c2048;
	
	Border blockBorder = BorderFactory.createLineBorder(lineColor, 3, true);
	Font numberFont = new Font("Serif", Font.BOLD, 55);
	Font titleFont = new Font("SansSerif", Font.PLAIN, 20);
	Font buttonFont = new Font("SansSerif", Font.BOLD | Font.ITALIC, 16);
	Font endTitleFont = new Font("SansSerif", Font.BOLD | Font.ITALIC, 30);
	Font endButtonFont = new Font("SansSerif", Font.BOLD | Font.ITALIC, 12);
	
	public static void main(String[] args) throws IOException {
		new GameBoard();
	}
	
	public GameBoard() throws IOException {
		createBoard();
		createBlocks();
		initializeBlocks();
		
		board.pack();
		board.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		board.setVisible(true);
		
		createColorPickerWindow();
	}
	
	public void createColorPickerWindow() {
		colorSchemeWindow = new JFrame("choose a color scheme :)");
		colorSchemeWindow.setLayout(new FlowLayout());
		colorSchemeWindow.setBackground(lineColor);
		schemeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollypoly = new JScrollPane(schemeList);
		chooseColor = new JButton("yeet");
		chooseColor.setBackground(backgroundColor);
		chooseColor.addActionListener(this);
		
		colorSchemeWindow.add(scrollypoly);
		colorSchemeWindow.add(chooseColor);
		colorSchemeWindow.pack();
		colorSchemeWindow.setVisible(true);
	}
	
	public void colorSchemeGenerator(int input) {
		switch (input) {
			case 0:  // vintage
				c2 = new Color(184, 242, 230);
				c4 = new Color(220, 182, 179);
				c8 = new Color(108, 70, 79);
				c16 = new Color(202, 190, 189);
				c32 = new Color(200, 159, 156);
				c64 = new Color(184, 197, 199);
				c128 = new Color(215, 253, 240);
				c256 = new Color(137, 106, 103);
				c512 = new Color(231, 207, 188);
				c1024 = new Color(135, 141, 152);
				c2048 = new Color(208, 173, 167);
				break;
			case 1:  // neon dion
				c2 = new Color(236, 8, 104);
				c4 = new Color(85, 214, 190);
				c8 = new Color(247, 197, 72);
				c16 = new Color(94, 35, 157);
				c32 = new Color(131, 33, 97);
				c64 = new Color(252, 100, 113);
				c128 = new Color(125, 21, 56);
				c256 = new Color(69, 31, 85);
				c512 = new Color(57, 0, 153);
				c1024 = new Color(68, 255, 0);
				c2048 = new Color(0, 255, 212);
				break;
			case 2:  // ice cream shop
				c2 = new Color(63, 41, 43);
				c4 = new Color(211, 79, 115);
				c8 = new Color(163, 123, 115);
				c16 = new Color(219, 190, 161);
				c32 = new Color(66, 226, 184);
				c64 = new Color(250, 188, 60);
				c128 = new Color(235, 138, 144);
				c256 = new Color(219, 127, 103);
				c512 = new Color(193, 211, 127);
				c1024 = new Color(45, 130, 183);
				c2048 = new Color(194, 207, 178);
				break;
			case 3:  // tree hugger
				c2 = new Color(99, 193, 50);
				c4 = new Color(205, 199, 118);
				c8 = new Color(165, 170, 82);
				c16 = new Color(172, 235, 152);
				c32 = new Color(118, 117, 34);
				c64 = new Color(135, 255, 101);
				c128 = new Color(164, 194, 168);
				c256 = new Color(233, 235, 158);
				c512 = new Color(207, 252, 255);
				c1024 = new Color(170, 239, 223);
				c2048 = new Color(237, 238, 192);
				break;
			case 4:  // galaxy
				c2 = new Color(0, 18, 66);
				c4 = new Color(71, 19, 108);
				c8 = new Color(232, 7, 112);
				c16 = new Color(111, 19, 74);
				c32 = new Color(24, 63, 74);
				c64 = new Color(58, 0, 95);
				c128 = new Color(35, 46, 209);
				c256 = new Color(251, 254, 249);
				c512 = new Color(2, 57, 74);
				c1024 = new Color(114, 9, 183);
				c2048 = new Color(23, 18, 25);
				break;
			case 5:  // autumn leaves
				c2 = new Color(60, 21, 24);
				c4 = new Color(105, 20, 14);
				c8 = new Color(164, 66, 0);
				c16 = new Color(213, 137, 54);
				c32 = new Color(255, 251, 70);
				c64 = new Color(33, 15, 4);
				c128 = new Color(69, 33, 3);
				c256 = new Color(187, 107, 0);
				c512 = new Color(153, 0, 51);
				c1024 = new Color(255, 144, 0);
				c2048 = new Color(96, 32, 0);
				break;
			case 6:  // the future
				c2 = new Color(179, 179, 179);
				c4 = new Color(160, 160, 160);
				c8 = new Color(136, 136, 136);
				c16 = new Color(142, 142, 142);
				c32 = new Color(161, 163, 163);
				c64 = new Color(173, 170, 170);
				c128 = new Color(137, 135, 137);
				c256 = new Color(158, 158, 158);
				c512 = new Color(164, 170, 167);
				c1024 = new Color(139, 137, 136);
				c2048 = new Color(123, 127, 126);
				break;
			case 7:  // fig newton
				c2 = new Color(149, 113, 134);
				c4 = new Color(217, 184, 196);
				c8 = new Color(112, 61, 87);
				c16 = new Color(64, 42, 44);
				c32 = new Color(36, 23, 21);
				c64 = new Color(55, 57, 46);
				c128 = new Color(246, 189, 96);
				c256 = new Color(96, 77, 83);
				c512 = new Color(191, 139, 133);
				c1024 = new Color(188, 175, 156);
				c2048 = new Color(102, 76, 67);
				break;
			case 8:  // miserlou
				c2 = new Color(1, 111, 185);
				c4 = new Color(34, 174, 209);
				c8 = new Color(0, 129, 167);
				c16 = new Color(254, 217, 183);
				c32 = new Color(109, 142, 160);
				c64 = new Color(19, 41, 61);
				c128 = new Color(253, 231, 76);
				c256 = new Color(27, 152, 224);
				c512 = new Color(232, 241, 242);
				c1024 = new Color(253, 252, 220);
				c2048 = new Color(175, 169, 141);
				break;
			case 9:  // sparky
				c2 = new Color(1, 82, 155);
				c4 = new Color(244, 124, 51);
				c8 = new Color(255, 255, 255);
				c16 = new Color(0, 49, 138);
				c32 = new Color(248, 89, 0);
				c64 = new Color(22, 141, 154);
				c128 = new Color(28, 26, 101);
				c256 = new Color(241, 99, 36);
				c512 = new Color(232, 241, 242);
				c1024 = new Color(8, 67, 128);
				c2048 = new Color(220, 114, 51);
				break;
		}
	}
	
	public void createBoard() {
		board = new JFrame("2048!!!!!!!!");
		board.setBackground(backgroundColor);
		board.setLayout(new GridLayout(4, 5));
		board.setFocusable(true);
		board.addKeyListener(this);
	}
	
	public void createBlocks() throws IOException {
		for (int i = 1; i <= 20; i++) {
			if (i % 5 != 0) {
				JPanel block = new JPanel(new GridBagLayout());
				block.setBackground(blockColor);
				block.setBorder(blockBorder);
				blocks.add(block);
				board.add(block);
			} else {
				switch (i) {
					case 5:
						JPanel bg1 = new JPanel(new BorderLayout());
						bg1.setBackground(backgroundColor);
						title = new JLabel("Welcome to 2048!");
						title.setFont(titleFont);
						title.setHorizontalAlignment(SwingConstants.CENTER);
						bg1.add(title, BorderLayout.CENTER);
						board.add(bg1);
						break;
					case 10:
						JPanel arrows = new JPanel(new GridLayout(3, 3));
						for (int x = 0; x < 9; x++) {
							if (x % 2 == 0 && x != 4) {
								JPanel blank = new JPanel();
								blank.setBackground(backgroundColor);
								arrows.add(blank);
							} else {
								switch (x) {
									case 1:
										Image pic1 = ImageIO.read(getClass().getResource("up_arrow.png"));
										ImageIcon up = new ImageIcon(pic1);
										JButton upArrow = new JButton(up);
										upArrow.addActionListener(this);
										arrowButtons.add(upArrow);
										arrows.add(upArrow);
										break;
									case 3:
										Image pic2 = ImageIO.read(getClass().getResource("left_arrow.png"));
										ImageIcon left = new ImageIcon(pic2);
										JButton leftArrow = new JButton(left);
										leftArrow.addActionListener(this);
										arrowButtons.add(leftArrow);
										arrows.add(leftArrow);
										break;
									case 4:
										begin = new JButton("Begin!");
										begin.setFont(buttonFont);
										begin.setBackground(backgroundColor);
										arrows.add(begin);
										begin.addActionListener(this);
										begin.setEnabled(false);
										break;
									case 5:
										Image pic3 = ImageIO.read(getClass().getResource("right_arrow.png"));
										ImageIcon right = new ImageIcon(pic3);
										JButton rightArrow = new JButton(right);
										rightArrow.addActionListener(this);
										arrowButtons.add(rightArrow);
										arrows.add(rightArrow);
										break;
									case 7:
										Image pic4 = ImageIO.read(getClass().getResource("down_arrow.png"));
										ImageIcon down = new ImageIcon(pic4);
										JButton downArrow = new JButton(down);
										downArrow.addActionListener(this);
										arrowButtons.add(downArrow);
										arrows.add(downArrow);
								}
							}
						}
						arrows.setBorder(BorderFactory.createEmptyBorder());
						board.add(arrows);
						for (JButton button : arrowButtons) {
							button.setEnabled(false);
						}
						break;
					case 15:
						JPanel bg2 = new JPanel(new BorderLayout());
						bg2.setBackground(backgroundColor);
						score = new JLabel("Current Score:");
						score.setFont(titleFont);
						score.setHorizontalAlignment(SwingConstants.CENTER);
						bg2.add(score, BorderLayout.CENTER);
						board.add(bg2);
						break;
					case 20:
						JPanel bg3 = new JPanel(new BorderLayout());
						bg3.setBackground(backgroundColor);
						best = new JLabel("Best Score:");
						best.setFont(titleFont);
						best.setHorizontalAlignment(SwingConstants.CENTER);
						bg3.add(best, BorderLayout.CENTER);
						board.add(bg3);
				}
			}
			
		}
	}
	
	public void initializeBlocks() {
		for (int i = 0; i < 16; i++) {
			values[i] = 0;
		}
	}
	
	public void throwBlock() {
		int block = 0;
		boolean filled = true;
		while (filled) {
			block = (int) (Math.random() * 16);
			filled = values[block] != 0;
		}
		values[block] = 2;
		blocks.get(block).setBackground(c2);
		blocks.get(block).removeAll();
		JLabel num = new JLabel("2");
		num.setFont(numberFont);
		blocks.get(block).add(num);
		board.validate();
	}
	
	public void setBlockEmpty(int blockPos) {
		values[blockPos] = 0;
		blocks.get(blockPos).setBackground(blockColor);
		blocks.get(blockPos).removeAll();
		board.validate();
	}
	
	public void combineBlocks(int currentPos, int nextPos) {
		values[nextPos] = values[nextPos] * 2;
		blocks.get(nextPos).setBackground(getBlockColor(values[nextPos]));
		blocks.get(nextPos).removeAll();
		JLabel num = new JLabel("" + values[nextPos]);
		num.setFont(numberFont);
		blocks.get(nextPos).add(num);
		setBlockEmpty(currentPos);
		changeScores(values[nextPos]);
		combinedBlockPositions.add(nextPos);
		somethingMoved = true;
	}
	
	public void moveBlock(int currentPos, int nextPos) {
		values[nextPos] = values[currentPos];
		blocks.get(nextPos).setBackground(getBlockColor(values[nextPos]));
		blocks.get(nextPos).removeAll();
		JLabel num = new JLabel("" + values[nextPos]);
		num.setFont(numberFont);
		blocks.get(nextPos).add(num);
		setBlockEmpty(currentPos);
		somethingMoved = true;
	}
	
	public void shiftBlockNorth(int blockPosToMove) {
		int currentBlock = blockPosToMove;
		int nextBlock = blockPosToMove - 4;
		// wanna know if it reaches another block or if it reaches top row
		boolean reachedGoodBlock = false;
		while (!reachedGoodBlock) {
			if (nextBlock < 4 || values[nextBlock] != 0) {
				if ((values[currentBlock] == values[nextBlock]) && !combinedBlockPositions.contains(nextBlock)) {
					combineBlocks(currentBlock, nextBlock);
				} else if (values[nextBlock] == 0) {
					moveBlock(currentBlock, nextBlock);
				}
				reachedGoodBlock = true;
			} else {
				moveBlock(currentBlock, nextBlock);
				currentBlock = nextBlock;
				nextBlock -= 4;
			}
		}
	}
	
	public void shiftBlockWest(int blockPosToMove) {
		int currentBlock = blockPosToMove;
		int nextBlock = blockPosToMove - 1;
		// wanna know if it reaches another block or if it reaches the beginning of the row
		boolean reachedGoodBlock = false;
		while (!reachedGoodBlock) {
			if (nextBlock % 4 == 0 || values[nextBlock] != 0) {
				if ((values[currentBlock] == values[nextBlock]) && !combinedBlockPositions.contains(nextBlock)) {
					combineBlocks(currentBlock, nextBlock);
				} else if (values[nextBlock] == 0) {
					moveBlock(currentBlock, nextBlock);
				}
				reachedGoodBlock = true;
			} else {
				moveBlock(currentBlock, nextBlock);
				currentBlock = nextBlock;
				nextBlock -= 1;
			}
		}
	}
	
	public void shiftBlockEast(int blockPosToMove) {
		int currentBlock = blockPosToMove;
		int nextBlock = blockPosToMove + 1;
		// wanna know if it reaches another block or if it reaches the end of the row
		boolean reachedGoodBlock = false;
		while (!reachedGoodBlock) {
			if ((nextBlock + 1) % 4 == 0 || values[nextBlock] != 0) {
				if ((values[currentBlock] == values[nextBlock]) && !combinedBlockPositions.contains(nextBlock)) {
					combineBlocks(currentBlock, nextBlock);
				} else if (values[nextBlock] == 0) {
					moveBlock(currentBlock, nextBlock);
				}
				reachedGoodBlock = true;
			} else {
				moveBlock(currentBlock, nextBlock);
				currentBlock = nextBlock;
				nextBlock += 1;
			}
		}
	}
	
	public void shiftBlockSouth(int blockPosToMove) {
		int currentBlock = blockPosToMove;
		int nextBlock = blockPosToMove + 4;
		// wanna know if it reaches another block or if it reaches bottom row
		boolean reachedGoodBlock = false;
		while (!reachedGoodBlock) {
			if (nextBlock > 11 || values[nextBlock] != 0) {
				if ((values[currentBlock] == values[nextBlock]) && !combinedBlockPositions.contains(nextBlock)) {
					combineBlocks(currentBlock, nextBlock);
				} else if (values[nextBlock] == 0) {
					moveBlock(currentBlock, nextBlock);
				}
				reachedGoodBlock = true;
			} else {
				moveBlock(currentBlock, nextBlock);
				currentBlock = nextBlock;
				nextBlock += 4;
			}
		}
	}
	
	public Color getBlockColor(int number) {
		switch (number) {
			case 2:
				return c2;
			case 4:
				return c4;
			case 8:
				return c8;
			case 16:
				return c16;
			case 32:
				return c32;
			case 64:
				return c64;
			case 128:
				return c128;
			case 256:
				return c256;
			case 512:
				return c512;
			case 1024:
				return c1024;
			case 2048:
				return c2048;
			default:
				return null;
		}
	}
	
	public void changeScores(int points) {
		currentScore += points;
		score.setText("Current Score:  " + currentScore);
		
		if (currentScore > bestScore) {
			bestScore = currentScore;
			best.setText("Best Score:  " + bestScore);
		}
	}
	
	public boolean checkGameOver() {
		for (int z = 0; z < 16; z++) {
			int block = values[z];
			
			if (z - 4 >= 0) {
				int blockUp = values[z - 4];
				if (block == blockUp) {
					return false;
				}
			}
			if (z + 4 < 16) {
				int blockDown = values[z + 4];
				if (block == blockDown) {
					return false;
				}
			}
			if (z - 1 >= 0 && z % 4 != 0) {
				int blockLeft = values[z - 1];
				if (block == blockLeft) {
					return false;
				}
			}
			if (z + 1 < 16 && (z + 1) % 4 != 0) {
				int blockRight = values[z + 1];
				if (block == blockRight) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void loser() {
		for (JButton b : arrowButtons) {
			b.setEnabled(false);
		}
		title.setText("GAME OVER!!!");
		title.setFont(endTitleFont);
		begin.setText("Restart?");
		begin.setFont(endButtonFont);
		begin.setEnabled(true);
	}
	
	public void winner() throws IOException {
		for (JButton b : arrowButtons) {
			b.setEnabled(false);
		}
		title.setText("CONGRATS!!!!!");
		title.setFont(endTitleFont);
		begin.setText("Replay?");
		begin.setFont(endButtonFont);
		begin.setEnabled(true);
		
		Image yay = ImageIO.read(getClass().getResource("winnerwinner.jpg"));
		ImageIcon hazah = new ImageIcon(yay);
		JLabel yass = new JLabel(hazah);
		JFrame yeet = new JFrame("hehe");
		yeet.add(yass);
		yeet.pack();
		yeet.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == chooseColor) {
			colorSchemeGenerator(schemeList.getSelectedIndex());
			colorSchemeWindow.setVisible(false);
			begin.setEnabled(true);
		} else if (event.getSource() == begin) {
			for (int b = 0; b < 16; b++) {
				blocks.get(b).setBackground(blockColor);
				blocks.get(b).removeAll();
				values[b] = 0;
			}
			currentScore = 0;
			
			throwBlock();
			throwBlock();
			begin.setText("");
			begin.setEnabled(false);
			title.setText("Welcome to 2048!");
			title.setFont(titleFont);
			for (JButton i : arrowButtons) {
				i.setEnabled(true);
			}
		} else {
			for (int i = 0; i < arrowButtons.size(); i++) {
				if (event.getSource() == arrowButtons.get(i)) {
					switch (i) {
						case 0:  // up button
							for (int b = 4; b < 16; b++) {
								if (values[b] != 0) {
									shiftBlockNorth(b);
								}
							}
							break;
						case 1:  // left button
							for (int b = 1; b < 16; b++) {
								if (values[b] != 0 && b % 4 != 0) {
									shiftBlockWest(b);
								}
							}
							break;
						case 2:  // right button
							for (int r = 0; r < 4; r++) {
								for (int b = 4 * r + 3; b >= 4 * r; b--) {
									if (values[b] != 0 && (b + 1) % 4 != 0) {
										shiftBlockEast(b);
									}
								}
							}
							break;
						case 3:  // down button
							for (int b = 11; b >= 0; b--) {
								if (values[b] != 0) {
									shiftBlockSouth(b);
								}
							}
					}
					combinedBlockPositions.clear();
					if (somethingMoved) {
						throwBlock();
						somethingMoved = false;
					}
					
					boolean full = true;
					for (int v : values) {
						if (v == 0) {
							full = false;
							break;
						}
					}
					if (full && checkGameOver()) {
						loser();
					}
					
					for (int m : values) {
						if (m == 2048) {
							try {
								winner();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			arrowButtons.get(0).doClick();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			arrowButtons.get(1).doClick();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			arrowButtons.get(2).doClick();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			arrowButtons.get(3).doClick();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
}

/*
random functionality that could be cool
--------------------------------------------
:2: undo and restart buttons in upper left and right squares on keypad
:6: different sized boards
:11: throw a 4 block every so often
*/