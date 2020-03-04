import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class View {
	
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel gamePanel;
	private static JButton [][] buttonField;
	private JPanel titlePanel;
	private JLabel title;
	
	private int fieldLength;
	private int buttonSize;
	private JMenuBar menuBar;
	private JMenu pathFinder;
	private JMenuItem resetPath;
	private JMenuItem preferences;
	private JMenuItem quit;
	private JMenu maze;
	private JMenuItem resetMaze;
	private JMenuItem loadMaze;
	private JMenuItem saveMaze;
	private JMenu help;
	private JMenuItem howToPlay;
	private JMenuItem about;
	public final static String TITLE = "PathFinder 1.0";
	
	public View() {
		
	fieldLength = 10;
	buttonSize = 30;
	frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setTitle(TITLE);
	frame.setLocation(500,60);
	frame.setResizable(false);
	
	intializeMenuBar();
	
	mainPanel = new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    frame.getContentPane().add(mainPanel);
  	
    titlePanel = new JPanel();
	title = new JLabel("<html><span style='color: Orange;'><b>PathFinder 1.0</b></span></html>");
    title.setFont (new Font(Font.SANS_SERIF,0,40));
    title.setSize(700,100);
    title.setOpaque(true);
    title.setBackground(new Color(0));
    titlePanel.add(title);
   
    gamePanel = new JPanel(new GridLayout(fieldLength,fieldLength));
    buttonField = getField(fieldLength);
  	
  	mainPanel.add(titlePanel);
  	mainPanel.add(gamePanel);
  	frame.pack();
  	frame.setVisible(true);
  	
	}
	
	

	private JButton[][] getField(int size) {
		JButton [][] field = new JButton [size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				JButton tile = new JButton();
				tile.setOpaque(true);
				
				tile.setFont(new Font(Font.SANS_SERIF, 0, buttonSize));
				
				tile.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (tile.getText() == ">") {
							resetPath();
							PathFinder.updateMaze(getMazePicture());
							PathFinder.runMaze();
						} else if (tile.getBackground() == Color.BLACK) {
							tile.setBackground(Color.WHITE);
						} else {
							tile.setBackground(Color.BLACK);
						}
						
					}
				});
				
				if (i == 0 && j == 0) {
					tile.setText(">");
				} else if (i == PathFinder.maze.length - 1 && j == PathFinder.maze[0].length - 1) {
					tile.setText(">");
				}
					
					
				if (i < PathFinder.maze.length && j < PathFinder.maze[0].length) {
					if (PathFinder.maze[i][j] == true) {
						tile.setBackground(Color.WHITE);
					} else {
						tile.setBackground(Color.BLACK);
					}
				} else {
					tile.setBackground(Color.BLUE);
				}
				
				field[i][j] = tile;
				gamePanel.add(tile);
				
			}
		}
		return field;
	}
	
	public static void leaveMark(int x, int y) {
		
		if (x == PathFinder.maze.length - 1 && y == x) {
			buttonField[x][y].setBackground(Color.GREEN);
		}
		
		if (buttonField[x][y].getBackground() == Color.WHITE) {
			buttonField[x][y].setBackground(Color.YELLOW);
		} else if (buttonField[x][y].getBackground() == Color.YELLOW) {
			buttonField[x][y].setBackground(Color.ORANGE);
		} else if (buttonField[x][y].getBackground() == Color.ORANGE) {
			buttonField[x][y].setBackground(Color.RED);	
		} 
	}
	
private void intializeMenuBar() {
		
		menuBar = new JMenuBar();
		
		pathFinder = new JMenu("PathFinder");
		resetPath = new JMenuItem("Reset Path");
		resetPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetPath();
			}

			
		});
		preferences = new JMenuItem("Preferences");
		preferences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPreferences();
			}

			private void showPreferences() {
				// TODO Auto-generated method stub
				
			}
		});
		quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pathFinder.add(resetPath);
		pathFinder.add(preferences);
		pathFinder.add(quit);
		
		maze = new JMenu("Maze");
		resetMaze = new JMenuItem("Reset Maze");
		resetMaze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetButtonField();
			}
		});
		loadMaze = new JMenuItem("Load Maze");
		loadMaze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Do you want to save this maze before loading a new one?", 
						TITLE , JOptionPane.YES_NO_OPTION) == 1) {
					PathFinder.updateMaze(Maze.maze01);
					updateButtonField();
				}
			}
		});
		saveMaze = new JMenuItem("Save Maze");
		saveMaze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog(null, "What do you call this maze?", 
						TITLE , JOptionPane.OK_CANCEL_OPTION);
			}
		});
		
		maze.add(resetMaze);
		maze.add(loadMaze);
		maze.add(saveMaze);
		
		
		help = new JMenu("Help");
		howToPlay = new JMenuItem("How To Play");
		howToPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showHowToPlay();
			}

			private void showHowToPlay() {
				// TODO Auto-generated method stub
				
			}
		});
		about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}

			private void showAbout() {
				// TODO Auto-generated method stub
				
			}
		});
		help.add(howToPlay);
		help.add(about);
		
		menuBar.add(pathFinder);
		menuBar.add(maze);
		menuBar.add(help);
		frame.setJMenuBar(menuBar);
	}

	protected void updateButtonField() {
		for (int x = 0; x < buttonField.length; x++) {
			for (int y = 0; y < buttonField[0].length; y++) {
				if (PathFinder.maze[x][y])  {
					buttonField[x][y].setBackground(Color.WHITE);
				} else {
					buttonField[x][y].setBackground(Color.BLACK);
				}
					
			}
		}
}

	private void resetButtonField() {
		for (int x = 0; x < buttonField.length; x++) {
			for (int y = 0; y < buttonField[0].length; y++) {
					buttonField[x][y].setBackground(Color.BLACK);
			}
		}
		buttonField[0][0].setBackground(Color.WHITE);
		buttonField[buttonField.length-1][buttonField[0].length-1].setBackground(Color.WHITE);
	}

	private void resetPath() {
		for (int x = 0; x < buttonField.length; x++) {
			for (int y = 0; y < buttonField[0].length; y++) {
				if (buttonField[x][y].getBackground() != Color.WHITE && 
						buttonField[x][y].getBackground() != Color.BLACK) {
					buttonField[x][y].setBackground(Color.WHITE);
				}
					
			}
		}
		
	}

	public boolean [][] getMazePicture() {
		boolean [][] mazePicture = new boolean [buttonField.length][buttonField.length];
		
		for (int x = 0; x < buttonField.length; x++) {
			for (int y = 0; y < buttonField[0].length; y++) {
				if (buttonField[x][y].getBackground() == Color.WHITE) {
					mazePicture[x][y] = true;
				} 
			}
		}
		return mazePicture;
	}
}
