import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.*;


public class RodentsRevengeJFrame extends JFrame {

	private static final long serialVersionUID = 8583901963397094682L;
	private JPanel contentPane;
	private RodentsRevengeGame game;
	private JMenuBar menubar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					RodentsRevengeJFrame frame = new RodentsRevengeJFrame();
					frame.setVisible(true);
					frame.startGame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RodentsRevengeJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Komi's Revenge!");
		setResizable(false);
		this.game = new RodentsRevengeGame();
		contentPane = this.game;
		contentPane.setBorder(null);
		contentPane.setFocusable(true);
		setContentPane(contentPane);
		contentPane.setVisible(true);
		bindGameKeys(contentPane);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				contentPane.requestFocusInWindow();
			}
		});

		menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		newGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.newGame();
			}
			
		});
		JMenuItem pause = new JMenuItem("Pause");
		pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		pause.addActionListener(new ActionListener(){
			/*
			 * If the game is not over, toggle the paused flag and update
			 * the logicTimer's pause flag accordingly.
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.pause();
			}
			
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		file.add(newGame);
		file.add(pause);
		file.addSeparator();
		file.add(exit);
		menubar.add(file);
		
		JMenu options = new JMenu("Options");
		
		JMenu difficulty = new JMenu("Difficulty");
		
		ButtonGroup difficultyGroup = new ButtonGroup();
		JRadioButtonMenuItem slow = new JRadioButtonMenuItem("Slow");
		slow.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent a) {
				if(a.getStateChange() == ItemEvent.SELECTED){
					game.changeDifficulty(Difficulty.SLOW);
				}
			}
			
		});
		difficultyGroup.add(slow);
		difficulty.add(slow);
		
		JRadioButtonMenuItem medium = new JRadioButtonMenuItem("Medium");
		medium.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent a) {
				if(a.getStateChange() == ItemEvent.SELECTED){
					game.changeDifficulty(Difficulty.MEDIUM);
				}
			}
			
		});
		medium.setSelected(true);
		
		JRadioButtonMenuItem fast = new JRadioButtonMenuItem("Fast");
		fast.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent a) {
				if(a.getStateChange() == ItemEvent.SELECTED){
					game.changeDifficulty(Difficulty.FAST);
				}
			}
			
		});
		JCheckBoxMenuItem repeatLevel = new JCheckBoxMenuItem("Repeat Level");
		repeatLevel.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent a) {
				if(a.getStateChange() == ItemEvent.SELECTED){
					game.repeatLevel(true);
				} else {
					game.repeatLevel(false);
				}
				
			}
			
		});
		difficultyGroup.add(slow);
		difficultyGroup.add(medium);
		difficultyGroup.add(fast);
		difficulty.add(slow);
		difficulty.add(medium);
		difficulty.add(fast);
		
		JMenu levels = new JMenu("Levels");
		for(int i = 10; i <= 20; i+=10){
			JMenu range = new JMenu((i-9)+"-"+i+"");
			for(int j = i-9; j <= i; j++){
				JMenuItem level = new JMenuItem(j+"");
				level.addActionListener(new LevelActionListener(j-1, game));
				range.add(level);
			}
			levels.add(range);
		}
		
		options.add(difficulty);
		options.add(levels);
		options.addSeparator();
		options.add(repeatLevel);
		
		menubar.add(options);
		
		JMenu about = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(RodentsRevengeJFrame.this, 
					"Komi's Revenge! v1.0\nBy Stan Alam forked from maxxboehme's A Mouses Vengeance\nfor Windows 94", 
					"About", 
					JOptionPane.INFORMATION_MESSAGE);
			}
		});
		about.add(aboutItem);
		menubar.add(about);

		this.setJMenuBar(menubar);

		pack();
		setLocationRelativeTo(null);
		EventQueue.invokeLater(() -> contentPane.requestFocusInWindow());
	}

	private void bindGameKeys(JComponent component) {
		InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = component.getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "moveNorth");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveNorth");
		actionMap.put("moveNorth", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.turn(Direction.North);
			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "moveSouth");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveSouth");
		actionMap.put("moveSouth", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.turn(Direction.South);
			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "moveWest");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveWest");
		actionMap.put("moveWest", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.turn(Direction.West);
			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "moveEast");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveEast");
		actionMap.put("moveEast", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.turn(Direction.East);
			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterGame");
		actionMap.put("enterGame", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.enter();
			}
		});
	}

	private class LevelActionListener implements ActionListener{

		private int l;
		private RodentsRevengeGame game;
		LevelActionListener(int i, RodentsRevengeGame g){
			l = i;
			this.game = g;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			game.setLevel(l);
		}
		
	}
	
	public void startGame(){
		Thread t = new Thread() {
			public void run(){
				game.startGame();
			}
		};
		t.start();
	}

}
