import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class DrawSpanner extends JFrame implements ActionListener,
		MouseListener {
	Vector<Ring> vectorRing = new Vector<Ring>();
	Vector<Ring> worstPair = new Vector<Ring>();
	// Vector<Ring> deleteRing = new Vector<Ring>();
	Vector<Line> vectorLine = new Vector<Line>();
	Dijkstra dij;
	int[][] arr;
	static final int WIDTH = 600;
	static final int HEIGHT = 400;
	float t = 1;
	JPopupMenu pop;

	JMenuItem item1;

	JMenuItem item2;

	JFrame f;
	JFrame frame;
	JPanel p;

	JToolBar bar;
	Canvas c;
	JMenuItem clear, item3;
	JButton button1, enter, cancel;
	JTextField jtf2;
	List<Entry<Entry<Integer, Integer>, Double>> dList;

	public DrawSpanner() {

		Toolkit kit = Toolkit.getDefaultToolkit();

		Dimension screenSize = kit.getScreenSize();
		f = new JFrame("Greedy Spanner");

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menubar1 = new JMenuBar();

		p = new JPanel();

		c = new Canvas();
		c.setBounds(100, 100, 600, 400);

		f.setContentPane(p);

		f.setJMenuBar(menubar1);

		JMenu menu1 = new JMenu("New");

		JMenu menu2 = new JMenu("Show");

		menubar1.add(menu1);

		menubar1.add(menu2);

		item1 = new JMenuItem("New");

		item2 = new JMenuItem("Show");

		clear = new JMenuItem("Clear");
		clear.addActionListener(button);

		JMenuItem item2 = new JMenuItem("Insert t");

		item2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// panel for t
				frame = new JFrame("Insert t");
				JPanel panel = new JPanel();
				frame.setContentPane(panel);
				int width = screenSize.width;

				int height = screenSize.height;

				JLabel jlb1 = new JLabel("insert t:");
				jtf2 = new JTextField(8);
				JTextField jtf3 = new JTextField("Note: t>=1", 8);

				jtf3.setEnabled(false);

				enter = new JButton("enter");
				enter.addActionListener(button);

				cancel = new JButton("cancel");
				cancel.addActionListener(button);
				panel.add(jlb1, BorderLayout.NORTH);
				panel.add(jtf2, BorderLayout.NORTH);
				panel.add(jtf3, BorderLayout.NORTH);
				panel.add(enter, BorderLayout.SOUTH);
				panel.add(cancel, BorderLayout.SOUTH);

				int x = width / 3;

				int y = height / 3;

				frame.setLocation(x, y);
				frame.setVisible(true);

				frame.setSize(WIDTH / 2, HEIGHT / 3);
			}
		});

		item3 = new JMenuItem("Show the worst pair");
		item3.addActionListener(button);

		menu1.add(clear);

		menu1.addSeparator();

		menu1.add(item2);

		menu2.add(item3);

		button1 = new JButton("Compute");
		button1.addActionListener(button);
		// JButton button2 = new JButton("Delete");
		// button2.addActionListener(button);

		bar = new JToolBar();

		bar.add(button1);

		// bar.add(button2);

		BorderLayout bord = new BorderLayout();

		p.setLayout(bord);

		p.add("North", bar);

		p.add(c);
		c.addMouseListener(this);

		f.setVisible(true);

		f.setSize(WIDTH, HEIGHT);

		int width = screenSize.width;

		int height = screenSize.height;

		int x = (width - WIDTH) / 2;

		int y = (height - HEIGHT) / 2;

		f.setLocation(x, y);

	}

	public static void main(String[] args)

	{

		new DrawSpanner();

	}

	ActionListener button = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == enter) {
				// enter
				if (jtf2.getText().isEmpty() == true
						|| !isNumeric(jtf2.getText())) {
					JOptionPane.showMessageDialog(null,
							"please enter number>=1 for t", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else if (Float.parseFloat(jtf2.getText()) < 1) {
					JOptionPane.showMessageDialog(null, "t should be >=1",
							"Error", JOptionPane.ERROR_MESSAGE);

				} else {
					t = Float.parseFloat(jtf2.getText());
					frame.dispose();
					System.out.println("t:" + t);
				}

			} else if (e.getSource() == cancel) {
				// cancel
				frame.dispose();
			} else if (e.getSource() == button1) {
				// compute
				System.out.println("compute");
				PointsByDistance fun = new PointsByDistance();
				dList = fun.DistanceList(vectorRing);
				System.out.println("hMap:" + dList.toString());
				// Dij
				dij = new Dijkstra(dList, t, vectorRing.size());
				arr = dij.getEdge();
				vectorLine.removeAllElements();
				for (int i = 0; i < vectorRing.size(); i++) {
					for (int j = 0; j < vectorRing.size(); j++)
						if (arr[i][j] == 1) {

							Ring startRing = vectorRing.get(i);
							Ring endRing = vectorRing.get(j);

							Line line = new Line(startRing.x0, startRing.y0,
									endRing.x0, endRing.y0);
							vectorLine.add(line);
						}

				}
				for (int i = 0; i < vectorLine.size(); i++) {
					vectorLine.get(i).draw(c, Color.black);
				}

			} else if (e.getSource() == clear) {
				// clear
				System.out.println("clear");

				c.getGraphics().clearRect(0, 0, f.getWidth(), f.getHeight());
				vectorRing.removeAllElements();
				vectorLine.removeAllElements();
			} else if (e.getSource() == item3) {
				if (vectorLine.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please Press Compute First!", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else {

					worstPair.removeAllElements();

					// show the worst pair
					System.out.println("show worst");
					if (t != 1) {

						int first = dij.getWorstPairFirst();
						int second = dij.getWorstPairSecond();
						System.out.println("first:" + first + " second:"
								+ second);

						int x1first = vectorRing.get(first).x0;
						int y1first = vectorRing.get(first).y0;
						Ring ringFirst = new Ring(x1first, y1first, 15, 15);
						worstPair.add(ringFirst);

						int x1second = vectorRing.get(second).x0;
						int y1second = vectorRing.get(second).y0;
						Ring ringSecond = new Ring(x1second, y1second, 15, 15);
						worstPair.add(ringSecond);

						worstPair.get(0).draw(c, Color.red);
						worstPair.get(1).draw(c, Color.red);
					} else {
						// worstPair.addAll(vectorRing);
						for (int temp = 0; temp < vectorRing.size(); temp++) {
							int x1 = vectorRing.get(temp).x0;
							int y1 = vectorRing.get(temp).y0;
							Ring ringSecond = new Ring(x1, y1, 15, 15);
							worstPair.add(ringSecond);
						}
						for (int num = 0; num < vectorRing.size(); num++) {
							worstPair.get(num).draw(c, Color.red);
						}
					}

				}
			}
		}
	};

	public void actionPerformed(ActionEvent e) {

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	int x, y;

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[1-9]+\\d*\\.*\\d*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public void paint(Graphics g) {

		// for (int i = 0; i < vectorRing.size(); i++) {
		//
		// vectorRing.get(i).draw(g);
		// }
		// super.paint (g);
		// g.drawOval(x, y, 40, 40);
		// c.getGraphics().drawOval(x, y, 40, 40);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		boolean flag = false;
		setX(x);
		setY(y);
		// c.getGraphics().drawOval(x, y, 20, 20);
		System.out.println("size:" + vectorRing.size() + "flag" + flag);
		if (vectorRing.size() == 0) {
			Ring ring = new Ring(x, y, 12, 12);
			vectorRing.add(ring);
			vectorRing.get(0).draw(c, Color.black);
			flag = false;
		} else {
			for (int i = 0; i < vectorRing.size(); i++) {
				System.out.println("y:" + y + "y1:" + vectorRing.get(i).y1);

				if ((vectorRing.get(i).x0 - x) * (vectorRing.get(i).x0 - x)
						+ (vectorRing.get(i).y0 - y)
						* (vectorRing.get(i).y0 - y) <= vectorRing.get(i).x2
						* vectorRing.get(i).x2 / 1.4) // select the circle
				{
					flag = true;
					System.out.println("existed!");
					// vectorRing.get(i).remove(c);

					c.getGraphics()
							.clearRect(0, 0, f.getWidth(), f.getHeight());
					vectorLine.removeAllElements();

					vectorRing.remove(i);
					System.out.println(vectorRing.size());
					for (int ring = 0; ring < vectorRing.size(); ring++) {
						vectorRing.get(ring).draw(c, Color.black);
						System.out.println(vectorRing.get(ring).x0);
					}

					break;
				}
			}
			if (flag == false) {
				Ring ring = new Ring(x, y, 12, 12);
				vectorRing.add(ring);
				for (int i = 0; i < vectorRing.size(); i++) {
					vectorRing.get(i).draw(c, Color.black);
				}

			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}