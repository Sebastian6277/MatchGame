import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import javax.swing.JPanel;

import java.util.ArrayList;

import java.util.Random;

public class Simon implements ActionListener, MouseListener {        //In this case we are going to use labels instead of buttons, so we are going to use
                                                                     // a MouseListener
	private JFrame frmMatchGame;

	private static final int START_LEVEL = 1;                        //We set the start level at 1 
	private static final int START_SCORE = 0;                        // We set the start score at 0

	private JButton btnPlay;                                         //We create our global variables
	private JLabel lbllevel;
	private JLabel lblScore;
	private JLabel lblinfo;
	JPanel contentPanel;
	private JPanel redPanel;
	private JPanel greenPanel;
	private JPanel yellowPanel;
	private JPanel bluePanel;
	
	private boolean redSoundPlayed = false;
	private boolean greenSoundPlayed = false;
	private boolean yellowSoundPlayed = false;
	private boolean blueSoundPlayed = false;

	public int light;                                                  //the variable light will be the color 
	public int ticks;                                                  //the variable ticks will be the time 
	public int stepsIndex;                                             //the variable stepsIndex will be the steps that the user will enter
	public int dark;                                                   
	public int score = 10;                                             //The score variable will always be 10 
	public int level = 1;                                              //The level variable will always be 1 
	public int complete = 0;                                           
	public int numberofcolors = 3;                                     //This variable are the colors that will be display
	public int winning = 10;                                                

	public boolean creatingSteps = true, gameOver;

	public ArrayList<Integer> sequence = new ArrayList<Integer>();      //We represent the steps as a series of integers
	public Random random = new Random();                                //We create our Timer 

	Timer gameTimer = new Timer(20, this);                              //We define our timer 
	
	Audio audio = new Audio();                                          //We instantiate our Audio class

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : void main(String[]args)
		// Method Parameters     : args - The method permits String commands line parameters to be entered. 
		// Method return         : void
		// Synopsis              : In this method we call all the functions.
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Simon window = new Simon();
					window.frmMatchGame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Simon() {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : Simon
		// Method Parameters     : none 
		// Method return         : none
		// Synopsis              : In this method we call all the functions.
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------
		
		welcomeMessage();                                                                    //we call the welcome message function
		initialize();                                                                        //We call the initialize function 
	} 

	/**
	 * Initialize the contents of the frame.
	 */
	private void welcomeMessage() {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : welcomeMessage
		// Method Parameters     : void
		// Method return         : none
		// Synopsis              : In this method we welcome the user.
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------

		JOptionPane.showMessageDialog(null, "Welcome to MatchGame");                                   //We welcome the user 
	}

	private void initialize() {	
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : initialize
		// Method Parameters     : void
		// Method return         : none
		// Synopsis              : In this method we display the  user interface.
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------

		frmMatchGame = new JFrame();
		frmMatchGame.setTitle("Match Game");
		frmMatchGame.setResizable(false);
		frmMatchGame.setBounds(100, 100, 800, 600);
		frmMatchGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMatchGame.getContentPane().setLayout(null);

		contentPanel = new JPanel();
		contentPanel.setBounds(74, 80, 501, 357);
		frmMatchGame.getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		contentPanel.addMouseListener(this);

		btnPlay = new JButton("PLAY");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPlay.addMouseListener(new MouseAdapter() {                               //We call the mouse listener if the user push the button 
			@Override
			public void mouseClicked(MouseEvent e) {
				SimonSays();                                                        //We call the Simon says function 
				btnPlay.setEnabled(false);                                          //We disable the button play 
			}
		});
		btnPlay.setBounds(623, 199, 129, 84);
		frmMatchGame.getContentPane().add(btnPlay);

		lbllevel = new JLabel("LEVEL : " + " " + START_LEVEL);
		lbllevel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbllevel.setBounds(274, 33, 118, 21);
		frmMatchGame.getContentPane().add(lbllevel);

		lblScore = new JLabel("SCORE :" + " " + START_SCORE);
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblScore.setBounds(274, 449, 163, 32);
		frmMatchGame.getContentPane().add(lblScore);

		// NewPanel
		redPanel = new JPanel();
		redPanel.setBounds(84, 47, 170, 130);
		contentPanel.add(redPanel);
		redPanel.setBackground(Color.RED.darker());

		greenPanel = new JPanel();
		greenPanel.setBounds(264, 47, 170, 130);
		contentPanel.add(greenPanel);
		greenPanel.setBackground(Color.GREEN.darker());

		yellowPanel = new JPanel();
		yellowPanel.setBounds(84, 202, 170, 130);
		contentPanel.add(yellowPanel);
		yellowPanel.setBackground(Color.YELLOW.darker());

		bluePanel = new JPanel();
		bluePanel.setBounds(264, 202, 170, 130);
		contentPanel.add(bluePanel);
		bluePanel.setBackground(Color.BLUE.darker());

		lblinfo = new JLabel("");
		lblinfo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblinfo.setBounds(562, 20, 198, 32);
		frmMatchGame.getContentPane().add(lblinfo);
	}

