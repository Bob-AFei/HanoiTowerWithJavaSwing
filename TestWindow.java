import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;


class TestWindow extends JFrame implements ActionListener{

	private JFrame frame;
	private JPanel pole1Top,pole2Top,pole3Top,poleBottem;
	private JLabel l1,l2;
	private JTextField t1;
	private JButton b1,b2;
	private JPanel [] diskPanel;
	private JTextArea tA1;
	private String info;
	private int input,pole1DiskNumber,pole2DiskNumber,pole3DiskNumber;

	public TestWindow (){
		
		input = 0;
		pole1DiskNumber = 0;
		pole2DiskNumber = 0;
		pole3DiskNumber = 0;

		frame = new JFrame();
		frame.setTitle("HanoiTower");
		frame.setSize(800,800);
		frame.setLayout(null);
		frame.setVisible(true);

		pole1Top = new JPanel ();
		pole1Top.setBounds(180,20,20,350);
		pole1Top.setBackground(Color.GRAY);
		frame.add(pole1Top);

		pole2Top = new JPanel ();
		pole2Top.setBounds(400,20,20,350);
		pole2Top.setBackground(Color.GRAY);
		frame.add(pole2Top);

		pole3Top = new JPanel ();
		pole3Top.setBounds(620,20,20,350);
		pole3Top.setBackground(Color.GRAY);
		frame.add(pole3Top);

		poleBottem = new JPanel();
		poleBottem.setBounds(50,370,700,20);
		poleBottem.setBackground(Color.BLACK);
		frame.add(poleBottem);

		l1 = new JLabel ("Disk Number:");
		l1.setBounds(100,400,100,30);
		frame.add(l1);
	
		t1 = new JTextField ();
		t1.setBounds(210,400,100,30);
		frame.add(t1);

		b1 = new JButton ("Generate Game Boards");
		b1.setBounds(320,400,200,30);
		b1.addActionListener(this);
		frame.add(b1);

		b2 = new JButton ("Start Move Disk");
		b2.setBounds(530,400,200,30);
		b2.addActionListener(this);
		frame.add(b2);

		tA1 = new JTextArea();
		tA1.setBounds(100,450,600,320);
		frame.add(tA1);

	}

	public void actionPerformed (ActionEvent e){
 	

	 	new Thread(new Runnable() {
	 		public void run() {
	 			if (e.getSource() == b1){

	 				//generate the game board
					try {
						//to refresh the disk
						if(input != 0) {
							for (int i=0; i<input; i++) {
								diskPanel[i].setVisible(false);
								}
							}
						// to generate the disk
						input = Integer.parseInt(t1.getText());
						diskPanel = new JPanel [input];
						Random ran = new Random ();
						for (int i = 0; i<input ; i++) {
							diskPanel[i] = new JPanel();
							//to ensure the first one is the samllest top one,
							//and the last one is the bigest bottom one.
							diskPanel[i].setBounds(85+10*(input-i-1),(350-20*(input-i-1)),(210-20*(input-i-1)),20);
							diskPanel[i].setBackground(new Color (ran.nextInt(256),ran.nextInt(256),ran.nextInt(256) ) );
							frame.add(diskPanel[i]); 
							diskPanel[i].repaint();
							info ="please press Start";
							tA1.setText(info);
						}
					} catch (Exception a) {
						info = "Input Error!";
						tA1.setText(info);
					}
				}
				if (e.getSource() == b2){
					if (input != 0){
						pole1DiskNumber = input;
						pole2DiskNumber = 0;
						pole3DiskNumber = 0;

						b1.setEnabled(false);
						b2.setEnabled(false);

						info = "";

						moveDisk(diskPanel.length,1,2,3);	
						b1.setEnabled(true);
						b2.setEnabled(true);
					}else {
						info = "Error!";
						tA1.setText(info);
					}
				}
	 		}
	 	}).start();
	}

	private void moveDisk(int number,int a, int b, int c){ //disck number, from a to c, b is temp;
		if (number == 1){
			printMovement(number,a,c);
		}else{
			moveDisk(number-1,a,c,b);
			printMovement(number,a,c);	
			moveDisk(number-1,b,a,c);
		}
	}


	private void printMovement(int number, int a, int b){
		// TO SOLOW DOWN THE SPEED
		try {
            Thread.sleep(250);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
        }

		if (a == 1){
			pole1DiskNumber--;
		}
		if (a == 2){
			pole2DiskNumber--;
		}
		if (a == 3) {
			pole3DiskNumber--;
		}        

		if (b == 1){
			diskPanel[number-1].setLocation(85+10*(input-number),350-20*(pole1DiskNumber) );
			pole1DiskNumber++;
		}
		if (b == 2){
			diskPanel[number-1].setLocation(85+10*(input-number)+220,350-20*(pole2DiskNumber) );
			pole2DiskNumber++;
		}
		if (b == 3) {
			diskPanel[number-1].setLocation(85+10*(input-number)+440,350-20*(pole3DiskNumber) );
			pole3DiskNumber++;
		}

		diskPanel[number-1].repaint();
		info += "Move the disk "+ number + " from Pole" + a + " to Pole"+ b +"\n";
		tA1.setText(info);
	}

	public static void main(String[] args) {
			new TestWindow();
	}
}


