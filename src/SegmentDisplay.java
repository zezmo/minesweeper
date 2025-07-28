/**
 *
 * @author kiunwong
 * https://kiunwong.blogspot.com/2012/09/seven-segments-display-in-java.html
 * 
 * note: changed slightly from original.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;

public class SegmentDisplay extends JPanel {

    private Polygon[] segments;
    private boolean[] number;
    private int x;
    private int y;

    private final static int NUM_SEGMENTS = 7;
    private final static int PANEL_WIDTH = 31;
    private final static int PANEL_HEIGHT = 55;

    private final static Color offColor = Color.CYAN.darker().darker().darker().darker();
    private final static Color onColor = Color.CYAN.brighter();

    private final static boolean zero[] = {true, true, true, true, true, true, false};
    private final static boolean one[] = {false, true, true, false, false, false, false};
    private final static boolean two[] = {true, true, false, true, true, false, true};
    private final static boolean three[] = {true, true, true, true, false, false, true};
    private final static boolean four[] = {false, true, true, false, false, true, true};
    private final static boolean five[] = {true, false, true, true, false, true, true};
    private final static boolean six[] = {true, false, true, true, true, true, true};
    private final static boolean seven[] = {true, true, true, false, false, false, false};
    private final static boolean eight[] = {true, true, true, true, true, true, true};
    private final static boolean nine[] = {true, true, true, true, false, true, true};

    public SegmentDisplay() {
        Dimension panelSize = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
        setPreferredSize(panelSize);
        setBackground(Color.BLACK);

        x = 0;
        y = 0;

        number=zero;
        createSegments();
        number = zero;

    }

    public void writeNumber(int n) {
        switch (n) {
            case 0:
                number = zero;
                break;
            case 1:
                number = one;
                break;
            case 2:
                number = two;
                break;

            case 3:
                number = three;
                break;
            case 4:
                number = four;
                break;
            case 5:
                number = five;
                break;
            case 6:
                number = six;
                break;
            case 7:
                number = seven;
                break;
            case 8:
                number = eight;
                break;
            case 9:
                number = nine;
                break;
            default:
                break;
        }
    }

    public void doCountUp() {
        int i=0;

        do {
            writeNumber(i);
            repaint();

            i++;

            try {
                Thread.sleep(300);
            } catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
            }
        } while (i !=10);
    }

    private void createSegments() {
        segments = new Polygon[NUM_SEGMENTS];

        segments[0] = new Polygon();
        segments[0].addPoint(x+9, y+2);
        segments[0].addPoint(x+21, y+2);
        segments[0].addPoint(x+24, y+5);
        segments[0].addPoint(x+21, y+8);
        segments[0].addPoint(x+9, y+8);
        segments[0].addPoint(x+6, y+5);

        segments[1] = new Polygon();
        segments[1].addPoint(x+29, y+10);
        segments[1].addPoint(x+29, y+22);
        segments[1].addPoint(x+26, y+25);
        segments[1].addPoint(x+23, y+22);
        segments[1].addPoint(x+23, y+10);
        segments[1].addPoint(x+26, y+7);

        segments[2] = new Polygon();
        segments[2].addPoint(x+29, y+32);
        segments[2].addPoint(x+29, y+44);
        segments[2].addPoint(x+26, y+47);
        segments[2].addPoint(x+23, y+44);
        segments[2].addPoint(x+23, y+32);
        segments[2].addPoint(x+26, y+29);

        segments[3] = new Polygon();
        segments[3].addPoint(x+9, y+46);
        segments[3].addPoint(x+21, y+46);
        segments[3].addPoint(x+24, y+49);
        segments[3].addPoint(x+21, y+52);
        segments[3].addPoint(x+9, y+52);
        segments[3].addPoint(x+6, y+49);

        segments[4] = new Polygon();
        segments[4].addPoint(x+7, y+32);
        segments[4].addPoint(x+7, y+44);
        segments[4].addPoint(x+4, y+47);
        segments[4].addPoint(x+1, y+44);
        segments[4].addPoint(x+1, y+32);
        segments[4].addPoint(x+4, y+29);

        segments[5] = new Polygon();
        segments[5].addPoint(x+7, y+10);
        segments[5].addPoint(x+7, y+22);
        segments[5].addPoint(x+4, y+25);
        segments[5].addPoint(x+1, y+22);
        segments[5].addPoint(x+1, y+10);
        segments[5].addPoint(x+4, y+7);

        segments[6] = new Polygon();
        segments[6].addPoint(x+9, y+24);
        segments[6].addPoint(x+21, y+24);
        segments[6].addPoint(x+24, y+27);
        segments[6].addPoint(x+21, y+30);
        segments[6].addPoint(x+9, y+30);
        segments[6].addPoint(x+6, y+27);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i=0; i<NUM_SEGMENTS; i++) {
            setSegmentState(g, segments[i], number[i]);
        }


    }



    public void setSegmentState(Graphics graphics, Polygon segment, boolean active) {
        if (!active) {
            graphics.setColor(offColor);
        } else {
            graphics.setColor(onColor);
        }
        graphics.fillPolygon(segment);
        graphics.drawPolygon(segment);
    }

    public boolean[] getSegZero() {
        return zero;
    }
    public boolean[] getSegOne() {
        return one;
    }
    public boolean[] getSegTwo() {
        return two;
    }
    public boolean[] getSegThree() {
        return three;
    }
    public boolean[] getSegFour() {
        return four;
    }
    public boolean[] getSegFive() {
        return five;
    }
    public boolean[] getSegSix() {
        return six;
    }
    public boolean[] getSegSeven() {
        return seven;
    }
    public boolean[] getSegEight() {
        return eight;
    }
    public boolean[] getSegNine() {
        return nine;
    }
}