	public void SimonSays() {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : SimonSays
		// Method Parameters     : void
		// Method return         : none
		// Synopsis              : In this method we call the start function and start our timer.
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------

		start();                                                                                 //We call the start function 
		gameTimer.start();                                                                       //We start our timer 
		Graphics g = contentPanel.getGraphics();                                                 //We use the graphics library and paint to create the animation of the                       
		this.paint((Graphics2D) g);                                                              //buttons when they switch on  and switch off.
        //Oracle(2023): https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
	}

	public void start() {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : start
		// Method Parameters     : void
		// Method return         : none
		// Synopsis              : In this method we call the createSequence function to create a new game.
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------
		
		lblinfo.setText(" ");                                               //Each time the user push the button "play" the info label will be empty
		numberofcolors = 3;                                                 // we set the number of colors to its original sequence
		winning = 10;                                                       // we set the points to its original value 
		createSequence();                                                   // we call our function 
		score = 0;                                                          //we set the score at 0 
	}

	public void createSequence() {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : createSequence
		// Method Parameters     : void
		// Method return         : none
		// Synopsis              : In this method we create our random sequence 
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------
		 
		sequence.clear();                                                                                       //we clear our array. 
 
		for (int counter = 0; counter < numberofcolors; counter++) {
			int color = random.nextInt(4) + 1;
			sequence.add(color);                                                                               //we add the new color to the array. 
		}
	}

