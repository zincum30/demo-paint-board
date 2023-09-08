package drawing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Paint extends JFrame {
	
	JPanel gui_panel, paint_panel;
	JButton pencil_bt, eraser_bt;
	JButton colorSelect_bt;
	JLabel thicknessInfo_label;
	JTextField thicknessControl_tf;
	
	Color selectedColor;
	Graphics graphics;
	Graphics2D g;
	
	int thickness = 10;
	int startX; // 마우스클릭시작의 X좌표값이 저장될 변수
	int startY; // 마우스클릭시작의 Y좌표값이 저장될 변수
	int endX;
	int endY;
	
	boolean tf = false;
	
	public Paint() { // Paint Class의 default 생성자
		
		setLayout(null);
		setTitle("그림판");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		gui_panel = new JPanel();
		gui_panel.setBackground(Color.gray);
		gui_panel.setLayout(null); // null: 컴포넌트 위치 직접 지정
		
		pencil_bt = new JButton("연필");
		pencil_bt.setFont(new Font("함초롱돋움", Font.BOLD, 20));
		pencil_bt.setBackground(Color.LIGHT_GRAY);
		eraser_bt = new JButton("지우개");
		eraser_bt.setFont(new Font("함초롱돋움", Font.BOLD, 20));		
		eraser_bt.setBackground(Color.WHITE);
	        
		colorSelect_bt = new JButton("선색상"); 
	    colorSelect_bt.setBackground(Color.PINK);
	    
	    thicknessInfo_label = new JLabel("도구굵기"); 
        thicknessInfo_label.setFont(new Font("함초롬돋움", Font.BOLD, 20));
		
		thicknessControl_tf = new JTextField("10", 5);
		thicknessControl_tf.setHorizontalAlignment(JTextField.CENTER);
		thicknessControl_tf.setFont(new Font("궁서체", Font.PLAIN, 25));
		
		pencil_bt.setBounds(10, 10, 90, 55);
		eraser_bt.setBounds(105,10,109,55); 
        colorSelect_bt.setBounds(785,10,90,55); 
        thicknessInfo_label.setBounds(640,10,100,55); 
        thicknessControl_tf.setBounds(720,22,50,35);
        
        gui_panel.add(pencil_bt); 
        gui_panel.add(eraser_bt); 
        gui_panel.add(colorSelect_bt); 
        gui_panel.add(thicknessInfo_label); 
        gui_panel.add(thicknessControl_tf); 
        
        gui_panel.setBounds(0,0,900,75); 
        
        paint_panel = new JPanel();
        paint_panel.setBackground(Color.WHITE);
        paint_panel.setLayout(null); 
        
        paint_panel.setBounds(0,90,885,620);
        
        add(gui_panel); 
        add(paint_panel);
        
        setVisible(true);
        
        graphics = getGraphics();
        g = (Graphics2D)graphics;
        g.setColor(selectedColor);
        
        paint_panel.addMouseListener(new MouseListener() {
        	public void mousePressed(MouseEvent e) {
        		startX = e.getX();
        		startY = e.getY();      		
        	}
			
			public void mouseClicked(MouseEvent e) {}		
			public void mouseReleased(MouseEvent e) {} 		
			public void mouseEntered(MouseEvent e) {}	// paint_panel 범위 내에 진입 시 이벤트 처리		
			public void mouseExited(MouseEvent e) {}
        });
        
        paint_panel.addMouseMotionListener(new PaintDraw());
        pencil_bt.addActionListener(new ToolActionListener());
        eraser_bt.addActionListener(new ToolActionListener());
        colorSelect_bt.addActionListener(new ActionListener() { // 선색상버튼 액션처리를 익명 클래스로 작성
        	public void actionPerformed(ActionEvent e) {
        		tf = true;
        		JColorChooser chooser = new JColorChooser();
        		selectedColor = chooser.showDialog(null, "Color", Color.ORANGE);
        		g.setColor(selectedColor);
        	}
        });
        
	}
	
	public class PaintDraw implements MouseMotionListener {
		
		@Override
		public void mouseDragged(MouseEvent e) {
			thickness = Integer.parseInt(thicknessControl_tf.getText());				
				endX = e.getX();
				endY = e.getY();
				
				g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND,0)); // 선굵기
				g.drawLine(startX+10, startY+121, endX+10, endY+121);
				
				startX=endX;
				startY=endY;					
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {}
				
	}
	
	public class ToolActionListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if(e.getSource()==pencil_bt) {
				if(tf==false) g.setColor(Color.BLACK);
				else g.setColor(selectedColor);
			} else if (e.getSource() == eraser_bt) {
				g.setColor(Color.WHITE);
			}
		}
	}
	
	
	public static void main(String[] args) {
		new Paint();
	}
}
