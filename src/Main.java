import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame{
	public Main() {
		setTitle("Two Board Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //�⺻ �ʱ�ȭ
		Container c = getContentPane(); 
		c.setLayout(new FlowLayout()); //��ġ ������ ����
		c.setBackground(Color.ORANGE); //���� ����
		
		JLabel title = new JLabel("Two Board Game"); //����
		title.setFont(new Font("Arial",Font.BOLD,50));
		
		JButton firstGame = new JButton(" Sea Battle "); // ��Ʋ ��
		JButton secondGame = new JButton(" Minesweeper "); // ���� ã��
		
		firstGame.setFont(new Font("Arial",Font.PLAIN,40));
		secondGame.setFont(new Font("Arial",Font.PLAIN,40));
		firstGame.addActionListener(new ActionListener() { // �̺�Ʈ �߰�
			public void actionPerformed(ActionEvent e) {
				/* Sea Battle ������ ��� */
				SeaThread st = new SeaThread();
				st.start();
			}
		});
		secondGame.addActionListener(new ActionListener() { // �̺�Ʈ �߰�
			public void actionPerformed(ActionEvent e) {
				/* ���� ã�� ������ ��� */
				new Minesweeper(); // �� â���� ����
			}
		});
		c.add(title);
		c.add(firstGame);
		c.add(secondGame); //��ҵ� �ֱ�
		
		setSize(500, 300);
		setVisible(true);
	}
	
	private void startingSeaBattle() {
		new GameLogic().setUpWindow();
	}
	
	public static void main(String[] args) {
		new Main();

	}

}
