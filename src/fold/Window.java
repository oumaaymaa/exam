package fold;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import helpers.CircleButton;
import helpers.Helper;
import helpers.CButton;

public class Window extends JFrame {
	
	private static final String[] ops = {"+","-","*","/"};
	private static final String tester = "0123456789+-*/().";
	private static final int w = 320, h = 620;
	private boolean held = false;

	private JButton[] numbers = new JButton[9];
	private JButton zero = new JButton("0");
	private JButton point = new JButton("."); 
	private JButton equal = new JButton("="); 
	private JButton[] operations = new JButton[4];
	private JButton C = new JButton("C");
	private JButton CA = new JButton("CA");
	private JButton LP = new JButton("(");
	private JButton RP = new JButton(")");
	private CircleButton close = new CircleButton("×");
	private JLabel title = new JLabel("Calculator");
	private JPanel[] rows = new JPanel[7];
	private JTextField resultField = new JTextField() {
		@Override public void setBorder(Border border) {
	        // No!
	    }
	};
 	private	Runnable restrict;
	
 	private int pmx = 0;
 	private int pmy = 0;
 	
 	private int fx = 0;
 	private int fy = 0;
 	
 	private Graphics graphics;
 	
	public Window() throws FontFormatException, IOException {
		
		restrict = new Runnable() {
	        @Override
	        public void run() {
				String txt = resultField.getText();
				String tmp = "";
				for(int i=0;i<txt.length();i++) {
					if(tester.contains(txt.charAt(i)+"")) {
						tmp += txt.charAt(i);
					}
				}
				resultField.setText(tmp);
	        }
	    };
		
		equal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				try {
					resultField.setText(Calculator.eval(txt)+"");
				} catch(Exception ex) {
					resultField.setText("");
				}
			}
		});	
		
		centreWindow();
		JPanel main = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
		        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		        Color color1 = new Color(13,40,65);
		        Color color2 = new Color(10,20,45);
		        GradientPaint gp = new GradientPaint(0,0, color1, 0, h, color2);
		        g2d.setPaint(gp);
		        g2d.fillRect(0, 0, w, h);
		        setBackground(Color.BLACK);
			}
		};
		main.setSize(w, h);
		main.setLayout(new GridLayout(7,1));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setShape(new RoundRectangle2D.Double(0,0, w,h, 30, 30));
		setSize(w,h);


        
		resultField.getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
			  SwingUtilities.invokeLater(restrict);
		  }
		  public void removeUpdate(DocumentEvent e) {
		  }
		  public void insertUpdate(DocumentEvent e) {
			  SwingUtilities.invokeLater(restrict);
		  }
		});
		

		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("assets/Roboto-Thin.ttf");
		InputStream is2 = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("assets/Roboto-Bold.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, is);
		Font font2 = Font.createFont(Font.TRUETYPE_FONT, is2);
		Font sizedFont = font.deriveFont(28f);
		Font robotothin25 = font.deriveFont(25f);
		Font robotoBold35 = font2.deriveFont(35f);
		Font robotoBold23 = font2.deriveFont(16f);
		
		Consumer<JButton> setTransparentBg = (comp) -> { 
			comp.setOpaque(false);
			comp.setContentAreaFilled(false);
			comp.setBorderPainted(false);
		};
		
		C.setFont(sizedFont);
		CA.setFont(sizedFont);
		LP.setFont(sizedFont);
		RP.setFont(sizedFont);
		zero.setFont(sizedFont);
		point.setFont(sizedFont);
		equal.setFont(sizedFont);
		resultField.setFont(robotoBold35);
		title.setFont(robotoBold23);
		close.setFont(sizedFont);
		
		C.setForeground(new Color(230,140,45));
		CA.setForeground(new Color(140,230,45));
		LP.setForeground(new Color(10,170,255));
		RP.setForeground(new Color(10,170,255));
		zero.setForeground(new Color(10,170,255));
		point.setForeground(new Color(10,170,255));
		equal.setForeground(new Color(10,170,255));
		resultField.setForeground(Color.WHITE);
		resultField.setCaretColor(new Color(10,170,255));
		title.setForeground(Color.WHITE);
		//close.setForeground(Color.WHITE);
		
		setTransparentBg.accept(C);
		setTransparentBg.accept(CA);
		setTransparentBg.accept(LP);
		setTransparentBg.accept(RP);
		setTransparentBg.accept(zero);
		setTransparentBg.accept(point);
		setTransparentBg.accept(equal);
		setTransparentBg.accept(close);
		resultField.setOpaque(false);
		close.setColor(new Color(13,50,75))
		.setPressColor(new Color(13,65,85))
		.setFnt(robotothin25)
		.setFontColor(new Color(10,170,255));
		
		/*C.setColor(new Color(13,50,75))
		.setPressColor(new Color(13,65,85))
		.setFnt(sizedFont)
		.setFontColor(new Color(230,140,45));*/
		
		close.setSize(40, 40);
		// C.setSize(80, 40);

		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		C.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt.substring(0,txt.length()-1));
			}
		});
		CA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultField.setText("");
			}
		});
		LP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt + "(");
			}
		});
		RP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt + ")");
			}
		});
		zero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+0);
			}
		});
		point.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+".");
			}
		});
		
		for(int i=0;i<9;i++) {
			numbers[i] = new JButton(""+(i+1));
			numbers[i].setOpaque(false);
			numbers[i].setContentAreaFilled(false);
			numbers[i].setBorderPainted(false);
			numbers[i].setFont(sizedFont);
			numbers[i].setForeground(Color.WHITE);
		}
		
		//////////////// NUMBER ACTIONS //////////////////////////
		numbers[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+1);
			}
		});
		numbers[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+2);
			}
		});
		numbers[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+3);
			}
		});
		numbers[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+4);
			}
		});
		numbers[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+5);
			}
		});
		numbers[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+6);
			}
		});
		numbers[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+7);
			}
		});
		numbers[7].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+8);
			}
		});
		numbers[8].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+9);
			}
		});
		//////////////////////////////////////////////////////////
		
		for(int i=0;i<4;i++) {
			operations[i] = new JButton(ops[i]);
			operations[i].setOpaque(false);
			operations[i].setContentAreaFilled(false);
			operations[i].setBorderPainted(false);
			operations[i].setFont(sizedFont);
			operations[i].setForeground(new Color(10,170,255));
		}
		
		
		//////////////// OPERATOR ACTIONS ////////////////////////
		operations[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+ops[0]);
			}
		});
		operations[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+ops[1]);
			}
		});
		operations[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+ops[2]);
			}
		});
		operations[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = resultField.getText();
				resultField.setText(txt+ops[3]);
			}
		});
		//////////////////////////////////////////////////////////
		
		for(int i=0;i<7;i++) {
			if(i==2)
				rows[i] = new JPanel() {
					public void paintComponent(Graphics g) {
						g.setColor(new Color(10,170,255));
				        g.fillRect(0, 0, w, 1);
					}
				};
			else
				rows[i] = new JPanel();
			rows[i].setOpaque(false);
			rows[i].setBorder(new EmptyBorder(8,8,25,8));
		}

		JPanel redandent = new JPanel();
		
		
		rows[0].setLayout(new BorderLayout());
		rows[0].add(redandent,BorderLayout.WEST);
		redandent.setBorder(new EmptyBorder(0,w/6,0,w/6));
		redandent.setOpaque(false);
		rows[0].add(close,BorderLayout.EAST);
		rows[0].add(title,BorderLayout.CENTER);
		rows[0].addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				pmx = e.getX();
				pmy = e.getY();
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				fx = getLocation().x;
				fy = getLocation().y;
				int nx = Helper.iLerp(fx, fx+e.getX()-pmx, 0.1f);
				int ny = Helper.iLerp(fy, fy+e.getY()-pmy, 0.1f);
				setLocation(nx,ny);
			}
		});
		
		rows[1].setLayout(new BorderLayout());
		rows[1].add(resultField,BorderLayout.CENTER);
		
		rows[2].setLayout(new GridLayout(1,4));
		rows[2].add(C);
		rows[2].add(CA);
		rows[2].add(LP);
		rows[2].add(RP);
		
		for(int i=3;i<6;i++) {
			rows[i].setLayout(new GridLayout(1,4));
			rows[i].add(numbers[(5-i)*3]);
			rows[i].add(numbers[(5-i)*3+1]);
			rows[i].add(numbers[(5-i)*3+2]);
			rows[i].add(operations[(i-3)]);
		}
		
		rows[6].setLayout(new GridLayout(1,4));
		rows[6].add(zero);
		rows[6].add(point);
		rows[6].add(equal);
		rows[6].add(operations[3]);
		
		for(JPanel p:rows) {
			main.add(p);
		}
		add(main);
		setVisible(true);
		
	}
	public void centreWindow() {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    fx = (int) ((dimension.getWidth() - w) / 2);
	    fy = (int) ((dimension.getHeight() - h) / 2);
	    setLocation(fx, fy);
	}
	
}

