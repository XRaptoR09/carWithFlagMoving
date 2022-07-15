package carWithFlagMoving;

import java.awt.*;
import java.awt.event.*; //or: import java.awt.event.WindowAdapter;&import java.awt.event.WindowEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class CarPainter extends Frame{

	public static void main(String[] args) throws Exception {
		CarPainter CarPainter = new CarPainter("Moving auto!");
		CarPainter.setSize(frameWidth, frameHeight);
		CarPainter.setVisible(true);
		CarPainter.go();
	}

	CarPainter(String title) {
		super(title);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});
	}
	double animationShift = 0;
	
	// Vars for declaring
	static int frameWidth = 1200;
	static int frameHeight = 1000;
	int shiftFromFrame = 30, shiftBetweenCars = 50;
	double angle = 0;
	double angleRotate = 0.01;
	int sleep = 1;

	double speed;
	
	
	int leftX = 20;
//початок координат знаходиться поза фреймом, координати початку фрейму: (8;30)
	int flagY = 141, flagWidth = 45, flagHeight = 27, amountOfColors;
	
	int amountOfCars = 4;
	
	
	int flagstockWidth = 3, flagstockHeight = 40;
	
	int cabinWidth = 100, cabinHeight = 20;
	Color cabinColor = (new Color(0x9DDCF7));
	
	int bodyHeight = 30;
	//!Enter bodycolors in states' order
	Color[] bodyColors = {(new Color(0x009000)), (new Color(0x008000)), (new Color(0x2E8B57)), (new Color(0x6B9E23))};
	
	int wheelPadding = 20, wheelDiameter = 30;
	
	String[] states = {"Ukraine", "Germany", "France", "Belgium"};
	
	Color[] ukraine = {Color.blue, Color.yellow};
	Color[] germany = {Color.black, Color.red, Color.yellow};
	Color[] france = {Color.blue.darker(), Color.white, Color.red};
	Color[] belgium = {Color.blue.darker(), Color.yellow, Color.red};
	
	boolean[] isVerticalFlag = {false, false, true, true};
	Color[][] flagsColors= {ukraine, germany, france, belgium};
	//*Counting variables
	Color normal = Color.black;
	int cabinY = flagY + flagstockHeight,
	
	flagstockX = leftX + flagWidth, 
	
	bodyY = cabinY + cabinHeight, bodyWidth = (cabinWidth / 2) * 3,
	
	
	leftWheelX = leftX + wheelPadding,
	rightWheelX = ((leftX + bodyWidth) - wheelPadding) - wheelDiameter,
	wheelY = bodyY + bodyHeight,
	
	leftWheelCenterX = leftWheelX + (wheelDiameter / 2),
	rightWheelCenterX = rightWheelX + (wheelDiameter / 2),
	wheelCenterY = wheelY + (wheelDiameter / 2),
	
	horLeftLineX1 = leftWheelX,
	horLeftLineX2 = horLeftLineX1 + wheelDiameter,
	
	horLinesY = wheelY + (wheelDiameter / 2),
	
	horRightLineX1 = rightWheelX,
	horRightLineX2 = horRightLineX1 + wheelDiameter,
	
	vertLinesY1 = wheelY,
	vertLinesY2 = vertLinesY1 + wheelDiameter,
	
	vertLeftLineX = leftWheelX + (wheelDiameter / 2),
	vertRightLineX = rightWheelX + (wheelDiameter / 2);

	Color wheelColor = Color.black;
	Color lineColor = Color.white;
	
	int carHeight = (flagstockHeight + cabinHeight + bodyHeight + wheelDiameter),
	
	colorWidth, colorHeight;
	
	int fontSize = 15,
	fontY = bodyY + fontSize,
	fontX = leftX + (fontSize / 3) * 10;
	Color fontColor = (Color.WHITE);

	public void update(Graphics g) {
//		 public void paint(Graphics g) {
		int w = getSize().width, h = getSize().height;
		BufferedImage bi = (BufferedImage) createImage(w, h);
		Graphics2D big = bi.createGraphics(); // 3
		//!Amount of graphics should == flagsColors.length!
		Graphics2D[] wheelLeftGraph = {bi.createGraphics(), bi.createGraphics(), bi.createGraphics(), bi.createGraphics()}; // 3
		Graphics2D[] wheelRightGraph = {bi.createGraphics(), bi.createGraphics(), bi.createGraphics(), bi.createGraphics()}; // 3
	
		// Graphics2D wheelLeft2 = bi.createGraphics(); // 3
		// Graphics2D wheelRight2 = bi.createGraphics(); // 3

		//! //////////////////
		for(int q = 0; q < flagsColors.length; q++) {
			
			for(int d = 0; d < flagsColors[q].length; d++) {
				if (isVerticalFlag[q]) {
					colorWidth = flagWidth / flagsColors[q].length;
					big.setColor(flagsColors[q][d]);
					Rectangle2D.Double colorVert = new Rectangle2D.Double (leftX + colorWidth * d + animationShift, flagY + (carHeight + shiftBetweenCars) * q, colorWidth, flagHeight);
					big.fill(colorVert);
				} else {
					colorHeight = flagHeight / flagsColors[q].length;
					big.setColor(flagsColors[q][d]);
					Rectangle2D.Double colorHor = new Rectangle2D.Double (leftX + animationShift, flagY + colorHeight * d + (carHeight + shiftBetweenCars) * q, flagWidth, colorHeight);
					big.fill(colorHor);
				}
			}
			Rectangle2D.Double cabin = new Rectangle2D.Double (leftX + animationShift, cabinY + (carHeight + shiftBetweenCars) * q, cabinWidth, cabinHeight);
			Rectangle2D.Double body = new Rectangle2D.Double (leftX + animationShift, bodyY + (carHeight + shiftBetweenCars) * q, bodyWidth, bodyHeight);
			Rectangle2D.Double flagstock = new Rectangle2D.Double (flagstockX + animationShift, flagY + (carHeight + shiftBetweenCars) * q, flagstockWidth, flagstockHeight);
			Rectangle2D.Double flag = new Rectangle2D.Double (leftX + animationShift, flagY + (carHeight + shiftBetweenCars) * q, flagWidth, flagHeight);
			
			GeneralPath carWithoutWheels = new GeneralPath(body); 
			carWithoutWheels.append(cabin, false);
			carWithoutWheels.append(flagstock, false);
			carWithoutWheels.append(flag, false);
			big.setColor(cabinColor);
			big.fill(cabin);
			big.setColor(bodyColors[q]);
			big.fill(body);
			big.setColor(normal);
			big.draw(carWithoutWheels);
			
			//!wheel1
			Ellipse2D.Double leftWheel = new Ellipse2D.Double(leftWheelX + animationShift, wheelY + (carHeight + shiftBetweenCars) * q, wheelDiameter, wheelDiameter);
			Line2D.Double horLeftLine = new Line2D.Double(horLeftLineX1 + animationShift, horLinesY + (carHeight + shiftBetweenCars) * q, horLeftLineX2 + animationShift, horLinesY + (carHeight + shiftBetweenCars) * q);
			Line2D.Double vertLeftLine = new Line2D.Double(vertLeftLineX + animationShift, vertLinesY1 + (carHeight + shiftBetweenCars) * q, vertLeftLineX + animationShift, vertLinesY2 + (carHeight + shiftBetweenCars) * q);

			GeneralPath leftLines = new GeneralPath(horLeftLine);
			leftLines.append(vertLeftLine, false);

			wheelLeftGraph[q].rotate(angle, leftWheelCenterX + animationShift, wheelCenterY + (carHeight + shiftBetweenCars) * q);
			wheelLeftGraph[q].setColor(wheelColor);
			wheelLeftGraph[q].fill(leftWheel);
			wheelLeftGraph[q].setColor(lineColor);
			wheelLeftGraph[q].draw(leftLines);;
			wheelLeftGraph[q].setColor(wheelColor);
			wheelLeftGraph[q].draw(leftWheel);
	
			//!wheel2
			Ellipse2D.Double rightWheel = new Ellipse2D.Double(rightWheelX + animationShift, wheelY + (carHeight + shiftBetweenCars) * q, wheelDiameter, wheelDiameter);
			Line2D.Double horRightLine = new Line2D.Double(horRightLineX1 + animationShift, horLinesY + (carHeight + shiftBetweenCars) * q, horRightLineX2 + animationShift, horLinesY + (carHeight + shiftBetweenCars) * q);
			Line2D.Double vertRightLine = new Line2D.Double(vertRightLineX + animationShift, vertLinesY1 + (carHeight + shiftBetweenCars) * q, vertRightLineX + animationShift, vertLinesY2 + (carHeight + shiftBetweenCars) * q);
			
			GeneralPath rightLines = new GeneralPath(horRightLine);
			rightLines.append(vertRightLine, false);

			wheelRightGraph[q].rotate(angle, rightWheelCenterX + animationShift, wheelCenterY + (carHeight + shiftBetweenCars) * q);
			wheelRightGraph[q].setColor(wheelColor);
			wheelRightGraph[q].fill(rightWheel);
			wheelRightGraph[q].setColor(lineColor);
			wheelRightGraph[q].draw(rightLines);
			wheelRightGraph[q].setColor(wheelColor);
			wheelRightGraph[q].draw(rightWheel);
			
			g.setFont(new Font("Arial", Font.BOLD, fontSize));
			big.setColor(fontColor);
			big.drawString(states[q].toUpperCase(), (int)fontX + (int)animationShift, (int)fontY + (int)((carHeight + shiftBetweenCars) * q));
		}
		
		//! //////////////////////////////////////
		g.drawImage(bi, 0, 0, this);
	}
	public void go() throws Exception{
		
		while((leftX + bodyWidth + animationShift) < frameWidth - shiftFromFrame) {
			repaint();
			Thread.sleep(sleep);
			animationShift += 0.25;
			angle += angleRotate;
		}
	}
}

