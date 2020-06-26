import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Minesweeper extends JFrame{
	/***/
	private static final long serialVersionUID = 1L;
	Random rad = new Random();
	JPanel infoScreen = new JPanel();
	JPanel gameScreen = new JPanel();
	
	private static boolean ending = false;
	private static final int ROWS = 18; // ��(����)
	private static final int COLS = 18; // ��(����)
	private static final int MINE = 40; // �� ������ ����
	private int mineLeft = MINE; // ���� ���� ����(���� - ���)
	
	ImageIcon mineIcon = new ImageIcon("images/mine.png");
	ImageIcon init = new ImageIcon("images/init.png");
	ImageIcon flag = new ImageIcon("images/flag.png");
	ImageIcon flagx = new ImageIcon("images/flagx.png");
	ImageIcon icon0 = new ImageIcon("images/mine0.png");
	ImageIcon icon1 = new ImageIcon("images/mine1.png");
	ImageIcon icon2 = new ImageIcon("images/mine2.png");
	ImageIcon icon3 = new ImageIcon("images/mine3.png");
	ImageIcon icon4 = new ImageIcon("images/mine4.png");
	ImageIcon icon5 = new ImageIcon("images/mine5.png");
	ImageIcon icon6 = new ImageIcon("images/mine6.png");
	ImageIcon icon7 = new ImageIcon("images/mine7.png");
	ImageIcon icon8 = new ImageIcon("images/mine8.png");
	
	
	int [][] plate = new int[ROWS][COLS]; // ��ǥ�� ������ ���� �迭
	JButton [][] btnPlate = new JButton[ROWS][COLS]; // ���� ������ ��ư
	JLabel numOfMine = new JLabel(mineLeft+" Mines left."); // ���� ������ ����(�� ���� - ��� ��)
	
	public void setInfoScreen() { // �� ������ ������ �ʱ�ȭ ��ư
		infoScreen.setLayout(new FlowLayout(FlowLayout.CENTER,25, 0));
		numOfMine.setFont(new Font("Arial", Font.BOLD, 25));
		
		JButton reset = new JButton(init); //�ʱ�ȭ ����
		reset.setPreferredSize(new Dimension(60, 60));
		
		reset.addActionListener(new ActionListener() { // �ʱ�ȭ �̺�Ʈ
			public void actionPerformed(ActionEvent arg0) {
				dispose(); // ���� â �ݱ�
				new Minesweeper(); // ���ο� â ����
			}
		});
		
		infoScreen.add(reset);
		infoScreen.add(numOfMine);
	}
	
	
	public void setMinePlate() { //������ �ʱ�ȭ
		for(int i, j, count = 0; count < MINE;) {
			i=rad.nextInt(ROWS);
			j=rad.nextInt(COLS);

			if(plate[i][j] != -1) {
				plate[i][j] = -1;
				count++;
			}
		}// ���� ���� �߻�
		
		for(int i=0; i<ROWS; i++) { // �ֺ� ���� ������ �ִ� �ݺ���
			for(int j=0; j<COLS; j++) {
				int count=0; //���� ���� ����
				
				if(plate[i][j] != -1) { // ���ڰ� �ƴ϶��
					if(i >= 0 && j>=0 && i<=ROWS && j<=COLS) {
						
						if(i-1 >= 0 && j-1 >= 0) { // ��ǥ�� �����Ѵٸ� 0,0�� �ּ��̱� ������
							if(plate[i-1][j-1] == -1) count++; // ���� ��
						}
						if(i-1 >= 0) { // 
							if(plate[i-1][j] == -1) count++; // ��
						}
						if(i-1 >= 0 && j+1 < COLS) { //������ ��
							if(plate[i-1][j+1] == -1) count++;
						}
						if(j-1 >= 0) {
							if(plate[i][j-1] == -1) count++; // ����
						}
						if(j+1 < COLS) {
							if(plate[i][j+1] == -1) count++; // ������
						}
						if(i+1 <ROWS && j-1 >= 0) { 
							if(plate[i+1][j-1] == -1) count++; // ���� �Ʒ�
						}
						if(i+1 < ROWS) { // �Ʒ�
							if(plate[i+1][j] == -1) count++;
						}
						if(i+1 <ROWS && j+1 < COLS) {  //������ �Ʒ�
							if(plate[i+1][j+1] == -1) count++;
						}
					}
					plate[i][j] = count;
				}
				count = 0;
			}
		}
		
		gameScreen.setLayout(new GridLayout(ROWS, COLS)); 
		for(int i=0; i<ROWS; i++) { // ��ư ����
			for(int j=0; j<COLS; j++) {
				/* Ŭ��, ��� �̺�Ʈ �߰�, Ŭ��ImageIcon �ֱ�*/
				btnPlate[i][j] = new JButton();
				btnPlate[i][j].setBackground(Color.GRAY);
				btnPlate[i][j].addMouseListener(new clickListener());
				
				if(plate[i][j] == -1) {
					/* ���� �̺�Ʈ �߰� */
					btnPlate[i][j].addActionListener(new MineListener());
				}else{
					btnPlate[i][j].addActionListener(new ButtonListener());
				}
				gameScreen.add(btnPlate[i][j]);
			}
		}
	}
	
	
	
	
	private class MineListener implements ActionListener{ //���ڸ� Ŭ������ ��
		@Override
		public void actionPerformed(ActionEvent e) {
			if(ending == false) {
				JOptionPane.showMessageDialog(null, "���ڸ� Ŭ���߽��ϴ�. ���콺�� ���ƽ��ϴ�.");
			
				for(int i=0; i<ROWS; i++) { // ������ ���ڸ� ã�Ƽ� �����ֱ�
					for(int j=0; j<COLS; j++) {
						if(plate[i][j] == -1) {
							btnPlate[i][j].setBackground(Color.GRAY);
							btnPlate[i][j].setIcon(mineIcon);
							ending = true; //�ٸ� ��ư�� ������ �������� ���ϵ���
						}
						else if(plate[i][j] != -1 && btnPlate[i][j].getBackground() != Color.GRAY) { // ����� ���� ����
							btnPlate[i][j].setBorderPainted(false);
							btnPlate[i][j].setBackground(Color.GRAY);
							btnPlate[i][j].setIcon(flagx);
						}
						else {
							btnPlate[i][j].setBorderPainted(false);
							btnPlate[i][j].setBackground(Color.GRAY);
						}
					}
				} // for��
			}
		}
	}
	
	private class ButtonListener implements ActionListener{ // NO ���� �̺�Ʈ ����
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			for(int i=0; i<ROWS; i++) {
				for(int j=0; j<COLS; j++) {
					
					if(b.equals(btnPlate[i][j]) && btnPlate[i][j].getBackground() == Color.GRAY) { 
						// COLOR�� ��� �Ǵ�
						checkNum(i, j);
						break;
					}
				}
			}
		}
		
	}
	
	private class clickListener extends MouseAdapter { // ��Ŭ�� ��� ����
		@Override
		public void mousePressed(MouseEvent e) {
			
			if(e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) { //��Ŭ���̶�� 
				JButton b = (JButton)e.getSource();
				for(int i=0; i<ROWS; i++) {
					for(int j=0; j<COLS; j++){
						if(b == btnPlate[i][j] && btnPlate[i][j].isBorderPainted()==true && btnPlate[i][j].getBackground() == Color.GRAY ) {
							// ���� ����� �� �����־��ٸ�
							b.setIcon(flag);
							b.setBackground(Color.DARK_GRAY);//���
							b.setEnabled(false);
							mineLeft--;
						}else if(b == btnPlate[i][j] && btnPlate[i][j].isBorderPainted()==true && btnPlate[i][j].getBackground() == Color.DARK_GRAY){
							b.setIcon(null);
							b.setBackground(Color.GRAY);
							b.setEnabled(true);
							mineLeft++;
						}
					}
				}
				numOfMine.setText(mineLeft + " Mines left.");
			}
		}
	}
	

	
	public void checkNum(int i, int j) {
		try { // indexOutOfRangeException ����
			if(plate[i][j] == 0 && btnPlate[i][j].isBorderPainted() == true) {
				//	0						�⺻����
				btnPlate[i][j].setIcon(icon0);
				btnPlate[i][j].setBorderPainted(false);
				btnPlate[i][j].setContentAreaFilled(false);
				btnPlate[i][j].setFocusPainted(false);
				
				checkNum(i-1, j-1); //0�̶�� �ֺ��� ��ư ����
				checkNum(i-1, j);
				checkNum(i-1, j+1);
				checkNum(i, j-1);
				checkNum(i, j+1);
				checkNum(i+1, j-1);
				checkNum(i+1, j);
				checkNum(i+1, j+1);
			} else {
				if(btnPlate[i][j].isBorderPainted() == true) {
					switch(plate[i][j]) {
					case 1: 
						btnPlate[i][j].setIcon(icon1);
						break;
					case 2: 
						btnPlate[i][j].setIcon(icon2);
						break;
					case 3: 
						btnPlate[i][j].setIcon(icon3);
						break;
					case 4: 
						btnPlate[i][j].setIcon(icon4);
						break;
					case 5: 
						btnPlate[i][j].setIcon(icon5);
						break;
					case 6: 
						btnPlate[i][j].setIcon(icon6);
						break;
					case 7: 
						btnPlate[i][j].setIcon(icon7);
						break;
					case 8: 
						btnPlate[i][j].setIcon(icon8);
						break;
					}
					btnPlate[i][j].setBorderPainted(false);
					btnPlate[i][j].setContentAreaFilled(false);
					btnPlate[i][j].setFocusPainted(false);
				}
				
			}
			victory();
			
		}catch(Exception e) {}
	}
	
	private void victory() { // �¸� �Ǵ�
		int count = 0;
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLS; j++) {
				if(plate[i][j] != -1 && btnPlate[i][j].isBorderPainted() == false) {
					//���ڰ� �ƴϸ�					Ŭ���� �͸� ī��Ʈ
					count++;
				}
			}
		}
		if(count >= ROWS * COLS - MINE) { // Ŭ���� ���� �� ��ư���� ���ڸ� ������
			/* ��, ���ڸ� ������ ��ư�� �� �����ٸ� */
			for(int i=0; i<ROWS; i++) {
				for(int j=0; j<COLS; j++) {
					if(btnPlate[i][j].isBorderPainted() == true) { //Ŭ�� ���� ���� ��
						btnPlate[i][j].setBorderPainted(false);
						
						if(plate[i][j] == -1) { // ���ڶ��
							btnPlate[i][j].setIcon(mineIcon);
						}
					}
				}
			}
			if(ending == false) {
				JOptionPane.showMessageDialog(null, "�¸�!");
				ending = true;
			}
		}
	}
	
	public Minesweeper() {
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		ending = false;
		
		c.setLayout(new BorderLayout(0, 20));
		this.setInfoScreen(); //����� �� �����
		this.setMinePlate(); // ������ ó�� ����
		
		c.add(infoScreen, BorderLayout.NORTH);
		c.add(gameScreen, BorderLayout.CENTER);
		
		setSize(800,900);
		setVisible(true);
	}
	
	
	//���� �޼���
	public static void main(String [] args) {
		new Minesweeper();
	}

}