package Assignment_2;

/* Implementation of an XO Piece */

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;

public class XOPiece extends Group{

	/* left...top... to store XOpieces around this piece */
	private XOPiece left, leftTop, top, rightTop, right, rightBottom, bottom, leftBottom;
	private Line l1, l2; // Lines for drawing the x piece.
	private Ellipse e; // Ellipse for rendering the O piece.
	private int type; // Maintain a copy of the type we have.
	private Translate pos; // Translate that set the position of this piece.
	
	public XOPiece(int type) {
		pos = new Translate();
		this.type = type;
		
		/* default value for pieces around this piece */
		left = null; leftTop = null; 
		top = null; rightTop = null; 
		right = null; rightBottom = null;
		bottom = null; leftBottom = null;
		
		if (type == 1) { // XPiece
			/* 
			 * We have an X piece generate two lines and add them to
			 * as render nodes add in the translate for our lines.
			 */
			l1 = new Line();
			l2 = new Line();
			this.getChildren().addAll(l1, l2);
			l1.getTransforms().add(pos);
			l2.getTransforms().add(pos);
			l1.setStroke(Color.AQUAMARINE);
			l2.setStroke(Color.AQUAMARINE);
			
			/* As l1 starts top left startX, startY will always be zero */
			/* As l2 starts top right startY, endX will always be zero */
			l1.setStartX(0);
			l1.setStartY(0);
			l2.setStartY(0);
			l2.setEndX(0);
			
		} else { // OPiece
			e = new Ellipse();
			this.getChildren().addAll(e);
			e.getTransforms().add(pos);
			e.setStroke(Color.BURLYWOOD);
			e.setFill(null);
			
		}
	}
	
	/* return list of attributes from this piece */
	public int getType() { return type; }
	public XOPiece left() { return left; }
	public XOPiece leftTop() { return leftTop ;}
	public XOPiece top() { return top; }
	public XOPiece rightTop() { return rightTop; }
	public XOPiece right() { return right; }
	public XOPiece rightBottom() { return rightBottom; }
	public XOPiece bottom() { return bottom; }
	public XOPiece leftBottom() { return leftBottom; }
	public XOPiece left2() { return this.left.left; }
	
	/* Methods to set value for piece of position */
	public void setLeft(XOPiece l) { left = l; }
	public void setLeftTop(XOPiece lt) { leftTop = lt; }
	public void setTop(XOPiece t) { top = t; }
	public void setRightTop(XOPiece rt) { rightTop = rt; }
	public void setRight(XOPiece r) { right = r; }
	public void setRightBottom(XOPiece rb) { rightBottom = rb; }
	public void setBottom(XOPiece b) { bottom = b; }
	public void setLeftBottom(XOPiece lb) { leftBottom = lb; }
	
	@Override
	public void resize(double width, double height) {
		super.resize(width, height);
		
		if (type == 1) {
			l1.setEndX(width);
			l1.setEndY(height);
			l2.setStartX(width);
			l2.setEndY(height);
		
		} else {
			e.setCenterX(width / 2);
			e.setCenterY(height / 2);
			e.setRadiusX(width / 2);
			e.setRadiusY(height / 2);
			
		}
	}
	
	@Override
	public void relocate(double x, double y) {
		super.relocate(x, y);
		pos.setX(x);
		pos.setY(y);
	}
	
}