	public void paint(Graphics2D g) {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : paint
		// Method Parameters     : Graphics2D 
		// Method return         : none
		// Synopsis              : In this method we display the color by assigning them the numbers that are generated in the sequence,
		//                         turning them off and on as they are displayed
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------
		System.out.print("\n" + light);                                                                           //We print the light just as a test .
		if (light == 1) {
	        redPanel.setBackground(Color.RED);
	        if (!redSoundPlayed) {
	            audio.playSound("Audio/red.wav");
	            redSoundPlayed = true;
	        }
	    } else {
	        redPanel.setBackground(Color.RED.darker());
	        redSoundPlayed = false;
	    }

	    if (light == 2) {
	        greenPanel.setBackground(Color.GREEN);
	        if (!greenSoundPlayed) {
	            audio.playSound("Audio/green.wav");
	            greenSoundPlayed = true;
	        }
	    } else {
	        greenPanel.setBackground(Color.GREEN.darker());
	        greenSoundPlayed = false;
	    }

	    if (light == 3) {
	        yellowPanel.setBackground(Color.YELLOW);
	        if (!yellowSoundPlayed) {
	            audio.playSound("Audio/yellow.wav");
	            yellowSoundPlayed = true;
	        }
	    } else {
	        yellowPanel.setBackground(Color.YELLOW.darker());
	        yellowSoundPlayed = false;
	    }

	    if (light == 4) {
	        bluePanel.setBackground(Color.BLUE);
	        if (!blueSoundPlayed) {
	            audio.playSound("Audio/blue.wav");
	            blueSoundPlayed = true;
	        }
	    } else {
	        bluePanel.setBackground(Color.BLUE.darker());
	        blueSoundPlayed = false;
	    }
		if (score < 0) {                                                                                       //If the score is below to 0 the game is over.
			score = 0;                                                                                         //we set the score to its original value.
			level = 1;                                                                                         //we set the level to its original value.
			lblinfo.setText("GAME OVER!!");                                                                    //We let the user know. 
			JOptionPane.showMessageDialog(null, "PLEASE, PLAY AGAIN");                                         //We tell the user to play again.
			gameTimer.stop();                                                                                  //we stop the timer.
			btnPlay.setEnabled(true);                                                                          //if the user wants to play again we enable the button.
			light = 0;                                                                                         //we set the light to its original value to avoid extra sounds.
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : actionPerformed
		// Method Parameters     : ActionEvent
		// Method return         : none
		// Synopsis              : In this method we set the duration of lighting and we add a new color to the sequence 
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------
		paint((Graphics2D) contentPanel.getGraphics());
		ticks++;

		if (ticks % 20 == 0) {                                      //The illumination lasts for one second if we 
			light = 0;                                              //have not clicked on the second field.
			if (dark >= 0) {
				dark--;
			}
		}

		if (creatingSteps) {                                        //At start-up the field lights up.
			if (dark <= 0) {
				if (stepsIndex >= sequence.size()) {

					stepsIndex = 0;
					creatingSteps = false;
				} else {
					light = sequence.get(stepsIndex);
					stepsIndex++;
				}
				dark = 2;
			}
		} else if (stepsIndex == sequence.size()) {
			creatingSteps = true;
			stepsIndex = 0;
			dark = 2;
		}
		contentPanel.repaint();
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : mausePressed
		// Method Parameters     : MouseEvent
		// Method return         : none
		// Synopsis              : In this method we locate where the user clicks, if the user clicks on any panel, the panel will light up. 
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		//
		// Oracle(2023): https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html
		// --------------------------------------------------------------------------------------------------------------------------------------------

		if (!creatingSteps && !gameOver) {
			Component clicked = contentPanel.getComponentAt(me.getPoint());
			if (clicked == redPanel) {                                           //If the user clicked on red panel: 
				System.out.print("\n Red Panel");                                // we print the panel just for testing. 
				audio.playSound("Audio/red.wav");                                // we display the audio.
				light = 1;                                                       //we turn on the light. 
				ticks = 1;                                                       //we set the delay.
				lblinfo.setText("");
				
			} else if (clicked == greenPanel) {                                  //If the user clicked on green panel: 
				System.out.print("\n Green Panel");                              // we print the panel just for testing. 
				audio.playSound("Audio/green.wav");                              // we display the audio.
				light = 2;                                                       //we turn on the light. 
				ticks = 1;                                                       //we set the delay.
				lblinfo.setText("");
				
			} else if (clicked == yellowPanel) {                                 //If the user clicked on green panel: 
				System.out.print("\n Yellow Panel");                             // we print the panel just for testing. 
				audio.playSound("Audio/yellow.wav");                             // we display the audio.
				light = 3;                                                       //we turn on the light .
				ticks = 1;                                                       //we set the delay.
				lblinfo.setText("");
				
			} else if (clicked == bluePanel) {                                   //If the user clicked on blue panel: 
				System.out.print("\n Blue Panel");                               // we print the panel just for testing. 
				audio.playSound("Audio/blue.wav");                               // we display the audio.
				light = 4;                                                       //we turn on the light. 
				ticks = 1;                                                       //we set the delay.
				lblinfo.setText("");			
			}

			if (light != 0) {                                                    //if the light is not 0 
				if (sequence.get(stepsIndex) == light) {                         //if the user clicks are equal to the lights displayed. 
					stepsIndex++;                                                // we allowed to user to click until the sequence is complete. 
					if (sequence.size() == stepsIndex) {                         // If the player completes the sequence, it updates the score and level.
						if (complete == 3) {                                     // If the player completes the 4 sequences.
							numberofcolors++;                                    //number of colors increase.
							level++;                                             //level increase. 

							creatingSteps = true;                              
							stepsIndex = 0;
						}
						if (complete == 4) {                                     //At the 5th sequence we multiply the points by 2. 
							winning = winning * 2;
							complete = 0;
						} else {
							complete++;                                          //Complete increase.
						}
						score = score + winning;                                 //We add the point to the score. 
						lblinfo.setText("!!!CORRECT!!");                        //We let the user know if he complete the sequence correctly .
						audio.playSound("Audio/celebrate.wav");                 //We display the audio.
						lbllevel.setText("LEVEL : " + " " + level);             //We update the level and score labels .
						lblScore.setText("SCORE :" + " " + score);

						createSequence();                                       //we create a new sequence. 
						
					}					
				} else {
					score = score - (winning * 2);                               //if the level increase the points will be multiply by 2. 
					complete--;                                                  // if the user fails in a sequence, the complete is lowered to redo the sequence again.
					lblinfo.setText("!!!WRONG!!");
					audio.playSound("Audio/boo.wav");                           //we display an audio to let the user know about it.
					JOptionPane.showMessageDialog(null, "!!WRONG!!");           //We display a message .
					lblScore.setText("SCORE : " + score);                       //We update the score and level labels. 
					lbllevel.setText("LEVEL : " + level);

					creatingSteps = true;
					stepsIndex = 0;
					createSequence();
				}
			}
		} else if (gameOver) {                                                 //If the game is over. 
			start();                                                           //we call the start function.
			gameOver = false;
			lbllevel.setText("LEVEL : " + " " + level);                       //We update the level and score to its original values. 
			lblScore.setText("SCORE : " + score);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
