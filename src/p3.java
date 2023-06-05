import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.lang.Byte;
import java.lang.Math;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class p3 {

    //Origin for transformations is in the center of the screen as default

    public int datalines[][];
    int num;
    public static Scanner s;
    static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    JFrame jf;
    JLabel jl;
    public static final int max_width = d.width; //1440 pixels
    public static final int max_height = d.height - 120; //780 pixels
    public BufferedImage img;

    public static void main(String[] args) {
        p3 p = new p3();
        s = new Scanner(System.in);
        int choice = 0;
        int xVal;
        int yVal;
        double xStr;
        double yStr;
        double ang;
        while(true) {
            System.out.println("(0 for Inputting Lines)");
            System.out.println("(1 for Basic Translate)");
            System.out.println("(2 for Basic Scale)");
            System.out.println("(3 for Basic Rotate)");
            System.out.println("(4 for General Scale)");
            System.out.println("(5 for General Rotate)");
            System.out.println("(6 for Output to File)");
            System.out.println("(7 for Exit)");
            System.out.println("Choose Transformation: (Enter a number)");
            choice = s.nextInt();
            if(choice == 0) {
                p.inputLines();
            } else if(choice == 1) {
                System.out.println("You have chosen a Basic Translate operation.");
                System.out.println("How much to shift in x-direction (positive goes right)(int)?");
                try {
                    xVal = s.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter an int. Please press 1 again and enter an int.");
                    s.next();
                    continue;
                }
                System.out.println("How much to shift in y-direction (positive goes up)(int)?");
                try {
                    yVal = s.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter an int. Please press 1 again and enter an int.");
                    s.next();
                    continue;
                }
                p.applyTransformation(p.basicTranslate((double)xVal, (double)yVal));
            } else if(choice == 2) {
                System.out.println("You have chosen a Basic Scale operation.");
                System.out.println("How much to scale in x-direction (double)?");
                try {
                    xStr = s.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter a double. Please press 2 again and enter a double.");
                    s.next();
                    continue;
                }
                System.out.println("How much to scale in y-direction (double)?");
                try {
                    yStr = s.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter a double. Please press 2 again and enter a double.");
                    s.next();
                    continue;
                }
                p.applyTransformation(p.basicScale(xStr, yStr));
            } else if(choice == 3) {
                System.out.println("You have chosen a Basic Rotation operation.");
                System.out.println("How much would you like to rotate counterclockwise (double)?");
                try {
                    ang = s.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter a double. Please press 3 again and enter a double.");
                    s.next();
                    continue;
                }
                p.applyTransformation(p.basicRotate(ang));
            } else if(choice == 4) {
                System.out.println("You have chosen a General Scale operation.");
                System.out.println("Please enter x scale value and then y scale value separated by a space (xdouble ydouble).");
                try {
                    xStr = s.nextDouble();
                    yStr = s.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter a double. Please press 4 again and enter two doubles separated by a space (xdouble ydouble)");
                    s.next();
                    s.next();
                    continue;
                }
                System.out.println("Please enter coordinates for center of scale separated by a space (xint yint).");
                try {
                    xVal = s.nextInt();
                    yVal = s.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter an int. Please press 4 again and enter two ints separated by a space. (xint yint)");
                    s.next();
                    s.next();
                    continue;
                }
                p.applyTransformation(p.scale(xStr, yStr, (double)xVal, (double)yVal));
            } else if(choice == 5) {
                System.out.println("You have chosen a General Rotation operation.");
                System.out.println("Enter the center of rotation separated by a space (xint yint).");
                try {
                    xVal = s.nextInt();
                    yVal = s.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter an int. Please press 5 again and enter two ints separated by a space.");
                    s.next();
                    s.next();
                    continue;
                }
                System.out.println("Enter the angle of counterclockwise rotation (double).");
                try {
                    ang = s.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter a double. Please press 5 again and enter a double.");
                    s.next();
                    continue;
                }
                p.applyTransformation(p.rotate(ang, (double)xVal, (double)yVal));
            } else if(choice == 6) {
                p.outputLines();
            } else if(choice == 7) {
                break;
            } else {
                System.out.println("Invalid number. Please try again.");
            }
            p.displayLines();
        }
        s.close();
        System.exit(0);
    }

    public void inputLines() {
        System.out.println("Enter file name: (ex: file.txt)");
        this.num = 0;
        String name = s.next();
        File f = new File(name);
        Scanner scan = null;
        if(!f.exists()){
            System.out.println("The file " + name + " does not exist.\nPlease press 0 and try again.");
            return;
        }
        try{
            scan = new Scanner(f);
            while(scan.hasNext()) {
                scan.nextLine();
                this.num++;
            }
            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.datalines = new int[4][this.num];
        try{
            scan = new Scanner(f);
            int n = 0;
            while(n < num) {
                this.datalines[0][n] = scan.nextInt();
                this.datalines[1][n] = scan.nextInt();
                this.datalines[2][n] = scan.nextInt();
                this.datalines[3][n] = scan.nextInt();
                n++;
            }
            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void applyTransformation(double[][] matrix) {
        int n = 0;
        while(n < num) {
            double[] p1 = {(double)this.datalines[0][n] - 720, (double)this.datalines[1][n] - 390, 1.0};
            p1 = applyMatrix(p1, matrix);
            this.datalines[0][n] = (int)p1[0] + 720;
            this.datalines[1][n] = (int)p1[1] + 390;
            double[] p2 = {(double)this.datalines[2][n] - 720, (double)this.datalines[3][n] - 390, 1.0};
            p2 = applyMatrix(p2, matrix);
            this.datalines[2][n] = (int)p2[0] + 720;
            this.datalines[3][n] = (int)p2[1] + 390;
            n++;
        }
    }

    public void displayLines() {
        this.img = new BufferedImage(max_width, max_height, BufferedImage.TYPE_INT_RGB);
        int n = 0;
        int x0;
        int y0;
        int x1;
        int y1;
        x0 = this.datalines[0][0];
        y0 = this.datalines[1][0];
        x1 = this.datalines[2][0];
        y1 = this.datalines[3][0];
        while(n < num) {
            int point1 = 0b0000;
            int point2 = 0b0000;
            if(x0 < 0) {
                point1 = point1 + 1;
            }
            if(max_width <= x0) {
                point1 = point1 + 2;
            }
            if(max_height <= y0) {
                point1 = point1 + 4;
            }
            if(y0 < 0) {
                point1 = point1 + 8;
            }
            if(x1 < 0) {
                point2 = point2 + 1;
            }
            if(max_width <= x1) {
                point2 = point2 + 2;
            }
            if(max_height <= y1) {
                point2 = point2 + 4;
            }
            if(y1 < 0) {
                point2 = point2 + 8;
            }
            if((point1 | point2) == 0) {
                this.img = basicLine(this.img, x0, y0, x1, y1);
                n++;
                try{
                    x0 = this.datalines[0][n];
                    y0 = this.datalines[1][n];
                    x1 = this.datalines[2][n];
                    y1 = this.datalines[3][n];
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }
            } else if((point1 & point2) != 0) {
                n++;
                try{
                    x0 = this.datalines[0][n];
                    y0 = this.datalines[1][n];
                    x1 = this.datalines[2][n];
                    y1 = this.datalines[3][n];
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }
            } else {
                if(point1 != 0) {
                    if(x0 < 0) {
                        y0 = (int)((((double)(y1 - y0))/((double)(x1 - x0))) * (0 - x0)) + y0;
                        x0 = 0;
                    } else if(max_width <= x0) {
                        y0 = -(int)((((double)(y1 - y0))/((double)(x1 - x0))) * ((x0 - max_width) + 1)) + y0;
                        x0 = max_width - 1;
                    } else if(max_height <= y0) {
                        x0 = -(int)((((double)(x1 - x0))/((double)(y1 - y0))) * ((y0 - max_height) + 1)) + x0;
                        y0 = max_height - 1;
                    } else {
                        x0 = (int)((((double)(x1 - x0))/((double)(y1 - y0))) * (0 - y0)) + x0;
                        y0 = 0;
                    }
                } else {
                    if(x1 < 0) {
                        y1 = (int)((((double)(y0 - y1))/((double)(x0 - x1))) * (0 - x1)) + y1;
                        x1 = 0;
                    } else if(max_width <= x1) {
                        y1 = -(int)((((double)(y0 - y1))/((double)(x0 - x1))) * ((x1 - max_width) + 1)) + y1;
                        x1 = max_width - 1;
                    } else if(max_height <= y1) {
                        x1 = -(int)((((double)(x0 - x1))/((double)(y0 - y1))) * ((y1 - max_height) + 1)) + x1;
                        y1 = max_height - 1;
                    } else {
                        x1 = (int)((((double)(x0 - x1))/((double)(y0 - y1))) * (0 - y1)) + x1;
                        y1 = 0;
                    }
                }
            }
        }
        if(jf == null) {
            jf = new JFrame("Assignment 2");
            jf.setVisible(true);
            jf.setSize(max_width, max_height);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setResizable(false);
            jl = new JLabel();
            jl.setIcon(new ImageIcon(this.img));
            jf.getContentPane().add(jl,BorderLayout.CENTER);
            jf.pack();
        } else {
            jl.setIcon(new ImageIcon(img));
        }
    }

    public void outputLines() {
        System.out.println("What would you like to name your output file? (ex: file.txt)");
        String name = s.next();
        File f = new File(name);
        try{
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("File Creation Error");
            e.printStackTrace();
            return;
        }
        FileWriter pen;
        try{
            pen = new FileWriter(f);
            for(int j = 0; j < this.num; j++) {
                pen.write(this.datalines[0][j] + " " + this.datalines[1][j] + " " + this.datalines[2][j] + " " + this.datalines[3][j] + "\n");
            }
            pen.close();
        } catch (IOException e) {
            System.out.println("File Writer Error");
            e.printStackTrace();
            return;
        }
        return;
    }

    double[][] basicTranslate(double tx, double ty) { //matrix translates image to the right by tx and up by ty
        double[][] tranMat = {
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {tx, -ty, 1.0}
        };
        return tranMat;
    }

    double[][] basicScale(double sx, double sy) { //matrix scales the image by sx in the x-direction and sy in the y-direction
        double[][] tranMat = {
            {sx, 0.0, 0.0},
            {0.0, sy, 0.0},
            {0.0, 0.0, 1.0}
        };
        return tranMat;
    }

    double[][] basicRotate(double angle) { //rotates the image counterclockwise by 'angle'
        angle = Math.toRadians(angle);
        double[][] tranMat = {
            {(Math.round(Math.cos(angle) * 100.0))/100.0, -((Math.round(Math.sin(angle) * 100.0))/100.0), 0.0},
            {(Math.round(Math.sin(angle) * 100.0))/100.0, (Math.round(Math.cos(angle) * 100.0))/100.0, 0.0},
            {0.0, 0.0, 1.0}
        };
        return tranMat;
    }

    double[][] scale(double sx, double sy, double cx, double cy) { //scales the image by sx in the x-direction and sy in the y-direction
        //cx is the x-coordinate and cy is the y-coordinate. origin is in the middle of the screen and y-value increases as you go down
        double[][] t1 = {
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {-cx, -cy, 1.0}
        };
        double[][] t2 = basicScale(sx, sy);
        double[][] t3 = {
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {cx, cy, 1.0}
        };
        double[][] t4 = threeByThreeMMult(t1, t2);
        double[][] t5 = threeByThreeMMult(t4, t3);
        return t5;
    }

    double[][] rotate(double angle, double cx, double cy) { //rotates image counterclockwise by 'angle'. cx and cy give the point of rotation
        double[][] t1 = {
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {-cx, -cy, 1.0}
        };
        double[][] t2 = basicRotate(angle);
        double[][] t3 = {
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {cx, cy, 1.0}
        };
        double[][] t4 = threeByThreeMMult(t1, t2);
        double[][] t5 = threeByThreeMMult(t4, t3);
        return t5;
    }

    public static double[][] threeByThreeMMult(double[][] one, double[][] two) {
        double[][] product = new double[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    product[i][j] += one[i][k] * two[k][j];
                }
            }
        }
        return product;
    }

    public static void outputMatrix(double[][] mat) {
        for(int i = 0; i < 3; i++) {
            System.out.print("{ ");
            for(int j = 0; j < 3; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println("}");
        }
    }

    public static double[] applyMatrix(double[] point, double[][] tranMat) {
        double[] solution = new double[3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                solution[i] += point[j] * tranMat[j][i];
            }
        }
        return solution;
    }

    public BufferedImage basicLine(BufferedImage bi, int x0, int y0, int x1, int y1) {
        int delX = Math.max(x0, x1) - Math.min(x0, x1);
        int delY = Math.max(y0, y1) - Math.min(y0, y1);
        double m;
        m = ((double)(y1 - y0))/((double)(x1 - x0));
        int x;
        int y;
        if(m <= 0.5 && m > -0.5) {
            if(x0 > x1) {
                int tmp = x0;
                x0 = x1;
                x1 = tmp;
                tmp = y0;
                y0 = y1;
                y1 = tmp;
            }
            for(int i = 0; i <= delX - 1; i++) {
                x = x0 + i;
                y = (int)(m*i) + y0;
                img.setRGB(x, y, 0xFFFFFF);
            }
        } else {
            if(y0 > y1) {
                int tmp = x0;
                x0 = x1;
                x1 = tmp;
                tmp = y0;
                y0 = y1;
                y1 = tmp;
            }
            m = ((double)(x1-x0))/((double)(y1-y0));
            for(int i = 0; i <= delY - 1; i++) {
                y = y0 + i;
                x = (int)(m*i) + x0;
                img.setRGB(x, y, 0xFFFFFF);
            }
        }
        return bi;
    }
};
